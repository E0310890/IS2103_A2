/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session.stateless.remote;

import java.util.List;
import model.Member;
import util.exception.InvalidInputException;
import util.exception.InvalidLoginCredentialException;
import util.exception.MemberNotFoundException;

/**
 *
 * @author lester
 */
public interface MemberEntityControllerRemote {
    
    public boolean registerMember(Member member) throws InvalidInputException;
    public Member viewMember (long id) throws MemberNotFoundException;
    public List<Member> viewMember();
    public boolean updateMember(Member member) throws InvalidInputException;
    public boolean deleteMember(Member member);
    
    public Member memberLogin(String identityNumber, String securityCode) throws InvalidLoginCredentialException;
}
