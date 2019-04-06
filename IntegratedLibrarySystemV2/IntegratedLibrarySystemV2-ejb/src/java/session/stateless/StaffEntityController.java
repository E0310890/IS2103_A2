package session.stateless;

import entity.StaffEntity;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.ejb.Remote;
import javax.ejb.Remove;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.validation.ConstraintViolationException;
import model.Staff;
import session.stateless.local.StaffEntityControllerLocal;
import session.stateless.remote.StaffEntityControllerRemote;
import util.exception.InvalidInputException;
import util.exception.InvalidLoginCredentialException;
import util.exception.StaffNotFoundException;

@Stateless
@LocalBean
@Remote(StaffEntityControllerRemote.class)
@Local(StaffEntityControllerLocal.class)
public class StaffEntityController implements StaffEntityControllerRemote, StaffEntityControllerLocal {

    @PersistenceContext
    private EntityManager em;
    
    @Override
    public boolean registerStaff(Staff staff) throws InvalidInputException{
        StaffEntity se = new StaffEntity(staff);
        try {
            if (se.getStaffID() == null) {
                em.persist(se);
                em.flush();
            }
            return true;
        } catch (Exception ex) {
            throw new InvalidInputException("Please input correct personal details");
        }
    }
    
    @Override
    public Staff viewStaff(long staffID) throws StaffNotFoundException {
        Staff staff = new Staff();
        try {
            StaffEntity se = retrieve(staffID);
            staff = se.toStaff();
        } catch (PersistenceException ex) {
            throw new StaffNotFoundException("No such staff with ID: " + staffID);
        }
        return staff;
    }    
    
    @Override
    public List<Staff> viewStaff() {
        List<Staff> staffs;
        try {
            staffs = retrieveAll()
                    .stream()
                    .map(m -> m.toStaff())
                    .collect(Collectors.toList());
            return staffs;
        } catch (PersistenceException ex) {
            return new ArrayList();
        }
    }    
    
    @Override
    public boolean updateStaff(Staff staff) throws InvalidInputException{
        try {
            update(new StaffEntity(staff));
            return true;
        } catch (PersistenceException ex) {
            return false;
        } catch (ConstraintViolationException cex){
            throw new InvalidInputException();
        }
    }

    @Override
    public void deleteStaff(long staffID) throws StaffNotFoundException{
        try {
            StaffEntity staffE = retrieve(staffID);
            remove(staffE);
        } catch (PersistenceException ex) {
            throw new StaffNotFoundException("No such staff with ID: " + staffID);
        }
    }
   
    @Override
    public Staff staffLogin(String username, String password) throws PersistenceException, InvalidLoginCredentialException {
        Staff staff = new Staff();
        String jpql = "SELECT s FROM StaffEntity s WHERE s.userName =:username AND s.password =:password";
        Query query = em.createQuery(jpql);
        query.setParameter("username", username);
        query.setParameter("password", password);
        try {
            StaffEntity se  = (StaffEntity)query.getSingleResult();   
            staff = se.toStaff();   
        return staff;
        } catch (PersistenceException ex) {
            throw new InvalidLoginCredentialException("Invalid credentials");
        }
    }    
    
   @Override
   public StaffEntity viewStaff(String userName) throws StaffNotFoundException{
        try {
            return retrieve(userName);
        } catch (PersistenceException ex) {
            throw new StaffNotFoundException("No such staff with username: " + userName);
        }
   }
   
    public void remove(StaffEntity se) throws PersistenceException {
        try {
            se = em.find(StaffEntity.class, se.getStaffID());
            em.remove(se);
        } catch (PersistenceException ex) {
            throw ex;
        }
    }
    
    public void update(StaffEntity se) throws PersistenceException {
        try {
            if (se.getStaffID() != null) {
                em.merge(se);
            }
        } catch (PersistenceException ex) {
            throw ex;
        }
    }    
    
    public StaffEntity retrieve(long staffID) throws PersistenceException {
        String jpql = "SELECT m FROM StaffEntity m WHERE m.staffID = :staffID";
        Query query = em.createQuery(jpql);
        query.setParameter("staffID", staffID);
        StaffEntity staffE = new StaffEntity();
        try {
            staffE = (StaffEntity) query.getSingleResult();
        } catch (PersistenceException ex) {
            throw ex;
        }
        return staffE;
    }
    
     public StaffEntity retrieve(String userName) throws PersistenceException {
        String jpql = "SELECT m FROM StaffEntity m WHERE m.userName = :userName";
        TypedQuery query = em.createQuery(jpql, StaffEntity.class);
        query.setParameter("userName", userName);
        StaffEntity staffE = new StaffEntity();
        try {
            staffE = (StaffEntity) query.getSingleResult();
            em.refresh(staffE);
        } catch (PersistenceException ex) {
            throw ex;
        }
        return staffE;
    }
     
    public List<StaffEntity> retrieveAll() throws PersistenceException {
        String jpql = "SELECT m FROM StaffEntity m";
        Query query = em.createQuery(jpql);
        List<StaffEntity> staffs;
        try {
            staffs = query.getResultList();
        } catch (PersistenceException ex) {
            throw ex;
        }
        return staffs;
    }
    
    @Remove
    public void destroy() {
        em.close();
    }    
}