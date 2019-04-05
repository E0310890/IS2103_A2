package staffOperationModule;

import java.util.Scanner;
import model.Staff;
import session.stateless.remote.StaffEntityControllerRemote;

public class AddStaffOperation {
    
    private Scanner sc = new Scanner(System.in);
    //API
    private StaffEntityControllerRemote SEC;
    //modules
    private StaffManagementModule staffManagementModule;

    //fields
    private Staff staff;

    public AddStaffOperation(StaffEntityControllerRemote SEC) {
        this.SEC = SEC;
    }

    private void displayMenu() {
        System.out.println("*** ILS :: Administration Operation :: Staff Management :: Add Staff***\n");
    }

    private void getInput() {
        System.out.println("Enter First Name> ");
        String firstName = sc.nextLine();
        
        System.out.println("Enter Last Name> ");
        String lastName = sc.nextLine();
        
        System.out.println("Enter Username> ");
        String userName = sc.nextLine();
        
        System.out.println("Enter Password> ");
        String password = sc.nextLine();
        
        this.staff = new Staff(firstName, lastName, userName, password);
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
        System.out.println("Staff has been registered successfully!");
    }

    private boolean executeOperation() {
        boolean result = false;
        try {
            this.staff = SEC.createStaff(this.staff);
            result = true;
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
        return result;
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
