package memberOperationModule;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import memberOperationModule.MemberMenuModule;
import model.Lend;
import model.Member;
import services.Helper;
import session.stateless.remote.BookEntityControllerRemote;
import session.stateless.remote.LendEntityControllerRemote;
import session.stateless.remote.MemberEntityControllerRemote;
import session.stateless.remote.StaffEntityControllerRemote;
import session.stateless.remote.ReservationEntityControllerRemote;
import util.exception.BookNotFoundException;
import util.exception.BookNotLendException;
import util.exception.FineNotPaidException;
import util.exception.LendBySelfException;
import util.exception.MemberNotFoundException;
import util.exception.ReserveBySelfException;

public class ReserveBookOperation {

    private Scanner sc = new Scanner(System.in);

    //API
    private StaffEntityControllerRemote SEC;
    private MemberEntityControllerRemote MEC;
    private BookEntityControllerRemote BEC;
    private LendEntityControllerRemote LEC;
    private ReservationEntityControllerRemote REC;

    //modules
    private MemberMenuModule MemberMenuModIn;

    //Dependencies
    private SearchBookOperation seachBookOps;

    //fields
    public Member member;
    private String identityNumber;
    private Long bookID;
    private List<Lend> lendList;

    public ReserveBookOperation(StaffEntityControllerRemote SEC, MemberEntityControllerRemote MEC, BookEntityControllerRemote BEC, LendEntityControllerRemote LEC, ReservationEntityControllerRemote REC) {
        this.SEC = SEC;
        this.MEC = MEC;
        this.BEC = BEC;
        this.LEC = LEC;
        this.REC = REC;
    }

    private void displayMenu() {
        System.out.println("*** Self-Service Kiosk :: Reserve Book ***\n");
    }

    private boolean executeViewOperation() {
        seachBookOps = new SearchBookOperation(SEC, MEC, BEC, LEC);
        seachBookOps.setMember(member);
        return seachBookOps.displayAllMembers();
    }

    private void transferRequiredFields() {
//        this.memberList = viewAllMembersOps.getMemberList();
    }

    private void getInput() {
        System.out.println("Enter Book ID to Reserve:");
        bookID = sc.nextLong();
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
        System.out.println("Book successfully reserved.");
    }

    private boolean executeOperation() {
        boolean result = false;

        try {
            return REC.reserveBook(this.member, this.bookID);
        } catch (BookNotFoundException | MemberNotFoundException | BookNotLendException | FineNotPaidException | LendBySelfException | ReserveBySelfException ex) {
            System.err.println(ex.getMessage());
        }
        return result;
    }

    private void onOperationSuccessNavigate() {
        this.MemberMenuModIn.start();
    }

    private void onOperationFailNavigate() {
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

    public boolean viewLendBooks() {
        getInput();

        boolean executeSuccess = executeOperation();
        if (executeSuccess) {
            successDisplay();
            return true;
        } else {
            return false;
        }
    }

    public String getIdentityNumber() {
        return identityNumber;
    }
}
