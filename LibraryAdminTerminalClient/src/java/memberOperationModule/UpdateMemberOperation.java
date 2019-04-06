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

    // API
    private StaffEntityControllerRemote SEC;
    private MemberEntityControllerRemote MEC;
    private BookEntityControllerRemote BEC;
    private LendEntityControllerRemote LEC;
    
    // Modules
    private MemberManagementModule memberManageModIn;
    
    // Dependecies
    private ViewAllMembersOperation viewAllMembersOps;

    // Fields
    private List<Member> memberList;
    private Member member;

    public UpdateMemberOperation(StaffEntityControllerRemote SEC, MemberEntityControllerRemote MEC, BookEntityControllerRemote BEC, LendEntityControllerRemote LEC) {
        this.SEC = SEC;
        this.MEC = MEC;
        this.BEC = BEC;
        this.LEC = LEC;
    }

    private void displayMenu() {
        System.out.println("*** ILS :: Administration Operation :: Member Management :: Update Member Details ***\n");
    }

    private boolean executeViewOperation() {
        viewAllMembersOps = new ViewAllMembersOperation(SEC, MEC, BEC, LEC);
        return viewAllMembersOps.displayAllMembers();
    }

    private void transferRequiredFields() {
        this.memberList = viewAllMembersOps.getMemberList();
    }

    private void getInput() {
        System.out.println("Enter Member ID of member to update> ");
        Long memberID = sc.nextLong();

        // For validation, check if the ID choosen is in 'memberList';
        this.member = memberList.stream()
                .filter(m -> m.getMemberID().equals(memberID))
                .findFirst()
                .get();

        updateInput();
    }

    private void updateInput() {
        System.out.println("Select the field to update: \n");
        System.out.println(
                "1: Identity Number\n"
                + "2: First Name\n"
                + "3: Last Name\n"
                + "4: Gender\n"
                + "5: Age\n"
                + "6: Phone\n"
                + "7: Address\n"
                + "8: Security Code"
        );
        
        System.out.print(">");    
        int fieldSelectId = sc.nextInt();

        System.out.print("Update to: ");

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
            this.member.setAddress(sc.nextLine());
        } else if (fieldSelectId == 8) {
            this.member.setSecurityCode(sc.next());
        }
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
        System.out.println("Member have been successfully updated.");
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

    private void onOperationSuccessNavigate() {
        this.memberManageModIn.start();
    }

    private void onOperationFailNavigate() {
        start();
    }

    // Setter
    public void setMemberManageModIn(MemberManagementModule memberManageModIn) {
        this.memberManageModIn = memberManageModIn;
    }
}