package Entity;

import Entity.Lend;
import Entity.Reservation;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-03-19T01:32:51")
@StaticMetamodel(Book.class)
public class Book_ { 

    public static volatile SingularAttribute<Book, String> year;
    public static volatile SingularAttribute<Book, String> isbn;
    public static volatile SingularAttribute<Book, Reservation> reservation;
    public static volatile SingularAttribute<Book, String> title;
    public static volatile SingularAttribute<Book, Lend> lend;
    public static volatile SingularAttribute<Book, Long> bookID;

}