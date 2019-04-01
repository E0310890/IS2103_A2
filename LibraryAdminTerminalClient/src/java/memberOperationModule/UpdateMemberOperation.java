package memberOperationModule;

import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Member;
import session.stateless.remote.BookEntityControllerRemote;
import session.stateless.remote.LendEntityControllerRemote;
import session.stateless.remote.MemberEntityControllerRemote;
import session.stateless.remote.StaffEntityControllerRemote;
import util.enumeration.Gender;
import util.exception.InvalidInputException;
import util.exception.MemberNotFoundException;

public class UpdateMemberOperation {

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
    private Member member;

    public UpdateMemberOperation(StaffEntityControllerRemote SEC, MemberEntityControllerRemote MEC, BookEntityControllerRemote BEC, LendEntityControllerRemote LEC) {
        this.SEC = SEC;
        this.MEC = MEC;
        this.BEC = BEC;
        this.LEC = LEC;
    }

    private void displayMenu() {
        System.out.println("*** ILS :: Administration Operation :: Member Management :: Update Member ***\n");
    }

    private boolean executeViewOperation() {
        vam = new ViewAllMembersOperation(SEC, MEC, BEC, LEC);
        return vam.displayAllMembers();
    }

    private void transferRequiredFields() {
        this.memberList = vam.getMemberList();
    }

    private void getInput() {
        System.out.println("Enter member Id of member to Update> ");
        Long id = sc.nextLong();

        //for validation, check if the id choosen is in 'memberList';
        this.member = memberList.stream()
                .filter(m -> m.getMemberID().equals(id))
                .findFirst()
                .get();

        updateInput();
    }

    private void updateInput() {
        System.out.println("Select the field to Update: \n");
        System.out.println(
                "1: identityNumber\n"
                + "2: firstName\n"
                + "3: lastName\n"
                + "4: gender\n"
                + "5: age\n"
                + "6: phone\n"
                + "7: address\n"
                + "8: securityCode"
        );
        System.out.println("> ");
        int fieldSelectId = sc.nextInt();

        System.out.println("Update to: ");

        if (fieldSelectId == 1) {
            this.member.setIdentityNumber(sc.next());
        } else if (fieldSelectId == 2) {
            this.member.setFirstName(sc.next());
        } else if (fieldSelectId == 3) {
            this.member.setLastName(sc.next());
        } else if (fieldSelectId == 4) {
            this.member.setGender(Gender.getEnumGender(sc.next()));
        } else if (fieldSelectId == 5) {
            this.member.setAge(sc.nextInt());
        } else if (fieldSelectId == 6) {
            this.member.setPhone(sc.next());
        } else if (fieldSelectId == 7) {
            this.member.setAddress(sc.next());
        } else if (fieldSelectId == 8) {
            this.member.setSecurityCode(sc.next());
        }

    }

    public void start() throws InterruptedException {
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
        System.out.println("member have been Successfully Updated.");
    }

    private boolean executeOperation() {
        boolean result = false;
        try {
            result = MEC.updateMember(member);
        } catch (InvalidInputException ex) {
            System.err.println(ex.getMessage());
        }
        return result;
    }

    private void onOperationSuccessNavigate() throws InterruptedException {
        this.memManageModIn.start();
    }

    private void onOperationFailNavigate() throws InterruptedException {
        start();
    }

    //    Settter ..........
    public void setMemManageModIn(MemberManagementModule memManageModIn) {
        this.memManageModIn = memManageModIn;
    }
}