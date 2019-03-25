/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;

/**
 *
 * @author lester
 */
public class Book implements Serializable {

    private Long bookID;
    private String title;
    private String isbn;
    private String year;

    public Book() {
    }

    public Book(Long bookID, String title, String isbn, String year) {
        this.bookID = bookID;
        this.title = title;
        this.isbn = isbn;
        this.year = year;
    }

    public Book(String title, String isbn, String year) {
        this.title = title;
        this.isbn = isbn;
        this.year = year;
    }

    public Long getBookID() {
        return bookID;
    }

    public void setBookID(Long bookID) {
        this.bookID = bookID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }
    
    
    
    
    
    
}
