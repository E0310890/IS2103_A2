/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package operationModules;

import java.util.Scanner;
import model.Member;
import model.Staff;
import session.stateless.remote.BookEntityControllerRemote;
import session.stateless.remote.LendEntityControllerRemote;
import session.stateless.remote.MemberEntityControllerRemote;
import session.stateless.remote.StaffEntityControllerRemote;

/**
 *
 * @author lester
 */
public class MainMenuModule {

    private final Scanner sc = new Scanner(System.in);

    //API
    private StaffEntityControllerRemote SEC;
    private MemberEntityControllerRemote MEC;
    private BookEntityControllerRemote BEC;
    private LendEntityControllerRemote LEC;
    //modules
    private RegistrationModule registerMod;
    //fields
    private int input;
    private Staff staff;

    public MainMenuModule(StaffEntityControllerRemote SEC, MemberEntityControllerRemote MEC, BookEntityControllerRemote BEC, LendEntityControllerRemote LEC) {
        this.SEC = SEC;
        this.MEC = MEC;
        this.BEC = BEC;
        this.LEC = LEC;
        registerMod = new RegistrationModule(SEC, MEC, BEC, LEC);
    }
    
    private void displayMenu() {
        System.out.println("*** ILS :: Main ***");
        System.out.println("You are login as " + staff.getFirstName() + " " + staff.getLastName());
        System.out.println();
        System.out.println(
                "1: Registration Operation\n"
                + "2: Library Operation\n"
                + "3: Administration Operation\n"
                + "4: Logout");

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
                registerMod.start();
            case 2:
                System.exit(0);
        }

    }
    
    private void setBackInstance(){
        registerMod.setMainMemuMod(this);
    }

    public void setMember(Staff staff) {
        this.staff = staff;
    }

}
