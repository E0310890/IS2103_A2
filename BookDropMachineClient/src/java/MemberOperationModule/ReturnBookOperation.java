/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MemberOperationModule;

import java.util.List;
import java.util.Scanner;
import session.stateless.LendNotFoundException_Exception;
import session.stateless.Lendws;
import session.stateless.Member;
import session.stateless.MemberNotFoundException_Exception;

/**
 *
 * @author lester
 */
public class ReturnBookOperation {

    private Scanner sc = new Scanner(System.in);

    //API

    //modules
    private MemberMenuModule memMenuModIn;
    //Dependecies
    private ViewLendBookOperation vlb;

    //fields
    private Member member;
    private Long bookId;
    private List<Lendws> lendList;

    public ReturnBookOperation() {
    }


    private void displayMenu() {
        System.out.println("*** BDM Client :: Return Book ***\n");
    }

    private boolean executeViewOperation() {
        vlb = new ViewLendBookOperation();
        vlb.setMember(member);
        return vlb.viewLendBooksDisplay();
    }

    private void transferRequiredFields() {
        this.lendList = vlb.getLendList();
    }

    private void getInput() {
        System.out.println("Enter Book to Return> ");
        this.bookId = sc.nextLong();
    }

    public void start() {
        displayMenu();
        if (!executeViewOperation()) {
            onOperationFailNavigate();
        }
        transferRequiredFields();

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
        System.out.println("Book successfully returned.");
    }

    private boolean executeOperation() {
        boolean result = false;
        try {
            result = returnLendBook(this.member.getIdentityNumber(), this.bookId);
        } catch (MemberNotFoundException_Exception| LendNotFoundException_Exception ex) {
            System.err.println(ex.getMessage());
        }
        return result;
    }

    private void onOperationSuccessNavigate() {
        this.memMenuModIn.start();
    }

    private void onOperationFailNavigate() {
        this.memMenuModIn.start();
    }

    // Setter

    public MemberMenuModule getMemMenuModIn() {
        return memMenuModIn;
    }

    public void setMemMenuModIn(MemberMenuModule memMenuModIn) {
        this.memMenuModIn = memMenuModIn;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    private static boolean returnLendBook(java.lang.String arg0, java.lang.Long arg1) throws MemberNotFoundException_Exception, LendNotFoundException_Exception {
        session.stateless.LendService service = new session.stateless.LendService();
        session.stateless.LendEntityController port = service.getLendEntityControllerPort();
        return port.returnLendBook(arg0, arg1);
    }
    
    


}
