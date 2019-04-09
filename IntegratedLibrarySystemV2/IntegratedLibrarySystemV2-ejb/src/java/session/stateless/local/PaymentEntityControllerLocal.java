package session.stateless.local;

import entity.LendingEntity;
import entity.PaymentEntity;
import java.util.List;
import model.Fine;
import model.Member;
import util.exception.FineNotFoundException;
import util.exception.LendNotFoundException;
import util.exception.MemberNotFoundException;


public interface PaymentEntityControllerLocal {

    public PaymentEntity createFine(LendingEntity lendingEntity);
    public boolean payFine(Member member, Long lendId) throws MemberNotFoundException, LendNotFoundException, FineNotFoundException;
    public boolean payFine(String identityNumber, Long lendId) throws MemberNotFoundException, LendNotFoundException, FineNotFoundException;
    public List<Fine> viewFine(Member member) throws MemberNotFoundException;
    public List<Fine> viewFine(String identityNumber) throws MemberNotFoundException;
    public boolean fineExist(LendingEntity lendingE);
}