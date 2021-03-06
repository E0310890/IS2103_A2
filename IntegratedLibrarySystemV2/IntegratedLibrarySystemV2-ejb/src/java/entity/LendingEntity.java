package entity;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import model.Lend;

@Entity
public class LendingEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long lendID;

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Date lendDate;

    @ManyToOne
    @JoinColumn
    private MemberEntity member;

    @OneToOne
    @JoinColumn(unique = true, nullable = false)
    private BookEntity book;

    public LendingEntity() {
    }
    
    public Lend toLend() {
         return new Lend(this.lendID, this.book.toBook(), this.lendDate, this.member.toMember());
    }

    public LendingEntity(Date lendDate, MemberEntity member, BookEntity book) {
        this.lendDate = lendDate;
        this.member = member;
        this.book = book;
    }

    public Long getLendID() {
        return lendID;
    }

    public MemberEntity getMember() {
        return member;
    }

    public void setLendID(Long lendID) {
        this.lendID = lendID;
    }

    public BookEntity getBook() {
        return book;
    }
    
    public void setLendDate(Date lendDate) {
        this.lendDate = lendDate;
    }
   
    public Date getLendDate() {
        return lendDate;
    }

    public Date getDueDate() {
        Calendar c = Calendar.getInstance();
        c.setTime(this.lendDate);
        c.add(Calendar.DATE, 14);
        return c.getTime();
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
        if (!(object instanceof LendingEntity)) {
            return false;
        }
        LendingEntity other = (LendingEntity) object;
        if ((this.lendID == null && other.lendID != null) || (this.lendID != null && !this.lendID.equals(other.lendID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entity.LendingEntity[ id=" + lendID + " ]";
    }
}