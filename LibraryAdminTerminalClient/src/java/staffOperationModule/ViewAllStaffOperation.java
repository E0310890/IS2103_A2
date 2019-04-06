package staffOperationModule;

import java.util.List;
import java.util.Scanner;
import model.Staff;
import session.stateless.remote.StaffEntityControllerRemote;

public class ViewAllStaffOperation {
    private Scanner sc = new Scanner(System.in);
    //API
    private StaffEntityControllerRemote SEC;
    //modules
    private StaffManagementModule staffManageModIn;

    //fields
    private List<Staff> staffList;

    public ViewAllStaffOperation(StaffEntityControllerRemote SEC) {
        this.SEC = SEC;
    }

    private void displayMenu() {
        System.out.println("*** ILS :: Administration Operation :: Staff Management :: View All Staff***\n");
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
        staffList.forEach(m
                -> System.out.println("ID: " + m.getStaffID()
                        + " | Name: " + m.getFirstName() + " " + m.getLastName())
        );
    }

    private boolean executeOperation() {
        boolean result = false;
        try {
            this.staffList = SEC.retrieveAll();
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
        this.staffManageModIn.start();
    }

    private void onOperationFailNavigate() {
         try{
            Thread.sleep(1000);
        }catch(InterruptedException ex){
        }
        this.staffManageModIn.start();
    }

    //    Settter ..........

    public void setStaffManageModIn(StaffManagementModule staffManageModIn) {
        this.staffManageModIn = staffManageModIn;
    }
    
    public List<Staff> getStaffList(){
        executeOperation();
        return this.staffList;
    }
}