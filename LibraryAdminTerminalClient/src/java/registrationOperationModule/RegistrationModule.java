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

/**
 *
 * @author lester
 */
public class RegistrationModule {

    private final Scanner sc = new Scanner(System.in);

    //API
    private StaffEntityControllerRemote SEC;
    private MemberEntityControllerRemote MEC;
    private BookEntityControllerRemote BEC;
    private LendEntityControllerRemote LEC;
    //modules
    private RegistrationOperation registerOps;
    private MainMenuModule mainMemuMod;

    //fields
    private int input;

    public RegistrationModule(StaffEntityControllerRemote SEC, MemberEntityControllerRemote MEC, BookEntityControllerRemote BEC, LendEntityControllerRemote LEC) {
        this.SEC = SEC;
        this.MEC = MEC;
        this.BEC = BEC;
        this.LEC = LEC;
        this.registerOps = new RegistrationOperation(SEC, MEC, BEC, LEC);
    }

    private void displayMenu() {
        System.out.println("** ILS :: Registration Operation *** \n");
        System.out.println(
                "1: Register New Member\n"
                + "2: back\n"
        );

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
                registerOps.start();
            case 2:
                this.mainMemuMod.start();
        }

    }

    private void setBackInstance() {
        registerOps.setRegisterMod(this);
    }

    public void setMainMemuMod(MainMenuModule mainMemuMod) {
        this.mainMemuMod = mainMemuMod;
    }

}
