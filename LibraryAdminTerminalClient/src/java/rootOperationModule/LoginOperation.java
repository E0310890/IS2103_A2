package rootOperationModule;

import java.util.Scanner;
import model.Staff;
import session.stateless.remote.BookEntityControllerRemote;
import session.stateless.remote.LendEntityControllerRemote;
import session.stateless.remote.MemberEntityControllerRemote;
import session.stateless.remote.PaymentEntityControllerRemote;
import session.stateless.remote.ReservationEntityControllerRemote;
import session.stateless.remote.StaffEntityControllerRemote;
import util.exception.InvalidLoginCredentialException;

public class LoginOperation {

    private Scanner sc = new Scanner(System.in);

    // API
    private StaffEntityControllerRemote SEC;
    private MemberEntityControllerRemote MEC;
    private BookEntityControllerRemote BEC;
    private LendEntityControllerRemote LEC;
    private ReservationEntityControllerRemote REC;
    private PaymentEntityControllerRemote PEC;
    
    // Modules
    private final MainMenuModule mainMenuMod;
    private LATRootModule LATRootModIn;
    
    // Fields
    private String username;
    private String password;

    public LoginOperation(StaffEntityControllerRemote SEC, MemberEntityControllerRemote MEC, BookEntityControllerRemote BEC, LendEntityControllerRemote LEC, ReservationEntityControllerRemote REC, PaymentEntityControllerRemote PEC) {
        this.SEC = SEC;
        this.MEC = MEC;
        this.BEC = BEC;
        this.LEC = LEC;
        this.REC = REC;
        this.PEC = PEC;
        this.mainMenuMod = new MainMenuModule(SEC, MEC, BEC, LEC, REC, PEC);
    }

    public void displayMenu() {
        System.out.println("*** ILS :: Login ***\n");
    }

    private void getInput() {
        while (true) {
            try {
                System.out.print("Enter username> ");
                this.username = sc.next();
                System.out.print("Enter password> ");
                this.password = sc.next();
                break;
            } catch (Exception ex) {
                System.err.println("please type a valid input");
            }
        }
    }

    public void start() {
        displayMenu();
        getInput();

        boolean executeSuccess = executeOperation();
        if (executeSuccess) {
            setBackInstance();
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

    private void setBackInstance() {
        mainMenuMod.setLoginOpsIn(this);
    }

    private void setField(Staff staff) {
        this.mainMenuMod.setMember(staff);
    }

    private void onOperationSuccessNavigate() {
        this.mainMenuMod.start();
    }

    private void onOperationFailNavigate() {
        this.mainMenuMod.start();
    }

    // Setter
    public void setLATRootModIn(LATRootModule LATRootModIn) {
        this.LATRootModIn = LATRootModIn;
    }

    public LATRootModule getLATRootModIn() {
        return LATRootModIn;
    }
}