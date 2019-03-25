package entity;

import entity.LendingEntity;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-03-25T03:23:30")
@StaticMetamodel(PaymentEntity.class)
public class PaymentEntity_ { 

    public static volatile SingularAttribute<PaymentEntity, LendingEntity> lending;
    public static volatile SingularAttribute<PaymentEntity, Long> paymentID;
    public static volatile SingularAttribute<PaymentEntity, Boolean> paid;
    public static volatile SingularAttribute<PaymentEntity, Double> paidAmount;

}