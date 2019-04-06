package libraryOperationModule;

import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Lend;
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
    private LibraryModule LibModIn;
    //Dependecies
    private ViewLentBooksOperation vlb;

    //fields
    private String identityNumber;
    private Long bookId;

    public ReturnBookOperation(StaffEntityControllerRemote SEC, MemberEntityControllerRemote MEC, BookEntityControllerRemote BEC, LendEntityControllerRemote LEC) {
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
        System.out.println("Enter Book to Return> ");
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
        System.out.println("Book successfully returned.");
    }

    private boolean executeOperation() {
        boolean result = false;
        try {
            result = LEC.ReturnLendBook(this.identityNumber, this.bookId);
        } catch (MemberNotFoundException | LendNotFoundException ex) {
            System.err.println(ex.getMessage());
        }
        return result;
    }

    private void onOperationSuccessNavigate() {
        this.LibModIn.start();
    }

    private void onOperationFailNavigate() {
        start();
    }

    //    Settter ..........
    public void setLibModIn(LibraryModule LibModIn) {
        this.LibModIn = LibModIn;
    }
}