package staffOperationModule;

import java.util.List;
import java.util.Scanner;
import model.Staff;
import session.stateless.remote.StaffEntityControllerRemote;

public class ViewStaffDetailsOperation {
    private Scanner sc = new Scanner(System.in);
    //API
    private StaffEntityControllerRemote SEC;
    //modules
    private StaffManagementModule staffManagementModule;
     //Dependecies
    private ViewAllStaffOperation viewAllStaff;

    //fields
    private List<Staff> staffList;
    private long id;
    private Staff staff;

    public ViewStaffDetailsOperation(StaffEntityControllerRemote SEC) {
        this.SEC = SEC;
        viewAllStaff = new ViewAllStaffOperation(SEC);
    }

    private void displayMenu() {
        System.out.println("*** ILS :: Administration Operation :: Staff Management :: View Staff Details***\n");
    }

    private void getInput() {
        System.out.println("Enter staff Id to View Details> ");
        this.id = sc.nextLong();
    }

    public void start() {
        displayMenu();
        this.staffList = viewAllStaff.getStaffList();
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
            SEC.retrieve(this.id);
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