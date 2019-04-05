package libraryOperationModule;

import java.util.Date;
import java.util.Scanner;
import services.Helper;
import session.stateless.remote.BookEntityControllerRemote;
import session.stateless.remote.LendEntityControllerRemote;
import session.stateless.remote.MemberEntityControllerRemote;
import session.stateless.remote.StaffEntityControllerRemote;

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
        System.out.println("Enter Member Identity Number> ");
        this.identityNumber = sc.next();
        System.out.println("Enter Book ID: ");
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
        System.out.println("Successfully lent book to member. Due Date: "
                + Helper.dateToFormattedDateString(this.dueDate) + ".");
    }

    private boolean executeOperation() {
        boolean result = false;
        try {
            this.dueDate = LEC.lendBook(this.identityNumber, this.bookId);
            return true;
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }

        return result;
    }

    private void onOperationSuccessNavigate(){
        try{
            Thread.sleep(1000);
        }catch (InterruptedException ex){
        }
        this.LibModIn.start();
    }

    private void onOperationFailNavigate() {
        try{
            Thread.sleep(1000);
        }catch (InterruptedException ex){
        }
        this.LibModIn.start();
    }

    //    Settter ..........
    public void setLibModIn(LibraryModule LibModIn) {
        this.LibModIn = LibModIn;
    }

}
