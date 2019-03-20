package Entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class BookEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long bookID;
    @Column(length = 128, nullable = false)
    private String title;
    @Column(length = 16, nullable = false)
    private String isbn;
    @Column(length = 4, nullable = false)
    private String year;
    @OneToOne
    @JoinColumn(name = "lendID")
    private LendEntity lend;
    @OneToMany(mappedBy="book")
    private List<ReservationEntity> reservationList;

    public BookEntity() {
        reservationList = new ArrayList<>();
    }
    
    public BookEntity(String title, String isbn, String year){
        this();
        this.title = title;
        this.isbn = isbn;
        this.year = year;
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

    public LendEntity getLend() {
        return lend;
    }

    public void setLend(LendEntity lend) {
        this.lend = lend;
    }

    public List<ReservationEntity> getReservationList() {
        return reservationList;
    }

    public void setReservationList(List<ReservationEntity> reservationListe) {
        this.reservationList = reservationList;
    }

}
