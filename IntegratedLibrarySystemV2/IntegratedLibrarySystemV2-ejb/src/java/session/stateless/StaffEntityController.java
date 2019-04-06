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
    public Staff retrieve(long id) throws StaffNotFoundException{
        String jpql = "SELECT s FROM StaffEntity s WHERE s.staffID = :id";
        Query query = em.createQuery(jpql);
        query.setParameter("id", id);
        StaffEntity staffE = new StaffEntity();
        try {
            staffE = (StaffEntity) query.getSingleResult();
        } catch (PersistenceException ex) {
            throw new StaffNotFoundException("No such staff with id: " + id);
        }
        Staff staff = staffE.toStaff();
        return staff;
    }
    
    @Override
    public List<Staff> retrieveAll() throws PersistenceException {
        String jpql = "SELECT s FROM StaffEntity s";
        Query query = em.createQuery(jpql);
        List<StaffEntity> staffEntityList;
        try {
            staffEntityList = query.getResultList();
        } catch (PersistenceException ex) {
            throw ex;
        }
        List<Staff> staffList = staffEntityList.stream()
                                                .map(s -> s.toStaff())
                                                .collect(Collectors.toList());
        return staffList;
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
    public Staff updateStaff(Staff staff) throws InvalidInputException {
        StaffEntity staffE = new StaffEntity(staff);
        try {
            em.merge(staffE);
        }catch (PersistenceException ex) {
            throw ex;
        }catch(Exception ex){
            throw new InvalidInputException("Username used.");
        }  
        return staff;
    }

    @Override
    public Staff deleteStaff(Long id) throws StaffNotFoundException {
        Staff staff = retrieve(id);
        String jpql = "DELETE FROM StaffEntity s WHERE s.staffID =: id";
        Query query = em.createQuery(jpql);
        query.setParameter("id", id);
        query.executeUpdate();       
        return staff;
    } 
    
    public void remove(StaffEntity se) throws PersistenceException {
        try {
            se = em.find(StaffEntity.class, se.getStaffID());
            em.remove(se);
        } catch (PersistenceException ex) {
            throw ex;
        }
    }
}