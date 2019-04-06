package memberOperationModule;

import java.util.List;
import java.util.Scanner;
import model.Member;
import session.stateless.remote.BookEntityControllerRemote;
import session.stateless.remote.LendEntityControllerRemote;
import session.stateless.remote.MemberEntityControllerRemote;
import session.stateless.remote.StaffEntityControllerRemote;
import util.exception.InvalidInputException;

public class ViewAllMembersOperation {

    private Scanner sc = new Scanner(System.in);

    //API
    private StaffEntityControllerRemote SEC;
    private MemberEntityControllerRemote MEC;
    private BookEntityControllerRemote BEC;
    private LendEntityControllerRemote LEC;
    //modules
    private MemberManagementModule memberManageModIn;

    //fields
    private List<Member> memberList;

    public ViewAllMembersOperation(StaffEntityControllerRemote SEC, MemberEntityControllerRemote MEC, BookEntityControllerRemote BEC, LendEntityControllerRemote LEC) {
        this.SEC = SEC;
        this.MEC = MEC;
        this.BEC = BEC;
        this.LEC = LEC;
    }

    private void displayMenu() {
        System.out.println("*** ILS :: Administration Operation :: Member Management :: View All Members ***\n");
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
        memberList.forEach(m
                -> System.out.println("ID: " + m.getMemberID()
                        + " | Name: " + m.getFirstName() + " " + m.getLastName())
        );
    }

    private boolean executeOperation() {
        boolean result = false;
        try {
            this.memberList = MEC.viewMember();
            result = true;
        } catch (Exception ex) {
            result = false;
        }
        return result;
    }

    private void onOperationSuccessNavigate() {
        this.memberManageModIn.start();
    }

    private void onOperationFailNavigate() {
        start();
    }

    public boolean displayAllMembers() {
        boolean executeSuccess = executeOperation();
        if (executeSuccess) {
            successDisplay();
            return true;
        } else {
            return false;
        }
    }

    //    Settter ..........
    public void setMemberManageModIn(MemberManagementModule memManageModIn) {
        this.memberManageModIn = memberManageModIn;
    }

    public List<Member> getMemberList() {
        return memberList;
    }
}