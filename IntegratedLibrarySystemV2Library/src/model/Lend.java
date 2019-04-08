package model;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

public class Lend implements Serializable {

    private Long lendID;
    private Book book;
    private Date lendDate;
    private Member member;

    public Lend() {
    }

    public Lend(Long lendID, Book book, Date lendDate, Member member) {
        this.lendID = lendID;
        this.book = book;
        this.lendDate = lendDate;
        this.member = member;
    }
    
    public Long getLendID() {
        return lendID;
    }

    public Book getBook() {
        return book;
    }

    public Date getLendDate() {
        return lendDate;
    }

    public Member getMember() {
        return member;
    }
       
    public Date getDueDate() {
        Calendar c = Calendar.getInstance();
        c.setTime(this.lendDate);
        c.add(Calendar.DATE, 14);
        return c.getTime();
    }

}
