package memberOperationModule;

import adminOperationModule.AdminModule;
import java.util.Scanner;
import session.stateless.remote.BookEntityControllerRemote;
import session.stateless.remote.LendEntityControllerRemote;
import session.stateless.remote.MemberEntityControllerRemote;
import session.stateless.remote.StaffEntityControllerRemote;

public class MemberManagementModule {

    private final Scanner sc = new Scanner(System.in);

    //API
    private StaffEntityControllerRemote SEC;
    private MemberEntityControllerRemote MEC;
    private BookEntityControllerRemote BEC;
    private LendEntityControllerRemote LEC;
    //modules
    private AdminModule adminModIn;
    private AddMemberOperation addMemOps;
    private ViewMemberDetailsOperation viewMemOps;
    private UpdateMemberOperation updateMemOps;
    private DeleteMemberOperation delMemOps;
    private ViewAllMembersOperation viewAllMemOps;

    //fields
    private int input;

    public MemberManagementModule(StaffEntityControllerRemote SEC, MemberEntityControllerRemote MEC, BookEntityControllerRemote BEC, LendEntityControllerRemote LEC) {
        this.SEC = SEC;
        this.MEC = MEC;
        this.BEC = BEC;
        this.LEC = LEC;
        addMemOps = new AddMemberOperation(SEC, MEC, BEC, LEC);
        viewMemOps = new ViewMemberDetailsOperation(SEC, MEC, BEC, LEC);
        updateMemOps = new UpdateMemberOperation(SEC, MEC, BEC, LEC);
        delMemOps = new DeleteMemberOperation(SEC, MEC, BEC, LEC);
        viewAllMemOps = new ViewAllMembersOperation(SEC, MEC, BEC, LEC);
    }

    private void displayMenu() {
        System.out.println("*** ILS :: Library Operation ***");
        System.out.println();
        System.out.println(
                "1: Add Member\n"
                + "2: View Member Details\n"
                + "3: Update Member\n"
                + "4: Delete Member\n"
                + "5: View All Members\n"
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
                addMemOps.start();
            case 2:
                viewMemOps.start();
            case 3:
                updateMemOps.start();
            case 4:
                delMemOps.start();
            case 5:
                viewAllMemOps.start();
            case 6:
                adminModIn.start();
        }

    }

    private void setBackInstance() {
        addMemOps.setMemManageModIn(this);
        viewMemOps.setMemManageModIn(this);
        updateMemOps.setMemManageModIn(this);
        delMemOps.setMemManageModIn(this);
        viewAllMemOps.setMemManageModIn(this);
    }

//    Settter ..........
    public void setAdminModIn(AdminModule adminModIn) {
        this.adminModIn = adminModIn;
    }

}
