package staffOperationModule;

import java.util.Scanner;
import model.Staff;
import session.stateless.remote.BookEntityControllerRemote;
import session.stateless.remote.LendEntityControllerRemote;
import session.stateless.remote.MemberEntityControllerRemote;
import session.stateless.remote.StaffEntityControllerRemote;

public class AddStaffOperation {
    
    private Scanner sc = new Scanner(System.in);
    
    // API
    private StaffEntityControllerRemote SEC;
    private MemberEntityControllerRemote MEC;
    private BookEntityControllerRemote BEC;
    private LendEntityControllerRemote LEC;
    
    // Modules
    private StaffManagementModule staffManageModIn;

    // Fields
    private Staff staff;

    public AddStaffOperation(StaffEntityControllerRemote SEC, MemberEntityControllerRemote MEC, BookEntityControllerRemote BEC, LendEntityControllerRemote LEC) {
        this.SEC = SEC;
        this.MEC = MEC;
        this.BEC = BEC;
        this.LEC = LEC;
    }

    private void displayMenu() {
        System.out.println("*** ILS :: Administration Operation :: Staff Management :: Add Staff***\n");
    }

    private void getInput() {
        System.out.print("Enter First Name> ");
        String firstName = sc.nextLine();
        
        System.out.print("Enter Last Name> ");
        String lastName = sc.nextLine();
        
        System.out.print("Enter Username> ");
        String userName = sc.nextLine();
        
        System.out.print("Enter Password> ");
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
            result = SEC.registerStaff(this.staff);
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
        this.staffManageModIn.start();
    }

    private void onOperationFailNavigate() {
         try{
            Thread.sleep(1000);
        }catch(InterruptedException ex){
        }
        this.staffManageModIn.start();
    }

    // Setter
    public void setStaffManageModIn(StaffManagementModule staffManageModIn) {
        this.staffManageModIn = staffManageModIn;
    }
}