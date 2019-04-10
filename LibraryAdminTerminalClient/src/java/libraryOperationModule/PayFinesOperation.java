package libraryOperationModule;

import java.util.List;
import java.util.Scanner;
import model.Fine;
import services.Helper;
import session.stateless.remote.BookEntityControllerRemote;
import session.stateless.remote.LendEntityControllerRemote;
import session.stateless.remote.MemberEntityControllerRemote;
import session.stateless.remote.PaymentEntityControllerRemote;
import session.stateless.remote.StaffEntityControllerRemote;
import util.exception.FineNotFoundException;
import util.exception.LendNotFoundException;
import util.exception.MemberNotFoundException;

public class PayFinesOperation {

    private Scanner sc = new Scanner(System.in);

    // API
    private StaffEntityControllerRemote SEC;
    private MemberEntityControllerRemote MEC;
    private BookEntityControllerRemote BEC;
    private LendEntityControllerRemote LEC;
    private PaymentEntityControllerRemote PEC;
    
    // Modules
    private LibraryModule LibModIn;

    // Fields
    private String identityNumber;
    private List<Fine> fineList;
    private Long fineId;

    public PayFinesOperation(StaffEntityControllerRemote SEC, MemberEntityControllerRemote MEC, BookEntityControllerRemote BEC, LendEntityControllerRemote LEC, PaymentEntityControllerRemote PEC) {
        this.SEC = SEC;
        this.MEC = MEC;
        this.BEC = BEC;
        this.LEC = LEC;
        this.PEC = PEC;
    }

    private void displayMenu() {
        System.out.println("*** ILS :: Library Operation :: Pay Fines ***\n");
        System.out.println("Enter Member Identity Number> ");
        this.identityNumber = sc.next();
    }

    private boolean executeViewOperation() {
        boolean result = false;
        try {
            fineList = PEC.viewFine(identityNumber);
            if(fineList.isEmpty()){
                System.err.println("== There is no OutStanding fine ==");
                return false;
            }
            Helper.displayFine(fineList);
            result = true;
        } catch (MemberNotFoundException ex) {
            System.err.println(ex.getMessage());
        }
        return result;
    }

    private void getInput() {

        System.out.println("Enter Fine to Settle\n");
        fineId = sc.nextLong();
        System.out.println("Select Payment Method(1: Cash, 2: Card)\n");
        sc.next();
    }

    public void start() {
        displayMenu();
        if (!executeViewOperation()) {
            onOperationFailNavigate();
        }

        getInput();

        boolean executeSuccess = executeOperation();
        if (executeSuccess) {
            successDisplay();
            onOperationSuccessNavigate();
        } else {
            onOperationFailNavigate();
        }
    }

    private void successDisplay() {
        System.out.println("Fine successfully paid.");
    }

    private boolean executeOperation() {
        boolean result = false;
        try {
            result = PEC.payFine(identityNumber, fineId);
            result = true;
        } catch (MemberNotFoundException | LendNotFoundException | FineNotFoundException ex) {
            System.err.println(ex.getMessage());
        }
        return result;
    }

    private void onOperationSuccessNavigate() {
        this.LibModIn.start();
    }

    private void onOperationFailNavigate() {
        this.LibModIn.start();
    }

    // Setter
    public void setLibModIn(LibraryModule LibModIn) {
        this.LibModIn = LibModIn;
    }
}
