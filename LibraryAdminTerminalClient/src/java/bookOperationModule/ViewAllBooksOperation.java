package bookOperationModule;

import java.util.List;
import java.util.Scanner;
import model.Book;
import session.stateless.remote.BookEntityControllerRemote;
import session.stateless.remote.LendEntityControllerRemote;
import session.stateless.remote.MemberEntityControllerRemote;
import session.stateless.remote.StaffEntityControllerRemote;

public class ViewAllBooksOperation {

    private Scanner sc = new Scanner(System.in);

    // API
    private StaffEntityControllerRemote SEC;
    private MemberEntityControllerRemote MEC;
    private BookEntityControllerRemote BEC;
    private LendEntityControllerRemote LEC;
    
    // Modules
    private BookManagementModule bookManageModIn;

    // Fields
    private List<Book> bookList;

    public ViewAllBooksOperation(StaffEntityControllerRemote SEC, MemberEntityControllerRemote MEC, BookEntityControllerRemote BEC, LendEntityControllerRemote LEC) {
        this.SEC = SEC;
        this.MEC = MEC;
        this.BEC = BEC;
        this.LEC = LEC;
    }

    private void displayMenu() {
        System.out.println("*** ILS :: Administration Operation :: Member Management :: View All Books ***\n");
    }

    private void getInput() {
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
        bookList.forEach(book
                -> System.out.println("Book ID: " + book.getBookID()
                        + " | Title: " + book.getTitle())
        );
    }

    private boolean executeOperation() {
        boolean result = false;
        try {
            this.bookList = BEC.viewBook();
            result = true;
        } catch (Exception ex) {
            result = false;
        }
        return result;
    }

    private void onOperationSuccessNavigate() {
        this.bookManageModIn.start();
    }

    private void onOperationFailNavigate() {
        this.bookManageModIn.start();
    }

    public boolean displayAllBooks() {
        boolean executeSuccess = executeOperation();
        if (executeSuccess) {
            successDisplay();
            return true;
        } else {
            return false;
        }
    }

    // Setter
    public void setBookManageModIn(BookManagementModule bookManageModIn) {
        this.bookManageModIn = bookManageModIn;
    }

    public List<Book> getBookList() {
        return bookList;
    }
}