package Entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Lend implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long lendID;
    @Temporal(TemporalType.DATE)
    private Date lendDate;
    @OneToOne(mappedBy = "lend")
    private Book book; 
    @ManyToOne
    @JoinColumn(name = "memberID")
    private Member member;
    @OneToOne(mappedBy = "lend")
    private Payment payment;

    public Lend() {
    }

    public Lend(Date lendDate, Book book, Member member) {
        this.lendDate = lendDate;
        this.book = book;
        this.member = member;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (lendID != null ? lendID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the lendID fields are not set
        if (!(object instanceof Lend)) {
            return false;
        }
        Lend other = (Lend) object;
        if ((this.lendID == null && other.lendID != null) || (this.lendID != null && !this.lendID.equals(other.lendID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entity.LendingEntity[ id=" + lendID + " ]";
    }

    public Long getLendID() {
        return lendID;
    }

    public void setLendID(Long lendID) {
        this.lendID = lendID;
    }

    public Date getLendDate() {
        return lendDate;
    }

    public void setLendDate(Date lendDate) {
        this.lendDate = lendDate;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    
}
