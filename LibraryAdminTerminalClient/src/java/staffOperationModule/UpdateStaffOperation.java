package staffOperationModule;

import java.util.List;
import java.util.Scanner;
import model.Staff;
import session.stateless.remote.StaffEntityControllerRemote;
import util.exception.StaffNotFoundException;

public class UpdateStaffOperation {
    private Scanner sc = new Scanner(System.in);
    //API
    private StaffEntityControllerRemote SEC;
    //modules
    private StaffManagementModule staffManagementModule;

    //fields
    private long id;
    private Staff staff;

    public UpdateStaffOperation(StaffEntityControllerRemote SEC) {
        this.SEC = SEC;
    }

    private void displayMenu() {
        System.out.println("*** ILS :: Administration Operation :: Staff Management :: Update Staff Details***\n");
    }

    private void getInput() {
        System.out.println("Enter staff Id to Update Details> ");
        this.id = sc.nextLong();
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
        System.out.println(staff.toString());
    }

    private boolean executeOperation() {
        boolean result = false;
        try {
            this.staff = SEC.retrieve(this.id);
            updateInput();
            this.staff = SEC.updateStaff(staff);
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
        return result;
    }
    
    private void updateInput(){
        System.out.println("Select the field to Update: \n");
        System.out.println(
                "1: First Name\n"
                + "2: Last Name\n"
                + "3: Username\n"
                + "4: Password\n"
                + "5: Back\n"
        );
        System.out.println("> ");
        int fieldID = sc.nextInt();

        System.out.println("Update to: ");

         switch (fieldID) {
             case 1:
                 this.staff.setFirstName(sc.nextLine().trim()); break;
             case 2:
                 this.staff.setLastName(sc.nextLine().trim()); break;
             case 3:
                 this.staff.setUserName(sc.nextLine().trim()); break;
             case 4:
                 this.staff.setPassword(sc.nextLine().trim()); break;
             default: start(); break;
         }
    }

    private void onOperationSuccessNavigate(){
        try{
            Thread.sleep(1000);
        }catch(InterruptedException ex){
        }
        this.staffManagementModule.start();
    }

    private void onOperationFailNavigate() {
         try{
            Thread.sleep(1000);
        }catch(InterruptedException ex){
        }
        this.staffManagementModule.start();
    }

    //    Settter ..........

    public void setMemManageModIn(StaffManagementModule staffManagementModule) {
        this.staffManagementModule = staffManagementModule;
    }
}
