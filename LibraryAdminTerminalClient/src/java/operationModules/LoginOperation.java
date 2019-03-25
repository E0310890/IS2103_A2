/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package operationModules;

import java.util.Scanner;
import model.Staff;
import session.stateless.remote.BookEntityControllerRemote;
import session.stateless.remote.LendEntityControllerRemote;
import session.stateless.remote.MemberEntityControllerRemote;
import session.stateless.remote.StaffEntityControllerRemote;
import util.exception.InvalidLoginCredentialException;

/**
 *
 * @author lester
 */
public class LoginOperation {

    private Scanner sc = new Scanner(System.in);

    //API
    private StaffEntityControllerRemote SEC;
    private MemberEntityControllerRemote MEC;
    private BookEntityControllerRemote BEC;
    private LendEntityControllerRemote LEC;
    //modules
    private final MainMenuModule mainMenuMod;
    //fields
    private String username;
    private String password;

    public LoginOperation(StaffEntityControllerRemote SEC, MemberEntityControllerRemote MEC, BookEntityControllerRemote BEC, LendEntityControllerRemote LEC) {
        this.SEC = SEC;
        this.MEC = MEC;
        this.BEC = BEC;
        this.LEC = LEC;
        this.mainMenuMod = new MainMenuModule(SEC, MEC, BEC, LEC);
    }

    public void displayMenu() {
        System.out.println("*** ILS :: Login ***\n");
    }

    private void getInput() {
        System.out.print("Enter username> ");
        this.username = sc.next();
        System.out.print("Enter password> ");
        this.password = sc.next();
    }

    public void start() {
        displayMenu();
        getInput();

        boolean executeSuccess = executeOperation();
        if (executeSuccess) {
            onOperationSuccessNavigate();
        } else {
            onOperationFailNavigate();
        }
    }

    private boolean executeOperation() {
        try {
            Staff staff = SEC.staffLogin(this.username, this.password);
            setField(staff);

            return true;
        } catch (InvalidLoginCredentialException ex) {
            System.err.println(ex.getMessage());
            return false;
        }
    }

    private void setField(Staff staff) {
        this.mainMenuMod.setMember(staff);
    }

    private void onOperationSuccessNavigate() {
        this.mainMenuMod.start();
    }

    private void onOperationFailNavigate() {
        start();
    }

}
