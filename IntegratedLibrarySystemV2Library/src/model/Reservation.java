package model;

import java.io.Serializable;
import java.util.Date;

public class Reservation implements Serializable {
    private Long reservationID;
    private Date reserveDate;
    private Member member;
    private Book book;

    public Reservation() {
    }

    public Reservation(Long reservationID, Date reserveDate, Member member, Book book) {
        this.reservationID = reservationID;
        this.reserveDate = reserveDate;
        this.member = member;
        this.book = book;
    }

    public Date getReserveDate() {
        return reserveDate;
    }

    public void setReserveDate(Date reserveDate) {
        this.reserveDate = reserveDate;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }
   
    public Long getReservationID() {
        return reservationID;
    }

    public void setReservationID(Long reservationID) {
        this.reservationID = reservationID;
    }
    
    
    
    @Override
    public String toString() {
        return "Reservation ID: " + getReservationID() + " | Reserve Date: " + getReserveDate() + 
                " | Book ID: " + book.getBookID() + " | Member ID: " + member.getMemberID() + "\n";
    }     
}