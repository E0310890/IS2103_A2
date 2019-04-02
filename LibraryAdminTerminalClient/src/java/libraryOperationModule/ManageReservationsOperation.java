package libraryOperationModule;

import java.util.Scanner;
import session.stateless.remote.BookEntityControllerRemote;
import session.stateless.remote.LendEntityControllerRemote;
import session.stateless.remote.MemberEntityControllerRemote;
import session.stateless.remote.StaffEntityControllerRemote;

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
