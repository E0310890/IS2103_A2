package memberOperationModule;

import java.util.List;
import java.util.Scanner;
import model.Lend;
import model.Member;
import services.Helper;
import session.stateless.remote.BookEntityControllerRemote;
import session.stateless.remote.LendEntityControllerRemote;
import session.stateless.remote.MemberEntityControllerRemote;
import session.stateless.remote.StaffEntityControllerRemote;
import util.exception.MemberNotFoundException;

public class ReturnBookOperation {

    private Scanner sc = new Scanner(System.in);

    // API
    private StaffEntityControllerRemote SEC;
    private MemberEntityControllerRemote MEC;
    private BookEntityControllerRemote BEC;
    private LendEntityControllerRemote LEC;
    
    // Modules
    private MemberMenuModule MemberMenuModIn;
    
    // Dependecies
    private ViewLentBooksOperation viewLentBookOps;

    // Fields
    private Member member;    
    private Long bookId;
    private List<Lend> lendList;

    public ReturnBookOperation(StaffEntityControllerRemote SEC, MemberEntityControllerRemote MEC, BookEntityControllerRemote BEC, LendEntityControllerRemote LEC) {
        this.SEC = SEC;
        this.MEC = MEC;
        this.BEC = BEC;
        this.LEC = LEC;
    }

    private void displayMenu() {
        System.out.println("*** Self-Service Kiosk :: Return Book ***\n");
    }

    private void getInput() {
        System.out.print("Enter Book to Return> ");
        this.bookId = sc.nextLong();
    }

    public void start() {
        displayMenu();
        displayLentBook();
        
        getInput();
        boolean executeSuccess = executeOperation();
        if (executeSuccess) {
            // successDisplay();
            onOperationSuccessNavigate();
        } else {
            onOperationFailNavigate();
        }
    }
    
    private void displayLentBook(){
         try {
            this.lendList = LEC.ViewLendBooks(this.member);
        } catch (MemberNotFoundException ex) {
            System.err.println(ex.getMessage());
        }
        Helper.displayLending(this.lendList);
    }

    private void successDisplay() {
        System.out.println("Book successfully returned.");
    }

    private boolean executeOperation() {
        boolean result = false;
        try {
            result = LEC.ReturnLendBook(this.member, this.bookId);
            successDisplay();
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

    // Setter
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