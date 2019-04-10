package MemberOperationModule;

import java.util.List;
import java.util.Scanner;
import session.stateless.Lendws;
import session.stateless.Member;
import session.stateless.MemberNotFoundException_Exception;

public class ViewLendBookOperation {

    private Scanner sc = new Scanner(System.in);

    // Modules
    private MemberMenuModule memberMenuModIn;

    // Fields
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
        try{
            Thread.sleep(1000);
        }catch(InterruptedException ex){  
        }
        this.memberMenuModIn.start();
    }

    private void onOperationFailNavigate() {
        try{
            Thread.sleep(1000);
        }catch(InterruptedException ex){  
        }
        this.memberMenuModIn.start();
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

    // Setter
    public void setMember(Member member) {
        this.member = member;
    }

    public void setMemMenuModIn(MemberMenuModule memberMenuModIn) {
        this.memberMenuModIn = memberMenuModIn;
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
        System.out.println("Book ID   |   Title"
                + spacing.substring(3)
                + "|   Due Date");

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