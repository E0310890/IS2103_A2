package Entity;

import Entity.Book;
import Entity.Member;
import Entity.Payment;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-03-19T19:09:15")
@StaticMetamodel(Lend.class)
public class Lend_ { 

    public static volatile SingularAttribute<Lend, Long> lendID;
    public static volatile SingularAttribute<Lend, Date> lendDate;
    public static volatile SingularAttribute<Lend, Book> book;
    public static volatile SingularAttribute<Lend, Member> member;
    public static volatile SingularAttribute<Lend, Payment> payment;

}