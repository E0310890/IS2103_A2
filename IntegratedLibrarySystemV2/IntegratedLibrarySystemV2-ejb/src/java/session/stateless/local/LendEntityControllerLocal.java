package session.stateless.local;

import entity.LendingEntity;
import entity.MemberEntity;
import util.exception.LendNotFoundException;

public interface LendEntityControllerLocal {
    public LendingEntity getMemberLendCtx(MemberEntity memberE, Long lendId) throws LendNotFoundException;
}
