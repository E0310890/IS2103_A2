package session.stateless;

import dao.MemberEntityManager;
import entity.LendingEntity;
import entity.MemberEntity;
import entity.PaymentEntity;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.ejb.Remote;
import javax.persistence.PersistenceException;
import javax.validation.ConstraintViolationException;
import model.Member;
import session.stateless.local.MemberEntityControllerLocal;
import session.stateless.remote.MemberEntityControllerRemote;
import util.exception.InvalidInputException;
import util.exception.InvalidLoginCredentialException;
import util.exception.MemberNotFoundException;

@Stateless
@LocalBean
@Remote(MemberEntityControllerRemote.class)
@Local(MemberEntityControllerLocal.class)
public class MemberEntityController implements MemberEntityControllerRemote, MemberEntityControllerLocal {

    private final MemberEntityManager mem = new MemberEntityManager();

    @Override
    public boolean registerMember(Member member) throws InvalidInputException{
        try {
            mem.create(new MemberEntity(member));
            return true;
        } catch (PersistenceException ex) {
            return false;
        } catch (Exception cex){
            throw new InvalidInputException();
        }
    }

    @Override
    public Member viewMember(long id) throws MemberNotFoundException {
        Member member = new Member();
        try {
            MemberEntity me = mem.retrieve(id);
            member = me.toMember();
        } catch (PersistenceException ex) {
            throw new MemberNotFoundException("No such member with id: " + id);
        }
        return member;
    }

    @Override
    public List<Member> viewMember() {
        List<Member> members;
        try {
            members = mem.retrieveAll()
                    .stream()
                    .map(m -> m.toMember())
                    .collect(Collectors.toList());
            return members;
        } catch (PersistenceException ex) {
            return new ArrayList();
        }
    }

    @Override
    public boolean updateMember(Member member) throws InvalidInputException{
        try {
            mem.update(new MemberEntity(member));
            return true;
        } catch (PersistenceException ex) {
            return false;
        } catch (ConstraintViolationException cex){
            throw new InvalidInputException();
        }
    }

    @Override
    public void deleteMember(long id) throws MemberNotFoundException{
        try {
            MemberEntity memberE = mem.retrieve(id);
            mem.remove(memberE);
        } catch (PersistenceException ex) {
            throw new MemberNotFoundException("No such member with id: " + id);
        }
    }

    @Override
    public Member memberLogin(String identityNumber, String securityCode) throws InvalidLoginCredentialException {

        Member member = new Member();
        try {
            MemberEntity me = mem.login(identityNumber, securityCode);
            member = me.toMember();
        } catch (PersistenceException ex) {
            throw new InvalidLoginCredentialException("Invalid credentials");
        }
        return member;
    }
    
   @Override
   public MemberEntity viewMember(String identityNumber) throws MemberNotFoundException{
        try {
            return mem.retrieve(identityNumber);
        } catch (PersistenceException ex) {
            throw new MemberNotFoundException("No such member with identity Number: " + identityNumber);
        }
   }

    @Override
    public PaymentEntity createFine(LendingEntity lending){
        MemberEntity memberE = lending.getMember();
        PaymentEntity pe = new PaymentEntity(lending.getLendID(), lending.getDueDate());
        memberE.addPayment(pe);
        return pe;
    }
   
   
   
}
