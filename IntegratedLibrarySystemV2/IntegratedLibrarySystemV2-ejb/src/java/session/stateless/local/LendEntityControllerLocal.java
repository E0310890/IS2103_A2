package session.stateless.local;

import entity.LendingEntity;
import entity.MemberEntity;
import java.util.Date;
import util.exception.BookOverDueException;
import util.exception.FineNotPaidException;
import util.exception.LendNotFoundException;
import util.exception.MemberNotFoundException;

public interface LendEntityControllerLocal {
    public LendingEntity getMemberLendCtx(MemberEntity memberE, Long lendId) throws LendNotFoundException;
    public Date ExtendLendBook(String identityNumber, Long lendId) throws MemberNotFoundException, LendNotFoundException, BookOverDueException, FineNotPaidException;
}
