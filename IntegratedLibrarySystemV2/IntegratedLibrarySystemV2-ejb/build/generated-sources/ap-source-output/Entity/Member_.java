package Entity;

import Entity.Lend;
import Entity.Reservation;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-03-19T01:32:51")
@StaticMetamodel(Member.class)
public class Member_ { 

    public static volatile ListAttribute<Member, Reservation> reservationList;
    public static volatile SingularAttribute<Member, String> firstName;
    public static volatile SingularAttribute<Member, String> lastName;
    public static volatile SingularAttribute<Member, String> address;
    public static volatile ListAttribute<Member, Lend> lendList;
    public static volatile SingularAttribute<Member, String> gender;
    public static volatile SingularAttribute<Member, String> identityNumber;
    public static volatile SingularAttribute<Member, String> phone;
    public static volatile SingularAttribute<Member, String> securityCode;
    public static volatile SingularAttribute<Member, Integer> age;
    public static volatile SingularAttribute<Member, Long> memberID;

}