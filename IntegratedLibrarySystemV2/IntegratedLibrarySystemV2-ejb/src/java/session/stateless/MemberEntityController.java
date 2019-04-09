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
import javax.jws.WebMethod;
import javax.jws.WebService;
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
@WebService(serviceName = "MemberService")
public class MemberEntityController implements MemberEntityControllerRemote, MemberEntityControllerLocal {

    @PersistenceContext
    private EntityManager em;

    @WebMethod(exclude = true)
    @Override
    public boolean registerMember(Member member) throws InvalidInputException {
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

    @WebMethod(exclude = true)
    @Override
    public Member viewMember(long memberID) throws MemberNotFoundException {
        Member member = new Member();
        try {
            MemberEntity me = retrieve(memberID);
            member = me.toMember();
        } catch (PersistenceException ex) {
            throw new MemberNotFoundException("No such member with ID: " + memberID);
        }
        return member;
    }

    @WebMethod(exclude = true)
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

    @WebMethod(exclude = true)
    @Override
    public boolean updateMember(Member member) throws InvalidInputException {
        try {
            update(new MemberEntity(member));
            return true;
        } catch (PersistenceException ex) {
            return false;
        } catch (ConstraintViolationException cex) {
            throw new InvalidInputException();
        }
    }

    @WebMethod(exclude = true)
    @Override
    public void deleteMember(long memberID) throws MemberNotFoundException {
        try {
            MemberEntity memberE = retrieve(memberID);
            remove(memberE);
        } catch (PersistenceException ex) {
            throw new MemberNotFoundException("No such member with ID: " + memberID);
        }
    }

    @WebMethod(operationName = "memberLogin")
    @Override
    public Member memberLogin(String identityNumber, String securityCode) throws PersistenceException, InvalidLoginCredentialException {
        Member member = new Member();
        String jpql = "SELECT s FROM MemberEntity s WHERE s.identityNumber =:identityNumber AND s.securityCode =:securityCode";
        Query query = em.createQuery(jpql);
        query.setParameter("identityNumber", identityNumber);
        query.setParameter("securityCode", securityCode);
        try {
            MemberEntity me = (MemberEntity) query.getSingleResult();
            member = me.toMember();
            return member;
        } catch (PersistenceException ex) {
            throw new InvalidLoginCredentialException("Invalid credentials");
        }
    }

    @WebMethod(exclude = true)
    @Override
    public MemberEntity viewMember(String identityNumber) throws MemberNotFoundException {
        try {
            return retrieve(identityNumber);
        } catch (PersistenceException ex) {
            throw new MemberNotFoundException("No such member with identity number: " + identityNumber);
        }
    }

    private void remove(MemberEntity me) throws PersistenceException {
        try {
            me = em.find(MemberEntity.class, me.getMemberID());
            em.remove(me);
        } catch (PersistenceException ex) {
            throw ex;
        }
    }
    
    private void update(MemberEntity me) throws PersistenceException {
        try {
            if (me.getMemberID() != null) {
                em.merge(me);
            }
        } catch (PersistenceException ex) {
            throw ex;
        }
    }
    
    private MemberEntity retrieve(long memberID) throws PersistenceException {
        String jpql = "SELECT m FROM MemberEntity m WHERE m.memberID = :memberID";
        Query query = em.createQuery(jpql);
        query.setParameter("memberID", memberID);
        MemberEntity memberE = new MemberEntity();
        try {
            memberE = (MemberEntity) query.getSingleResult();
        } catch (PersistenceException ex) {
            throw ex;
        }
        return memberE;
    }

    private MemberEntity retrieve(String identityNumber) throws PersistenceException {
        String jpql = "SELECT m FROM MemberEntity m WHERE m.identityNumber = :identityNumber";
        TypedQuery query = em.createQuery(jpql, MemberEntity.class);
        query.setParameter("identityNumber", identityNumber);
        MemberEntity memberE = new MemberEntity();
        try {
            memberE = (MemberEntity) query.getSingleResult();
            em.refresh(memberE);
        } catch (PersistenceException ex) {
            throw ex;
        }
        return memberE;
    }

    private List<MemberEntity> retrieveAll() throws PersistenceException {
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

    @WebMethod(exclude = true)
    @Remove
    public void destroy() {
        em.close();
    }
}
