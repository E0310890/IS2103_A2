package dao;

import entity.LendingEntity;
import java.sql.SQLException;
import javax.ejb.Remove;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;
import javax.validation.ConstraintViolationException;

public class LendEntityManager {

    private final EntityManagerFactory emf = Persistence.createEntityManagerFactory("IntegratedLibrarySystemV2-ejbPU");
    private final EntityManager em = emf.createEntityManager();

    public LendEntityManager() {
    }

    public void create(LendingEntity le) throws PersistenceException, ConstraintViolationException {
        try {
            if (le.getLendID() == null) {
                em.joinTransaction();
                em.persist(le);
            }
        } catch (PersistenceException | ConstraintViolationException ex) {
            throw ex;
        }
    }

    public void remove(LendingEntity le) throws PersistenceException {
        try {
            em.joinTransaction();
            le = em.find(LendingEntity.class, le.getLendID());
            em.remove(le);
        } catch (PersistenceException ex) {
            throw ex;
        }
    }

    public void update(LendingEntity le) throws PersistenceException {
        try {
            if (le.getLendID() != null) {
                em.joinTransaction();
                em.merge(le);
            }
        } catch (PersistenceException ex) {
            throw ex;
        }
    }

    @Remove
    public void destroy() {
        em.close();
    }

}
