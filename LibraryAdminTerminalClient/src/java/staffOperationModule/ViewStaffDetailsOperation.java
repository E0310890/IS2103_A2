package staffOperationModule;

import java.util.List;
import java.util.Scanner;
import model.Staff;
import session.stateless.remote.BookEntityControllerRemote;
import session.stateless.remote.LendEntityControllerRemote;
import session.stateless.remote.MemberEntityControllerRemote;
import session.stateless.remote.StaffEntityControllerRemote;
import util.exception.StaffNotFoundException;

public class ViewStaffDetailsOperation {
    
    private Scanner sc = new Scanner(System.in);
    
    // API
    private StaffEntityControllerRemote SEC;
    private MemberEntityControllerRemote MEC;
    private BookEntityControllerRemote BEC;
    private LendEntityControllerRemote LEC;
    
    // Modules
    private StaffManagementModule staffManageModIn;
    
    // Dependecies
    private ViewAllStaffsOperation viewAllStaffsOps;

    // Fields
    private List<Staff> staffList;
    private long staffID;
    private Staff staff;

    public ViewStaffDetailsOperation(StaffEntityControllerRemote SEC, MemberEntityControllerRemote MEC, BookEntityControllerRemote BEC, LendEntityControllerRemote LEC) {
        this.SEC = SEC;
        this.MEC = MEC;
        this.BEC = BEC;
        this.LEC = LEC;
    }

    private void displayMenu() {
        System.out.println("*** ILS :: Administration Operation :: Staff Management :: View Staff Details ***\n");
    }

    private boolean executeViewOperation() {
        viewAllStaffsOps = new ViewAllStaffsOperation(SEC, MEC, BEC, LEC);
        return viewAllStaffsOps.displayAllStaffs();
    }

    private void transferRequiredFields() {
        this.staffList = viewAllStaffsOps.getStaffList();
    }

    private void getInput() {
        System.out.print("Enter Staff ID to View Details> ");
        this.staffID = sc.nextLong();
    }

    public void start() {
        displayMenu();
        if (!executeViewOperation()) {
            onOperationFailNavigate();
        }
        transferRequiredFields();
        
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
        System.out.println(staff.toString());
    }

    private boolean executeOperation() {
        boolean result = false;
        try {
            this.staff = SEC.viewStaff(this.staffID);
            return true;
        } catch (StaffNotFoundException ex) {
            System.err.println(ex.getMessage());
        }
        return result;
    }

    private void onOperationSuccessNavigate() {
        this.staffManageModIn.start();
    }

    private void onOperationFailNavigate() {
        this.staffManageModIn.start();
    }

    // Setter
    public void setStaffManageModIn(StaffManagementModule staffManageModIn) {
        this.staffManageModIn = staffManageModIn;
    }
}