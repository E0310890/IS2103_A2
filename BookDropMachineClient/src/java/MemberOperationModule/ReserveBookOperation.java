package MemberOperationModule;

import java.util.List;
import java.util.Scanner;
import session.stateless.BookNotFoundException_Exception;
import session.stateless.Member;
import session.stateless.Book;
import session.stateless.BookNotLendException_Exception;
import session.stateless.FineNotPaidException_Exception;
import session.stateless.LendBySelfException_Exception;
import session.stateless.MemberNotFoundException_Exception;
import session.stateless.ReserveBySelfException_Exception;

public class ReserveBookOperation {

    private Scanner sc = new Scanner(System.in);

    // Modules
    private MemberMenuModule MemMenuModIn;
    
    // Fields
    public Member member;
    private String searchTitle;
    private Long bookID;
    private List<Book> bookList;

    public ReserveBookOperation() {
    }

    private void displayMenu() {
        System.out.println("*** BDM Client :: Reserve Book ***\n");
        System.out.println("Enter Title to Search> ");
        this.searchTitle = sc.nextLine();
    }

    private boolean executeViewOperation() {
        boolean result = false;
        try {
            this.bookList = searchBook(this.searchTitle, this.member);
            return true;
        } catch (BookNotFoundException_Exception ex) {
            System.err.println(ex.getMessage());
        }
        return result;
    }


    private void getInput() {
        System.out.println("Enter Book ID to Reserve:");
        bookID = sc.nextLong();
    }

    public void start() {
        displayMenu();
        if (!executeViewOperation()) {
            onOperationFailNavigate();
        }
        displayBook(this.bookList);
        
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
        System.out.println("Book successfully reserved.");
    }

    private boolean executeOperation() {
        boolean result = false;
        try {
            return reserveBook(this.member, this.bookID);
        } catch (LendBySelfException_Exception | BookNotFoundException_Exception | ReserveBySelfException_Exception | BookNotLendException_Exception | MemberNotFoundException_Exception | FineNotPaidException_Exception ex) {
            System.err.println(ex.getMessage());
        }
        return result;
    }

    private void onOperationSuccessNavigate() {
        try{
            Thread.sleep(1000);
        }catch(InterruptedException ex){  
        }
        this.MemMenuModIn.start();
    }

    private void onOperationFailNavigate() {
        try{
            Thread.sleep(1000);
        }catch(InterruptedException ex){  
        }
        this.MemMenuModIn.start();
    }

    //    Settter ..........
    public MemberMenuModule getMemMenuModIn() {
        return MemMenuModIn;
    }

    public void setMemMenuModIn(MemberMenuModule MemMenuModIn) {
        this.MemMenuModIn = MemMenuModIn;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    private static java.util.List<session.stateless.Book> searchBook(java.lang.String arg0, session.stateless.Member arg1) throws BookNotFoundException_Exception {
        session.stateless.BookService service = new session.stateless.BookService();
        session.stateless.BookEntityController port = service.getBookEntityControllerPort();
        return port.searchBook(arg0, arg1);
    }
    
    private void displayBook(List<Book> bookList) {
        if (bookList.isEmpty()) {
            System.out.println("===== No book with such a title.======");
            return;
        }
        System.out.println("Search Results:");
        System.out.println("Id  | Title                   | Availability ");
        bookList.forEach((bl) -> {
            System.out.println(bl.getBookID() + " | " + bl.getTitle() + " | " + bl.getStatus());
        });
        System.out.println();

    }

    private static boolean reserveBook(session.stateless.Member arg0, java.lang.Long arg1) throws LendBySelfException_Exception, BookNotFoundException_Exception, ReserveBySelfException_Exception, BookNotLendException_Exception, MemberNotFoundException_Exception, FineNotPaidException_Exception {
        session.stateless.ReservationService service = new session.stateless.ReservationService();
        session.stateless.ReservationEntityController port = service.getReservationEntityControllerPort();
        return port.reserveBook(arg0, arg1);
    }
}