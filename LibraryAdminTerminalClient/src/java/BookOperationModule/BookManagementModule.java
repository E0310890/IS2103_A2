package BookOperationModule;

import adminOperationModule.AdminModule;
import java.util.Scanner;
import session.stateless.remote.BookEntityControllerRemote;
import session.stateless.remote.LendEntityControllerRemote;
import session.stateless.remote.MemberEntityControllerRemote;
import session.stateless.remote.StaffEntityControllerRemote;

public class BookManagementModule {

    private final Scanner sc = new Scanner(System.in);

    //API
    private StaffEntityControllerRemote SEC;
    private MemberEntityControllerRemote MEC;
    private BookEntityControllerRemote BEC;
    private LendEntityControllerRemote LEC;
    //modules
    private AdminModule adminModIn;
    //fields
    private int input;

    public BookManagementModule(StaffEntityControllerRemote SEC, MemberEntityControllerRemote MEC, BookEntityControllerRemote BEC, LendEntityControllerRemote LEC) {
        this.SEC = SEC;
        this.MEC = MEC;
        this.BEC = BEC;
        this.LEC = LEC;
    }

    private void displayMenu() {
        System.out.println("*** ILS :: Administration Operation :: Book Management ***");
        System.out.println();
        System.out.println(
                "1: Add Book\n"
                + "2: View Book Details\n"
                + "3: Update Book\n"
                + "4: Delete Book\n"
                + "5: View All Book\n"
                + "6: Back");

        System.out.print("\n>");
    }

    private void getInput() {
        this.input = sc.nextInt();
    }

    public void start() throws InterruptedException {
        displayMenu();
        getInput();

        setBackInstance();
        navigate(this.input);
    }

    private void navigate(int input) throws InterruptedException {
        switch (input) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
                adminModIn.start();

        }

    }

    private void setBackInstance() {
    }

//    Settter ..........
    
    public void setAdminModIn(AdminModule adminModIn) {
        this.adminModIn = adminModIn;
    }
}
