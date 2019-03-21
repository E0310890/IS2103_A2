package ejb.session.stateless;

import Entity.StaffEntity;
import javax.ejb.Local;
import util.exception.StaffNotFoundException;


@Local
public interface StaffEntityControllerLocal {
    
    StaffEntity retrieveStaffByUsername(String username) throws StaffNotFoundException;
    
    StaffEntity createNewStaff(StaffEntity staffEntity);
}
