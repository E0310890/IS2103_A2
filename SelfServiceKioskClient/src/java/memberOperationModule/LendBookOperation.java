package memberOperationModule;

import java.text.SimpleDateFormat;
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
import util.exception.BookAlreadyLendedException;
import util.exception.BookNotFoundException;
import util.exception.FineNotPaidException;
import util.exception.LoanLimitHitException;
import util.exception.MemberNotFoundException;

public class LendBookOperation {

    private Scanner sc = new Scanner(System.in);
    //API
    private StaffEntityControllerRemote SEC;
    private MemberEntityControllerRemote MEC;
    private BookEntityControllerRemote BEC;
    private LendEntityControllerRemote LEC;
    
    //modules
    private MemberMenuModule MemberMenuModIn;
    
    //fields
    private Member member;
    private Long bookId;
    private Date dueDate;
    
    public LendBookOperation(StaffEntityControllerRemote SEC, MemberEntityControllerRemote MEC, BookEntityControllerRemote BEC, LendEntityControllerRemote LEC) {
        this.SEC = SEC;
        this.MEC = MEC;
        this.BEC = BEC;
        this.LEC = LEC;
    }

    private void displayMenu() {
        System.out.println("*** ILS :: Library Operation :: Lend Book ***\n");     
    }

    private void getInput() {
        System.out.print("Enter Book ID: ");
        this.bookId = sc.nextLong();
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
        System.out.println("\nSuccessfully lent book. Due Date: "
                + Helper.dateToFormattedDateString(this.dueDate) + ".");

    }

    private boolean executeOperation() {
        boolean result = false;
        try {
            this.dueDate = LEC.lendBook(this.member, this.bookId);
            return true;
         } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
        return result;
    }

    private void onOperationSuccessNavigate() {
        try{
            Thread.sleep(1000);
        }catch (InterruptedException ex){
        }
        this.MemberMenuModIn.start();
    }

    private void onOperationFailNavigate() {
        try{
            Thread.sleep(1000);
        }catch (InterruptedException ex){
        }
        this.MemberMenuModIn.start();
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
