package staffOperationModule;

import java.util.List;
import java.util.Scanner;
import model.Staff;
import session.stateless.remote.BookEntityControllerRemote;
import session.stateless.remote.LendEntityControllerRemote;
import session.stateless.remote.MemberEntityControllerRemote;
import session.stateless.remote.StaffEntityControllerRemote;
import util.exception.InvalidInputException;

public class ViewAllStaffsOperation {
    
    private Scanner sc = new Scanner(System.in);
    
    // API
    private StaffEntityControllerRemote SEC;
    private MemberEntityControllerRemote MEC;
    private BookEntityControllerRemote BEC;
    private LendEntityControllerRemote LEC;
    
    // Modules
    private StaffManagementModule staffManageModIn;

    // Fields
    private List<Staff> staffList;

    public ViewAllStaffsOperation(StaffEntityControllerRemote SEC, MemberEntityControllerRemote MEC, BookEntityControllerRemote BEC, LendEntityControllerRemote LEC) {
        this.SEC = SEC;
        this.MEC = MEC;
        this.BEC = BEC;
        this.LEC = LEC;
    }

    private void displayMenu() {
        System.out.println("*** ILS :: Administration Operation :: Staff Management :: View All Staffs ***\n");
    }

    private void getInput() {
    }

    public void start() {
        displayMenu();
        getInput();

        boolean executeSuccess = executeOperation();
        if (executeSuccess) {
            successDisplay();
            onOperationSuccessNavigate();
        } else {
            onOperationFailNavigate();
        }
    }

    private void successDisplay() {
        staffList.forEach(staff
                -> System.out.println("Staff ID: " + staff.getStaffID()
                        + " | Full Name: " + staff.getFirstName() + " " + staff.getLastName())
        );
    }

    private boolean executeOperation() {
        boolean result = false;
        try {
            this.staffList = SEC.viewStaff();
            result = true;
        } catch (Exception ex) {
            result = false;
        }
        return result;
    }

    private void onOperationSuccessNavigate() {
        this.staffManageModIn.start();
    }

    private void onOperationFailNavigate() {
        start();
    }
    
    public boolean displayAllStaffs() {
        boolean executeSuccess = executeOperation();
        if (executeSuccess) {
            successDisplay();
            return true;
        } else {
            return false;
        }
    }    

    // Setter
    public void setStaffManageModIn(StaffManagementModule staffManageModIn) {
        this.staffManageModIn = staffManageModIn;
    }

    public List<Staff> getStaffList() {
        return staffList;
    }
}