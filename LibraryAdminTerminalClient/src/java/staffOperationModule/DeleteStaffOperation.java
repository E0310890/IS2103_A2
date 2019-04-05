package staffOperationModule;

import java.util.Scanner;
import model.Staff;
import session.stateless.remote.StaffEntityControllerRemote;

public class DeleteStaffOperation {
    private Scanner sc = new Scanner(System.in);
    //API
    private StaffEntityControllerRemote SEC;
    //modules
    private StaffManagementModule staffManagementModule;

    //fields
    private long id;
    private Staff staff;

    public DeleteStaffOperation(StaffEntityControllerRemote SEC) {
        this.SEC = SEC;
    }

    private void displayMenu() {
        System.out.println("*** ILS :: Administration Operation :: Staff Management :: Delete Staff***\n");
    }

    private void getInput() {
        System.out.println("Enter staff Id to Delete> ");
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
        System.out.println("Staff " + staff.toString() + " has been deleted.");
    }

    private boolean executeOperation() {
        boolean result = false;
        try {
            this.staff = SEC.deleteStaff(this.id);
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
