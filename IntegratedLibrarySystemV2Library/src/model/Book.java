package model;

import java.io.Serializable;
import java.util.Date;

public class Book implements Serializable {

    private Long bookID;
    private String isbn;    
    private String title;
    private String year;
    
    private String status;
    private Date dueDate;

    public Book() {
    }

    public Book(Long bookID, String isbn, String title, String year) {
        this.bookID = bookID;
        this.isbn = isbn;
        this.title = title;
        this.year = year;
    }

    public Book(String isbn, String title, String year) {
        this.isbn = isbn;
        this.title = title;
        this.year = year;
    }

    public Long getBookID() {
        return bookID;
    }

    public void setBookID(Long bookID) {
        this.bookID = bookID;
    }
    
    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }    

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
    @Override
    public String toString() {
        return "Book ID: " + getBookID() + " | ISBN: " + getIsbn() + " | Title: " + getTitle() + " | Year: " + getYear();
    }     
}