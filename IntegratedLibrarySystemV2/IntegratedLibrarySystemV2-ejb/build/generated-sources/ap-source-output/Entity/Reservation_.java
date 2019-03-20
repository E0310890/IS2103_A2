package Entity;

import Entity.Book;
import Entity.Member;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-03-19T19:09:15")
@StaticMetamodel(Reservation.class)
public class Reservation_ { 

    public static volatile SingularAttribute<Reservation, Long> reservationID;
    public static volatile SingularAttribute<Reservation, Book> book;
    public static volatile SingularAttribute<Reservation, Member> member;

}