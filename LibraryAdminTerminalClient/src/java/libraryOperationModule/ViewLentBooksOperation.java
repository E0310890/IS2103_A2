package libraryOperationModule;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Lend;
import services.Helper;
import session.stateless.remote.BookEntityControllerRemote;
import session.stateless.remote.LendEntityControllerRemote;
import session.stateless.remote.MemberEntityControllerRemote;
import session.stateless.remote.StaffEntityControllerRemote;
import util.exception.FineNotFoundException;
import util.exception.FineNotPaidException;
import util.exception.MemberNotFoundException;
import util.exception.ReservedByOthersException;

public class ViewLentBooksOperation {

    private Scanner sc = new Scanner(System.in);

    //API
    private StaffEntityControllerRemote SEC;
    private MemberEntityControllerRemote MEC;
    private BookEntityControllerRemote BEC;
    private LendEntityControllerRemote LEC;
    //modules
    private LibraryModule LibModIn;

    //fields
    private String identityNumber;
    private List<Lend> lendList;

    public ViewLentBooksOperation(StaffEntityControllerRemote SEC, MemberEntityControllerRemote MEC, BookEntityControllerRemote BEC, LendEntityControllerRemote LEC) {
        this.SEC = SEC;
        this.MEC = MEC;
        this.BEC = BEC;
        this.LEC = LEC;
    }

    private void displayMenu() {
        System.out.println("*** ILS :: Library Operation :: View Lent Books ***\n");
    }

    private void getInput() {
        System.out.print("Enter Member Identity Number> ");
        this.identityNumber = sc.next();
    }

    public void start() throws InterruptedException, FineNotPaidException, ReservedByOthersException, MemberNotFoundException, FineNotFoundException {
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
            this.lendList = LEC.ViewLendBooks(this.identityNumber);
            return true;
        } catch (MemberNotFoundException ex) {
            System.err.println(ex.getMessage());
        }
        return result;
    }

    private void onOperationSuccessNavigate() throws InterruptedException, FineNotPaidException, ReservedByOthersException, MemberNotFoundException, FineNotFoundException {
        this.LibModIn.start();
    }

    private void onOperationFailNavigate() throws InterruptedException, FineNotPaidException, ReservedByOthersException, MemberNotFoundException, FineNotFoundException {
        Thread.sleep(1000);
        this.LibModIn.start();
    }

    //    Settter ..........
    public void setLibModIn(LibraryModule LibModIn) {
        this.LibModIn = LibModIn;
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
