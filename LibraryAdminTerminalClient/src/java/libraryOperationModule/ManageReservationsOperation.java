package libraryOperationModule;

import java.util.Scanner;
import session.stateless.remote.BookEntityControllerRemote;
import session.stateless.remote.LendEntityControllerRemote;
import session.stateless.remote.MemberEntityControllerRemote;
import session.stateless.remote.StaffEntityControllerRemote;
import util.exception.BookNotFoundException;
import util.exception.FineNotFoundException;
import util.exception.FineNotPaidException;
import util.exception.LoanLimitHitException;
import util.exception.MemberNotFoundException;
import util.exception.ReservedByOthersException;

public class ManageReservationsOperation {
       private Scanner sc = new Scanner(System.in);

    //API
    private StaffEntityControllerRemote SEC;
    private MemberEntityControllerRemote MEC;
    private BookEntityControllerRemote BEC;
    private LendEntityControllerRemote LEC;
    //modules
    private LibraryModule LibModIn;
    
    private int option;

    //fields
    public ManageReservationsOperation(StaffEntityControllerRemote SEC, MemberEntityControllerRemote MEC, BookEntityControllerRemote BEC, LendEntityControllerRemote LEC) {
        this.SEC = SEC;
        this.MEC = MEC;
        this.BEC = BEC;
        this.LEC = LEC;
    }

    private void displayMenu() {
        System.out.println("*** ILS :: Library Operation :: Manage Reservations ***\n");
        System.out.println("1: View Reservations for Book");
        System.out.println("2: Delete Reservation");
        System.out.println("3: Back");
        System.out.println();
        System.out.print("> ");
    }

    private void getInput() {
        option = sc.nextInt();
    }

    public void start() throws InterruptedException, FineNotPaidException, ReservedByOthersException, MemberNotFoundException, FineNotFoundException, LoanLimitHitException, BookNotFoundException {
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
        System.out.println("Member has been registered successfully!");
    }

    private boolean executeOperation() {
        boolean result = false;
//        try {
//            result = MEC.registerMember(this.member);
//        } catch (InvalidInputException ex) {
//            System.err.println(ex.getMessage());
//        }
        return result;
    }

    private void onOperationSuccessNavigate() throws InterruptedException, FineNotPaidException, ReservedByOthersException, MemberNotFoundException, FineNotFoundException, LoanLimitHitException, BookNotFoundException {
        this.LibModIn.start();
    }

    private void onOperationFailNavigate() throws InterruptedException, FineNotPaidException, ReservedByOthersException, MemberNotFoundException, FineNotFoundException, LoanLimitHitException, BookNotFoundException {
        Thread.sleep(1000);
        start();
    }

    //    Settter ..........
    public void setLibModIn(LibraryModule LibModIn) {
        this.LibModIn = LibModIn;
    }
}
