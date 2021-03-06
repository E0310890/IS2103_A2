package registrationOperationModule;

import java.util.Scanner;
import rootOperationModule.MainMenuModule;
import session.stateless.remote.BookEntityControllerRemote;
import session.stateless.remote.LendEntityControllerRemote;
import session.stateless.remote.MemberEntityControllerRemote;
import session.stateless.remote.StaffEntityControllerRemote;

public class RegistrationModule {

    private final Scanner sc = new Scanner(System.in);

    // API
    private StaffEntityControllerRemote SEC;
    private MemberEntityControllerRemote MEC;
    private BookEntityControllerRemote BEC;
    private LendEntityControllerRemote LEC;
    
    // Modules
    private RegistrationOperation registerOps;
    private MainMenuModule mainMenuModIn;

    // Fields
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
                + "2: Back\n"
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
                this.mainMenuModIn.start();
        }
    }

    private void setBackInstance() {
        registerOps.setRegisterModIn(this);
    }

    // Setter
    public MainMenuModule getMainMenuModIn() {
        return mainMenuModIn;
    }

    public void setMainMemuModIn(MainMenuModule mainMenuModIn) {
        this.mainMenuModIn = mainMenuModIn;
    }
}