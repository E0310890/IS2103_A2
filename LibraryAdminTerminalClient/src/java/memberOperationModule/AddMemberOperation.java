/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package memberOperationModule;

import java.util.Scanner;
import model.Member;
import model.Staff;
import session.stateless.remote.BookEntityControllerRemote;
import session.stateless.remote.LendEntityControllerRemote;
import session.stateless.remote.MemberEntityControllerRemote;
import session.stateless.remote.StaffEntityControllerRemote;
import util.enumeration.Gender;
import util.exception.InvalidInputException;

/**
 *
 * @author lester
 */
public class AddMemberOperation {

    private Scanner sc = new Scanner(System.in);

    //API
    private StaffEntityControllerRemote SEC;
    private MemberEntityControllerRemote MEC;
    private BookEntityControllerRemote BEC;
    private LendEntityControllerRemote LEC;
    //modules
    private MemberManagementModule memManageModIn;

    //fields
    private Member member;

    public AddMemberOperation(StaffEntityControllerRemote SEC, MemberEntityControllerRemote MEC, BookEntityControllerRemote BEC, LendEntityControllerRemote LEC) {
        this.SEC = SEC;
        this.MEC = MEC;
        this.BEC = BEC;
        this.LEC = LEC;
    }

    private void displayMenu() {
        System.out.println("*** ILS :: Administration Operation :: Member Management :: Add Member***\n");
    }

    private void getInput() {
        System.out.print("Enter Identity Number> ");
        String identityNum = sc.next();
        System.out.print("Enter Security Code> ");
        String secCode = sc.next();
        System.out.print("Enter First Name> ");
        String firstName = sc.next();
        System.out.print("Enter Last Name> ");
        String lastName = sc.next();
        System.out.print("Enter Gender> ");
        String gen = sc.next();
        System.out.print("Enter Age> ");
        int age = sc.nextInt();
        System.out.print("Enter Phone> ");
        String phone = sc.next();
        System.out.print("Enter Address> ");
        String address = sc.next();

        Gender gender = Gender.getEnumGender(gen);

        this.member = new Member(identityNum, firstName, lastName, gender, age, phone, address, secCode);
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
        System.out.println("Member has been registered successfully!");
    }

    private boolean executeOperation() {
        boolean result = false;
        try {
            result = MEC.registerMember(this.member);
        } catch (InvalidInputException ex) {
            System.err.println(ex.getMessage());
        }
        return result;
    }

    private void onOperationSuccessNavigate() {
        this.memManageModIn.start();
    }

    private void onOperationFailNavigate() {
        start();
    }

    //    Settter ..........

    public void setMemManageModIn(MemberManagementModule memManageModIn) {
        this.memManageModIn = memManageModIn;
    }


}
