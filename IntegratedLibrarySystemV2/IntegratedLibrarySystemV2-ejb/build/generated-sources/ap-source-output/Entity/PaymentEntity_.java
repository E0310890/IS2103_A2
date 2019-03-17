package Entity;

import Entity.LendingEntity;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-03-17T22:00:57")
@StaticMetamodel(PaymentEntity.class)
public class PaymentEntity_ { 

    public static volatile SingularAttribute<PaymentEntity, Double> amount;
    public static volatile SingularAttribute<PaymentEntity, LendingEntity> lendID;
    public static volatile SingularAttribute<PaymentEntity, Long> paymentID;

}