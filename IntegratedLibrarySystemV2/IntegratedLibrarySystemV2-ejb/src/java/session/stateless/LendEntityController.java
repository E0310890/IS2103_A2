/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session.stateless;

import dao.LendEntityManager;
import entity.BookEntity;
import entity.LendingEntity;
import entity.MemberEntity;
import java.sql.SQLException;
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
import session.stateless.remote.LendEntityControllerRemote;
import util.exception.BookAlreadyLendedException;
import util.exception.BookNotFoundException;
import util.exception.BookOverDueException;
import util.exception.LendNotFoundException;
import util.exception.MemberNotFoundException;

/**
 *
 * @author lester
 */
@Stateless
@LocalBean
@Remote(LendEntityControllerRemote.class)
@Local(LendEntityControllerLocal.class)
public class LendEntityController implements LendEntityControllerRemote, LendEntityControllerLocal {

    @EJB
    private MemberEntityControllerLocal MEC;
    @EJB
    private BookEntityControllerLocal BEC;

    private final LendEntityManager lem = new LendEntityManager();

    private final int lendThreshold = 3;

    @Override
    public Date lendBook(Member member, Long bookId) throws MemberNotFoundException, BookNotFoundException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Date lendBook(String identityNumber, Long bookId) throws MemberNotFoundException, BookNotFoundException, BookAlreadyLendedException {
        try {
            MemberEntity memberE = MEC.viewMember(identityNumber);
            BookEntity bookE = BEC.getBook(bookId);
            LendingEntity lendingE = new LendingEntity(new Date(), memberE, bookE);

            if (validateLend(lendingE)) {
                lem.create(lendingE);
                return lendingE.getDueDate();
            } else {
                //failed validation
                throw new BookAlreadyLendedException("Book is currently lended by someone.");
            }
        } catch (MemberNotFoundException | BookNotFoundException ex) {
            throw ex;
        } catch (ConstraintViolationException | PersistenceException ex) {
            throw new BookAlreadyLendedException("Book is currently lended by someone.");
        } catch (Exception e) {
            throw new BookAlreadyLendedException("Book is currently lended by someone.");
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
            lem.update(currentLendCtx);
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
