package Entity;

import Entity.BookEntity;
import Entity.MemberEntity;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-03-17T22:00:57")
@StaticMetamodel(LendingEntity.class)
public class LendingEntity_ { 

    public static volatile SingularAttribute<LendingEntity, Long> lendID;
    public static volatile SingularAttribute<LendingEntity, Date> lendDate;
    public static volatile SingularAttribute<LendingEntity, BookEntity> bookID;
    public static volatile SingularAttribute<LendingEntity, MemberEntity> memberID;

}