package rootOperationModule;

import memberOperationModule.MemberMenuModule;
import java.util.Scanner;
import model.Member;
import session.stateless.remote.BookEntityControllerRemote;
import session.stateless.remote.LendEntityControllerRemote;
import session.stateless.remote.MemberEntityControllerRemote;
import session.stateless.remote.PaymentEntityControllerRemote;
import session.stateless.remote.ReservationEntityControllerRemote;
import session.stateless.remote.StaffEntityControllerRemote;

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
    private final MemberMenuModule memberMenuMod;
    private SSKRootModule SSKRootModIn;
    
    // Fields
    private String username;
    private String password;

    public LoginOperation(StaffEntityControllerRemote SEC, MemberEntityControllerRemote MEC, BookEntityControllerRemote BEC, 
            LendEntityControllerRemote LEC, ReservationEntityControllerRemote REC,PaymentEntityControllerRemote PEC) {
        this.SEC = SEC;
        this.MEC = MEC;
        this.BEC = BEC;
        this.LEC = LEC;
        this.REC = REC;
        this.PEC = PEC;
        this.memberMenuMod = new MemberMenuModule(SEC, MEC, BEC, LEC, REC, PEC);
    }

    public void displayMenu() {
        System.out.println("\n*** Self-Service Kiosk :: Login ***\n");
    }

    private void getInput() {
        System.out.print("Enter Identity Number> ");
        this.username = sc.next();
        System.out.print("Enter Security Code> ");
        this.password = sc.next();
    }

    public void start(){
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
            Member member = MEC.memberLogin(this.username, this.password);
            setField(member);
            return true;
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
            return false;
        }
    }

    private void setBackInstance() {
        memberMenuMod.setLoginOpsIn(this);
    }

    private void setField(Member member) {
        this.memberMenuMod.setMember(member);
    }

    private void onOperationSuccessNavigate() {
        try{
            System.out.print("Login successful!\n");
            Thread.sleep(1000);
                this.memberMenuMod.start();
        }catch (InterruptedException ex){
        }
    }

    private void onOperationFailNavigate(){
        try{
            Thread.sleep(1000);
        }catch (InterruptedException ex){
        }
        this.SSKRootModIn.startRoot();
    }
    
    // Setter
    public void setSSKRootModIn(SSKRootModule SSKRootModIn) {
        this.SSKRootModIn = SSKRootModIn;
    }
    
    public SSKRootModule getSSKRootModIn() {
        return SSKRootModIn;
    }
}