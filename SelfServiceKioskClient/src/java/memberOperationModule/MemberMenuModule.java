package memberOperationModule;

import java.util.Scanner;
import model.Member;
import rootOperationModule.LoginOperation;
import session.stateless.remote.BookEntityControllerRemote;
import session.stateless.remote.LendEntityControllerRemote;
import session.stateless.remote.MemberEntityControllerRemote;
import session.stateless.remote.PaymentEntityControllerRemote;
import session.stateless.remote.ReservationEntityControllerRemote;
import session.stateless.remote.StaffEntityControllerRemote;

public class MemberMenuModule {

    private final Scanner sc = new Scanner(System.in);

    //API
    private StaffEntityControllerRemote SEC;
    private MemberEntityControllerRemote MEC;
    private BookEntityControllerRemote BEC;
    private LendEntityControllerRemote LEC;
    private ReservationEntityControllerRemote REC;   
    private PaymentEntityControllerRemote PEC;
    //modules
    private final LendBookOperation lendBookOps;
    private final ViewLentBooksOperation viewLentBooksOps;
    private final ReturnBookOperation returnBookOps;   
    private final ExtendBookOperation extendBookOps;
    private final PayFinesOperation payFinesOps;
    private final SearchBookOperation searchBookOps;
    private final ReserveBookOperation reserveBookOps;   
    private LoginOperation loginOpsIn;
    //fields
    private int input;
    private Member member;

    public MemberMenuModule(StaffEntityControllerRemote SEC, MemberEntityControllerRemote MEC, BookEntityControllerRemote BEC, 
            LendEntityControllerRemote LEC, ReservationEntityControllerRemote REC, PaymentEntityControllerRemote PEC) {
        this.SEC = SEC;
        this.MEC = MEC;
        this.BEC = BEC;
        this.LEC = LEC;
        this.REC = REC;
        lendBookOps = new LendBookOperation(SEC, MEC, BEC, LEC);   
        viewLentBooksOps = new ViewLentBooksOperation(SEC, MEC, BEC, LEC);   
        returnBookOps = new ReturnBookOperation(SEC, MEC, BEC, LEC); 
        extendBookOps = new ExtendBookOperation(SEC, MEC, BEC, LEC); 
        payFinesOps = new PayFinesOperation(SEC, MEC, BEC, LEC, PEC);
        searchBookOps = new SearchBookOperation(SEC, MEC, BEC, LEC);
        reserveBookOps = new ReserveBookOperation(SEC, MEC, BEC, LEC, REC);          
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
        
        setField(member);
        
        setBackInstance();
        navigate(this.input);
    }

    private void navigate(int input){
        switch (input) {
            case 1:
                lendBookOps.start();
            case 2:
                viewLentBooksOps.start();
            case 3:              
                returnBookOps.start();
            case 4:
                extendBookOps.start();
            case 5:
                payFinesOps.start();      
            case 6:
                searchBookOps.start();  
            case 7:
                reserveBookOps.start();                      
            case 8:
                loginOpsIn.getSSKRootModIn().startRoot();
        }
    }

    private void setBackInstance() {
        lendBookOps.setMemberMenuModIn(this);     
        viewLentBooksOps.setMemberMenuModIn(this);
        returnBookOps.setMemberMenuModIn(this);  
        extendBookOps.setMemberMenuModIn(this);     
        payFinesOps.setMemberMenuModIn(this);  
        searchBookOps.setMemberMenuModIn(this);
        reserveBookOps.setMemberMenuModIn(this);
    }
    
    private void setField(Member member) {
        this.lendBookOps.setMember(member);
        this.viewLentBooksOps.setMember(member);
        this.returnBookOps.setMember(member);
        this.extendBookOps.setMember(member);
        this.payFinesOps.setMember(member);
        this.searchBookOps.setMember(member);    
        this.reserveBookOps.setMember(member);   
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