package entity;

import entity.BookEntity;
import entity.MemberEntity;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-04-09T23:57:21")
@StaticMetamodel(ReservationEntity.class)
public class ReservationEntity_ { 

    public static volatile SingularAttribute<ReservationEntity, Long> reservationID;
    public static volatile SingularAttribute<ReservationEntity, BookEntity> book;
    public static volatile SingularAttribute<ReservationEntity, MemberEntity> member;
    public static volatile SingularAttribute<ReservationEntity, Date> reserveDate;

}