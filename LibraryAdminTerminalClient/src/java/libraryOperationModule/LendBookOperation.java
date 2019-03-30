package libraryOperationModule;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import services.Helper;
import session.stateless.remote.BookEntityControllerRemote;
import session.stateless.remote.LendEntityControllerRemote;
import session.stateless.remote.MemberEntityControllerRemote;
import session.stateless.remote.StaffEntityControllerRemote;
import util.exception.BookAlreadyLendedException;
import util.exception.BookNotFoundException;
import util.exception.MemberNotFoundException;

public class LendBookOperation {

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
        System.out.print("Enter Member Identity Number> ");
        this.identityNumber = sc.next();
        System.out.print("Enter Book ID: ");
        this.bookId = sc.nextLong();
    }

    public void start() throws InterruptedException{
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
        System.out.println("Successfully lent book to member. Due Date: "
                + Helper.dateToFormattedDateString(this.dueDate) + ".\n");

    }

    private boolean executeOperation() {
        boolean result = false;
        try {
            this.dueDate = LEC.lendBook(this.identityNumber, this.bookId);
            return true;
        } catch (MemberNotFoundException | BookNotFoundException | BookAlreadyLendedException ex) {
            System.err.println(ex.getMessage());
        } catch (Exception e) {
            System.err.println("Failed to Lend deal to:\n"
                    + "1) Book already lended to someone or yourself\n"
                    + "2) You have lend more than 3 books\n"
                    + "3) Fines not cleared \n");
        }

        return result;
    }

    private void onOperationSuccessNavigate() throws InterruptedException{
        this.LibModIn.start();
    }

    private void onOperationFailNavigate() throws InterruptedException{
        Thread.sleep(1000);
        this.LibModIn.start();
    }

    //    Settter ..........
    public void setLibModIn(LibraryModule LibModIn) {
        this.LibModIn = LibModIn;
    }

}
