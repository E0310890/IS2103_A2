package memberOperationModule;

import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import memberOperationModule.MemberMenuModule;
import model.Lend;
import model.Member;
import session.stateless.remote.BookEntityControllerRemote;
import session.stateless.remote.LendEntityControllerRemote;
import session.stateless.remote.MemberEntityControllerRemote;
import session.stateless.remote.StaffEntityControllerRemote;
import util.exception.LendNotFoundException;
import util.exception.MemberNotFoundException;

public class ReturnBookOperation {

    private Scanner sc = new Scanner(System.in);

    //API
    private StaffEntityControllerRemote SEC;
    private MemberEntityControllerRemote MEC;
    private BookEntityControllerRemote BEC;
    private LendEntityControllerRemote LEC;
    //modules
    private MemberMenuModule MemberMenuModIn;
    //Dependecies
    private ViewLentBooksOperation viewLentBookOps;

    //fields
    private Member member;    
    // private String identityNumber;
    private Long bookId;

    public ReturnBookOperation(StaffEntityControllerRemote SEC, MemberEntityControllerRemote MEC, BookEntityControllerRemote BEC, LendEntityControllerRemote LEC) {
        this.SEC = SEC;
        this.MEC = MEC;
        this.BEC = BEC;
        this.LEC = LEC;
    }

    private void displayMenu() {
        System.out.println("*** Self-Service Kiosk :: Return Book ***\n");
        // System.out.println(member.getIdentityNumber());
    }

    private boolean executeViewOperation() {
        viewLentBookOps = new ViewLentBooksOperation(SEC, MEC, BEC, LEC);
        return viewLentBookOps.viewLendBooks();
    }

    /* private void transferRequiredFields() {
        this.identityNumber = viewLentBookOps.member.getIdentityNumber();
    } */

    private void getInput() {
        System.out.print("Enter Book to Return> ");
        this.bookId = sc.nextLong();
    }

    public void start() {
        displayMenu();
        /* if (!executeViewOperation()) {
            onOperationFailNavigate();
        } */
        // transferRequiredFields();
        
        getInput();
        boolean executeSuccess = executeOperation();
        if (executeSuccess) {
            // successDisplay();
            onOperationSuccessNavigate();
        } else {
            onOperationFailNavigate();
        }
    }

    private void successDisplay() {
        System.out.println("Book successfully returned.");
    }

    private boolean executeOperation() {
        boolean result = false;
        try {
            result = LEC.ReturnLendBook(this.member, this.bookId);
            successDisplay();
        } catch (MemberNotFoundException | LendNotFoundException ex) {
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
    
    /* public String getIdentityNumber() {
        return identityNumber;
    } */
}
