package bookOperationModule;

import java.util.Scanner;
import model.Book;
import session.stateless.remote.BookEntityControllerRemote;
import session.stateless.remote.LendEntityControllerRemote;
import session.stateless.remote.MemberEntityControllerRemote;
import session.stateless.remote.StaffEntityControllerRemote;
import util.exception.InvalidInputException;

public class AddBookOperation {

    private Scanner sc = new Scanner(System.in);

    // API
    private StaffEntityControllerRemote SEC;
    private MemberEntityControllerRemote MEC;
    private BookEntityControllerRemote BEC;
    private LendEntityControllerRemote LEC;
    
    // Modules
    private BookManagementModule bookManageModIn;

    // Fields
    private Book book;

    public AddBookOperation(StaffEntityControllerRemote SEC, MemberEntityControllerRemote MEC, BookEntityControllerRemote BEC, LendEntityControllerRemote LEC) {
        this.SEC = SEC;
        this.MEC = MEC;
        this.BEC = BEC;
        this.LEC = LEC;
    }

    private void displayMenu() {
        System.out.println("*** ILS :: Administration Operation :: Book Management :: Add Book ***\n");
    }

    private void getInput() {
        System.out.print("Enter ISBN> ");
        String isbn = sc.nextLine();
        
        System.out.print("Enter Title> ");
        String title = sc.nextLine();
        
        System.out.print("Enter Year> ");
        String year = sc.nextLine();
                
        this.book = new Book(isbn, title, year);
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
        System.out.println("Book has been registered successfully!");
    }

    private boolean executeOperation() {
        boolean result = false;
        try {
            result = BEC.registerBook(this.book);
        } catch (InvalidInputException ex) {
            System.err.println(ex.getMessage());
        }
        return result;
    }

    private void onOperationSuccessNavigate(){
        try{
            Thread.sleep(1000);
        }catch(InterruptedException ex){
        }
        this.bookManageModIn.start();
    }

    private void onOperationFailNavigate() {
         try{
            Thread.sleep(1000);
        }catch(InterruptedException ex){
        }
        this.bookManageModIn.start();
    }

    // Setter
    public void setBookManageModIn(BookManagementModule bookManageModIn) {
        this.bookManageModIn = bookManageModIn;
    }
}