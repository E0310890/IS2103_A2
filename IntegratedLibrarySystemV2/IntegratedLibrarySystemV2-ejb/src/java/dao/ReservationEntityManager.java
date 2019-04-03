package dao;

import entity.ReservationEntity;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;

public class ReservationEntityManager {

    private final EntityManagerFactory emf = Persistence.createEntityManagerFactory("IntegratedLibrarySystemV2-ejbPU");
    private final EntityManager em = emf.createEntityManager();

    public ReservationEntityManager() {
    }
    
    public void create(ReservationEntity re) throws PersistenceException {
        try {
            if (re.getReservationID() == null) {
                em.joinTransaction();
                em.persist(re);
            }
        } catch (PersistenceException ex) {
            throw ex;
        }
    } 

    public void update(ReservationEntity re) throws PersistenceException {
        try {
            
            if (re.getReservationID() != null) {
                em.joinTransaction();
                em.merge(re);
            }
        } catch (PersistenceException ex) {
            throw ex;
        }
    }
    
    public List<ReservationEntity> retrieveAll() throws PersistenceException {
        String jpql = "SELECT r FROM ReservationEntity r";
        TypedQuery<ReservationEntity> query = em.createQuery(jpql, ReservationEntity.class);
        List<ReservationEntity> reservationList;
        try {
            reservationList = query.getResultList();
        } catch (PersistenceException ex) {
            throw ex;
        }
        return reservationList;
    }
    
    public void delete(ReservationEntity pe) throws PersistenceException{
         try {
            em.joinTransaction();
            em.remove(pe);
        } catch (PersistenceException ex) {
            throw ex;
        }
    }
}
