package adminOperationModule;

import BookOperationModule.BookManagementModule;
import memberOperationModule.MemberManagementModule;
import java.util.Scanner;
import rootOperationModule.MainMenuModule;
import session.stateless.remote.BookEntityControllerRemote;
import session.stateless.remote.LendEntityControllerRemote;
import session.stateless.remote.MemberEntityControllerRemote;
import session.stateless.remote.StaffEntityControllerRemote;
import staffOperationModule.StaffManagementModule;
import util.exception.FineNotFoundException;
import util.exception.FineNotPaidException;
import util.exception.MemberNotFoundException;
import util.exception.ReservedByOthersException;

public class AdminModule {
    
    private final Scanner sc = new Scanner(System.in);

    //API
    private StaffEntityControllerRemote SEC;
    private MemberEntityControllerRemote MEC;
    private BookEntityControllerRemote BEC;
    private LendEntityControllerRemote LEC;
    //modules
    private MainMenuModule MainMenuModIn;
    private MemberManagementModule memManageMod;
    private BookManagementModule bookManageMod;
    private StaffManagementModule staffManageMod;
    //fields
    private int input;

    public AdminModule(StaffEntityControllerRemote SEC, MemberEntityControllerRemote MEC, BookEntityControllerRemote BEC, LendEntityControllerRemote LEC) {
        this.SEC = SEC;
        this.MEC = MEC;
        this.BEC = BEC;
        this.LEC = LEC;
        memManageMod = new MemberManagementModule(SEC, MEC, BEC, LEC);
        bookManageMod = new BookManagementModule(SEC, MEC, BEC, LEC);
        staffManageMod = new StaffManagementModule(SEC, MEC, BEC, LEC);
    }

    private void displayMenu() {
        System.out.println("*** ILS :: Administration Operation***");
        System.out.println();
        System.out.println(
                "1: Member Management\n"
                + "2: Book Management\n"
                + "3: Staff Management\n"
                + "4: Back");

        System.out.print("\n>");
    }

    private void getInput() {
        this.input = sc.nextInt();
    }

    public void start() throws InterruptedException, FineNotPaidException, ReservedByOthersException, MemberNotFoundException, FineNotFoundException {
        displayMenu();
        getInput();

        setBackInstance();
        navigate(this.input);
    }

    private void navigate(int input) throws InterruptedException, FineNotPaidException, ReservedByOthersException, MemberNotFoundException, FineNotFoundException {
        switch (input) {
            case 1:
                memManageMod.start();
            case 2:
                bookManageMod.start();
            case 3:
                staffManageMod.start();
            case 4:
                MainMenuModIn.start();
        }

    }

    private void setBackInstance() {
        memManageMod.setAdminModIn(this);
        bookManageMod.setAdminModIn(this);
        staffManageMod.setAdminModIn(this);
    }

//    Settter ..........
    public MainMenuModule getMainMenuModIn() {
        return MainMenuModIn;
    }

    public void setMainMenuModIn(MainMenuModule MainMenuModIn) {
        this.MainMenuModIn = MainMenuModIn;
    }
    
}
