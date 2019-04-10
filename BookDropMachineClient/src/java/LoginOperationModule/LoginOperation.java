package LoginOperationModule;

import MemberOperationModule.MemberMenuModule;
import java.util.Scanner;
import session.stateless.InvalidLoginCredentialException_Exception;
import session.stateless.Member;

public class LoginOperation {

    private Scanner sc = new Scanner(System.in);

    // Modules
    private LoginModule loginModIn;
    private MemberMenuModule memMenuMod;
    
    // Fields
    private String identityNum;
    private String secCode;

    public LoginOperation() {
        memMenuMod = new MemberMenuModule();
    }

    public void displayMenu() {
        System.out.println("*** BDM Client :: Login ***\n");
    }

    private void getInput() {
        while (true) {
            try {
                System.out.print("Enter Identity Number> ");
                this.identityNum = sc.next();
                System.out.print("Enter Security Code> ");
                this.secCode = sc.next();
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
            Member member = memberLogin(this.identityNum, this.secCode);
            setField(member);
            return true;
        } catch (InvalidLoginCredentialException_Exception ex) {
            System.err.println(ex.getMessage());
        }
        return false;
    }

    private void setField(Member member) {
        this.memMenuMod.setMember(member);
    }

    private void onOperationSuccessNavigate() {
        this.memMenuMod.start();
    }

    private void onOperationFailNavigate() {
        start();
    }

    private void setBackInstance() {
        memMenuMod.setLoginOpsIn(this);
    }

    // Setter
    public void setLoginModIn(LoginModule loginModIn) {
        this.loginModIn = loginModIn;
    }

    public LoginModule getLoginModIn() {
        return loginModIn;
    }

    private static Member memberLogin(java.lang.String arg0, java.lang.String arg1) throws InvalidLoginCredentialException_Exception {
        session.stateless.MemberService service = new session.stateless.MemberService();
        session.stateless.MemberEntityController port = service.getMemberEntityControllerPort();
        return port.memberLogin(arg0, arg1);
    }
}