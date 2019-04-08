package session.stateless;

import entity.BookEntity;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.ejb.Remote;
import javax.ejb.Remove;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.validation.ConstraintViolationException;
import model.Book;
import session.stateless.local.BookEntityControllerLocal;
import session.stateless.remote.BookEntityControllerRemote;
import util.exception.InvalidInputException;
import util.exception.InvalidLoginCredentialException;
import util.exception.BookNotFoundException;

@Stateless
@LocalBean
@Remote(BookEntityControllerRemote.class)
@Local(BookEntityControllerLocal.class)
public class BookEntityController implements BookEntityControllerRemote , BookEntityControllerLocal {

    @PersistenceContext
    private EntityManager em;
    
    @Override
    public boolean registerBook(Book Book) throws InvalidInputException{
        BookEntity be = new BookEntity(Book);
        try {
            if (be.getBookID() == null) {
                em.persist(be);
                em.flush();
            }
            return true;
        } catch (Exception ex) {
            throw new InvalidInputException("Please input correct book details");
        }
    }

    @Override
    public BookEntity viewBookE(long BookID) throws BookNotFoundException {
        BookEntity Book = new BookEntity();
        try {
            BookEntity be = retrieve(BookID);
        } catch (PersistenceException ex) {
            throw new BookNotFoundException("No such Book with ID: " + BookID);
        }
        return Book;
    }
 

    @Override
    public Book viewBook(long BookID) throws BookNotFoundException {
        Book Book = new Book();
        try {
            BookEntity be = retrieve(BookID);
        } catch (PersistenceException ex) {
            throw new BookNotFoundException("No such Book with ID: " + BookID);
        }
        return Book;
    }

    @Override
    public List<Book> viewBook() {
        List<Book> Books;
        try {
            Books = retrieveAll()
                    .stream()
                    .map(b -> b.toBook())
                    .collect(Collectors.toList());
            return Books;
        } catch (PersistenceException ex) {
            return new ArrayList();
        }
    }

    @Override
    public List<Book> viewBook(String title) throws BookNotFoundException{
        return viewBook()
                .stream()
                .filter(b -> b.getTitle().contains(title))
                .collect(Collectors.toList());
    }
   
    @Override
    public boolean updateBook(Book Book) throws InvalidInputException{
        try {
            update(new BookEntity(Book));
            return true;
        } catch (PersistenceException ex) {
            return false;
        } catch (ConstraintViolationException cex){
            throw new InvalidInputException();
        }
    }

    @Override
    public void deleteBook(long BookID) throws BookNotFoundException{
        try {
            BookEntity BookE = retrieve(BookID);
            remove(BookE);
        } catch (PersistenceException ex) {
            throw new BookNotFoundException("No such Book with ID: " + BookID);
        }
    }
       
    public void remove(BookEntity be) throws PersistenceException {
        try {
            be = em.find(BookEntity.class, be.getBookID());
            em.remove(be);
        } catch (PersistenceException ex) {
            throw ex;
        }
    }

    public void update(BookEntity be) throws PersistenceException {
        try {
            if (be.getBookID() != null) {
                em.merge(be);
            }
        } catch (PersistenceException ex) {
            throw ex;
        }
    }

    public BookEntity retrieve(long bookID) throws PersistenceException {
        String jpql = "SELECT b FROM BookEntity b WHERE b.bookID = :bookID";
        Query query = em.createQuery(jpql);
        query.setParameter("bookID", bookID);
        BookEntity BookE = new BookEntity();
        try {
            BookE = (BookEntity) query.getSingleResult();
        } catch (PersistenceException ex) {
            throw ex;
        }
        return BookE;
    }

    public BookEntity retrieve(String title) throws PersistenceException {
        String jpql = "SELECT b FROM BookEntity b WHERE b.title = :title";
        TypedQuery query = em.createQuery(jpql, BookEntity.class);
        query.setParameter("title", title);
        BookEntity BookE = new BookEntity();
        try {
            BookE = (BookEntity) query.getSingleResult();
            em.refresh(BookE);
        } catch (PersistenceException ex) {
            throw ex;
        }
        return BookE;
    }

    public List<BookEntity> retrieveAll() throws PersistenceException {
        String jpql = "SELECT b FROM BookEntity b";
        Query query = em.createQuery(jpql);
        List<BookEntity> Books;
        try {
            Books = query.getResultList();
        } catch (PersistenceException ex) {
            throw ex;
        }
        return Books;
    }
    
    @Remove
    public void destroy() {
        em.close();
    }
}