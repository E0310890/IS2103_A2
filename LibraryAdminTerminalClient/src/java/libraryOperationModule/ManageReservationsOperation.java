/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package libraryOperationModule;

import java.util.Scanner;
import session.stateless.remote.BookEntityControllerRemote;
import session.stateless.remote.LendEntityControllerRemote;
import session.stateless.remote.MemberEntityControllerRemote;
import session.stateless.remote.StaffEntityControllerRemote;

/**
 *
 * @author lester
 */
public class ManageReservationsOperation {
       private Scanner sc = new Scanner(System.in);

    //API
    private StaffEntityControllerRemote SEC;
    private MemberEntityControllerRemote MEC;
    private BookEntityControllerRemote BEC;
    private LendEntityControllerRemote LEC;
    //modules
    private LibraryModule LibModIn;

    //fields
    public ManageReservationsOperation(StaffEntityControllerRemote SEC, MemberEntityControllerRemote MEC, BookEntityControllerRemote BEC, LendEntityControllerRemote LEC) {
        this.SEC = SEC;
        this.MEC = MEC;
        this.BEC = BEC;
        this.LEC = LEC;
    }

    private void displayMenu() {
        System.out.println("*** ILS :: Library Operation :: Manage Reservations ***\n");
    }

    private void getInput() {

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
