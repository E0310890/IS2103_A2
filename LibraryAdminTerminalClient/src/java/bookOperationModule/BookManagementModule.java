package bookOperationModule;

import adminOperationModule.AdminModule;
import java.util.Scanner;
import session.stateless.remote.BookEntityControllerRemote;
import session.stateless.remote.LendEntityControllerRemote;
import session.stateless.remote.MemberEntityControllerRemote;
import session.stateless.remote.StaffEntityControllerRemote;

public class BookManagementModule {

    private final Scanner sc = new Scanner(System.in);

    // API
    private StaffEntityControllerRemote SEC;
    private MemberEntityControllerRemote MEC;
    private BookEntityControllerRemote BEC;
    private LendEntityControllerRemote LEC;
    
    // Modules
    private AddBookOperation addBookOps;
    private ViewBookDetailsOperation viewBookOps;
    private UpdateBookOperation updateBookOps;
    private DeleteBookOperation deleteBookOps;
    private ViewAllBooksOperation viewAllBookOps;
    private AdminModule adminModIn;    

    // Fields
    private int input;

    public BookManagementModule(StaffEntityControllerRemote SEC, MemberEntityControllerRemote MEC, BookEntityControllerRemote BEC, LendEntityControllerRemote LEC) {
        this.SEC = SEC;
        this.MEC = MEC;
        this.BEC = BEC;
        this.LEC = LEC;
        addBookOps = new AddBookOperation(SEC, MEC, BEC, LEC);
        viewBookOps = new ViewBookDetailsOperation(SEC, MEC, BEC, LEC);
        updateBookOps = new UpdateBookOperation(SEC, MEC, BEC, LEC);
        deleteBookOps = new DeleteBookOperation(SEC, MEC, BEC, LEC);
        viewAllBookOps = new ViewAllBooksOperation(SEC, MEC, BEC, LEC);
    }

    private void displayMenu() {
        System.out.println("*** ILS :: Library Operation ***");
        System.out.println();
        System.out.println(
                "1: Add Book\n"
                + "2: View Book Details\n"
                + "3: Update Book\n"
                + "4: Delete Book\n"
                + "5: View All Books\n"
                + "6: Back");

        System.out.print("\n>");
    }

    private void getInput() {
        this.input = sc.nextInt();
    }

    public void start() {
        displayMenu();
        getInput();

        setBackInstance();
        navigate(this.input);
    }

    private void navigate(int input) {
        switch (input) {
            case 1:
                addBookOps.start();
            case 2:
                viewBookOps.start();
            case 3:
                updateBookOps.start();
            case 4:
                deleteBookOps.start();
            case 5:
                viewAllBookOps.start();
            case 6:
                adminModIn.start();
        }
    }

    private void setBackInstance() {
        addBookOps.setBookManageModIn(this);
        viewBookOps.setBookManageModIn(this);
        updateBookOps.setBookManageModIn(this);
        deleteBookOps.setBookManageModIn(this);
        viewAllBookOps.setBookManageModIn(this);
    }

    // Setter
    public void setAdminModIn(AdminModule adminModIn) {
        this.adminModIn = adminModIn;
    }
}