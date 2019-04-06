package staffOperationModule;

import adminOperationModule.AdminModule;
import java.util.Scanner;
import session.stateless.remote.BookEntityControllerRemote;
import session.stateless.remote.LendEntityControllerRemote;
import session.stateless.remote.MemberEntityControllerRemote;
import session.stateless.remote.StaffEntityControllerRemote;

public class StaffManagementModule {

    private final Scanner sc = new Scanner(System.in);

    //API
    private StaffEntityControllerRemote SEC;
    private MemberEntityControllerRemote MEC;
    private BookEntityControllerRemote BEC;
    private LendEntityControllerRemote LEC;
    //modules
    private AdminModule adminModIn;
    private AddStaffOperation addStaffOps;
    private DeleteStaffOperation deleteStaffOps;
    private UpdateStaffOperation updateStaffOps;
    private ViewStaffDetailsOperation viewStaffDetailsOps;
    private ViewAllStaffOperation viewAllStaffOps;
    //fields
    private int input;

    public StaffManagementModule(StaffEntityControllerRemote SEC, MemberEntityControllerRemote MEC, BookEntityControllerRemote BEC, LendEntityControllerRemote LEC) {
        this.SEC = SEC;
        this.MEC = MEC;
        this.BEC = BEC;
        this.LEC = LEC;
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
            case 1: addStaffOps.start(); break;
            case 2: viewStaffDetailsOps.start(); break;
            case 3: updateStaffOps.start(); break;
            case 4: deleteStaffOps.start(); break;
            case 5: viewAllStaffOps.start(); break;
            case 6: adminModIn.start();
        }
    }

    private void setBackInstance() {
        addStaffOps.setStaffManageModIn(this);
        viewStaffDetailsOps.setStaffManageModIn(this);
        updateStaffOps.setStaffManageModIn(this);
        deleteStaffOps.setStaffManageModIn(this);
        viewAllStaffOps.setStaffManageModIn(this);
    }

//    Settter ..........

    public void setAdminModIn(AdminModule adminModIn) {
        this.adminModIn = adminModIn;
    }
}
