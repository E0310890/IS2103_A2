package session.stateless;

import entity.BookEntity;
import entity.LendingEntity;
import entity.MemberEntity;
import entity.PaymentEntity;
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
import model.Fine;
import model.Lend;
import model.Member;
import model.Reservation;
import session.stateless.local.BookEntityControllerLocal;
import session.stateless.local.LendEntityControllerLocal;
import session.stateless.local.MemberEntityControllerLocal;
import session.stateless.local.PaymentEntityControllerLocal;
// import session.stateless.local.ReservationEntityControllerLocal;
import session.stateless.remote.ReservationEntityControllerRemote;
import session.stateless.remote.PaymentEntityControllerRemote;
import util.exception.BookNotFoundException;
import util.exception.BookNotLendException;
import util.exception.FineNotFoundException;
import util.exception.FineNotPaidException;
import util.exception.LendBySelfException;
import util.exception.LendNotFoundException;
import util.exception.MemberNotFoundException;
import util.exception.ReservationNotFoundException;
import util.exception.ReserveBySelfException;

@Stateless
@LocalBean
@Remote(ReservationEntityControllerRemote.class)
// @Local(ReservationEntityControllerLocal.class)
public class ReservationEntityController implements ReservationEntityControllerRemote {

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

            // If book not lend (TESTED WOOHOO)
            List<Lend> lendList = LEC.ViewLendBooks();
            boolean islend = lendList.stream()
                    .noneMatch(l -> l.getBook().getBookID().equals(bookID));
                       
            if (islend) {
                throw new BookNotLendException("Book is still Available in the library!");
            }

            // Book lend by self
            boolean islendBySelf = LEC.ViewLendBooks().stream()
                    .anyMatch(l -> l.getMember()
                            .getMemberID()
                            .equals(member.getMemberID())); 
            
            if (islendBySelf) {
                throw new LendBySelfException("You already lend this book!");
            }
            
            //book lend by other, reserved by current member
            boolean isReservedBySelf = retrieveByBookID(bookID).stream()
                    .anyMatch(l -> l.getMember().getMemberID().equals(member.getMemberID()));

            if (isReservedBySelf) {
                throw new ReserveBySelfException("You already reserve this book!");
            }

            //fine not paid
            boolean isFinePaid = memberE.getPayment().isEmpty();
            if (!isFinePaid) {
                throw new FineNotPaidException("You still have outstanding fine!");
            }

            //book lend by other, NOT reserved by current member
            //if lended > lenddate = lend.overdue
            //if in reservation > use most recent date.overdue;
            
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
        em.remove(retrieveByReservationID(reservationID));
        return true;
    }

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

    public ReservationEntity retrieveByReservationID(Long reservationID) throws PersistenceException {
        String jpql = "SELECT r FROM ReservationEntity r WHERE r.reservationID = :reservationID";
        Query query = em.createQuery(jpql);
        query.setParameter("reservationID", reservationID);
        ReservationEntity reservation = (ReservationEntity) query.getSingleResult();
        return reservation;
    }    
}