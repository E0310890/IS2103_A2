package session.stateless.remote;

import java.util.Date;
import java.util.List;
import model.Lend;
import model.Member;
import util.exception.BookAlreadyLendedException;
import util.exception.BookNotFoundException;
import util.exception.BookOverDueException;
import util.exception.FineNotPaidException;
import util.exception.LendNotFoundException;
import util.exception.LoanLimitHitException;
import util.exception.MemberNotFoundException;

public interface LendEntityControllerRemote {
    
    public Date lendBook(Member member, Long bookId) throws MemberNotFoundException, BookNotFoundException, BookAlreadyLendedException, 
            LoanLimitHitException, FineNotPaidException;
    public Date lendBook(String identityNumber, Long bookId) throws MemberNotFoundException, BookNotFoundException, BookAlreadyLendedException, 
            MemberNotFoundException, BookNotFoundException, BookAlreadyLendedException, LoanLimitHitException, FineNotPaidException;
    public List<Lend> ViewLendBooks(Member member) throws MemberNotFoundException;
    public List<Lend> ViewLendBooks(String identityNumber) throws MemberNotFoundException;
    public boolean ReturnLendBook(Member member, Long lendId) throws MemberNotFoundException, LendNotFoundException;
    public boolean ReturnLendBook(String identityNumber, Long lendId) throws MemberNotFoundException, LendNotFoundException;
    public Date ExtendLendBook(Member member, Long lendId) throws MemberNotFoundException, LendNotFoundException, BookOverDueException, FineNotPaidException;
    public Date ExtendLendBook(String identityNumber, Long lendId) throws MemberNotFoundException, LendNotFoundException, BookOverDueException, FineNotPaidException;   
}