package rootOperationModule;

import java.util.Scanner;
import model.Member;
import session.stateless.remote.BookEntityControllerRemote;
import session.stateless.remote.LendEntityControllerRemote;
import session.stateless.remote.MemberEntityControllerRemote;
import session.stateless.remote.StaffEntityControllerRemote;
import util.enumeration.Gender;

public class RegistrationOperation {

    private Scanner sc = new Scanner(System.in);

    //API
    private StaffEntityControllerRemote SEC;
    private MemberEntityControllerRemote MEC;
    private BookEntityControllerRemote BEC;
    private LendEntityControllerRemote LEC;
    //modules
    private SSKRootModule sskRootModIn;

    //fields
    private Member member;

    public RegistrationOperation(StaffEntityControllerRemote SEC, MemberEntityControllerRemote MEC, BookEntityControllerRemote BEC, LendEntityControllerRemote LEC) {
        this.SEC = SEC;
        this.MEC = MEC;
        this.BEC = BEC;
        this.LEC = LEC;
    }

    private void displayMenu() {
        System.out.println("*** Self-Service Kiosk :: Register ***\n");
    }

    private void getInput() {
        System.out.print("Enter Identity Number> ");
        String identityNum = sc.nextLine();
        System.out.print("Enter Security Code> ");
        String secCode = sc.nextLine();
        System.out.print("Enter First Name> ");
        String firstName = sc.nextLine();
        System.out.print("Enter Last Name> ");
        String lastName = sc.nextLine();
        System.out.print("Enter Gender> ");
        String gen = sc.nextLine();
        System.out.print("Enter Age> ");
        int age = sc.nextInt();
        sc.nextLine();
        System.out.print("Enter Phone> ");
        String phone = sc.nextLine();
        System.out.print("Enter Address> ");
        String address = sc.nextLine();

        Gender gender = Gender.getEnumGender(gen);

        this.member = new Member(identityNum, firstName, lastName, gender, age, phone, address, secCode);
    }

    public void start(){
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
        System.out.println("Member has been registered successfully!\n");
    }

    private boolean executeOperation() {
        boolean result = false;
        try {
            result = MEC.registerMember(this.member);
        } catch (Exception ex) {
            System.err.println("Member registeration fail. Please Enter correct information.\n");
        }
        return result;
    }

    private void setField(Member member) {
    }

    private void onOperationSuccessNavigate() {
        try{
            Thread.sleep(1000);
        }catch (InterruptedException ex){
        }
        this.sskRootModIn.startRoot();
    }

    private void onOperationFailNavigate() {
        try{
            Thread.sleep(1000);
        }catch (InterruptedException ex){
        }
        this.sskRootModIn.startRoot();
    }

    //    Settter ..........

    public void setSSKRootModIn(SSKRootModule sskRootModIn) {
        this.sskRootModIn = sskRootModIn;
    }
}