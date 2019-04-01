package session.stateless;

import dao.LendEntityManager;
import entity.BookEntity;
import entity.LendingEntity;
import entity.MemberEntity;
import entity.PaymentEntity;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.stream.Collectors;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.ejb.Remote;
import javax.persistence.PersistenceException;
import javax.validation.ConstraintViolationException;
import model.Book;
import model.Lend;
import model.Member;
import session.stateless.local.BookEntityControllerLocal;
import session.stateless.local.LendEntityControllerLocal;
import session.stateless.local.MemberEntityControllerLocal;
import session.stateless.local.PaymentEntityControllerLocal;
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
public class LendEntityController implements LendEntityControllerRemote, LendEntityControllerLocal {

    @EJB
    private MemberEntityControllerLocal MEC;
    @EJB
    private BookEntityControllerLocal BEC;
    @EJB
    private PaymentEntityControllerLocal PEC;

    private final LendEntityManager lem = new LendEntityManager();
    
    private Date currentDate;
    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    private final int lendThreshold = 3;
    
    @Override
    public Date lendBook(Member member, Long bookId) throws MemberNotFoundException, BookNotFoundException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Date lendBook(String identityNumber, Long bookId) throws MemberNotFoundException, BookNotFoundException, BookAlreadyLendedException,  
    FineNotPaidException, LoanLimitHitException, ReservedByOthersException{
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
            if(!bookE.getReservedList().isEmpty() && bookE.getReservedList().getFirst().getMember() != memberE){
                throw new ReservedByOthersException("Fail to borrow. This book had been reserved!");
            }else if(!bookE.getReservedList().isEmpty() && bookE.getReservedList().getFirst().getMember() == memberE){
                bookE.getReservedList().removeFirst();
            }
            
            //check for book not already lend
            //use the table unqiue propety to check this
            
            //no problem, lend book
            lem.create(lendingE);
            return lendingE.getDueDate();
            
        } catch (MemberNotFoundException | BookNotFoundException ex) {
            throw ex;
        } catch (FineNotPaidException | LoanLimitHitException | ReservedByOthersException ex){
            throw ex;
        } catch (Exception e) {
            throw new BookAlreadyLendedException("This book is currently lended by someone.");
        }
    }

    @Override
    public List<Lend> ViewLendBooks(Member member) throws MemberNotFoundException{        
        try {
            List<Lend> lList;
            lList = new ArrayList<>();
            MemberEntity memberE = MEC.viewMember(member.getIdentityNumber());
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
            
            //overdued
            if(isOverDue(currentLendCtx)){
                PaymentEntity paymentE = PEC.createFine(currentLendCtx); 
                lem.remove(currentLendCtx);
                return false;
            }
            
            // not overdue
            lem.remove(currentLendCtx);
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
    public Date ExtendLendBook(String identityNumber, Long lendId) throws MemberNotFoundException, LendNotFoundException, BookOverDueException,  
            FineNotPaidException, ReservedByOthersException{
        try {
            MemberEntity memberE = MEC.viewMember(identityNumber);
            LendingEntity currentLendCtx = getMemberLendCtx(memberE, lendId);
            BookEntity bookE = currentLendCtx.getBook();
            
            // Check If the book is already overdue
            if(isOverDue(currentLendCtx)){
                throw new BookOverDueException("Unable to extend. Book already overdue.");
            }

            // Member has unpaid fines
            if(!memberE.getPayment().isEmpty()){
                throw new FineNotPaidException("Fail to extend. You need to pay your outstanding fines first!");
            }

            // The book is reserved by another member
            if(!bookE.getReservedList().isEmpty() && bookE.getReservedList().getFirst().getMember() != memberE ){
                throw new ReservedByOthersException("Unable to extend.This book had been reserved!");
            }

            currentLendCtx.setLendDate(currentLendCtx.getDueDate());
            lem.update(currentLendCtx);
            return currentLendCtx.getDueDate();

        } catch (MemberNotFoundException | LendNotFoundException | FineNotPaidException | ReservedByOthersException | BookOverDueException ex) {
            throw ex;
        } catch(PersistenceException ex){
            throw new BookOverDueException("Failed to Extend.");
        }

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

    private boolean isOverDue(LendingEntity currentLendCtx){
        Date dueDate = currentLendCtx.getDueDate();
        currentDate = new Date();  
        // FOR TESTING PURPOSE, SET currentDate = yyyy-mm-dd
        System.out.println(sdf.format(dueDate) + "         TEST DATE");
        if(sdf.format(dueDate).compareTo("2019-08-08"/*sdf.format(currentDate)*/) < 0){
            return true;
        }
        return false;
    }
}
