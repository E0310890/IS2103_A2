package session.stateless;

import entity.BookEntity;
import entity.LendingEntity;
import entity.MemberEntity;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.stream.Collectors;
import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.ejb.Remote;
import javax.ejb.Remove;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.validation.ConstraintViolationException;
import model.Lend;
import model.Member;
import session.stateless.local.BookEntityControllerLocal;
import session.stateless.local.LendEntityControllerLocal;
import session.stateless.local.MemberEntityControllerLocal;
import session.stateless.remote.LendEntityControllerRemote;
import util.exception.BookAlreadyLendedException;
import util.exception.BookNotFoundException;
import util.exception.BookOverDueException;
import util.exception.FineNotPaidException;
import util.exception.LendNotFoundException;
import util.exception.LoanLimitHitException;
import util.exception.MemberNotFoundException;

@Stateless
@LocalBean
@Remote(LendEntityControllerRemote.class)
@Local(LendEntityControllerLocal.class)
public class LendEntityController implements LendEntityControllerRemote, LendEntityControllerLocal {

    @EJB
    private MemberEntityControllerLocal MEC;
    @EJB
    private BookEntityControllerLocal BEC;

    private final int lendThreshold = 3;

    @PersistenceContext
    private EntityManager em;    

    public void remove(LendingEntity le) throws PersistenceException {
        try {
            em.joinTransaction();
            le = em.find(LendingEntity.class, le.getLendID());
            em.remove(le);
        } catch (PersistenceException ex) {
            throw ex;
        }
    }

    public void update(LendingEntity le) throws PersistenceException {
        try {
            if (le.getLendID() != null) {
                em.joinTransaction();
                em.merge(le);
            }
        } catch (PersistenceException ex) {
            throw ex;
        }
    }

    @Remove
    public void destroy() {
        em.close();
    }
    
    @Override
    public Date lendBook(Member member, Long bookId) throws MemberNotFoundException, BookNotFoundException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Date lendBook(String identityNumber, Long bookId) throws MemberNotFoundException, BookNotFoundException, BookAlreadyLendedException, 
            LoanLimitHitException, FineNotPaidException {
        try {
            MemberEntity memberE = MEC.viewMember(identityNumber);
            BookEntity bookE = BEC.getBook(bookId);
            LendingEntity lendingE = new LendingEntity(new Date(), memberE, bookE);

            //check for outstanding fine
            if(!memberE.getPayment().isEmpty()){
                throw new FineNotPaidException("Fail to borrow. You need to pay your outstanding fines first!");
            }
            
            //check for lend < 3
            if(memberE.getLending().size() >= lendThreshold) {
                throw new LoanLimitHitException("Fail to borrow. You have already borrowed 3 books (Max loan is 3).");
            }
            
            //check for not reserved 
//            if(!bookE.getReservedList().isEmpty() && bookE.getReservedList().getFirst().getMember().getIdentityNumber() != memberE.getIdentityNumber()){
//                throw new ReservedByOthersException("Fail to borrow. This book had been reserved!");
//            }else if(!bookE.getReservedList().isEmpty() && bookE.getReservedList().getFirst().getMember().getIdentityNumber() == memberE.getIdentityNumber()){
//                bookE.getReservedList().removeFirst();
//            }
            
            //check for book not already lend
            //use the table unqiue propety to check this, catched with Exceptio
            
            //no problem, lend book
            em.persist(lendingE);
            em.flush();
            return lendingE.getDueDate();

        } catch (MemberNotFoundException | BookNotFoundException | FineNotPaidException | LoanLimitHitException /*| ReservedByOthersException*/ ex){
            throw ex;
        } catch (Exception e) {
            throw new BookAlreadyLendedException("This book is currently lended by someone.");
        }
    }

    @Override
    public List<Lend> ViewLendBooks(Member member) throws MemberNotFoundException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Lend> ViewLendBooks(String identityNumber) throws MemberNotFoundException {
        try {
            List<Lend> lList;
            lList = new ArrayList<>();
            MemberEntity memberE = MEC.viewMember(identityNumber);
            List<LendingEntity> leList = memberE.getLending();

            leList.forEach((le) -> {
                lList.add(new Lend(le.getLendID(), le.getBook().toBook(), le.getLendDate()));
            });
            return lList;
        } catch (MemberNotFoundException ex) {
            throw ex;
        }
    }

    @Override
    public boolean ReturnLendBook(Member member, Long lendId) throws MemberNotFoundException, LendNotFoundException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates
    }

    @Override
    public boolean ReturnLendBook(String identityNumber, Long lendId) throws MemberNotFoundException, LendNotFoundException {
        try {
            MemberEntity memberE = MEC.viewMember(identityNumber);

            LendingEntity currentLendCtx = getMemberLendCtx(memberE, lendId);
            // if book have overdued
            //**implement here...
            

            remove(currentLendCtx);
            return true;
        } catch (MemberNotFoundException | LendNotFoundException ex) {
            throw ex;
        } catch (PersistenceException ex) {
            return false;
        }
    }

    @Override
    public Date ExtendLendBook(Member member, Long lendId) throws MemberNotFoundException, LendNotFoundException, BookOverDueException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Date ExtendLendBook(String identityNumber, Long lendId) throws MemberNotFoundException, LendNotFoundException, BookOverDueException {
        try {
            MemberEntity memberE = MEC.viewMember(identityNumber);
            LendingEntity currentLendCtx = getMemberLendCtx(memberE, lendId);
//        Check If the book is already overdue
            validateLendOverDue(currentLendCtx);

//        Member has unpaid fines
//        The book is reserved by another member
//        **implement here...

            currentLendCtx.setLendDate(currentLendCtx.getDueDate());
            update(currentLendCtx);
            return currentLendCtx.getDueDate();

        } catch (MemberNotFoundException | LendNotFoundException | BookOverDueException ex) {
            throw ex;
        } catch(PersistenceException ex){
            throw new BookOverDueException("Failed to Extend.");
        }

    }

    private boolean validateLend(LendingEntity le) {

        // Borrowed > 3
        int currentBookAmt = (le.getMember().getLending()).size();
        if (currentBookAmt >= this.lendThreshold) {
            return false;
        }
        // Have Unpaid fines validation
        //**implement here...

        return true;
    }

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

    private void validateLendOverDue(LendingEntity currentLendCtx) throws BookOverDueException {
        Date dueDate = currentLendCtx.getDueDate();
        Date currentDate = new Date();
        Boolean isOverDue = currentDate.after(dueDate);
        if (isOverDue) {
            throw new BookOverDueException("Book: '" + currentLendCtx.getBook().getTitle() + "' is Overdue.");
        }
    }
}