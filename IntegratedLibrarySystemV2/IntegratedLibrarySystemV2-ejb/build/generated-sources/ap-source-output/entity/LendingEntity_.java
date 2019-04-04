package entity;

import entity.BookEntity;
import entity.MemberEntity;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-04-04T12:49:15")
@StaticMetamodel(LendingEntity.class)
public class LendingEntity_ { 

    public static volatile SingularAttribute<LendingEntity, Long> lendID;
    public static volatile SingularAttribute<LendingEntity, Date> lendDate;
    public static volatile SingularAttribute<LendingEntity, BookEntity> book;
    public static volatile SingularAttribute<LendingEntity, MemberEntity> member;

}