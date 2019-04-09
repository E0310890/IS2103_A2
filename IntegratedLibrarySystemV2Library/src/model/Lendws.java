/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.Date;


/**
 *
 * @author lester
 */
public class Lendws {
    private Long lendId;
    private String bookTitle; 
    private Date dueDate;

    public Lendws(Long lendId, String bookTitle, Date dueDate) {
        this.lendId = lendId;
        this.bookTitle = bookTitle;
        this.dueDate = dueDate;
    }

    public Long getLendId() {
        return lendId;
    }

    public void setLendId(Long lendId) {
        this.lendId = lendId;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }
    
    
}
