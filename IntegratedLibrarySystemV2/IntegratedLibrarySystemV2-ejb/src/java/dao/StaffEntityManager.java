package dao;

import entity.StaffEntity;
import java.util.List;
import javax.ejb.Remove;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

public class StaffEntityManager {

    private final EntityManagerFactory emf = Persistence.createEntityManagerFactory("IntegratedLibrarySystemV2-ejbPU");
    private final EntityManager em = emf.createEntityManager();


    public StaffEntityManager() {
    }

    public void create(StaffEntity se) throws PersistenceException {
        try {
            if (se.getStaffID() == null) {
                em.joinTransaction();
                em.persist(se);
            }
        } catch (PersistenceException ex) {
            throw ex;
        }
    }

    public void remove(StaffEntity se) throws PersistenceException {
        try {
            em.joinTransaction();
            se = em.find(StaffEntity.class, se.getStaffID());
            em.remove(se);
        } catch (PersistenceException ex) {
            throw ex;
        }
    }

    public void update(StaffEntity se) throws PersistenceException {
        try {
            if (se.getStaffID() != null) {
                em.joinTransaction();
                em.merge(se);
            }
        } catch (PersistenceException ex) {
            throw ex;
        }
    }

    public StaffEntity retrieve(long id) throws PersistenceException {
        String jpql = "SELECT s FROM StaffEntity s WHERE s.staffID = :id";
        Query query = em.createQuery(jpql);
        query.setParameter("id", id);
        StaffEntity staffE = new StaffEntity();
        try {
            staffE = (StaffEntity) query.getSingleResult();
        } catch (PersistenceException ex) {
            throw ex;
        }
        return staffE;
    }

    public List<StaffEntity> retrieveAll() throws PersistenceException {
        String jpql = "SELECT s FROM StaffEntity s";
        Query query = em.createQuery(jpql);
        List<StaffEntity> staffs;
        try {
            staffs = query.getResultList();
        } catch (PersistenceException ex) {
            throw ex;
        }
        return staffs;
    }

    public StaffEntity login(String username, String password){

        String jpql = "SELECT s FROM StaffEntity s WHERE s.userName = :usr AND s.password = :pass";
        Query query = em.createQuery(jpql);
        query.setParameter("usr", username);
        query.setParameter("pass", password);
        StaffEntity staffE = new StaffEntity();
        System.out.println("em to.String() :     " + em.toString()); //debug
        System.out.println("Classloader of STAFFENTITY:"+ StaffEntity.class.getClassLoader()); //debug
        try {
            System.out.println(query.getSingleResult().getClass() + "   SSSSSSSSSSSSSSSSSSSSS"); //debug
            System.out.println(query.getSingleResult().toString()); //debug
            
             System.out.println("Classloader QUERY :" + query.getSingleResult().getClass().getClassLoader()); //debug
            
            staffE = (StaffEntity) query.getSingleResult();
            System.out.println("XXXXXXXXXXXXX"); //debug
        } catch (PersistenceException ex) {
            System.out.println("persistence exception     TTTTTTTTTTTTTTTTTTTTT"); //debug
            throw ex;
        }
        return staffE;
    }

    @Remove 
    public void destroy() {
        em.close();
    }

}
