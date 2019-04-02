package dao;

import entity.LendingEntity;
import java.sql.SQLException;
import java.util.List;
import javax.ejb.Remove;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;
import javax.validation.ConstraintViolationException;

public class LendEntityManager {

    private final EntityManagerFactory emf = Persistence.createEntityManagerFactory("IntegratedLibrarySystemV2-ejbPU");
    private final EntityManager em = emf.createEntityManager();

    public LendEntityManager() {
    }

    public void create(LendingEntity le) {
        try {
            if (le.getLendID() == null) {
                em.joinTransaction();
                em.persist(le);
            }
        } catch (PersistenceException | ConstraintViolationException ex) {
            throw ex;
        }
    }

    public void remove(LendingEntity le){
        try {
            em.joinTransaction();
            le = em.find(LendingEntity.class, le.getLendID());
            em.remove(le);
        } catch (PersistenceException ex) {
            throw ex;
        }
    }

    public void update(LendingEntity le){
        try {
            if (le.getLendID() != null) {
                em.joinTransaction();
                em.merge(le);
            }
        } catch (PersistenceException ex) {
            throw ex;
        }
    }
    
    public List<LendingEntity> retrieveAll() {
        String jpql = "SELECT l FROM LendEntity l";
        TypedQuery<LendingEntity> query = em.createQuery(jpql, LendingEntity.class);
        List<LendingEntity> lendList;
        try {
            lendList = query.getResultList();
        } catch (PersistenceException ex) {
            throw ex;
        }
        return lendList;
    }

    @Remove
    public void destroy() {
        em.close();
    }

}
