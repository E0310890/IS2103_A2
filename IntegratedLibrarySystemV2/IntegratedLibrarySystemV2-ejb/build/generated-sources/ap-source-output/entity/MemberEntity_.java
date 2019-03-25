package entity;

import entity.LendingEntity;
import entity.ReservationEntity;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import util.enumeration.Gender;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-03-25T03:23:29")
@StaticMetamodel(MemberEntity.class)
public class MemberEntity_ { 

    public static volatile SingularAttribute<MemberEntity, String> firstName;
    public static volatile SingularAttribute<MemberEntity, String> lastName;
    public static volatile SingularAttribute<MemberEntity, String> address;
    public static volatile SingularAttribute<MemberEntity, Gender> gender;
    public static volatile ListAttribute<MemberEntity, LendingEntity> lending;
    public static volatile SingularAttribute<MemberEntity, String> identityNumber;
    public static volatile SingularAttribute<MemberEntity, String> phone;
    public static volatile SingularAttribute<MemberEntity, String> securityCode;
    public static volatile ListAttribute<MemberEntity, ReservationEntity> reservation;
    public static volatile SingularAttribute<MemberEntity, Integer> age;
    public static volatile SingularAttribute<MemberEntity, Long> memberID;

}