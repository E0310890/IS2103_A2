package libraryOperationModule;

import java.util.Date;
import java.util.Scanner;
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
    private LibraryModule LibModIn;
    
    // Dependecies
    private ViewLentBooksOperation vlb;
    
    // Fields
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
        vlb = new ViewLentBooksOperation(SEC, MEC, BEC, LEC);
        return vlb.viewLendBooks();
    }
    
    private void transferRequiredFields() {
        this.identityNumber = vlb.getIdentityNumber();
    }
    
    private void getInput() {
        System.out.println("Enter Book to Extend> ");
        this.bookId = sc.nextLong();
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
        System.out.println("Book successfully extended. New due date: " + Helper.dateToFormattedDateString(this.dueDate));
    }
    
    private boolean executeOperation() {
        boolean result = false;
        try {
            this.dueDate = LEC.ExtendLendBook(this.identityNumber, this.bookId);
            return true;
        } catch (MemberNotFoundException | LendNotFoundException | BookOverDueException | FineNotPaidException | ReservedByOthersException ex) {
            System.err.println(ex.getMessage());
        }
        return result;
    }
    
    private void onOperationSuccessNavigate() {
        this.LibModIn.start();
    }
    
    private void onOperationFailNavigate() {
        this.LibModIn.start();
    }

    // Setter
    public void setLibModIn(LibraryModule LibModIn) {
        this.LibModIn = LibModIn;
    }
}