package rootOperationModule;

import adminOperationModule.AdminModule;
import libraryOperationModule.LibraryModule;
import registrationOperationModule.RegistrationModule;
import java.util.Scanner;
import model.Staff;
import session.stateless.remote.BookEntityControllerRemote;
import session.stateless.remote.LendEntityControllerRemote;
import session.stateless.remote.MemberEntityControllerRemote;
import session.stateless.remote.PaymentEntityControllerRemote;
import session.stateless.remote.StaffEntityControllerRemote;
import session.stateless.remote.ReservationEntityControllerRemote;

public class MainMenuModule {

    private final Scanner sc = new Scanner(System.in);

    // API
    private StaffEntityControllerRemote SEC;
    private MemberEntityControllerRemote MEC;
    private BookEntityControllerRemote BEC;
    private LendEntityControllerRemote LEC;
    private ReservationEntityControllerRemote REC;
    private PaymentEntityControllerRemote PEC;
    
    // Modules
    private LoginOperation loginOpsIn;
    private RegistrationModule registerMod;
    private LibraryModule libMod;
    private AdminModule adminMod;
    
    // Fields
    private int input;
    private Staff staff;

    public MainMenuModule(StaffEntityControllerRemote SEC, MemberEntityControllerRemote MEC, BookEntityControllerRemote BEC, LendEntityControllerRemote LEC, ReservationEntityControllerRemote REC, PaymentEntityControllerRemote PEC) {
        this.SEC = SEC;
        this.MEC = MEC;
        this.BEC = BEC;
        this.LEC = LEC;
        this.REC = REC;
        this.PEC = PEC;
        registerMod = new RegistrationModule(SEC, MEC, BEC, LEC);
        libMod = new LibraryModule(SEC, MEC, BEC, LEC, REC, PEC);
        adminMod = new AdminModule(SEC, MEC, BEC, LEC);
    }

    private void displayMenu() {
        System.out.println("\n*** ILS :: Main ***");
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
        while (true) {
            try {
                this.input = sc.nextInt();
                break;
            } catch (Exception ex) {
                System.err.println("please type a valid input");
            }
        }
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
                libMod.start();
            case 3:
                adminMod.start();
            case 4:
                loginOpsIn.getLATRootModIn().startRoot();
        }
    }

    private void setBackInstance() {
        registerMod.setMainMemuModIn(this);
        libMod.setMainMenuModIn(this);
        adminMod.setMainMenuModIn(this);
    }

    // Setter
    public void setMember(Staff staff) {
        this.staff = staff;
    }

    public void setLoginOpsIn(LoginOperation loginOpsIn) {
        this.loginOpsIn = loginOpsIn;
    }

    public LoginOperation getLoginOpsIn() {
        return loginOpsIn;
    }
}