package memberOperationModule;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import memberOperationModule.MemberMenuModule;
import model.Lend;
import model.Member;
import services.Helper;
import session.stateless.remote.BookEntityControllerRemote;
import session.stateless.remote.LendEntityControllerRemote;
import session.stateless.remote.MemberEntityControllerRemote;
import session.stateless.remote.StaffEntityControllerRemote;
import util.exception.MemberNotFoundException;

public class SearchBookOperation {

    private Scanner sc = new Scanner(System.in);

    //API
    private StaffEntityControllerRemote SEC;
    private MemberEntityControllerRemote MEC;
    private BookEntityControllerRemote BEC;
    private LendEntityControllerRemote LEC;
    
    //modules
    private MemberMenuModule MemberMenuModIn;

    //fields
    public Member member;    
    private String identityNumber;
    private List<Lend> lendList;

    public SearchBookOperation(StaffEntityControllerRemote SEC, MemberEntityControllerRemote MEC, BookEntityControllerRemote BEC, LendEntityControllerRemote LEC) {
        this.SEC = SEC;
        this.MEC = MEC;
        this.BEC = BEC;
        this.LEC = LEC;
    }

    private void displayMenu() {
        System.out.println("*** Self-Service Kiosk :: View Lent Books ***\n");
    }

    private void getInput() {
        this.member.getIdentityNumber();
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
        Helper.displayLending(this.lendList);
    }

    private boolean executeOperation() {
        boolean result = false;

        try {
            this.lendList = LEC.ViewLendBooks(this.member);
            return true;
        } catch (MemberNotFoundException ex) {
            System.err.println(ex.getMessage());
        }
        return result;
    }

    private void onOperationSuccessNavigate() {
        this.MemberMenuModIn.start();
    }

    private void onOperationFailNavigate() {
        start();
    }

    //    Settter ..........
    public void setMember(Member member) {
        this.member = member;
    }  
    
    public void setMemberMenuModIn(MemberMenuModule MemberMenuModIn) {
        this.MemberMenuModIn = MemberMenuModIn;
    }

    public MemberMenuModule getMemberMenuOpsIn() {
        return MemberMenuModIn;
    }

    public boolean viewLendBooks() {
        getInput();

        boolean executeSuccess = executeOperation();
        if (executeSuccess) {
            successDisplay();
            return true;
        } else {
            return false;
        }
    }

    public String getIdentityNumber() {
        return identityNumber;
    }
}