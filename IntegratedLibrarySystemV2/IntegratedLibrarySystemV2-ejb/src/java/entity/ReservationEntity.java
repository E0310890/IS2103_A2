package entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import model.Reservation;

@Entity
public class ReservationEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reservationID;
    
    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Date reserveDate;
    
    @ManyToOne
    private MemberEntity member;
    @ManyToOne
    private BookEntity book;
    
    public ReservationEntity(){
    }

    public ReservationEntity(Date reserveDate, MemberEntity member, BookEntity book) {
        this.reserveDate = reserveDate;
        this.member = member;
        this.book = book;
    } 
    
    public Reservation toReservation() {
         return new Reservation(this.reservationID, this.reserveDate, this.member.toMember(), this.book.toBook());
    }
       
    public Long getReservationID() {
        return reservationID;
    }

    public void setReservationID(Long reservationID) {
        this.reservationID = reservationID;
    }
    
    public Date getDate() {
        return reserveDate;
    }

    public MemberEntity getMember() {
        return member;
    }
    
    public BookEntity getBook(){
        return book;
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (reservationID != null ? reservationID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the reservationID fields are not set
        if (!(object instanceof ReservationEntity)) {
            return false;
        }
        ReservationEntity other = (ReservationEntity) object;
        if ((this.reservationID == null && other.reservationID != null) || (this.reservationID != null && !this.reservationID.equals(other.reservationID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entity.ReservationEntity[ id=" + reservationID + " ]";
    }
}