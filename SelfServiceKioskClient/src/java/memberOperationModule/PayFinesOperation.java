package memberOperationModule;

import java.util.List;
import java.util.Scanner;
import model.Fine;
import model.Member;
import services.Helper;
import session.stateless.remote.BookEntityControllerRemote;
import session.stateless.remote.LendEntityControllerRemote;
import session.stateless.remote.MemberEntityControllerRemote;
import session.stateless.remote.PaymentEntityControllerRemote;
import session.stateless.remote.StaffEntityControllerRemote;
import util.exception.InvalidInputException;
import util.exception.MemberNotFoundException;

public class PayFinesOperation {

    private Scanner sc = new Scanner(System.in);

    //API
    private StaffEntityControllerRemote SEC;
    private MemberEntityControllerRemote MEC;
    private BookEntityControllerRemote BEC;
    private LendEntityControllerRemote LEC;
    private PaymentEntityControllerRemote PEC;
    //modules
    private MemberMenuModule MemberMenuModIn;

    //fields
    public Member member;
    private List<Fine> fineList;
    private long input;
    
    public PayFinesOperation(StaffEntityControllerRemote SEC, MemberEntityControllerRemote MEC, BookEntityControllerRemote BEC, LendEntityControllerRemote LEC, PaymentEntityControllerRemote PEC) {
        this.SEC = SEC;
        this.MEC = MEC;
        this.BEC = BEC;
        this.LEC = LEC;
        this.PEC = PEC;
    }

    private void displayMenu() {
        System.out.println("*** Self-Service Kiosk :: Pay Fines ***\n");
    }

    private void getInput() {
        System.out.print("Enter Fine to Settle> ");
        this.input = sc.nextLong();
        System.out.print("Select Payment Method (1: Cash, 2: Card)> ");
        sc.next();
    }

    public void start() {
        displayMenu();
        displayFineList();
        getInput();

        boolean executeSuccess = executeOperation();
        if (executeSuccess) {
            successDisplay();
            onOperationSuccessNavigate();
        } else {
            onOperationFailNavigate();
        }
    }
    
    private void displayFineList(){
        try {
            this.fineList = PEC.viewFine(this.member);
        } catch (MemberNotFoundException ex) {
            System.err.println(ex.getMessage());
        }
        Helper.displayFine(this.fineList);
    }

    private void successDisplay() {
        System.out.println("Fine successfully paid.");
    }

    private boolean executeOperation() {
        boolean result = false;
        try {
            result = PEC.payFine(this.member, this.input);
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
        return result;
    }

    private void onOperationSuccessNavigate() {
        try{
            Thread.sleep(1000);
        }catch (InterruptedException ex){
        }
        this.MemberMenuModIn.start();
    }

    private void onOperationFailNavigate() {
        try{
            Thread.sleep(1000);
        }catch (InterruptedException ex){
        }
        this.MemberMenuModIn.start();
    }

    //    Settter ..........
    public void setMember(Member member) {
        this.member = member;
    }  
    
    public void setMemberMenuModIn(MemberMenuModule MemberMenuModIn) {
        this.MemberMenuModIn = MemberMenuModIn;
    }

    public MemberMenuModule getMemberMenuOpsIn() {
        return MemberMenuModIn;
    }
}
