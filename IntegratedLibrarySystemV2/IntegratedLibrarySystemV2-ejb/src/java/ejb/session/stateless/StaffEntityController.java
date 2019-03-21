package ejb.session.stateless;

import Entity.StaffEntity;
import dao.StaffEntityManager;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import util.exception.StaffNotFoundException;


@Stateless
@Remote(StaffEntityControllerRemote.class)
@Local(StaffEntityControllerLocal.class)

public class StaffEntityController implements StaffEntityControllerRemote, StaffEntityControllerLocal {
    
    private final StaffEntityManager staffEntityManager;
    
    public StaffEntityController(){
        staffEntityManager = new StaffEntityManager();
    }
    
    @Override
    public StaffEntity retrieveStaffByUsername(String username) throws StaffNotFoundException{
        return staffEntityManager.retrieveStaffByUsername(username);
    }
    
    @Override 
    public StaffEntity createNewStaff(StaffEntity staffEntity){
        return staffEntityManager.createNewStaff(staffEntity);
    }
}
