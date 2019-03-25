/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package operationModules;

import java.util.Scanner;
import session.stateless.remote.BookEntityControllerRemote;
import session.stateless.remote.LendEntityControllerRemote;
import session.stateless.remote.MemberEntityControllerRemote;
import session.stateless.remote.StaffEntityControllerRemote;

/**
 *
 * @author lester
 */
public class LATRootModule {

    private Scanner sc = new Scanner(System.in);
    //API
    private StaffEntityControllerRemote SEC;
    private MemberEntityControllerRemote MEC;
    private BookEntityControllerRemote BEC;
    private LendEntityControllerRemote LEC;

    //modules
    private final LoginOperation loginOps;

    //fields
    private int input;

    public LATRootModule(StaffEntityControllerRemote SEC, MemberEntityControllerRemote MEC, BookEntityControllerRemote BEC, LendEntityControllerRemote LEC) {
        this.SEC = SEC;
        this.MEC = MEC;
        this.BEC = BEC;
        this.LEC = LEC;
        this.loginOps = new LoginOperation(SEC, MEC, BEC, LEC);
    }

    private void displayMenu() {
        System.out.println("*** Welcome to Library Admin Terminal ***");
        System.out.println("1: Login\n" + "2: Exit");

        System.out.print(">");
    }

    private void getInput() {
        this.input = sc.nextInt();
    }

    public void startRoot() {
        displayMenu();
        getInput();

        navigate(this.input);
    }

    private void navigate(int input) {
        switch (input) {
            case 1:
                loginOps.start();
            case 2:
                System.exit(0);
        }

    }
}
