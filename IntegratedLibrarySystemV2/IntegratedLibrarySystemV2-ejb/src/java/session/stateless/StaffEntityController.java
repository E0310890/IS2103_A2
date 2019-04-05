package session.stateless;

import entity.StaffEntity;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.ejb.Remote;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.validation.ConstraintViolationException;
import model.Staff;
import session.stateless.remote.StaffEntityControllerRemote;
import util.exception.InvalidInputException;
import util.exception.InvalidLoginCredentialException;
import util.exception.StaffNotFoundException;

@Stateless
@LocalBean
@Remote(StaffEntityControllerRemote.class)
public class StaffEntityController implements StaffEntityControllerRemote {

    @PersistenceContext
    private EntityManager em;
    
    public void create(StaffEntity se) throws PersistenceException {
        try {
            if (se.getStaffID() == null) {
                em.persist(se);
            }
        } catch (PersistenceException ex) {
            throw ex;
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
    

    public boolean registerStaff(Staff staff) throws InvalidInputException {
        try {
            create(new StaffEntity(staff));
            return true;
        } catch (PersistenceException ex) {
            return false;
        } catch (ConstraintViolationException cex) {
            throw new InvalidInputException();
        }
    }

    public Staff viewStaff(long id) throws StaffNotFoundException {
        Staff staff = new Staff();
        try {
            StaffEntity se = retrieve(id);
            staff = se.toStaff();
        } catch (PersistenceException ex) {
            throw new StaffNotFoundException("No such staff with id: " + id);
        }
        return staff;
    }

    public List<Staff> viewStaff() {
        List<Staff> staffs;
        try {
            staffs = retrieveAll()
                    .stream()
                    .map(s -> s.toStaff())
                    .collect(Collectors.toList());
            return staffs;
        } catch (PersistenceException ex) {
            return new ArrayList();
        }
    }

    public boolean updateStaff(Staff staff) throws InvalidInputException {
        try {
            update(new StaffEntity(staff));
            return true;
        } catch (PersistenceException ex) {
            return false;
        } catch (ConstraintViolationException cex) {
            throw new InvalidInputException();
        }
    }

    public boolean deleteStaff(Staff staff) {
        try {
            em.remove(new StaffEntity(staff));
            return true;
        } catch (PersistenceException ex) {
            return false;
        }
    } 
}