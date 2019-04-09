/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MemberOperationModule;

import java.util.Date;
import java.util.List;
import java.util.Scanner;
import javax.xml.datatype.XMLGregorianCalendar;
import session.stateless.BookOverDueException_Exception;
import session.stateless.FineNotPaidException_Exception;
import session.stateless.LendNotFoundException_Exception;
import session.stateless.Lendws;
import session.stateless.Member;
import session.stateless.MemberNotFoundException_Exception;
import session.stateless.ReservedByOthersException_Exception;

/**
 *
 * @author lester
 */
public class ExtendBookOperation {
    
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
    private XMLGregorianCalendar dueDate;
    
    public ExtendBookOperation() {
    }
    
    private void displayMenu() {
        System.out.println("*** BDM Client :: Extend Book ***\n");
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
        System.out.println("Enter Book to Extend> ");
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
        System.out.println("Book successfully extended. New due date: " + dueDate.toString().substring(0, 10));
    }
    
    private boolean executeOperation() {
        boolean result = false;
        try {
            this.dueDate = extendLendBook(this.member.getIdentityNumber(), this.bookId);
            return true;
        } catch (LendNotFoundException_Exception | ReservedByOthersException_Exception | BookOverDueException_Exception | FineNotPaidException_Exception | MemberNotFoundException_Exception ex) {
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
    
    private static XMLGregorianCalendar extendLendBook(java.lang.String arg0, java.lang.Long arg1) throws LendNotFoundException_Exception, ReservedByOthersException_Exception, BookOverDueException_Exception, FineNotPaidException_Exception, MemberNotFoundException_Exception {
        session.stateless.LendService service = new session.stateless.LendService();
        session.stateless.LendEntityController port = service.getLendEntityControllerPort();
        return port.extendLendBook(arg0, arg1);
    }
    
}
