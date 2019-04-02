package libraryOperationModule;

import java.util.Scanner;
import session.stateless.remote.BookEntityControllerRemote;
import session.stateless.remote.LendEntityControllerRemote;
import session.stateless.remote.MemberEntityControllerRemote;
import session.stateless.remote.PaymentEntityControllerRemote;
import session.stateless.remote.StaffEntityControllerRemote;
import util.exception.BookNotFoundException;
import util.exception.FineNotFoundException;
import util.exception.FineNotPaidException;
import util.exception.InvalidInputException;
import util.exception.LendNotFoundException;
import util.exception.LoanLimitHitException;
import util.exception.MemberNotFoundException;
import util.exception.ReservedByOthersException;

public class PayFinesOperation {

    private Scanner sc = new Scanner(System.in);

    //API
    private StaffEntityControllerRemote SEC;
    private MemberEntityControllerRemote MEC;
    private BookEntityControllerRemote BEC;
    private LendEntityControllerRemote LEC;
    private PaymentEntityControllerRemote PEC;
    //modules
    private LibraryModule LibModIn;

    //the lendID column in payment, NOT A KEY, just a unique value for display
    private Long lendId;
    private String identityNumber;
    //fields
    public PayFinesOperation(StaffEntityControllerRemote SEC, MemberEntityControllerRemote MEC, BookEntityControllerRemote BEC, LendEntityControllerRemote LEC) {
        this.SEC = SEC;
        this.MEC = MEC;
        this.BEC = BEC;
        this.LEC = LEC;
    }

    private void displayMenu() {
        System.out.println("*** ILS :: Library Operation :: Pay Fines ***\n");
    }

    private void getInput() {
        System.out.println("Enter Member Identity Number> ");
        identityNumber = sc.nextLine();
        System.out.println("Enter fine to settle> ");
        lendId = sc.nextLong();
        sc.nextLine();
        System.out.println("Select Payment Method (1: Cash, 2: Card)> ");
        sc.nextLine();      
    }

    public void start(){
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
        System.out.println("Fine successfully paid.");
    }

    private boolean executeOperation(){
        boolean result = false;
        try {
            result = PEC.payFine(identityNumber, lendId);
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
        return result;
    }

    private void onOperationSuccessNavigate(){
        try{
            Thread.sleep(1000);
            this.LibModIn.start();
        }catch(InterruptedException ex){
        }
    }

    private void onOperationFailNavigate(){
        try{
            Thread.sleep(1000);
            this.LibModIn.start();
        }catch(InterruptedException ex){
        }
    }

    //    Settter ..........
    public void setLibModIn(LibraryModule LibModIn) {
        this.LibModIn = LibModIn;
    }
}
