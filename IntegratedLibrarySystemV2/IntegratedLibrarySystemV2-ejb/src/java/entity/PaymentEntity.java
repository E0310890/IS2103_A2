/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import model.Fine;

/**
 *
 * @author lester
 */
@Entity
public class PaymentEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long paymentID;

    private Double paidAmount;
    
    @Column(nullable = false)
    private boolean paid = false;

    @OneToOne(mappedBy = "payment")
    private LendingEntity lending;

    public Long getPaymentID() {
        return paymentID;
    }

    public void setPaymentID(Long paymentID) {
        this.paymentID = paymentID;
    }

    public boolean isPaid() {
        return paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }

    

    public LendingEntity getLending() {
        return lending;
    }

    public Fine toFine() {
        return new Fine(this.lending.getLendID(), this.getFineAmount());
    }

    public double getFineAmount() {
        Date nowDate = new Date();
        long dateDiff = this.lending.getDueDate().getTime() - nowDate.getTime();
        long diffHours = dateDiff / (60 * 60 * 1000) % 24;
        if (dateDiff <= 0) {
            return 0;
        }
        return (double) diffHours;
    }
    
    public boolean fineExist(){
        return this.getFineAmount() > 0 && !this.isPaid();
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
