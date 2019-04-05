package memberOperationModule;

import java.util.Scanner;
import model.Member;
import rootOperationModule.LoginOperation;
import session.stateless.remote.BookEntityControllerRemote;
import session.stateless.remote.LendEntityControllerRemote;
import session.stateless.remote.MemberEntityControllerRemote;
import session.stateless.remote.StaffEntityControllerRemote;
import util.exception.BookNotFoundException;
import util.exception.FineNotFoundException;
import util.exception.FineNotPaidException;
import util.exception.LoanLimitHitException;
import util.exception.MemberNotFoundException;
import util.exception.ReservedByOthersException;

public class MemberMenuModule {

    private final Scanner sc = new Scanner(System.in);

    //API
    private StaffEntityControllerRemote SEC;
    private MemberEntityControllerRemote MEC;
    private BookEntityControllerRemote BEC;
    private LendEntityControllerRemote LEC;
    //modules
    private LendBookOperation lendBookOps;
    private ViewLentBookOperation viewLentBookOps;
    private ReturnBookOperation returnBookOps;    
    private LoginOperation loginOpsIn;
    //fields
    private int input;
    private Member member;

    public MemberMenuModule(StaffEntityControllerRemote SEC, MemberEntityControllerRemote MEC, BookEntityControllerRemote BEC, LendEntityControllerRemote LEC) {
        this.SEC = SEC;
        this.MEC = MEC;
        this.BEC = BEC;
        this.LEC = LEC;
        lendBookOps = new LendBookOperation(SEC, MEC, BEC, LEC);        
    }

    private void displayMenu() {
        System.out.println("\n*** Self-Service Kiosk :: Main ***\n");
        System.out.println("You are login as " + member.getFirstName() + " " + member.getLastName());
        System.out.println();
        System.out.println(
                "1: Borrow Book\n"
                + "2: View Lent Books\n"
                + "3: Return Book\n"
                + "4: Extend Book\n"
                + "5: Pay Fines\n"
                + "6: Search Book\n"      
                + "7: Reserve Book\n"                              
                + "8: Logout");

        System.out.print("\n>");
    }

    private void getInput() {
        this.input = sc.nextInt();
    }

    public void start(){
        displayMenu();
        getInput();

        setBackInstance();
        navigate(this.input);
    }

    private void navigate(int input){
        switch (input) {
            case 1:
                lendBookOps.start();
            case 2:
                viewLentBookOps.start();
            case 3:
                returnBookOps.start();
            case 8:
                loginOpsIn.getSSKRootModIn().startRoot();
        }
    }

    private void setBackInstance() {
        lendBookOps.setMemberMenuModIn(this);     
        viewLentBookOps.setMemberMenuModIn(this);
        returnBookOps.setMemberMenuModIn(this);        
    }

    //    Settter ..........
    public void setMember(Member member) {
        this.member = member;
    }

    public void setLoginOpsIn(LoginOperation loginOpsIn) {
        this.loginOpsIn = loginOpsIn;
    }

    public LoginOperation getLoginOpsIn() {
        return loginOpsIn;
    }
}