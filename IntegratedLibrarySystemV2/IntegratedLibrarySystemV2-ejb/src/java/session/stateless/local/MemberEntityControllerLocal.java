package session.stateless.local;

import entity.LendingEntity;
import entity.MemberEntity;
import entity.PaymentEntity;
import util.exception.MemberNotFoundException;

public interface MemberEntityControllerLocal {
    public MemberEntity viewMember(String identityNumber) throws MemberNotFoundException;
    public PaymentEntity createFine(LendingEntity lending);
}
