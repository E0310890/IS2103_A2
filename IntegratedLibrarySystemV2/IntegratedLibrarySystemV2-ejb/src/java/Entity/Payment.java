package Entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class Payment implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long paymentID;
    private Double amount;
    
    @OneToOne
    @JoinColumn(name = "lendID")
    private Lend lend;

    public Payment() {
    }

    public Payment(Double amount, Lend lend) {
        this.amount = amount;
        this.lend = lend;
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
        if (!(object instanceof Payment)) {
            return false;
        }
        Payment other = (Payment) object;
        return !((this.paymentID == null && other.paymentID != null) || (this.paymentID != null && !this.paymentID.equals(other.paymentID)));
    }

    @Override
    public String toString() {
        return "Entity.PaymentEntity[ id=" + paymentID + " ]";
    }

    public Long getPaymentID() {
        return paymentID;
    }

    public void setPaymentID(Long paymentID) {
        this.paymentID = paymentID;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Lend getLend() {
        return lend;
    }

    public void setLend(Lend lend) {
        this.lend = lend;
    }
    
}
