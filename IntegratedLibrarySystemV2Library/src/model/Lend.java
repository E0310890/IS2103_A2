package model;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

public class Lend implements Serializable {

    private Long lendID;
    private Book book;
    private Date lendDate;

    public Lend() {
    }

    public Lend(Long lendID, Book book, Date lendDate) {
        this.lendID = lendID;
        this.book = book;
        this.lendDate = lendDate;
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

    public Date getDueDate() {
        Calendar c = Calendar.getInstance();
        c.setTime(this.lendDate);
        c.add(Calendar.DATE, 14);
        return c.getTime();
    }

}