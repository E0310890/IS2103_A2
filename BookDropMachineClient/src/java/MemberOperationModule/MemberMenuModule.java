/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MemberOperationModule;

import LoginOperationModule.LoginOperation;
import java.util.Scanner;
import session.stateless.Member;

/**
 *
 * @author lester
 */
public class MemberMenuModule {

    private final Scanner sc = new Scanner(System.in);

    //API
    //modules
    private LoginOperation loginOpsIn;
    private ViewLendBookOperation viewLBops;
    private ReturnBookOperation returnBookOps;
    private ExtendBookOperation extendBookOps;
    private PayFineOperation payFineOps;
    private ReserveBookOperation reserveOps;

    //fields
    private Member member;
    private int input;

    public MemberMenuModule() {
        viewLBops = new ViewLendBookOperation();
        returnBookOps = new ReturnBookOperation();
        extendBookOps = new ExtendBookOperation();
        payFineOps = new PayFineOperation();
        reserveOps = new ReserveBookOperation();
    }

    private void displayMenu() {
        System.out.println("\n*** BDM Client :: Main ***");
        System.out.println("You are login as " + member.getFirstName() + " " + member.getLastName());
        System.out.println();
        System.out.println(
                "1: View Lent Books\n"
                + "2: Return Book\n"
                + "3: Extend Book\n"
                + "4: Pay Fines\n"
                + "5: Reserve Book\n"
                + "6: Logout");

        System.out.print("\n>");
    }

    private void getInput() {
        while (true) {
            try {
                this.input = sc.nextInt();
                break;
            } catch (Exception ex) {
                System.err.println("please type a valid input");
            }
        }
    }

    public void start() {
        displayMenu();
        getInput();

        setBackInstance();
        setField(member);
        navigate(this.input);
    }

    private void setField(Member member) {
        this.viewLBops.setMember(member);
        this.returnBookOps.setMember(member);
        this.extendBookOps.setMember(member);
        this.payFineOps.setMember(member);
        this.reserveOps.setMember(member);
    }

    private void navigate(int input) {
        switch (input) {
            case 1:
                viewLBops.start();
            case 2:
                returnBookOps.start();
            case 3:
                extendBookOps.start();
            case 4:
                payFineOps.start();
            case 5:
                reserveOps.start();
            case 6:
                loginOpsIn.getLoginModIn().startRoot();
        }
    }

    private void setBackInstance() {
        viewLBops.setMemMenuModIn(this);
        returnBookOps.setMemMenuModIn(this);
        extendBookOps.setMemMenuModIn(this);
        payFineOps.setMemMenuModIn(this);
        reserveOps.setMemMenuModIn(this);
    }

    //    Settter ..........
    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public LoginOperation getLoginOpsIn() {
        return loginOpsIn;
    }

    public void setLoginOpsIn(LoginOperation loginOpsIn) {
        this.loginOpsIn = loginOpsIn;
    }

}
