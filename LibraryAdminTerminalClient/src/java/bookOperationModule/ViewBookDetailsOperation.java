package bookOperationModule;

import java.util.List;
import java.util.Scanner;
import model.Book;
import session.stateless.remote.BookEntityControllerRemote;

public class ViewBookDetailsOperation {
    private Scanner sc = new Scanner(System.in);
    //API
    private BookEntityControllerRemote BEC;
    //modules
    private BookManagementModule bookManagementModule;
     //Dependecies
    private ViewAllBooksOperation viewAllBook;

    //fields
    private List<Book> bookList;
    private long id;
    private Book book;

    public ViewBookDetailsOperation(BookEntityControllerRemote BEC) {
        this.BEC = BEC;
        viewAllBook = new ViewAllBooksOperation(BEC);
    }

    private void displayMenu() {
        System.out.println("*** ILS :: Administration Operation :: Book Management :: View Book Details***\n");
    }

    private void getInput() {
        System.out.println("Enter Book ID to View Details> ");
        this.id = sc.nextLong();
    }

    public void start() {
        displayMenu();
        this.bookList = viewAllBook.getBookList();
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
        System.out.println(book.toString());
    }

    private boolean executeOperation() {
        boolean result = false;
        try {
            BEC.retrieve(this.id);
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