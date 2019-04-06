package staffOperationModule;

import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Staff;
import session.stateless.remote.BookEntityControllerRemote;
import session.stateless.remote.LendEntityControllerRemote;
import session.stateless.remote.MemberEntityControllerRemote;
import session.stateless.remote.StaffEntityControllerRemote;
import util.exception.InvalidInputException;
import util.exception.MemberNotFoundException;

public class UpdateStaffOperation {
    
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
    private Staff staff;

    public UpdateStaffOperation(StaffEntityControllerRemote SEC, MemberEntityControllerRemote MEC, BookEntityControllerRemote BEC, LendEntityControllerRemote LEC) {
        this.SEC = SEC;
        this.MEC = MEC;
        this.BEC = BEC;
        this.LEC = LEC;
    }

    private void displayMenu() {
        System.out.println("*** ILS :: Administration Operation :: Staff Management :: Update Staff Details***\n");
    }

    private boolean executeViewOperation() {
        viewAllStaffsOps = new ViewAllStaffsOperation(SEC, MEC, BEC, LEC);
        return viewAllStaffsOps.displayAllStaffs();
    }

    private void transferRequiredFields() {
        this.staffList = viewAllStaffsOps.getStaffList();
    }
    
    private void getInput() {
        System.out.println("Enter Staff ID of staff to update> ");
        Long staffID = sc.nextLong();

        // For validation, check if the ID choosen is in 'staffList';
        this.staff = staffList.stream()
                .filter(m -> m.getStaffID().equals(staffID))
                .findFirst()
                .get();

        updateInput();
    }
 
    private void updateInput() {
        System.out.println("Select the field to update: \n");
        System.out.println(
                "1: First Name\n"
                + "2: Last Name\n"
                + "3: Username\n"
                + "4: Password"
        );
        System.out.print(">");    
        int fieldSelectId = sc.nextInt();

        System.out.print("Update to: ");

        if (fieldSelectId == 1) {
            this.staff.setFirstName(sc.next());
        } else if (fieldSelectId == 2) {
            this.staff.setLastName(sc.next());
        } else if (fieldSelectId == 3) {
            this.staff.setUserName(sc.next());
        } else if (fieldSelectId == 4) {
            this.staff.setPassword(sc.next());
        }
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
        System.out.println("Staff have been successfully updated.");
    }

    private boolean executeOperation() {
        boolean result = false;
        try {
            result = SEC.updateStaff(staff);
        } catch (InvalidInputException ex) {
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

    // Setter
    public void setStaffManageModIn(StaffManagementModule staffManageModIn) {
        this.staffManageModIn = staffManageModIn;
    }    
}