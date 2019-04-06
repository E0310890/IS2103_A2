package memberOperationModule;

import adminOperationModule.AdminModule;
import java.util.Scanner;
import session.stateless.remote.BookEntityControllerRemote;
import session.stateless.remote.LendEntityControllerRemote;
import session.stateless.remote.MemberEntityControllerRemote;
import session.stateless.remote.StaffEntityControllerRemote;

public class MemberManagementModule {

    private final Scanner sc = new Scanner(System.in);

    // API
    private StaffEntityControllerRemote SEC;
    private MemberEntityControllerRemote MEC;
    private BookEntityControllerRemote BEC;
    private LendEntityControllerRemote LEC;
    
    // Modules
    private AddMemberOperation addMemberOps;
    private ViewMemberDetailsOperation viewMemberOps;
    private UpdateMemberOperation updateMemberOps;
    private DeleteMemberOperation deleteMemberOps;
    private ViewAllMembersOperation viewAllMemberOps;
    private AdminModule adminModIn;    

    // Fields
    private int input;

    public MemberManagementModule(StaffEntityControllerRemote SEC, MemberEntityControllerRemote MEC, BookEntityControllerRemote BEC, LendEntityControllerRemote LEC) {
        this.SEC = SEC;
        this.MEC = MEC;
        this.BEC = BEC;
        this.LEC = LEC;
        addMemberOps = new AddMemberOperation(SEC, MEC, BEC, LEC);
        viewMemberOps = new ViewMemberDetailsOperation(SEC, MEC, BEC, LEC);
        updateMemberOps = new UpdateMemberOperation(SEC, MEC, BEC, LEC);
        deleteMemberOps = new DeleteMemberOperation(SEC, MEC, BEC, LEC);
        viewAllMemberOps = new ViewAllMembersOperation(SEC, MEC, BEC, LEC);
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
                addMemberOps.start();
            case 2:
                viewMemberOps.start();
            case 3:
                updateMemberOps.start();
            case 4:
                deleteMemberOps.start();
            case 5:
                viewAllMemberOps.start();
            case 6:
                adminModIn.start();
        }
    }

    private void setBackInstance() {
        addMemberOps.setMemberManageModIn(this);
        viewMemberOps.setMemberManageModIn(this);
        updateMemberOps.setMemberManageModIn(this);
        deleteMemberOps.setMemberManageModIn(this);
        viewAllMemberOps.setMemberManageModIn(this);
    }

//    Settter ..........
    public void setAdminModIn(AdminModule adminModIn) {
        this.adminModIn = adminModIn;
    }
}