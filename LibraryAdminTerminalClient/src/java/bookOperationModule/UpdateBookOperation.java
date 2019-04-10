package bookOperationModule;

import java.util.List;
import java.util.Scanner;
import model.Book;
import session.stateless.remote.BookEntityControllerRemote;
import session.stateless.remote.LendEntityControllerRemote;
import session.stateless.remote.MemberEntityControllerRemote;
import session.stateless.remote.StaffEntityControllerRemote;
import util.exception.InvalidInputException;

public class UpdateBookOperation {

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
    private List<Book> BookList;
    private Book Book;

    public UpdateBookOperation(StaffEntityControllerRemote SEC, MemberEntityControllerRemote MEC, BookEntityControllerRemote BEC, LendEntityControllerRemote LEC) {
        this.SEC = SEC;
        this.MEC = MEC;
        this.BEC = BEC;
        this.LEC = LEC;
    }

    private void displayMenu() {
        System.out.println("*** ILS :: Administration Operation :: Book Management :: Update Book Details ***\n");
    }

    private boolean executeViewOperation() {
        viewAllBooksOps = new ViewAllBooksOperation(SEC, MEC, BEC, LEC);
        return viewAllBooksOps.displayAllBooks();
    }

    private void transferRequiredFields() {
        this.BookList = viewAllBooksOps.getBookList();
    }

    private void getInput() {
        System.out.println("Enter Book ID of Book to update> ");
        Long BookID = sc.nextLong();

        // For validation, check if the ID choosen is in 'BookList';
        this.Book = BookList.stream()
                .filter(m -> m.getBookID().equals(BookID))
                .findFirst()
                .get();

        updateInput();
    }

    private void updateInput() {
        System.out.println("Select the field to update: \n");
        System.out.println(
                "1: ISBN\n"
                + "2: Title\n"
                + "3: Year"
        );
        
        System.out.print(">");    
        int fieldSelectId = sc.nextInt();

        System.out.print("Update to: ");
        
        String updateTo = sc.next();
        updateTo += sc.nextLine();

        switch (fieldSelectId) {
            case 1:
                this.Book.setIsbn(updateTo);
                break;
            case 2:
                this.Book.setTitle(updateTo);
                break;
            case 3:
                this.Book.setYear(updateTo);
                break;
            default:
                break;
        }
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
        System.out.println("Book have been successfully updated.");
    }

    private boolean executeOperation() {
        boolean result = false;
        try {
            result = BEC.updateBook(Book);
        } catch (InvalidInputException ex) {
            System.err.println(ex.getMessage());
        }
        return result;
    }

    private void onOperationSuccessNavigate() {
        this.bookManageModIn.start();
    }

    private void onOperationFailNavigate() {
        this.bookManageModIn.start();
    }

    // Setter
    public void setBookManageModIn(BookManagementModule bookManageModIn) {
        this.bookManageModIn = bookManageModIn;
    }
}