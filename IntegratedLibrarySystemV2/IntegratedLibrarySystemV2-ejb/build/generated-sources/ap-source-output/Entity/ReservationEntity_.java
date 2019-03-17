package Entity;

import Entity.BookEntity;
import Entity.MemberEntity;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-03-17T22:00:57")
@StaticMetamodel(ReservationEntity.class)
public class ReservationEntity_ { 

    public static volatile SingularAttribute<ReservationEntity, Long> reservationID;
    public static volatile SingularAttribute<ReservationEntity, MemberEntity> memberID;
    public static volatile SingularAttribute<ReservationEntity, BookEntity> bookID;

}