package rootOperationModule;

import java.util.Scanner;
import session.stateless.remote.BookEntityControllerRemote;
import session.stateless.remote.LendEntityControllerRemote;
import session.stateless.remote.MemberEntityControllerRemote;
import session.stateless.remote.StaffEntityControllerRemote;
import util.exception.FineNotFoundException;
import util.exception.FineNotPaidException;
import util.exception.MemberNotFoundException;
import util.exception.ReservedByOthersException;

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
        System.out.println("1: Login\n" + "2: Exit\n");

        System.out.print(">");
    }

    private void getInput() {
        this.input = sc.nextInt();
    }

    public void startRoot() {
        displayMenu();
        getInput();
        
        setBackInstance();
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

    private void setBackInstance() {
        loginOps.setLATRootModIn(this);
    }
}
