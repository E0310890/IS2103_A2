package session.stateless.remote;

import java.util.List;
import model.Member;
import model.Reservation;
import util.exception.BookNotFoundException;
import util.exception.MemberNotFoundException;
import util.exception.BookNotLendException;
import util.exception.FineNotPaidException;
import util.exception.LendBySelfException;
import util.exception.ReservationNotFoundException;
import util.exception.ReserveBySelfException;

public interface ReservationEntityControllerRemote {
    public boolean reserveBook (Member member, Long bookID) throws BookNotFoundException, MemberNotFoundException, BookNotLendException, FineNotPaidException, LendBySelfException, ReserveBySelfException;
    public List<Reservation>viewReservation () throws BookNotFoundException, ReservationNotFoundException;
    public boolean deleteReservation (Long reservationID) throws ReservationNotFoundException;
}