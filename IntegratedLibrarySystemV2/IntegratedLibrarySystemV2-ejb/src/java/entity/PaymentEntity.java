package entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import model.Fine;

@Entity
public class PaymentEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long paymentID;
    
    private Integer amount;
    @Column(unique = true)
    private Long lendID;

    @ManyToOne
    private MemberEntity member;    
    
    public PaymentEntity() {
    }
    
    public PaymentEntity(MemberEntity member, Long lendID, Date dueDate) {
        this.member = member;
        this.lendID = lendID;
        
        Date curr = new Date();
        double daysOver = (dueDate.getTime() - curr.getTime()) / (1000 * 60 * 60 * 24);
        amount = (int) Math.floor(daysOver);
    }

    public Long getPaymentID() {
        return paymentID;
    }

    public void setPaymentID(Long paymentID) {
        this.paymentID = paymentID;
    } 

    public Integer getAmount() {
        return amount;
    }  
    
    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Long getLendID() {
        return lendID;
    }
    
    public MemberEntity getMember() {
        return member;
    }
    
    public void setMember (MemberEntity member) {
        this.member = member;
    }
        
    public Fine toFine() {
        return new Fine(this.getLendID(), this.getAmount());
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (paymentID != null ? paymentID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the paymentID fields are not set
        if (!(object instanceof PaymentEntity)) {
            return false;
        }
        PaymentEntity other = (PaymentEntity) object;
        if ((this.paymentID == null && other.paymentID != null) || (this.paymentID != null && !this.paymentID.equals(other.paymentID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entity.PaymentEntity[ id=" + paymentID + " ]";
    }

}