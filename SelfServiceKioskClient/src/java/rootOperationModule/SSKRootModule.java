package rootOperationModule;

import java.util.Scanner;
import session.stateless.remote.BookEntityControllerRemote;
import session.stateless.remote.LendEntityControllerRemote;
import session.stateless.remote.MemberEntityControllerRemote;
import session.stateless.remote.PaymentEntityControllerRemote;
import session.stateless.remote.ReservationEntityControllerRemote;
import session.stateless.remote.StaffEntityControllerRemote;

public class SSKRootModule {

    private Scanner sc = new Scanner(System.in);
    
    // API
    private StaffEntityControllerRemote SEC;
    private MemberEntityControllerRemote MEC;
    private BookEntityControllerRemote BEC;
    private LendEntityControllerRemote LEC;
    private ReservationEntityControllerRemote REC;
    private PaymentEntityControllerRemote PEC;

    // Modules
    private final RegistrationOperation registerOps;    
    private final LoginOperation loginOps;

    // Fields
    private int input;

    public SSKRootModule(StaffEntityControllerRemote SEC, MemberEntityControllerRemote MEC, BookEntityControllerRemote BEC, 
            LendEntityControllerRemote LEC, ReservationEntityControllerRemote REC, PaymentEntityControllerRemote PEC) {
        this.SEC = SEC;
        this.MEC = MEC;
        this.BEC = BEC;
        this.LEC = LEC;
        this.REC = REC;
        this.PEC = PEC;
        this.registerOps = new RegistrationOperation(SEC, MEC, BEC, LEC);        
        this.loginOps = new LoginOperation(SEC, MEC, BEC, LEC, REC, PEC);
    }

    private void displayMenu() {
        System.out.println("\n*** Welcome to Self-Service Kiosk ***\n");
        System.out.println("1: Register\n" + "2: Login\n" + "3: Exit\n");

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
                registerOps.start();
            case 2:
                loginOps.start();                
            case 3:
                System.exit(0);
        }
    }

    private void setBackInstance() {
        registerOps.setSSKRootModIn(this);        
        loginOps.setSSKRootModIn(this);    
    }
}