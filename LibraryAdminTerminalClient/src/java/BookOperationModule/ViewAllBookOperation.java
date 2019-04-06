package bookOperationModule;

import java.util.List;
import java.util.Scanner;
import model.Book;
import session.stateless.remote.BookEntityControllerRemote;

public class ViewAllBookOperation {
    private Scanner sc = new Scanner(System.in);
    //API
    private BookEntityControllerRemote BEC;
    //modules
    private BookManagementModule bookManagementModule;

    //fields
    private List<Book> bookList;

    public ViewAllBookOperation(BookEntityControllerRemote BEC) {
        this.BEC = BEC;
    }

    private void displayMenu() {
        System.out.println("*** ILS :: Administration Operation :: Book Management :: View All Book***\n");
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
        bookList.forEach(m
                -> System.out.println("ID: " + m.getBookID()
                        + " | ISBN: " + m.getIsbn() + " | Title: " + m.getTitle() + " | Year: " + m.getYear())
        );
    }

    private boolean executeOperation() {
        boolean result = false;
        try {
            this.bookList = BEC.retrieveAll();
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

    public void setMemManageModIn(BookManagementModule bookManagementModule) {
        this.bookManagementModule = bookManagementModule;
    }
    
    public List<Book> getBookList(){
        executeOperation();
        return this.bookList;
    }
}