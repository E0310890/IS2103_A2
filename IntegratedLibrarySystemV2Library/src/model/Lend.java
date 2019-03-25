/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author lester
 */
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
    
    
    
    
    
}
