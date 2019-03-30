package session.stateless;

import dao.StaffEntityManager;
import entity.StaffEntity;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.ejb.Remote;
import javax.persistence.PersistenceException;
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

    private final StaffEntityManager sem = new StaffEntityManager();

    @Override
    public Staff staffLogin(String username, String password) throws InvalidLoginCredentialException {
        Staff staff = new Staff();
        try {
            StaffEntity se = sem.login(username, password);
            staff = se.toStaff();
        } catch (PersistenceException ex) {
            throw new InvalidLoginCredentialException("Invalid credentials");
        }
        return staff;
    }

    @Override
    public boolean registerStaff(Staff staff) throws InvalidInputException{
        try {
            sem.create(new StaffEntity(staff));
            return true;
        } catch (PersistenceException ex) {
            return false;
        } catch (ConstraintViolationException cex){
            throw new InvalidInputException();
        }
    }

    @Override
    public Staff viewStaff(long id) throws StaffNotFoundException {
        Staff staff = new Staff();
        try {
            StaffEntity se = sem.retrieve(id);
            staff = se.toStaff();
        } catch (PersistenceException ex) {
            throw new StaffNotFoundException("No such staff with id: " + id);
        }
        return staff;
    }

    @Override
    public List<Staff> viewStaff() {
        List<Staff> staffs;
        try {
            staffs = sem.retrieveAll()
                    .stream()
                    .map(s -> s.toStaff())
                    .collect(Collectors.toList());
            return staffs;
        } catch (PersistenceException ex) {
            return new ArrayList();
        }
    }

    @Override
    public boolean updateStaff(Staff staff) throws InvalidInputException{
        try {
            sem.update(new StaffEntity(staff));
            return true;
        } catch (PersistenceException ex) {
            return false;
        } catch (ConstraintViolationException cex){
            throw new InvalidInputException();
        }
    }

    @Override
    public boolean deleteStaff(Staff staff) {
        try {
            sem.remove(new StaffEntity(staff));
            return true;
        } catch (PersistenceException ex) {
            return false;
        }
    }

}
