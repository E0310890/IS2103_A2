package session.stateless;

import entity.LendingEntity;
import entity.MemberEntity;
import entity.PaymentEntity;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.ejb.Remote;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import model.Fine;
import model.Member;
import session.stateless.local.BookEntityControllerLocal;
import session.stateless.local.LendEntityControllerLocal;
import session.stateless.local.MemberEntityControllerLocal;
import session.stateless.local.PaymentEntityControllerLocal;
// import session.stateless.local.ReservationEntityControllerLocal;
import session.stateless.remote.LendEntityControllerRemote;
import session.stateless.remote.PaymentEntityControllerRemote;
import util.exception.FineNotFoundException;
import util.exception.LendNotFoundException;
import util.exception.MemberNotFoundException;

@Stateless
@LocalBean
@Remote(PaymentEntityControllerRemote.class)
@Local(PaymentEntityControllerLocal.class)
public class PaymentEntityController implements PaymentEntityControllerRemote, PaymentEntityControllerLocal {

    @EJB
    private MemberEntityControllerLocal MEC;
    @EJB
    private LendEntityControllerLocal LEC;
    
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
    public PaymentEntity createFine(LendingEntity lendingE){
        MemberEntity memberE = lendingE.getMember();
        PaymentEntity paymentE = new PaymentEntity(lendingE.getMember(), lendingE.getLendID(), lendingE.getDueDate());
        try{
            em.persist(paymentE);
            em.flush();
        }catch(Exception ex){
            System.out.println("ADMIN MESSAGE: THIS FINE HAS BEEN CREATED DURING SET UP --> " + lendingE.getLendID());
        }
        return paymentE;
    }
    
    @Override
    public boolean payFine(Member member, Long lendId) throws MemberNotFoundException, LendNotFoundException, FineNotFoundException {
        return payFine(member.getIdentityNumber(), lendId);
    }

    @Override
    public boolean payFine(String identityNumber, Long lendId) throws MemberNotFoundException, FineNotFoundException {
        try {
            MemberEntity memberE = MEC.viewMember(identityNumber);
            PaymentEntity paymentE = memberE.getPayment()
                                    .stream()
                                    .filter(p -> p.getLendID().equals(lendId))
                                    .findFirst()
                                    .get();
            em.remove(paymentE);
            return true;
        } catch (MemberNotFoundException ex) {
            throw ex;
        }catch ( NoSuchElementException ex){
            throw new FineNotFoundException("You entered an invalid fine ID"); 
        }
    }

    @Override
    public List<Fine> viewFine(Member member) throws MemberNotFoundException {
        return viewFine(member.getIdentityNumber());
    }

    @Override
    public List<Fine> viewFine(String identityNumber) throws MemberNotFoundException {
        try {
            MemberEntity memberE = MEC.viewMember(identityNumber);
            List<PaymentEntity> paymentList = memberE.getPayment();

            return paymentList.stream()
                    .map(p -> p.toFine())
                    .collect(Collectors.toList());

        } catch (MemberNotFoundException ex) {
            throw ex;
        }
    }

}