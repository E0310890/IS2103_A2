/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MemberOperationModule;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Scanner;
import session.stateless.Lendws;
import session.stateless.Member;
import session.stateless.MemberNotFoundException_Exception;

/**
 *
 * @author lester
 */
public class ViewLendBookOperation {

    private Scanner sc = new Scanner(System.in);

    //API
    //modules
    private MemberMenuModule memMenuModIn;

    //fields
    private Member member;
    private List<Lendws> lendList;

    public ViewLendBookOperation() {
    }

    private void displayMenu() {
        System.out.println("*** BDM Client :: View Lent Books ***\n");
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
        displayLendingWs(this.lendList);
    }

    private boolean executeOperation() {
        boolean result = false;
        try {
            this.lendList = viewLendBooksWs(this.member.getIdentityNumber());
            return true;
        } catch (MemberNotFoundException_Exception ex) {
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

    public boolean viewLendBooksDisplay() {
        getInput();

        boolean executeSuccess = executeOperation();
        if (executeSuccess) {
            successDisplay();
            return true;
        } else {
            return false;
        }
    }

    //    Settter ..........
    public void setMember(Member member) {
        this.member = member;
    }

    public void setMemMenuModIn(MemberMenuModule memMenuModIn) {
        this.memMenuModIn = memMenuModIn;
    }

    public List<Lendws> getLendList() {
        return lendList;
    }

    public void setLendList(List<Lendws> lendList) {
        this.lendList = lendList;
    }
    
    

    private static java.util.List<session.stateless.Lendws> viewLendBooksWs(java.lang.String arg0) throws MemberNotFoundException_Exception {
        session.stateless.LendService service = new session.stateless.LendService();
        session.stateless.LendEntityController port = service.getLendEntityControllerPort();
        return port.viewLendBooksWs(arg0);
    }

    private void displayLendingWs(List<Lendws> lendList) {

        if (lendList.isEmpty()) {
            return;
        }

        int maxLength = lendList.stream()
                .mapToInt(l -> l.getBookTitle().length())
                .max().getAsInt();
        String spacing = "";
        for (int i = 0; i < maxLength; i++) {
            spacing += " ";
        }

        String[] idSpacing = new String[]{" ", "  "};

        System.out.println("Currently Lent Books:");
        System.out.println("Id |Title"
                + spacing.substring(3)
                + "| Due Date");

        for (Lendws l : lendList) {
            String lendId = l.getLendId().toString();
            if (l.getLendId() < 10) {
                lendId += idSpacing[1];
            } else if (l.getLendId() < 100) {
                lendId += idSpacing[0];
            }
            int to = (spacing.length() - l.getBookTitle().length()) + 2;
            System.out.println(
                    lendId + "|"
                    + l.getBookTitle()
                    + (spacing.substring(0, to)) + "| "
                    + l.getDueDate().toString().substring(0, 10)
            );
        }
    }

}
