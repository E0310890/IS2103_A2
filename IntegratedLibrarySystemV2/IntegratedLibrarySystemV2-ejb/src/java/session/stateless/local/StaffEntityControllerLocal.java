package session.stateless.local;

import entity.StaffEntity;
import util.exception.StaffNotFoundException;

public interface StaffEntityControllerLocal {
    public StaffEntity viewStaff(String userName) throws StaffNotFoundException;
}
