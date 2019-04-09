package memberOperationModule;

import java.util.Date;
import java.util.List;
import java.util.Scanner;
import model.Lend;
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
import util.exception.ReservedByOthersException;

public class ExtendBookOperation {
    
    private Scanner sc = new Scanner(System.in);

    // API
    private StaffEntityControllerRemote SEC;
    private MemberEntityControllerRemote MEC;
    private BookEntityControllerRemote BEC;
    private LendEntityControllerRemote LEC;
    
    // Modules
    private MemberMenuModule MemberMenuModIn;
        
    // Fields
    private Member member;        
    private Long bookId;
    private Date dueDate;
    private List<Lend> lendList;
    
    public ExtendBookOperation(StaffEntityControllerRemote SEC, MemberEntityControllerRemote MEC, BookEntityControllerRemote BEC, LendEntityControllerRemote LEC) {
        this.SEC = SEC;
        this.MEC = MEC;
        this.BEC = BEC;
        this.LEC = LEC;
    }
    
    private void displayMenu() {
        System.out.println("*** Self-Service Kiosk :: Extend Book ***\n");
    }
    
    private void getInput() {
        System.out.println("Enter Book to Extend> ");
        this.bookId = sc.nextLong();
    }
    
    public void start() {
        displayMenu();
        displayLentBook();
        
        getInput();
        boolean executeSuccess = executeOperation();
        if (executeSuccess) {
            successDisplay();
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
        System.out.println("Book successfully extended. New due date: " + Helper.dateToFormattedDateString(this.dueDate));
    }
    
    private boolean executeOperation() {
        boolean result = false;
        try {try{
            Thread.sleep(1000);
        }catch (InterruptedException ex){
        }
            this.dueDate = LEC.ExtendLendBook(this.member, this.bookId);
            return true;
        } catch (MemberNotFoundException | LendNotFoundException | BookOverDueException | FineNotPaidException | ReservedByOthersException ex) {
            System.err.println(ex.getMessage());
        }
        return result;
    }
    
    private void onOperationSuccessNavigate() {
        
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