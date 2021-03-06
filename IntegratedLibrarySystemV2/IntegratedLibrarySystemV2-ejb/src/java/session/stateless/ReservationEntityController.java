package session.stateless;

import entity.BookEntity;
import entity.MemberEntity;
import entity.ReservationEntity;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.ejb.Remote;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import model.Lend;
import model.Member;
import model.Reservation;
import session.stateless.local.BookEntityControllerLocal;
import session.stateless.local.LendEntityControllerLocal;
import session.stateless.local.MemberEntityControllerLocal;
import session.stateless.local.ReservationEntityControllerLocal;
import session.stateless.remote.ReservationEntityControllerRemote;
import util.exception.BookNotFoundException;
import util.exception.BookNotLendException;
import util.exception.FineNotPaidException;
import util.exception.LendBySelfException;
import util.exception.MemberNotFoundException;
import util.exception.ReservationNotFoundException;
import util.exception.ReserveBySelfException;

@Stateless
@LocalBean
@Remote(ReservationEntityControllerRemote.class)
@Local(ReservationEntityControllerLocal.class)
public class ReservationEntityController implements ReservationEntityControllerRemote, ReservationEntityControllerLocal {
    
    @EJB
    private MemberEntityControllerLocal MEC;
    @EJB
    private LendEntityControllerLocal LEC;
    @EJB
    private BookEntityControllerLocal BEC;
    
    @PersistenceContext
    private EntityManager em;
    
    @Override
    public boolean reserveBook(Member member, Long bookID) throws BookNotFoundException, MemberNotFoundException, BookNotLendException, FineNotPaidException, LendBySelfException, ReserveBySelfException {
        
        try {
            MemberEntity memberE = MEC.viewMember(member.getIdentityNumber());
            BookEntity bookE = BEC.viewBookE(bookID);

            // If book not lend
            List<Lend> lendList = LEC.ViewLendBooks();
            boolean islend = lendList.stream()
                    .noneMatch(l -> l.getBook().getBookID().equals(bookID));
            
            if (islend) {
                throw new BookNotLendException("Book is still Available in the library!");
            }

            // Book lent by self
            boolean islendBySelf = LEC.ViewLendBooks().stream()
                    .anyMatch(l -> (l.getMember()
                    .getMemberID()
                    .equals(member.getMemberID())) && (l.getBook().getBookID().equals(bookE.getBookID())));
            
            if (islendBySelf) {
                throw new LendBySelfException("You already lend this book!");
            }

            // Book lent by other, reserved by current member
            boolean isReservedBySelf = retrieveAll().stream()
                    .anyMatch(l -> (l.getMember().getMemberID().equals(member.getMemberID())) && (l.getBook().getBookID().equals(bookE.getBookID())));
            
            if (isReservedBySelf) {
                throw new ReserveBySelfException("You already reserve this book!");
            }

            // Fine not paid
            boolean isFinePaid = memberE.getPayment().isEmpty();
            if (!isFinePaid) {
                throw new FineNotPaidException("You still have outstanding fine!");
            }

            // Book lent by other, NOT reserved by current member
            // If lended > lenddate = lend.overdue
            // If in reservation > use most recent date.overdue;
            ReservationEntity res = new ReservationEntity(new Date(), memberE, bookE);
            em.persist(res);
            
        } catch (BookNotFoundException | MemberNotFoundException ex) {
            throw ex;
        }
        return true;
    }
    
    @Override
    public List<Reservation> viewReservation() throws BookNotFoundException, ReservationNotFoundException {
        return retrieveAll().stream().map(r -> r.toReservation()).collect(Collectors.toList());
    }
    
    @Override
    public boolean deleteReservation(Long reservationID) throws ReservationNotFoundException {
        try {
            em.remove(retrieveByReservationID(reservationID));
            return true;
        } catch (PersistenceException ex) {
            throw new ReservationNotFoundException("Reservation with Id " + reservationID + " is not Found.");
        }
    }
    
    @Override
    public List<ReservationEntity> retrieveAll() throws PersistenceException {
        String jpql = "SELECT r FROM ReservationEntity r";
        Query query = em.createQuery(jpql);
        List<ReservationEntity> reservation;
        try {
            reservation = query.getResultList();
        } catch (PersistenceException ex) {
            throw ex;
        }
        return reservation;
    }
    
    public List<ReservationEntity> retrieveByBookID(Long bookID) throws PersistenceException {
        String jpql = "SELECT r FROM ReservationEntity r WHERE r.reservationID = :reservationID";
        Query query = em.createQuery(jpql);
        query.setParameter("reservationID", bookID);
        List<ReservationEntity> reservation;
        try {
            reservation = query.getResultList();
        } catch (PersistenceException ex) {
            throw ex;
        }
        return reservation;
    }
    
    public List<ReservationEntity> retrieveByMemberIdentityNumber(MemberEntity identityNumber) throws PersistenceException {
        String jpql = "SELECT r FROM ReservationEntity r WHERE r.identityNumber = :identityNumber";
        Query query = em.createQuery(jpql);
        query.setParameter("identityNumber", identityNumber);
        List<ReservationEntity> reservation;
        try {
            reservation = query.getResultList();
        } catch (PersistenceException ex) {
            throw ex;
        }
        return reservation;
    }    
    
    public ReservationEntity retrieveByReservationID(Long reservationID) throws PersistenceException {
        String jpql = "SELECT r FROM ReservationEntity r WHERE r.reservationID = :reservationID";
        Query query = em.createQuery(jpql);
        query.setParameter("reservationID", reservationID);
        ReservationEntity reservation = (ReservationEntity) query.getSingleResult();
        return reservation;
    }
}