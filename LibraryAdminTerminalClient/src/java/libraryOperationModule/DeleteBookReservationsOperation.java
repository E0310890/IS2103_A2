package libraryOperationModule;

import java.util.List;
import java.util.Scanner;
import model.Reservation;
import session.stateless.remote.BookEntityControllerRemote;
import session.stateless.remote.LendEntityControllerRemote;
import session.stateless.remote.MemberEntityControllerRemote;
import session.stateless.remote.StaffEntityControllerRemote;
import session.stateless.remote.ReservationEntityControllerRemote;
import util.exception.ReservationNotFoundException;

public class DeleteBookReservationsOperation {

    private Scanner sc = new Scanner(System.in);

    // API
    private StaffEntityControllerRemote SEC;
    private MemberEntityControllerRemote MEC;
    private BookEntityControllerRemote BEC;
    private LendEntityControllerRemote LEC;
    private ReservationEntityControllerRemote REC;    
    
    // Modules
    private ManageReservationModule manageReservationModIn;
    
    // Dependecies
    private ViewBookReservationsOperation viewBookReservationsOps;

    // Fields
    private List<Reservation> reservationList;
    private Long reservationID;

    public DeleteBookReservationsOperation(StaffEntityControllerRemote SEC, MemberEntityControllerRemote MEC, BookEntityControllerRemote BEC, LendEntityControllerRemote LEC, ReservationEntityControllerRemote REC) {
        this.SEC = SEC;
        this.MEC = MEC;
        this.BEC = BEC;
        this.LEC = LEC;
        this.REC = REC;
    }

    private void displayMenu() {
        System.out.println("*** ILS :: Library Operation :: Delete Reservations ***\n");
    }

    private boolean executeViewOperation() {
        viewBookReservationsOps = new ViewBookReservationsOperation(SEC, MEC, BEC, LEC, REC);
        return viewBookReservationsOps.displayAllReservations();
    }

    private void getInput() {
        System.out.print("\nEnter Reservation ID to delete> ");
        this.reservationID = sc.nextLong();
    }

    public void start() {
        displayMenu();
        if (!executeViewOperation()) {
            onOperationFailNavigate();
        }
        
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
        System.out.println("Reservation has been deleted successfully!");
    }

    private boolean executeOperation() {
        boolean result = false;
        try {
            REC.deleteReservation(this.reservationID);
            return true;
        } catch (ReservationNotFoundException ex) {
            System.err.println(ex.getMessage());
        }
        return result;
    }

    private void onOperationSuccessNavigate() {
        this.manageReservationModIn.start();
    }

    private void onOperationFailNavigate() {
        this.manageReservationModIn.start();
    }

    // Setter
    public void setManageReservationModIn(ManageReservationModule manageReservationModIn) {
        this.manageReservationModIn = manageReservationModIn;
    }
}