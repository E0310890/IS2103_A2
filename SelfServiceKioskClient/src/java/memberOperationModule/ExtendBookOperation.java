package memberOperationModule;

import memberOperationModule.ViewLentBooksOperation;
import memberOperationModule.MemberMenuModule;
import java.util.Date;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Member;
import services.Helper;
import session.stateless.remote.BookEntityControllerRemote;
import session.stateless.remote.LendEntityControllerRemote;
import session.stateless.remote.MemberEntityControllerRemote;
import session.stateless.remote.StaffEntityControllerRemote;
import util.exception.BookOverDueException;
import util.exception.FineNotPaidException;
import util.exception.LendNotFoundException;
import util.exception.MemberNotFoundException;

public class ExtendBookOperation {
    
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
    private String identityNumber;
    private Long bookId;
    private Date dueDate;
    
    public ExtendBookOperation(StaffEntityControllerRemote SEC, MemberEntityControllerRemote MEC, BookEntityControllerRemote BEC, LendEntityControllerRemote LEC) {
        this.SEC = SEC;
        this.MEC = MEC;
        this.BEC = BEC;
        this.LEC = LEC;
    }
    
    private void displayMenu() {
        System.out.println("*** ILS :: Library Operation :: Return Book ***\n");
    }
    
    private boolean executeViewOperation() {
        viewLentBookOps = new ViewLentBooksOperation(SEC, MEC, BEC, LEC);
        return viewLentBookOps.viewLendBooks();
    }
    
    /* private void transferRequiredFields() {
        this.identityNumber = viewLentBookOps.getIdentityNumber();
    } */
    
    private void getInput() {
        System.out.println("Enter Book to Extend> ");
        this.bookId = sc.nextLong();
    }
    
    public void start() {
        displayMenu();
        if (!executeViewOperation()) {
            onOperationFailNavigate();
        }
        // transferRequiredFields();
        
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
        System.out.println("Book successfully extended. New due date: " + Helper.dateToFormattedDateString(this.dueDate));
    }
    
    private boolean executeOperation() {
        boolean result = false;
        try {
            this.dueDate = LEC.ExtendLendBook(this.member, this.bookId);
            return true;
        } catch (MemberNotFoundException | LendNotFoundException | BookOverDueException | FineNotPaidException ex) {
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
}