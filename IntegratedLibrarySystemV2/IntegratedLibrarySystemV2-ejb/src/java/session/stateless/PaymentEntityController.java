package session.stateless;

import entity.LendingEntity;
import entity.MemberEntity;
import entity.PaymentEntity;
import java.util.List;
import java.util.stream.Collectors;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.ejb.Remote;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import model.Fine;
import model.Member;
import session.stateless.local.LendEntityControllerLocal;
import session.stateless.local.MemberEntityControllerLocal;
import session.stateless.remote.PaymentEntityControllerRemote;
import util.exception.FineNotFoundException;
import util.exception.LendNotFoundException;
import util.exception.MemberNotFoundException;

@Stateless
@LocalBean
@Remote(PaymentEntityControllerRemote.class)
public class PaymentEntityController implements PaymentEntityControllerRemote {

    @EJB
    private MemberEntityControllerLocal MEC;
    @EJB
    private LendEntityControllerLocal LEC;

    // private final PaymentEntityManager pem = new PaymentEntityManager();
    
    @PersistenceContext
    private EntityManager em;


    public void update(PaymentEntity pe) throws PersistenceException {
        try {
            
            if (pe.getPaymentID() != null) {
                em.merge(pe);
            }
        } catch (PersistenceException ex) {
            throw ex;
        }
    }
    
    @Override
    public boolean payFine(Member member, Long lendId) throws MemberNotFoundException, LendNotFoundException, FineNotFoundException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean payFine(String identityNumber, Long lendId) throws MemberNotFoundException, LendNotFoundException, FineNotFoundException {
        try {
            MemberEntity memberE = MEC.viewMember(identityNumber);
            LendingEntity currentLendCtx = LEC.getMemberLendCtx(memberE, lendId);

            return true;
            
        } catch (MemberNotFoundException | LendNotFoundException ex) {
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
        List<LendingEntity> lendingList = memberE.getLending();
        return null;
                /* .filter(le -> le.getPayment().fineExist())
                .map(le -> le.getPayment())
                .collect(Collectors.toList()); */
    }

    private boolean pay(PaymentEntity payment) throws FineNotFoundException {
        try {
            /* if (payment.fineExist()) {
                payment.setPaid(true);
                update(payment); 
            } else {
                throw new FineNotFoundException("No Fine Found.");
            } */
            return true;
        } catch(PersistenceException ex){
            return false;
        }
    }
}