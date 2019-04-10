package session.stateless.local;

import entity.ReservationEntity;
import java.util.List;
import javax.persistence.PersistenceException;

public interface ReservationEntityControllerLocal {
    
    public List<ReservationEntity> retrieveAll() throws PersistenceException;
}