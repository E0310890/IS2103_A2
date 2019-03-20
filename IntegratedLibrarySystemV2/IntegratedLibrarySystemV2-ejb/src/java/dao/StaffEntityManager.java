package dao;

import Entity.StaffEntity;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import util.exception.InvalidLoginCredentialException;
import util.exception.StaffNotFoundException;


public class StaffEntityManager {
    
    @PersistenceContext(unitName = "IntegratedLibrarySystemV2-ejbPU")
    private EntityManager entityManager;
    
    public StaffEntityManager(){
    }
    
    public StaffEntity createNewStaff(StaffEntity newStaffEntity){
        entityManager.persist(newStaffEntity);
        entityManager.flush();
        
        return newStaffEntity;
    }
    
    
    public List<StaffEntity> retrieveAllStaffs(){
        Query query = entityManager.createQuery("SELECT s FROM staffentity s");
        
        return query.getResultList();
    }
    
    
    public StaffEntity retrieveStaffByStaffId(Long staffId) throws StaffNotFoundException{
        StaffEntity staffEntity = entityManager.find(StaffEntity.class, staffId);
        
        if(staffEntity != null){
            return staffEntity;
        }else{
            throw new StaffNotFoundException("Staff ID " + staffId + " does not exist!");
        }
    }
    
    
    public StaffEntity retrieveStaffByUsername(String username) throws StaffNotFoundException{
        Query query = entityManager.createQuery("SELECT s FROM staffentity s WHERE s.username = :inUsername");
        query.setParameter("inUsername", username);
        
        try{
            return (StaffEntity)query.getSingleResult();
        }catch(NoResultException | NonUniqueResultException ex){
            throw new StaffNotFoundException("Staff Username " + username + " does not exist!");
        }
    }
    

    public StaffEntity staffLogin(String username, String password) throws InvalidLoginCredentialException{
        try{
            StaffEntity staffEntity = retrieveStaffByUsername(username);
            
            if(staffEntity.getPassword().equals(password)){
                return staffEntity;
            }else{
                throw new InvalidLoginCredentialException("Username does not exist or invalid password!");
            }
        }catch(StaffNotFoundException ex){
            throw new InvalidLoginCredentialException("Username does not exist or invalid password!");
        }
    }
    
    
    public void updateStaff(StaffEntity staffEntity){
        entityManager.merge(staffEntity);
    }
    
    
    public void deleteStaff(Long staffId) throws StaffNotFoundException{
        StaffEntity staffEntityToRemove = retrieveStaffByStaffId(staffId);
        entityManager.remove(staffEntityToRemove);
    }
    
}
