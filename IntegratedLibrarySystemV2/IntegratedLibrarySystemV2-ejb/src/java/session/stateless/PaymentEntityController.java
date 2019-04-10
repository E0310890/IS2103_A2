package session.stateless;

import entity.LendingEntity;
import entity.MemberEntity;
import entity.PaymentEntity;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.ejb.Remote;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
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
@WebService(serviceName = "PaymentService")
public class PaymentEntityController implements PaymentEntityControllerRemote, PaymentEntityControllerLocal {

    @EJB
    private MemberEntityControllerLocal MEC;
    @EJB
    private LendEntityControllerLocal LEC;

    @PersistenceContext
    private EntityManager em;

    private void update(PaymentEntity pe) throws PersistenceException {
        try {
            if (pe.getPaymentID() != null) {
                em.merge(pe);
            }
        } catch (PersistenceException ex) {
            throw ex;
        }
    }

    @WebMethod(exclude = true)
    @Override
    public PaymentEntity createFine(LendingEntity lendingE) {
        MemberEntity memberE = lendingE.getMember();
        PaymentEntity paymentE = new PaymentEntity(lendingE.getMember(), lendingE.getLendID(), lendingE.getDueDate());
        try {
            em.persist(paymentE);
            em.flush();
        } catch (Exception ex) {
            System.out.println("ADMIN MESSAGE: THIS FINE HAS BEEN CREATED DURING SET UP --> " + lendingE.getLendID());
        }
        return paymentE;
    }
    
    @Override
    public PaymentEntity updateFine(LendingEntity lendingE) {
        MemberEntity memberE = lendingE.getMember();
        PaymentEntity paymentE = new PaymentEntity();
        
        String jpql = "SELECT p FROM PaymentEntity p WHERE p.lendID =:lendID";
        Query query = em.createQuery(jpql);
        query.setParameter("lendID", lendingE.getLendID());  
        
        paymentE = (PaymentEntity) query.getSingleResult();
                                
        Date dueDate = lendingE.getDueDate();
        
        long numberer = 1559318400000L;
        
        // Date currentDate = new Date(numberer);

        Date currentDate = new Date();
        
        long difference =  (currentDate.getTime()-dueDate.getTime())/86400000;
        int differenceInt = (int) difference;
        
        if (differenceInt != paymentE.getAmount()) {
            paymentE.setAmount(differenceInt);
            em.merge(paymentE);
            em.flush();
        }
        return paymentE;
    }    

    @WebMethod(exclude = true)
    @Override
    public boolean fineExist(LendingEntity lendingE) {
        MemberEntity memberE = lendingE.getMember();
        if (memberE.getPayment().isEmpty()) {
            return false;
        }
        return true;
    }

    @Override
    public boolean initFine(LendingEntity lendingE) {
        MemberEntity memberE = lendingE.getMember();
        if (memberE.getPayment().stream().anyMatch(p -> p.getLendID().equals(lendingE.getLendID()))) {
            return true;
        }
        return false;
    }
    
    @WebMethod(exclude = true)
    @Override
    public boolean payFine(Member member, Long lendId) throws MemberNotFoundException, LendNotFoundException, FineNotFoundException {
        return payFine(member.getIdentityNumber(), lendId);
    }

    @WebMethod(operationName = "payFine")
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
        } catch (NoSuchElementException ex) {
            throw new FineNotFoundException("You entered an invalid fine ID");
        }
    }

    @WebMethod(exclude = true)
    @Override
    public List<Fine> viewFine(Member member) throws MemberNotFoundException {
        return viewFine(member.getIdentityNumber());
    }

    @WebMethod(operationName = "viewFine")
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
