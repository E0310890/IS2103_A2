package session.stateless.local;

import entity.LendingEntity;
import entity.PaymentEntity;
import java.util.Date;
import java.util.List;
import model.Fine;
import model.Member;
import util.exception.FineNotFoundException;
import util.exception.LendNotFoundException;
import util.exception.MemberNotFoundException;


public interface PaymentEntityControllerLocal {
    public boolean payFine(Member member, Long lendId) throws MemberNotFoundException, LendNotFoundException, FineNotFoundException;
    public boolean payFine(String identityNumber, Long lendId) throws MemberNotFoundException, LendNotFoundException, FineNotFoundException;
    public List<Fine> ViewFine(Member member) throws MemberNotFoundException;
    public List<Fine> ViewFine(String identityNumber) throws MemberNotFoundException;
    public PaymentEntity createFine(LendingEntity lendingE);
    
}
