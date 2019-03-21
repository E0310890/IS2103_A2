package dao;

import Entity.MemberEntity;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.Persistence;
import javax.persistence.Query;
import util.exception.InvalidLoginCredentialException;
import util.exception.MemberNotFoundException;


public class MemberEntityManager {
    
    //Code when need to use Entity manager outside of container
    private static final EntityManagerFactory factory = Persistence.createEntityManagerFactory("IntegratedLibrarySystemV2-ejbPU");
    private final EntityManager entityManager = factory.createEntityManager();

    public MemberEntityManager() {
    }
    
    public MemberEntity createNewMember(MemberEntity newMemberEntity){
        entityManager.persist(newMemberEntity);
        entityManager.flush();
        
        return newMemberEntity;
    }
    
    
    public List<MemberEntity> retrieveAllMembers(){
        Query query = entityManager.createQuery("SELECT m FROM MemberEntity m");
        
        return query.getResultList();
    }
    
    
    public MemberEntity retrieveMemberByMemberId(Long memberId) throws MemberNotFoundException{
        MemberEntity memberEntity = entityManager.find(MemberEntity.class, memberId);
        
        if(memberEntity != null){
            return memberEntity;
        }else{
            throw new MemberNotFoundException("Member ID " + memberId + " does not exist!");
        }
    }
    
    
    public MemberEntity retrieveMemberByNRIC(String nric) throws MemberNotFoundException{
        Query query = entityManager.createQuery("SELECT s FROM MemberEntity s WHERE s.identiyNumber = :inNRIC");
        query.setParameter("inNRIC", nric);
        
        try{
            return (MemberEntity)query.getSingleResult();
        }catch(NoResultException | NonUniqueResultException ex){
            throw new MemberNotFoundException("Member Identity Number " + nric + " does not exist!");
        }
    }
    

    public MemberEntity memberLogin(String nric, String securityCode) throws InvalidLoginCredentialException{
        try{
            MemberEntity memberEntity = retrieveMemberByNRIC(nric);
            
            if(memberEntity.getSecurityCode().equals(securityCode)){
                return memberEntity;
            }else{
                throw new InvalidLoginCredentialException("Username does not exist or invalid password!");
            }
        }catch(MemberNotFoundException ex){
            throw new InvalidLoginCredentialException("Username does not exist or invalid password!");
        }
    }
    
    
    public void updateMember(MemberEntity memberEntity){
        entityManager.merge(memberEntity);
    }
    
    
    public void deleteMember(Long memberId) throws MemberNotFoundException{
        MemberEntity memberEntityToRemove = retrieveMemberByMemberId(memberId);
        entityManager.remove(memberEntityToRemove);
    }
    
}
