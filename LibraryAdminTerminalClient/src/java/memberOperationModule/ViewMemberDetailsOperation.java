/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package memberOperationModule;

import java.util.List;
import java.util.Scanner;
import libraryOperationModule.ViewLentBooksOperation;
import model.Member;
import session.stateless.remote.BookEntityControllerRemote;
import session.stateless.remote.LendEntityControllerRemote;
import session.stateless.remote.MemberEntityControllerRemote;
import session.stateless.remote.StaffEntityControllerRemote;
import util.exception.MemberNotFoundException;

/**
 *
 * @author lester
 */
public class ViewMemberDetailsOperation {


    private Scanner sc = new Scanner(System.in);

    //API
    private StaffEntityControllerRemote SEC;
    private MemberEntityControllerRemote MEC;
    private BookEntityControllerRemote BEC;
    private LendEntityControllerRemote LEC;
    //modules
    private MemberManagementModule memManageModIn;
    //Dependecies
    private ViewAllMembersOperation vam;

    //fields
    private List<Member> memberList;
    private Long id;
    private Member member;

    public ViewMemberDetailsOperation(StaffEntityControllerRemote SEC, MemberEntityControllerRemote MEC, BookEntityControllerRemote BEC, LendEntityControllerRemote LEC) {
        this.SEC = SEC;
        this.MEC = MEC;
        this.BEC = BEC;
        this.LEC = LEC;
    }

    private void displayMenu() {
        System.out.println("*** ILS :: Administration Operation :: Member Management :: View Member Details ***\n");
    }

    private boolean executeViewOperation() {
        vam = new ViewAllMembersOperation(SEC, MEC, BEC, LEC);
        return vam.displayAllMembers();
    }

    private void transferRequiredFields() {
        this.memberList = vam.getMemberList();
    }

    private void getInput() {
        System.out.println("Enter member Id to View Details> ");
        this.id = sc.nextLong();
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
        System.out.println(member.toString());
    }

    private boolean executeOperation() {
        boolean result = false;
        try {
            this.member = MEC.viewMember(this.id);
            return true;
        } catch (MemberNotFoundException ex) {
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
