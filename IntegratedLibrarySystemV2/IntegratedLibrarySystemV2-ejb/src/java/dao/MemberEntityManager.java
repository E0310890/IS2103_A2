/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.MemberEntity;
import java.util.List;
import javax.ejb.Remove;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

/**
 *
 * @author lester
 */
public class MemberEntityManager {

    private final EntityManagerFactory emf = Persistence.createEntityManagerFactory("IntegratedLibrarySystemV2-ejbPU");
    private final EntityManager em = emf.createEntityManager();

    public MemberEntityManager() {
    }

    public void create(MemberEntity me) throws PersistenceException {
        try {
            if (me.getMemberID() == null) {
                em.joinTransaction();
                em.persist(me);
            }
        } catch (PersistenceException ex) {
            throw ex;
        }
    }

    public void remove(MemberEntity me) throws PersistenceException {
        try {
            em.joinTransaction();
            me = em.find(MemberEntity.class, me.getMemberID());
            em.remove(me);
        } catch (PersistenceException ex) {
            throw ex;
        }
    }

    public void update(MemberEntity me) throws PersistenceException {
        try {
            if (me.getMemberID() != null) {
                em.joinTransaction();
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
