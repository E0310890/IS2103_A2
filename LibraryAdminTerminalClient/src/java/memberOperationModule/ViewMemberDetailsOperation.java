package memberOperationModule;

import java.util.List;
import java.util.Scanner;
import model.Member;
import session.stateless.remote.BookEntityControllerRemote;
import session.stateless.remote.LendEntityControllerRemote;
import session.stateless.remote.MemberEntityControllerRemote;
import session.stateless.remote.StaffEntityControllerRemote;
import util.exception.MemberNotFoundException;

public class ViewMemberDetailsOperation {

    private Scanner sc = new Scanner(System.in);

    // API
    private StaffEntityControllerRemote SEC;
    private MemberEntityControllerRemote MEC;
    private BookEntityControllerRemote BEC;
    private LendEntityControllerRemote LEC;
    
    // Modules
    private MemberManagementModule memberManageModIn;
    
    // Dependecies
    private ViewAllMembersOperation viewAllMembersOps;

    // Fields
    private List<Member> memberList;
    private Long memberID;
    private Member member;

    public ViewMemberDetailsOperation(StaffEntityControllerRemote SEC, MemberEntityControllerRemote MEC, BookEntityControllerRemote BEC, LendEntityControllerRemote LEC) {
        this.SEC = SEC;
        this.MEC = MEC;
        this.BEC = BEC;
        this.LEC = LEC;
    }

    private void displayMenu() {
        System.out.println("*** ILS :: Administration Operation :: Member Management :: View Member Details ***\n");
    }

    private boolean executeViewOperation() {
        viewAllMembersOps = new ViewAllMembersOperation(SEC, MEC, BEC, LEC);
        return viewAllMembersOps.displayAllMembers();
    }

    private void transferRequiredFields() {
        this.memberList = viewAllMembersOps.getMemberList();
    }

    private void getInput() {
        System.out.print("Enter Member ID to View Details> ");
        this.memberID = sc.nextLong();
    }

    public void start() {
        displayMenu();
        if (!executeViewOperation()) {
            onOperationFailNavigate();
        }
        transferRequiredFields();
        
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
        System.out.println(member.toString());
    }

    private boolean executeOperation() {
        boolean result = false;
        try {
            this.member = MEC.viewMember(this.memberID);
            return true;
        } catch (MemberNotFoundException ex) {
            System.err.println(ex.getMessage());
        }
        return result;
    }

    private void onOperationSuccessNavigate() {
        this.memberManageModIn.start();
    }

    private void onOperationFailNavigate() {
        this.memberManageModIn.start();
    }
    
    // Setter
    public void setMemberManageModIn(MemberManagementModule memberManageModIn) {
        this.memberManageModIn = memberManageModIn;
    }
}