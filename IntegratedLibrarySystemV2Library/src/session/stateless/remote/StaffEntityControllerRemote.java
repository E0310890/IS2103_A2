/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session.stateless.remote;

import java.util.List;
import model.Staff;
import util.exception.InvalidInputException;
import util.exception.InvalidLoginCredentialException;
import util.exception.StaffNotFoundException;

/**
 *
 * @author lester
 */
public interface StaffEntityControllerRemote {
    public boolean registerStaff(Staff staff) throws InvalidInputException;
    public Staff viewStaff (long id) throws StaffNotFoundException;
    public List<Staff> viewStaff();
    public boolean updateStaff(Staff staff) throws InvalidInputException;
    public boolean deleteStaff(Staff staff);
    
    public Staff staffLogin(String username, String password) throws InvalidLoginCredentialException;

}
