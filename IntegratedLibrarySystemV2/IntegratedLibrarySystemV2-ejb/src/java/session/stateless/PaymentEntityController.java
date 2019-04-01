package session.stateless;

import dao.PaymentEntityManager;
import entity.LendingEntity;
import entity.MemberEntity;
import entity.PaymentEntity;
import java.util.List;
import java.util.stream.Collectors;
import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.ejb.Remote;
import javax.persistence.PersistenceException;
import model.Fine;
import model.Member;
import session.stateless.local.LendEntityControllerLocal;
import session.stateless.local.MemberEntityControllerLocal;
import session.stateless.local.PaymentEntityControllerLocal;
import session.stateless.remote.PaymentEntityControllerRemote;
import util.exception.FineNotFoundException;
import util.exception.LendNotFoundException;
import util.exception.MemberNotFoundException;

@Stateless
@LocalBean
@Remote(PaymentEntityControllerRemote.class)
@Local(PaymentEntityControllerLocal.class)
public class PaymentEntityController implements PaymentEntityControllerRemote, PaymentEntityControllerLocal{

    @EJB
    private MemberEntityControllerLocal MEC;
    @EJB
    private LendEntityControllerLocal LEC;

    private final PaymentEntityManager pem = new PaymentEntityManager();

    @Override
    public PaymentEntity createFine(LendingEntity lending){
        MemberEntity memberE = lending.getMember();
        PaymentEntity pe = new PaymentEntity(lending.getLendID(), lending.getDueDate());
        memberE.addPayment(pe);
        return pe;
    }
    
    @Override
    public boolean payFine(Member member, Long lendId) throws MemberNotFoundException, FineNotFoundException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean payFine(String identityNumber, Long lendId) throws MemberNotFoundException, FineNotFoundException {
        try {
            MemberEntity memberE = MEC.viewMember(identityNumber);
            List<PaymentEntity> paymentList = memberE.getPayment(); 
            
            return pay((PaymentEntity) paymentList.stream()
                        .filter(pe -> pe.getLendID().equals(lendId))
                        .findFirst()
                        .get());
            
        } catch (MemberNotFoundException ex) {
            throw ex;
        }
    }

    @Override
    public List<Fine> ViewFine(Member member) throws MemberNotFoundException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Fine> ViewFine(String identityNumber) throws MemberNotFoundException {
        try {
            MemberEntity memberE = MEC.viewMember(identityNumber);
            List<PaymentEntity> paymentList = getAllFines(memberE);

            return paymentList.stream()
                    .map(p -> p.toFine())
                    .collect(Collectors.toList());

        } catch (MemberNotFoundException ex) {
            throw ex;
        }
    }

    private List<PaymentEntity> getAllFines(MemberEntity memberE) {
        List<PaymentEntity> paymentList = memberE.getPayment();
        return paymentList;
    }

    private boolean pay(PaymentEntity payment) throws FineNotFoundException {
        try {
            pem.delete(payment);
            return true;
        } catch(PersistenceException ex){
            return false;
        }
    }

}
