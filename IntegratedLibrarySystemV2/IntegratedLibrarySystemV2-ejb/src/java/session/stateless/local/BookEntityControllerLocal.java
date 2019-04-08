package session.stateless.local;

import java.util.List;
import entity.BookEntity;
import util.exception.BookAlreadyLendedException;
import util.exception.BookNotFoundException;
import util.exception.FineNotPaidException;
import util.exception.InvalidInputException;
import util.exception.LoanLimitHitException;
import util.exception.MemberNotFoundException;

public interface BookEntityControllerLocal {
   
    public BookEntity viewBookE (long BookID) throws BookNotFoundException;
}
