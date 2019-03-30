package session.stateless.remote;

import java.util.List;
import model.Staff;
import util.exception.InvalidInputException;
import util.exception.InvalidLoginCredentialException;
import util.exception.StaffNotFoundException;

public interface StaffEntityControllerRemote {
    public boolean registerStaff(Staff staff) throws InvalidInputException;
    public Staff viewStaff (long id) throws StaffNotFoundException;
    public List<Staff> viewStaff();
    public boolean updateStaff(Staff staff) throws InvalidInputException;
    public boolean deleteStaff(Staff staff);
    
    public Staff staffLogin(String username, String password) throws InvalidLoginCredentialException;

}
