package session.stateless.local;

import entity.LendingEntity;
import entity.MemberEntity;
import java.util.List;
import model.Lend;
import util.exception.LendNotFoundException;
import util.exception.MemberNotFoundException;

public interface LendEntityControllerLocal {

    public boolean isOverDue(LendingEntity currentLendCtx);
    public LendingEntity getMemberLendCtx(MemberEntity memberE, Long lendId) throws LendNotFoundException;
    public List<Lend> ViewLendBooks() throws MemberNotFoundException;
    public List<LendingEntity> retrieveAll();
}
