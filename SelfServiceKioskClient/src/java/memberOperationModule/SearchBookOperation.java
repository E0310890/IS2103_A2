package memberOperationModule;

import java.util.List;
import java.util.Scanner;
import memberOperationModule.MemberMenuModule;
import model.Book;
import model.Lend;
import model.Member;
import services.Helper;
import session.stateless.remote.BookEntityControllerRemote;
import session.stateless.remote.LendEntityControllerRemote;
import session.stateless.remote.MemberEntityControllerRemote;
import session.stateless.remote.StaffEntityControllerRemote;
import util.exception.BookNotFoundException;
import util.exception.MemberNotFoundException;

public class SearchBookOperation {

    private Scanner sc = new Scanner(System.in);

    //API
    private StaffEntityControllerRemote SEC;
    private MemberEntityControllerRemote MEC;
    private BookEntityControllerRemote BEC;
    private LendEntityControllerRemote LEC;

    //modules
    private MemberMenuModule MemberMenuModIn;

    //fields
    private String title;
    private Member member;
    private List<Book> bookList;

    public SearchBookOperation(StaffEntityControllerRemote SEC, MemberEntityControllerRemote MEC, BookEntityControllerRemote BEC, LendEntityControllerRemote LEC) {
        this.SEC = SEC;
        this.MEC = MEC;
        this.BEC = BEC;
        this.LEC = LEC;
    }

    private void displayMenu() {
        System.out.println("*** Self-Service Kiosk :: Search Book ***\n");
    }

    private void getInput() {
        System.out.println("Enter Title to Search> \n");
        this.title = sc.nextLine();
    }

    public void start() {
        displayMenu();
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
        Helper.displayBook(this.bookList);
    }

    private boolean executeOperation() {
        boolean result = false;

        try {
            this.bookList = BEC.searchBook(this.title, this.member);
            return true;
        } catch (BookNotFoundException ex) {
            System.err.println(ex.getMessage());
        }
        return result;
    }

    private void onOperationSuccessNavigate() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
        }
        this.MemberMenuModIn.start();
    }

    private void onOperationFailNavigate() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
        }
        this.MemberMenuModIn.start();
    }

    public boolean displayAllMembers() {
        getInput();
        boolean executeSuccess = executeOperation();
        if (executeSuccess) {
            successDisplay();
            return true;
        } else {
            return false;
        }
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

    public void setTitle(String title) {
        this.title = title;
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

}
