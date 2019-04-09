package session.stateless.remote;

import java.util.List;
import model.Member;
import util.exception.InvalidInputException;
import util.exception.InvalidLoginCredentialException;
import util.exception.MemberNotFoundException;

public interface MemberEntityControllerRemote {
    
    public boolean registerMember(Member member) throws InvalidInputException;
    public Member viewMember (long memberID) throws MemberNotFoundException;
    public List<Member> viewMember();
    public boolean updateMember(Member member) throws InvalidInputException;
    public void deleteMember(long memberID) throws MemberNotFoundException;
    public Member memberLogin(String identityNumber, String securityCode) throws InvalidLoginCredentialException;
}
