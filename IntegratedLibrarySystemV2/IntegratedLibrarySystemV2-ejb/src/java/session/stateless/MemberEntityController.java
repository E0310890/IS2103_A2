package session.stateless;

import entity.MemberEntity;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.ejb.Remote;
import javax.ejb.Remove;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
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

    @PersistenceContext
    private EntityManager em;
    
    @Override
    public boolean registerMember(Member member) throws InvalidInputException{
        MemberEntity me = new MemberEntity(member);
        try {
            if (me.getMemberID() == null) {
                em.persist(me);
                em.flush();
            }
            return true;
        } catch (Exception ex) {
            throw new InvalidInputException("Please input correct personal details");
        }
    }

    @Override
    public Member viewMember(long id) throws MemberNotFoundException {
        Member member = new Member();
        try {
            MemberEntity me = retrieve(id);
            member = me.toMember();
        } catch (PersistenceException ex) {
            throw new MemberNotFoundException("No such member with ID: " + id);
        }
        return member;
    }

    @Override
    public List<Member> viewMember() {
        List<Member> members;
        try {
            members = retrieveAll()
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
            update(new MemberEntity(member));
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
            MemberEntity memberE = retrieve(id);
            remove(memberE);
        } catch (PersistenceException ex) {
            throw new MemberNotFoundException("No such member with id: " + id);
        }
    }

    @Override
    public Member memberLogin(String identityNumber, String securityCode) throws InvalidLoginCredentialException {

        Member member = new Member();
        try {
            MemberEntity me = login(identityNumber, securityCode);
            member = me.toMember();
        } catch (PersistenceException ex) {
            throw new InvalidLoginCredentialException("Invalid credentials");
        }
        return member;
    }
     
   @Override
   public MemberEntity viewMember(String identityNumber) throws MemberNotFoundException{
        try {
            return retrieve(identityNumber);
        } catch (PersistenceException ex) {
            throw new MemberNotFoundException("No such member with identity Number: " + identityNumber);
        }
   }
   
    public void remove(MemberEntity me) throws PersistenceException {
        try {
            me = em.find(MemberEntity.class, me.getMemberID());
            em.remove(me);
        } catch (PersistenceException ex) {
            throw ex;
        }
    }

    public void update(MemberEntity me) throws PersistenceException {
        try {
            if (me.getMemberID() != null) {
                em.merge(me);
            }
        } catch (PersistenceException ex) {
            throw ex;
        }
    }

    public MemberEntity retrieve(long id) throws PersistenceException {
        String jpql = "SELECT m FROM MemberEntity m WHERE m.memberID = :id";
        Query query = em.createQuery(jpql);
        query.setParameter("id", id);
        MemberEntity memberE = new MemberEntity();
        try {
            memberE = (MemberEntity) query.getSingleResult();
        } catch (PersistenceException ex) {
            throw ex;
        }
        return memberE;
    }

    public MemberEntity retrieve(String identityNumber) throws PersistenceException {
        String jpql = "SELECT m FROM MemberEntity m WHERE m.identityNumber = :idn";
        TypedQuery query = em.createQuery(jpql, MemberEntity.class);
        query.setParameter("idn", identityNumber);
        MemberEntity memberE = new MemberEntity();
        try {
            memberE = (MemberEntity) query.getSingleResult();
            em.refresh(memberE);
        } catch (PersistenceException ex) {
            throw ex;
        }
        return memberE;
    }

    public List<MemberEntity> retrieveAll() throws PersistenceException {
        String jpql = "SELECT m FROM MemberEntity m";
        Query query = em.createQuery(jpql);
        List<MemberEntity> members;
        try {
            members = query.getResultList();
        } catch (PersistenceException ex) {
            throw ex;
        }
        return members;
    }

    public MemberEntity login(String identityNumber, String securityCode) throws PersistenceException {

        String jpql = "SELECT m FROM MemberEntity m WHERE m.identityNumber = :idn AND m.securityCode = :sc";
        Query query = em.createQuery(jpql);
        query.setParameter("idn", identityNumber);
        query.setParameter("sc", securityCode);
        MemberEntity memberE = new MemberEntity();
        try {
            memberE = (MemberEntity) query.getSingleResult();
        } catch (PersistenceException ex) {
            throw ex;
        }
        return memberE;
    }
    
    @Remove
    public void destroy() {
        em.close();
    }

}