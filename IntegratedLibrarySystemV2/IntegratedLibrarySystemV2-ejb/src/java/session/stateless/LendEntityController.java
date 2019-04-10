package session.stateless;

import entity.BookEntity;
import entity.LendingEntity;
import entity.MemberEntity;
import entity.PaymentEntity;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.ejb.Remote;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import model.Book;
import model.Lend;
import model.Lendws;
import model.Member;
import session.stateless.local.BookEntityControllerLocal;
import session.stateless.local.LendEntityControllerLocal;
import session.stateless.local.MemberEntityControllerLocal;
import session.stateless.local.PaymentEntityControllerLocal;
import session.stateless.local.ReservationEntityControllerLocal;
// import session.stateless.local.ReservationEntityControllerLocal;
import session.stateless.remote.LendEntityControllerRemote;
import util.exception.BookAlreadyLendedException;
import util.exception.BookNotFoundException;
import util.exception.BookOverDueException;
import util.exception.FineNotPaidException;
import util.exception.LendNotFoundException;
import util.exception.LoanLimitHitException;
import util.exception.MemberNotFoundException;
import util.exception.ReservedByOthersException;

@Stateless
@LocalBean
@Remote(LendEntityControllerRemote.class)
@Local(LendEntityControllerLocal.class)
@WebService(serviceName = "LendService")
public class LendEntityController implements LendEntityControllerRemote, LendEntityControllerLocal {

    @EJB
    private MemberEntityControllerLocal MEC;
    @EJB
    private BookEntityControllerLocal BEC;
    @EJB
    private PaymentEntityControllerLocal PEC;
    @EJB
    private LendEntityControllerLocal LEC;
    @EJB
    private ReservationEntityControllerLocal REC;

    private final int lendThreshold = 3;

    @PersistenceContext
    private EntityManager em;
    private Date currentDate;
    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    @WebMethod(exclude = true)
    @Override
    public Date lendBook(Member member, Long bookId) throws MemberNotFoundException, BookNotFoundException, BookAlreadyLendedException,
            LoanLimitHitException, FineNotPaidException {
        String identityNumber = member.getIdentityNumber();
        return lendBook(identityNumber, bookId);
    }

    @WebMethod(exclude = true)
    @Override
    public Date lendBook(String identityNumber, Long bookID) throws MemberNotFoundException, BookNotFoundException, BookAlreadyLendedException,
            LoanLimitHitException, FineNotPaidException {
        try {
            MemberEntity memberE = MEC.viewMember(identityNumber);
            BookEntity bookE = BEC.viewBookE(bookID);
            LendingEntity lendingE = new LendingEntity(new Date(), memberE, bookE);

            // Check for outstanding fine
            if (!memberE.getPayment().isEmpty()) {
                throw new FineNotPaidException("Fail to borrow. You need to pay your outstanding fines first!");
            }

            // Check for lend amount less than 3
            if (memberE.getLending().size() >= lendThreshold) {
                throw new LoanLimitHitException("Fail to borrow. You have already borrowed 3 books (Max loan is 3).");
            }

            //check if book alreaded lended by someone
            boolean isLended = LEC.ViewLendBooks().stream().anyMatch(l -> l.getBook().getBookID().equals(bookE.getBookID()));
            if (isLended) {
                throw new BookAlreadyLendedException("This book is currently lended by someone or you.");
            }

            // Check for not reserved 
            boolean isReserved = REC.retrieveByBookID(bookID).stream().anyMatch(r -> r.getBook().getBookID().equals(bookE.getBookID()));
            if (isReserved) {
                throw new ReservedByOthersException("Fail to borrow. This book had been reserved!");
            }
            
            /* boolean isReservedBySelf = REC.retrieveByMemberIdentityNumber(memberE).stream().anyMatch(m -> m.getBook().getBookID().equals(bookE.getBookID()));
            if (isReservedBySelf) {
                em.remove(REC.retrieveByMemberIdentityNumber(memberE));
            } */


//            if(!bookE.getReservedList().isEmpty() && bookE.getReservedList().getFirst().getMember().getIdentityNumber() != memberE.getIdentityNumber()){
//                throw new ReservedByOthersException("Fail to borrow. This book had been reserved!");
//            }else if(!bookE.getReservedList().isEmpty() && bookE.getReservedList().getFirst().getMember().getIdentityNumber() == memberE.getIdentityNumber()){
//                bookE.getReservedList().removeFirst();
//            }
            // Check for book not already lend
            // Use the table unqiue propety to check this, catched with Exceptio
            // No problem, lend book
            em.persist(lendingE);
            return lendingE.getDueDate();

        } catch (MemberNotFoundException | BookNotFoundException | FineNotPaidException | LoanLimitHitException ex) {
            throw ex;
        } catch (Exception e) {
            throw new BookAlreadyLendedException("This book is currently lended by someone");
        }
    }

