package bookOperationModule;

import java.util.List;
import java.util.Scanner;
import model.Book;
import session.stateless.remote.BookEntityControllerRemote;
import session.stateless.remote.LendEntityControllerRemote;
import session.stateless.remote.MemberEntityControllerRemote;
import session.stateless.remote.StaffEntityControllerRemote;
import util.exception.BookNotFoundException;

public class DeleteBookOperation {

    private Scanner sc = new Scanner(System.in);

    // API
    private StaffEntityControllerRemote SEC;
    private MemberEntityControllerRemote MEC;
    private BookEntityControllerRemote BEC;
    private LendEntityControllerRemote LEC;
    
    // Modules
    private BookManagementModule bookManageModIn;
    
    // Dependecies
    private ViewAllBooksOperation viewAllBooksOps;

    // Fields
    private List<Book> bookList;
    private Long bookID;

    public DeleteBookOperation(StaffEntityControllerRemote SEC, MemberEntityControllerRemote MEC, BookEntityControllerRemote BEC, LendEntityControllerRemote LEC) {
        this.SEC = SEC;
        this.MEC = MEC;
        this.BEC = BEC;
        this.LEC = LEC;
    }

    private void displayMenu() {
        System.out.println("*** ILS :: Administration Operation :: Book Management :: Delete Book ***\n");
    }

    private boolean executeViewOperation() {
        viewAllBooksOps = new ViewAllBooksOperation(SEC, MEC, BEC, LEC);
        return viewAllBooksOps.displayAllBooks();
    }

    private void transferRequiredFields() {
        this.bookList = viewAllBooksOps.getBookList();
    }

    private void getInput() {
        System.out.print("Enter Book ID to delete> ");
        this.bookID = sc.nextLong();
    }

    public void start() {
        displayMenu();
        if (!executeViewOperation()) {
            onOperationFailNavigate();
        }
        transferRequiredFields();
        
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
        System.out.println("Book has been deleted successfully!");
    }

    private boolean executeOperation() {
        boolean result = false;
        try {
            BEC.deleteBook(this.bookID);
            return true;
        } catch (BookNotFoundException ex) {
            System.err.println(ex.getMessage());
        }
        return result;
    }

    private void onOperationSuccessNavigate() {
        this.bookManageModIn.start();
    }

    private void onOperationFailNavigate() {
        start();
    }

    // Setter
    public void setBookManageModIn(BookManagementModule bookManageModIn) {
        this.bookManageModIn = bookManageModIn;
    }
}