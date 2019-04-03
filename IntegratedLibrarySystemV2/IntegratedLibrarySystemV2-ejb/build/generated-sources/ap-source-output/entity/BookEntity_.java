package entity;

import entity.LendingEntity;
import entity.ReservationEntity;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-04-03T20:37:16")
@StaticMetamodel(BookEntity.class)
public class BookEntity_ { 

    public static volatile ListAttribute<BookEntity, ReservationEntity> reservedList;
    public static volatile SingularAttribute<BookEntity, LendingEntity> lending;
    public static volatile SingularAttribute<BookEntity, String> year;
    public static volatile SingularAttribute<BookEntity, String> isbn;
    public static volatile SingularAttribute<BookEntity, String> title;
    public static volatile SingularAttribute<BookEntity, Long> bookID;

}