package staffOperationModule;

import adminOperationModule.AdminModule;
import java.util.Scanner;
import session.stateless.remote.BookEntityControllerRemote;
import session.stateless.remote.LendEntityControllerRemote;
import session.stateless.remote.MemberEntityControllerRemote;
import session.stateless.remote.StaffEntityControllerRemote;

public class StaffManagementModule {

    private final Scanner sc = new Scanner(System.in);

    // API
    private StaffEntityControllerRemote SEC;
    private MemberEntityControllerRemote MEC;
    private BookEntityControllerRemote BEC;
    private LendEntityControllerRemote LEC;
    
    // Modules
    private AddStaffOperation addStaffOps;
    private ViewStaffDetailsOperation viewStaffOps;
    private UpdateStaffOperation updateStaffOps;        
    private DeleteStaffOperation deleteStaffOps;
    private ViewAllStaffsOperation viewAllStaffOps;
    private AdminModule adminModIn;  
    
    // Fields
    private int input;

    public StaffManagementModule(StaffEntityControllerRemote SEC, MemberEntityControllerRemote MEC, BookEntityControllerRemote BEC, LendEntityControllerRemote LEC) {
        this.SEC = SEC;
        this.MEC = MEC;
        this.BEC = BEC;
        this.LEC = LEC;
        addStaffOps = new AddStaffOperation(SEC, MEC, BEC, LEC);
        deleteStaffOps = new DeleteStaffOperation(SEC, MEC, BEC, LEC);
        updateStaffOps = new UpdateStaffOperation(SEC, MEC, BEC, LEC);
        viewStaffOps = new ViewStaffDetailsOperation(SEC, MEC, BEC, LEC);
        viewAllStaffOps = new ViewAllStaffsOperation(SEC, MEC, BEC, LEC);        
    }

    private void displayMenu() {
        System.out.println("*** ILS :: Administration Operation :: Staff Management ***");
        System.out.println();
        System.out.println(
                "1: Add Staff\n"
                + "2: View Staff Details\n"
                + "3: Update Staff\n"
                + "4: Delete Staff\n"
                + "5: View All Staff\n"
                + "6: Back");

        System.out.print("\n>");
    }

    private void getInput() {
        this.input = sc.nextInt();
    }

    public void start() {
        displayMenu();
        getInput();

        setBackInstance();
        navigate(this.input);
    }

    private void navigate(int input) {
        switch (input) {
            case 1: 
                addStaffOps.start(); break;
            case 2: 
                viewStaffOps.start(); break;
            case 3: 
                updateStaffOps.start(); break;
            case 4: 
                deleteStaffOps.start(); break;
            case 5: 
                viewAllStaffOps.start(); break;
            case 6: 
                adminModIn.start();
        }
    }

    private void setBackInstance() {
        addStaffOps.setStaffManageModIn(this);
        viewStaffOps.setStaffManageModIn(this);
        updateStaffOps.setStaffManageModIn(this);
        deleteStaffOps.setStaffManageModIn(this);
        viewAllStaffOps.setStaffManageModIn(this);
    }

    // Setter
    public void setAdminModIn(AdminModule adminModIn) {
        this.adminModIn = adminModIn;
    }
}