    @WebMethod(exclude = true)
    @Override
    public List<Lend> ViewLendBooks() throws MemberNotFoundException {
        List<LendingEntity> le = retrieveAll();
        return le.stream().map(l -> l.toLend()).collect(Collectors.toList());
    }

    @WebMethod(exclude = true)
    @Override
    public List<Lend> ViewLendBooks(Member member) throws MemberNotFoundException {
        String identityNumber = member.getIdentityNumber();
        return ViewLendBooks(identityNumber);
    }

    @WebMethod(exclude = true)
    @Override
    public List<Lend> ViewLendBooks(String identityNumber) throws MemberNotFoundException {
        try {
            List<Lend> lList;
            lList = new ArrayList<>();
            MemberEntity memberE = MEC.viewMember(identityNumber);
            List<LendingEntity> leList = memberE.getLending();

            leList.forEach((le) -> {
                lList.add(new Lend(le.getLendID(), le.getBook().toBook(), le.getLendDate(), le.getMember().toMember()));
            });
//            return leList.stream().map(le -> le.toLend()).collect(Collectors.toList());
            return lList;
        } catch (MemberNotFoundException ex) {
            throw ex;
        }
    }

    @WebMethod(operationName = "ViewLendBooksWs")
    public List<Lendws> ViewLendBooksWs(String identityNumber) throws MemberNotFoundException {
        try {
            List<Lendws> lList;
            lList = new ArrayList<>();
            MemberEntity memberE = MEC.viewMember(identityNumber);
            List<LendingEntity> leList = memberE.getLending();

            leList.forEach((le) -> {
                long lendid = le.getLendID();
                String bookTitle = le.getBook().getTitle();
                Date dueDate = le.getDueDate();
                lList.add(new Lendws(lendid, bookTitle, dueDate));
            });
//            return leList.stream().map(le -> le.toLend()).collect(Collectors.toList());
            return lList;
        } catch (MemberNotFoundException ex) {
            throw ex;
        }
    }

    @WebMethod(exclude = true)
    @Override
    public boolean ReturnLendBook(Member member, Long lendId) throws MemberNotFoundException, LendNotFoundException {
        try {
            MemberEntity memberE = MEC.viewMember(member.getIdentityNumber());
            LendingEntity currentLendCtx = getMemberLendCtx(memberE, lendId);

            // If book have overdued
            if (isOverDue(currentLendCtx)) {
                PEC.createFine(currentLendCtx);
                remove(currentLendCtx);
                return false;
            } else {
                remove(currentLendCtx);
                return true;
            }

        } catch (MemberNotFoundException | LendNotFoundException ex) {
            throw ex;
        } catch (PersistenceException ex) {
            return false;
        }
    }

    @WebMethod(operationName = "ReturnLendBook")
    @Override
    public boolean ReturnLendBook(String identityNumber, Long lendId) throws MemberNotFoundException, LendNotFoundException {
        try {
            MemberEntity memberE = MEC.viewMember(identityNumber);
            LendingEntity currentLendCtx = getMemberLendCtx(memberE, lendId);

            // If book have overdued
            if (isOverDue(currentLendCtx)) {
                PEC.createFine(currentLendCtx);
                remove(currentLendCtx);
                return true;
            } else {
                remove(currentLendCtx);
                return true;
            }

        } catch (MemberNotFoundException | LendNotFoundException ex) {
            throw ex;
        } catch (PersistenceException ex) {
            return false;
        }
    }

