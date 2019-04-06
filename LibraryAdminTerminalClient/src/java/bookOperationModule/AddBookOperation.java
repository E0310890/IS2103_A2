package bookOperationModule;

import java.util.Scanner;
import model.Book;
import session.stateless.remote.BookEntityControllerRemote;

public class AddBookOperation {
    
    private Scanner sc = new Scanner(System.in);
    //API
    private BookEntityControllerRemote BEC;
    //modules
    private BookManagementModule bookManagementModule;

    //fields
    private Book book;

    public AddBookOperation(BookEntityControllerRemote BEC) {
        this.BEC = BEC;
    }

    private void displayMenu() {
        System.out.println("*** ILS :: Administration Operation :: Book Management :: Add Book***\n");
    }

    private void getInput() {
        System.out.println("Enter ISBN> ");
        String isbn = sc.nextLine();
        
        System.out.println("Enter Title> ");
        String title = sc.nextLine();
        
        System.out.println("Enter Year> ");
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
        System.out.println("Staff has been registered successfully!");
    }

    private boolean executeOperation() {
        boolean result = false;
        try {
            this.book = BEC.createBook(this.book);
            result = true;
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
        return result;
    }

    private void onOperationSuccessNavigate(){
        try{
            Thread.sleep(1000);
        }catch(InterruptedException ex){
        }
        this.bookManagementModule.start();
    }

    private void onOperationFailNavigate() {
         try{
            Thread.sleep(1000);
        }catch(InterruptedException ex){
        }
        this.bookManagementModule.start();
    }

    //    Settter ..........
    public void setBookManagementModIn(BookManagementModule bookManagementModule) {
        this.bookManagementModule = bookManagementModule;
    }
}
