package libraryOperationModule;

import javax.ejb.EJB;
import java.util.Scanner;
import session.stateless.remote.BookEntityControllerRemote;
import session.stateless.remote.LendEntityControllerRemote;
import session.stateless.remote.MemberEntityControllerRemote;
import session.stateless.remote.StaffEntityControllerRemote;
import session.stateless.remote.ReservationEntityControllerRemote;

public class ManageReservationModule {
    
    private final Scanner sc = new Scanner(System.in);

    //API
    private StaffEntityControllerRemote SEC;
    private MemberEntityControllerRemote MEC;
    private BookEntityControllerRemote BEC;
    private LendEntityControllerRemote LEC;
    private ReservationEntityControllerRemote REC;
    
    //modules
    private LibraryModule LibModIn;
    private ViewBookReservationsOperation viewBookReservationsOps; 
    private DeleteBookReservationsOperation deleteBookReservationsOps; 
    
//fields
    private int input;

    public ManageReservationModule(StaffEntityControllerRemote SEC, MemberEntityControllerRemote MEC, BookEntityControllerRemote BEC, LendEntityControllerRemote LEC, ReservationEntityControllerRemote REC) {
        this.SEC = SEC;
        this.MEC = MEC;
        this.BEC = BEC;
        this.LEC = LEC;
        this.REC = REC;       
        viewBookReservationsOps = new ViewBookReservationsOperation(SEC, MEC, BEC, LEC, REC);
        deleteBookReservationsOps = new DeleteBookReservationsOperation(SEC, MEC, BEC, LEC, REC);
    }

    private void displayMenu() {
        System.out.println("*** ILS :: Library Operation :: Manage Reservations ***");
        System.out.println();
        System.out.println(
                "1: View Reservations for Book\n"
                + "2: Delete Reservation\n"
                + "3: Back\n");

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
                viewBookReservationsOps.start();
            case 2:
                deleteBookReservationsOps.start();
            case 3:
                LibModIn.start();
        }

    }

    private void setBackInstance() {
        viewBookReservationsOps.setManageReservationModIn(this);
        deleteBookReservationsOps.setManageReservationModIn(this);        
    }

//    Settter ..........
    public LibraryModule getLibraryModIn() {
        return LibModIn;
    }

    public void setLibModIn(LibraryModule LibModIn) {
        this.LibModIn = LibModIn;
    }    
}