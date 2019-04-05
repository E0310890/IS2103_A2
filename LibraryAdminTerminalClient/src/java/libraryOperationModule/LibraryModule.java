package libraryOperationModule;

import java.util.Scanner;
import rootOperationModule.MainMenuModule;
import session.stateless.remote.BookEntityControllerRemote;
import session.stateless.remote.LendEntityControllerRemote;
import session.stateless.remote.MemberEntityControllerRemote;
import session.stateless.remote.StaffEntityControllerRemote;

public class LibraryModule {

    private final Scanner sc = new Scanner(System.in);

    //API
    private StaffEntityControllerRemote SEC;
    private MemberEntityControllerRemote MEC;
    private BookEntityControllerRemote BEC;
    private LendEntityControllerRemote LEC;
    //modules
    private MainMenuModule MainMenuModIn;
    private LendBookOperation lendBookOps;
    private ViewLentBooksOperation viewLendBooksOps;
    private ReturnBookOperation returnBookOps;
    private ExtendBookOperation extendBookOps;
    private PayFinesOperation payFinesOps;
    private ManageReservationsOperation manageReservationOps;
    //fields
    private int input;

    public LibraryModule(StaffEntityControllerRemote SEC, MemberEntityControllerRemote MEC, BookEntityControllerRemote BEC, LendEntityControllerRemote LEC) {
        this.SEC = SEC;
        this.MEC = MEC;
        this.BEC = BEC;
        this.LEC = LEC;
        lendBookOps = new LendBookOperation(SEC, MEC, BEC, LEC);
        viewLendBooksOps = new ViewLentBooksOperation(SEC, MEC, BEC, LEC);
        returnBookOps = new ReturnBookOperation(SEC, MEC, BEC, LEC);
        extendBookOps = new ExtendBookOperation(SEC, MEC, BEC, LEC);
        payFinesOps = new PayFinesOperation(SEC, MEC, BEC, LEC);
        manageReservationOps = new ManageReservationsOperation(SEC, MEC, BEC, LEC);
    }

    private void displayMenu() {
        System.out.println("*** ILS :: Library Operation ***");
        System.out.println();
        System.out.println(
                "1: Lend Book\n"
                + "2: View Lent Books\n"
                + "3: Return Book\n"
                + "4: Extend Book\n"
                + "5: Pay Fines\n"
                + "6: Manage Reservations\n"
                + "7: Back\n");

        System.out.print("\n>");
    }

    private void getInput() {
        this.input = sc.nextInt();
    }

    public void start() {
        displayMenu();
        getInput();

        setBackInstance();
        navigate(this.input);
    }

    private void navigate(int input) {
        switch (input) {
            case 1:
                lendBookOps.start();
            case 2:
                viewLendBooksOps.start();
            case 3:
                returnBookOps.start();
            case 4:
                extendBookOps.start();
            case 5:
                payFinesOps.start();
            case 6:
                manageReservationOps.start();
            case 7:
                MainMenuModIn.start();

        }

    }

    private void setBackInstance() {
        lendBookOps.setLibModIn(this);
        viewLendBooksOps.setLibModIn(this);
        returnBookOps.setLibModIn(this);
        extendBookOps.setLibModIn(this);
        payFinesOps.setLibModIn(this);
        manageReservationOps.setLibModIn(this);
    }

//    Settter ..........
    public MainMenuModule getMainMenuModIn() {
        return MainMenuModIn;
    }

    public void setMainMenuModIn(MainMenuModule MainMenuModIn) {
        this.MainMenuModIn = MainMenuModIn;
    }

}
