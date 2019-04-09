/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MemberOperationModule;

import java.util.List;
import java.util.Scanner;
import session.stateless.Fine;
import session.stateless.FineNotFoundException_Exception;
import session.stateless.Member;
import session.stateless.MemberNotFoundException_Exception;

/**
 *
 * @author lester
 */
public class PayFineOperation {

    private Scanner sc = new Scanner(System.in);

    //API
    //modules
    private MemberMenuModule memMenuModIn;

    //fields
    private Member member;
    private Long fineId;
    private List<Fine> fineList;

    public PayFineOperation() {
    }

    private void displayMenu() {
        System.out.println("*** BDM Client :: Pay Fines ***\n");
        System.out.println("Unpaid Fines for Member: ");
    }

    private boolean executeViewOperation() {
        boolean result = false;
        try {
            fineList = viewFine(this.member.getIdentityNumber());
            if (fineList.isEmpty()) {
                System.err.println("== There is no OutStanding fine ==");
                return false;
            }
            displayFine(fineList);
            result = true;
        } catch (MemberNotFoundException_Exception ex) {
            System.err.println(ex.getMessage());
        }
        return result;
    }

    private void getInput() {

        System.out.print("Enter Fine to Settle> ");
        fineId = sc.nextLong();
        System.out.print("\nEnter Card Number> ");
        sc.next();
        System.out.print("\nEnter Card Expiry (MMYYYY)> ");
        sc.next();
        System.out.print("\nEnter Pin> ");
        String pin = sc.next();
        
        if(!member.getSecurityCode().equals(pin)){
            System.err.println("Wrong security code");
            onOperationFailNavigate();
        }
    }

    public void start() {
        displayMenu();
        if (!executeViewOperation()) {
            onOperationFailNavigate();
        }

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
        System.out.println("Fine successfully paid.");
    }

    private boolean executeOperation() {
        boolean result = false;
        try {
            result = payFine(this.member.getIdentityNumber(), fineId);
            result = true;
        } catch (FineNotFoundException_Exception | MemberNotFoundException_Exception ex) {
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

    //    Settter ..........
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

    public void displayFine(List<Fine> fineList) {
        if (fineList.isEmpty()) {
            return;
        }

        System.out.println("Id | Amount");
        String[] idSpacing = new String[]{" ", "  "};

        for (Fine f : fineList) {
            String fineId = f.getLendID().toString();
            if (f.getLendID() < 10) {
                fineId += idSpacing[1];
            } else if (f.getLendID() < 100) {
                fineId += idSpacing[0];
            }
            System.out.println(
                    fineId + "| $"
                    + f.getFineAmount());
        }

    }

    private static java.util.List<session.stateless.Fine> viewFine(java.lang.String arg0) throws MemberNotFoundException_Exception {
        session.stateless.PaymentService service = new session.stateless.PaymentService();
        session.stateless.PaymentEntityController port = service.getPaymentEntityControllerPort();
        return port.viewFine(arg0);
    }

    private static boolean payFine(java.lang.String arg0, java.lang.Long arg1) throws FineNotFoundException_Exception, MemberNotFoundException_Exception {
        session.stateless.PaymentService service = new session.stateless.PaymentService();
        session.stateless.PaymentEntityController port = service.getPaymentEntityControllerPort();
        return port.payFine(arg0, arg1);
    }
    
    

}
