package staffOperationModule;

import java.util.List;
import java.util.Scanner;
import model.Staff;
import session.stateless.remote.BookEntityControllerRemote;
import session.stateless.remote.LendEntityControllerRemote;
import session.stateless.remote.MemberEntityControllerRemote;
import session.stateless.remote.StaffEntityControllerRemote;
import util.exception.StaffNotFoundException;

public class DeleteStaffOperation {
    
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
    private Long staffID;

    public DeleteStaffOperation(StaffEntityControllerRemote SEC, MemberEntityControllerRemote MEC, BookEntityControllerRemote BEC, LendEntityControllerRemote LEC) {
        this.SEC = SEC;
        this.MEC = MEC;
        this.BEC = BEC;
        this.LEC = LEC;
    }
    
    private void displayMenu() {
        System.out.println("*** ILS :: Administration Operation :: Staff Management :: Delete Staff***\n");
    }

    private boolean executeViewOperation() {
        viewAllStaffsOps = new ViewAllStaffsOperation(SEC, MEC, BEC, LEC);
        return viewAllStaffsOps.displayAllStaffs();
    }
    
    private void transferRequiredFields() {
        this.staffList = viewAllStaffsOps.getStaffList();
    }
    
    private void getInput() {
        System.out.println("Enter Staff ID to delete> ");
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
        System.out.println("Staff has been deleted successfully!");
    }

    private boolean executeOperation() {
        boolean result = false;
        try {
            SEC.deleteStaff(this.staffID);
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
        start();
    }

    //    Settter ..........
    public void setMemberManageModIn(StaffManagementModule staffManageModIn) {
        this.staffManageModIn = staffManageModIn;
    }
}