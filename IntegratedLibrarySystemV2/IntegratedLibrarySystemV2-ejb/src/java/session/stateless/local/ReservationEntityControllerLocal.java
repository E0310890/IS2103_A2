package session.stateless.local;

import entity.MemberEntity;
import entity.ReservationEntity;
import java.util.List;
import javax.persistence.PersistenceException;

public interface ReservationEntityControllerLocal {
    
    public List<ReservationEntity> retrieveAll() throws PersistenceException;   
    public List<ReservationEntity> retrieveByBookID(Long bookID) throws PersistenceException;
    public List<ReservationEntity> retrieveByMemberIdentityNumber(MemberEntity identityNumber) throws PersistenceException;    
}