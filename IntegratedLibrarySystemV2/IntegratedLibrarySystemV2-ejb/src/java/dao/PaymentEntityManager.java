package dao;

import entity.PaymentEntity;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;

public class PaymentEntityManager {

    private final EntityManagerFactory emf = Persistence.createEntityManagerFactory("IntegratedLibrarySystemV2-ejbPU");
    private final EntityManager em = emf.createEntityManager();

    public PaymentEntityManager() {
    }
    
    public void create(PaymentEntity pe) throws PersistenceException {
        try {
            if (pe.getPaymentID() == null) {
                em.joinTransaction();
                em.persist(pe);
            }
        } catch (PersistenceException ex) {
            throw ex;
        }
    } 

    public void update(PaymentEntity pe) throws PersistenceException {
        try {
            
            if (pe.getPaymentID() != null) {
                em.joinTransaction();
                em.merge(pe);
            }
        } catch (PersistenceException ex) {
            throw ex;
        }
    }
    
    public List<PaymentEntity> retrieveAll() throws PersistenceException {
        String jpql = "SELECT p FROM PaymentEntity p";
        TypedQuery<PaymentEntity> query = em.createQuery(jpql, PaymentEntity.class);
        List<PaymentEntity> paymentList;
        try {
            paymentList = query.getResultList();
        } catch (PersistenceException ex) {
            throw ex;
        }
        return paymentList;
    }
    
    public void delete(PaymentEntity pe) throws PersistenceException{
         try {
            em.joinTransaction();
            em.remove(pe);
        } catch (PersistenceException ex) {
            throw ex;
        }
    }
}
