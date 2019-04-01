package session.stateless.remote;

import java.util.List;
import model.Fine;
import model.Member;
import util.exception.FineNotFoundException;
import util.exception.LendNotFoundException;
import util.exception.MemberNotFoundException;

public interface PaymentEntityControllerRemote {

    public boolean payFine(Member member, Long lendId) throws MemberNotFoundException, FineNotFoundException;
    public boolean payFine(String identityNumber, Long lendId) throws MemberNotFoundException, FineNotFoundException;
    public List<Fine> ViewFine(Member member) throws MemberNotFoundException;
    public List<Fine> ViewFine(String identityNumber) throws MemberNotFoundException;

}
