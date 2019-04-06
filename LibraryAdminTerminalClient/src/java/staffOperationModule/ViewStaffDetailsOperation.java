package staffOperationModule;

import java.util.List;
import java.util.Scanner;
import libraryOperationModule.ViewLentBooksOperation;
import model.Staff;
import session.stateless.remote.BookEntityControllerRemote;
import session.stateless.remote.LendEntityControllerRemote;
import session.stateless.remote.MemberEntityControllerRemote;
import session.stateless.remote.StaffEntityControllerRemote;
import util.exception.MemberNotFoundException;

public class ViewStaffDetailsOperation {
    
    private Scanner sc = new Scanner(System.in);
    
    //API
    private StaffEntityControllerRemote SEC;
    private MemberEntityControllerRemote MEC;
    private BookEntityControllerRemote BEC;
    private LendEntityControllerRemote LEC;
    //modules
    private StaffManagementModule staffManageModIn;
     //Dependecies
    private ViewAllStaffsOperation viewAllStaff;

    //fields
    private List<Staff> staffList;
    private long id;
    private Staff staff;

    public ViewStaffDetailsOperation(StaffEntityControllerRemote SEC, MemberEntityControllerRemote MEC, BookEntityControllerRemote BEC, LendEntityControllerRemote LEC) {
        this.SEC = SEC;
        this.MEC = MEC;
        this.BEC = BEC;
        this.LEC = LEC;
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
}