package entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import model.Book;

@Entity
public class BookEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookID;

    @Column(nullable = false, length = 64)
    private String title;
    @Column(nullable = false, length = 32, unique = true)
    private String isbn;
    @Column(nullable = false, length = 4)
    private String year;

    public BookEntity() {
    }
    
    public BookEntity(Book book){
        this.bookID = book.getBookID();
        this.isbn = book.getIsbn();
        this.title = book.getTitle();
        this.year = book.getYear();
    }

    public BookEntity(String title, String isbn, String year) {
        this.title = title;
        this.isbn = isbn;
        this.year = year;
    }
    
    public Book toBook(){
        return new Book(this.bookID, this.title, this.isbn, this.year);
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
    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (bookID != null ? bookID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the bookID fields are not set
        if (!(object instanceof BookEntity)) {
            return false;
        }
        BookEntity other = (BookEntity) object;
        if ((this.bookID == null && other.bookID != null) || (this.bookID != null && !this.bookID.equals(other.bookID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entity.BookEntity[ id=" + bookID + " ]";
    }

}
