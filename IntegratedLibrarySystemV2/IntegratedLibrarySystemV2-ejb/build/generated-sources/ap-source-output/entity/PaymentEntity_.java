package entity;

import entity.MemberEntity;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-04-04T12:49:15")
@StaticMetamodel(PaymentEntity.class)
public class PaymentEntity_ { 

    public static volatile SingularAttribute<PaymentEntity, Integer> amount;
    public static volatile SingularAttribute<PaymentEntity, Long> lendID;
    public static volatile SingularAttribute<PaymentEntity, Long> paymentID;
    public static volatile SingularAttribute<PaymentEntity, MemberEntity> member;

}