    @WebMethod(exclude = true)
    @Override
    public Date ExtendLendBook(Member member, Long lendId) throws MemberNotFoundException, LendNotFoundException, BookOverDueException, FineNotPaidException, ReservedByOthersException {
        String identityNumber = member.getIdentityNumber();
        return ExtendLendBook(identityNumber, lendId);
    }

    @WebMethod(operationName = "ExtendLendBook")
    @Override
    public Date ExtendLendBook(String identityNumber, Long lendId) throws MemberNotFoundException, LendNotFoundException, BookOverDueException, FineNotPaidException, ReservedByOthersException {
        try {
            MemberEntity memberE = MEC.viewMember(identityNumber);
            LendingEntity currentLendCtx = getMemberLendCtx(memberE, lendId);

            // Check If the book is already overdue
            validateLendOverDue(currentLendCtx);
//            if (isOverDue(currentLendCtx)) {
//                throw new BookOverDueException("Unable to extend. Book is overdue.");
//            }

            //Member has unpaid fines
            if (!memberE.getPayment().isEmpty()) {
                throw new FineNotPaidException("Unable to extend. You need to pay your outstanding fines first!");
            }

            //The book is reserved by another member
            boolean isReserveByOthers = REC.retrieveAll().stream().anyMatch(r -> r.getBook().getBookID().equals(currentLendCtx.getBook().getBookID()));
            if (isReserveByOthers) {
                throw new ReservedByOthersException("Unable to extend. Book Already Reserved by another member.");
            }

            //no problem, extent
            currentLendCtx.setLendDate(currentLendCtx.getDueDate());
            update(currentLendCtx);
            return currentLendCtx.getDueDate();

        } catch (MemberNotFoundException | LendNotFoundException | BookOverDueException | FineNotPaidException | ReservedByOthersException ex) {
            throw ex;
        }
    }

    @WebMethod(exclude = true)
    @Override
    public LendingEntity getMemberLendCtx(MemberEntity memberE, Long lendId) throws LendNotFoundException {
        try {
            return memberE.getLending()
                    .stream()
                    .filter(l -> l.getLendID().equals(lendId))
                    .findFirst()
                    .get();

        } catch (NoSuchElementException ex) {
            throw new LendNotFoundException("Member did not lend book with id: " + lendId.toString());
        }
    }

    private boolean isOverDue(LendingEntity currentLendCtx) {
        Date dueDate = currentLendCtx.getDueDate();
        currentDate = new Date();
        // FOR TESTING PURPOSE, SET currentDate = yyyy-mm-dd
        System.out.println(sdf.format(dueDate) + "         TEST DATE");
        if (sdf.format(dueDate).compareTo("2019-05-15"/*sdf.format(currentDate)*/) < 0) {
            return true;
        }
        return false;
    }

    private void validateLendOverDue(LendingEntity currentLendCtx) throws BookOverDueException {
        Date dueDate = currentLendCtx.getDueDate();
        Date currentDate = new Date();
        Boolean isOverDue = currentDate.after(dueDate);
        // Test for payment
//        Boolean isOverDue = currentDate.before(dueDate);

        if (isOverDue) {
            if (!PEC.fineExist(currentLendCtx)) {
                PEC.createFine(currentLendCtx);
            }
            throw new BookOverDueException("Unable to extend, Book: '" + currentLendCtx.getBook().getTitle() + "' is Overdue.");
        }
    }

    private void remove(LendingEntity le) throws PersistenceException {
        try {
            le = em.find(LendingEntity.class, le.getLendID());
            em.remove(le);
        } catch (PersistenceException ex) {
            throw ex;
        }
    }

    private void update(LendingEntity le) throws PersistenceException {
        try {
            if (le.getLendID() != null) {
                em.merge(le);
            }
        } catch (PersistenceException ex) {
            throw ex;
        }
    }

    private List<LendingEntity> retrieveAll() throws PersistenceException {
        String jpql = "SELECT l FROM LendingEntity l";
        Query query = em.createQuery(jpql);
        List<LendingEntity> lend;
        try {
            lend = query.getResultList();
        } catch (PersistenceException ex) {
            throw ex;
        }
        return lend;
    }
}