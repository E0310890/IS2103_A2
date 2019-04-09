package rootOperationModule;

import java.util.Scanner;
import session.stateless.remote.BookEntityControllerRemote;
import session.stateless.remote.LendEntityControllerRemote;
import session.stateless.remote.MemberEntityControllerRemote;
import session.stateless.remote.PaymentEntityControllerRemote;
import session.stateless.remote.ReservationEntityControllerRemote;
import session.stateless.remote.StaffEntityControllerRemote;

public class LATRootModule {

    private Scanner sc = new Scanner(System.in);
    //API
    private StaffEntityControllerRemote SEC;
    private MemberEntityControllerRemote MEC;
    private BookEntityControllerRemote BEC;
    private LendEntityControllerRemote LEC;
    private ReservationEntityControllerRemote REC;
    private PaymentEntityControllerRemote PEC;

    //modules
    private final LoginOperation loginOps;

    //fields
    private int input;

    public LATRootModule(StaffEntityControllerRemote SEC, MemberEntityControllerRemote MEC, BookEntityControllerRemote BEC, LendEntityControllerRemote LEC, ReservationEntityControllerRemote REC, PaymentEntityControllerRemote PEC) {
        this.SEC = SEC;
        this.MEC = MEC;
        this.BEC = BEC;
        this.LEC = LEC;
        this.REC = REC;
        this.PEC = PEC;
        this.loginOps = new LoginOperation(SEC, MEC, BEC, LEC, REC, PEC);
    }

    private void displayMenu() {
        System.out.println("*** Welcome to Library Admin Terminal ***");
        System.out.println("1: Login\n" + "2: Exit");

        System.out.print("> ");
    }

    private void getInput() {
        while (true) {
            try {
                this.input = sc.nextInt();
                break;
            } catch (Exception ex) {
                System.err.println("please type a valid input");
            }
        }
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
