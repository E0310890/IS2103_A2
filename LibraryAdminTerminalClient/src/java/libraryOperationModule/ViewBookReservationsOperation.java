package libraryOperationModule;

import java.util.List;
import java.util.Scanner;
import model.Reservation;
import services.Helper;
import session.stateless.remote.BookEntityControllerRemote;
import session.stateless.remote.LendEntityControllerRemote;
import session.stateless.remote.MemberEntityControllerRemote;
import session.stateless.remote.ReservationEntityControllerRemote;
import session.stateless.remote.StaffEntityControllerRemote;
import util.exception.BookNotFoundException;
import util.exception.ReservationNotFoundException;

public class ViewBookReservationsOperation {

    private Scanner sc = new Scanner(System.in);
    
    // API
    private StaffEntityControllerRemote SEC;
    private MemberEntityControllerRemote MEC;
    private BookEntityControllerRemote BEC;
    private LendEntityControllerRemote LEC;
    private ReservationEntityControllerRemote REC;
    
    // Modules
    private ManageReservationModule ManageReservationModIn;

    // Fields
    private List<Reservation> reservationList;

    public ViewBookReservationsOperation(StaffEntityControllerRemote SEC, MemberEntityControllerRemote MEC, BookEntityControllerRemote BEC, LendEntityControllerRemote LEC, ReservationEntityControllerRemote REC) {
        this.SEC = SEC;
        this.MEC = MEC;
        this.BEC = BEC;
        this.LEC = LEC;
        this.REC = REC;        
    }

    private void displayMenu() {
        System.out.println("*** ILS :: Library Operation :: View Book Reservations ***\n");
    }  

    public void start() {
        displayMenu();

        boolean executeSuccess = executeOperation();
        if (executeSuccess) {
            successDisplay();
            onOperationSuccessNavigate();
        } else {
            onOperationFailNavigate();
        }
    }

    private void successDisplay() {
        Helper.displayReservation(reservationList);
    }

    private boolean executeOperation() {
        boolean result = false;
            
        try {
            this.reservationList = REC.viewReservation();
        } catch (BookNotFoundException | ReservationNotFoundException ex) {
            return false;
        }
        return true;
    }

    private void onOperationSuccessNavigate() {
        this.ManageReservationModIn.start();
    }

    private void onOperationFailNavigate() {
        this.ManageReservationModIn.start();
    }

    // Setter
    public void setManageReservationModIn(ManageReservationModule ManageReservationModIn) {
        this.ManageReservationModIn = ManageReservationModIn;
    }

    public boolean viewBookRerservations() {
        boolean executeSuccess = executeOperation();
        if (executeSuccess) {
            successDisplay();
            return true;
        } else {
            return false;
        }
    }
    
    public boolean displayAllReservations() {
        boolean executeSuccess = executeOperation();
        if (executeSuccess) {
            successDisplay();
            return true;
        } else {
            return false;
        }
    }
}