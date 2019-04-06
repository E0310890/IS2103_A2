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
import util.exception.BookNotFoundException;
import util.exception.InvalidInputException;

@Stateless
@LocalBean
@Remote(BookEntityControllerRemote.class)
@Local(BookEntityControllerLocal.class)
public class BookEntityController implements BookEntityControllerRemote, BookEntityControllerLocal {

    // private final BookEntityManager bem = new BookEntityManager();
    
    @PersistenceContext
    private EntityManager em; 
    
    /* public void create(BookEntity be) throws PersistenceException {
        try {
            if (be.getBookID() == null) {
                em.persist(be);
            }
        } catch (PersistenceException ex) {
            throw ex;
        }
    } */
    
    @Override
    public Book createBook(Book book) throws PersistenceException {
        BookEntity bookE = new BookEntity(book);
        try {
            if (bookE.getBookID() == null) {
                em.persist(bookE);
            }
        } catch (PersistenceException ex) {
            throw ex;
        }
        return book; 
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

    public BookEntity retrieve(long id) throws PersistenceException {
        String jpql = "SELECT b FROM BookEntity b WHERE b.bookID = :id";
        Query query = em.createQuery(jpql);
        query.setParameter("id", id);
        BookEntity bookE = new BookEntity();
        try {
            bookE = (BookEntity) query.getSingleResult();
        } catch (PersistenceException ex) {
            throw ex;
        }
        return bookE;
    }

    public List<BookEntity> retrieveAll() throws PersistenceException {
        String jpql = "SELECT b FROM BookEntity b";
        TypedQuery<BookEntity> query = em.createQuery(jpql, BookEntity.class);
        List<BookEntity> books;
        try {
            books = query.getResultList();
        } catch (PersistenceException ex) {
            throw ex;
        }
        return books;
    }

    @Remove
    public void destroy() {
        em.close();
    }
    
    /* @Override
    public boolean registerBook(Book book) throws InvalidInputException {
        try {
            create(new BookEntity(book));
            return true;
        } catch (PersistenceException ex) {
            return false;
        } catch (ConstraintViolationException cex) {
            throw new InvalidInputException();
        }
    } */

    @Override
    public Book viewBook(long id) throws BookNotFoundException {
        Book book = new Book();
        try {
            BookEntity be = retrieve(id);
            book = be.toBook();
        } catch (PersistenceException ex) {
            throw new BookNotFoundException("No such book with id: " + id);
        }
        return book;
    }

    @Override
    public List<Book> viewBook() {
        List<Book> books;
        try {
            books = retrieveAll()
                    .stream()
                    .map(m -> m.toBook())
                    .collect(Collectors.toList());
            return books;
        } catch (PersistenceException ex) {
            return new ArrayList();
        }
    }

    @Override
    public boolean updateBook(Book book) throws InvalidInputException {
        try {
            update(new BookEntity(book));
            return true;
        } catch (PersistenceException ex) {
            return false;
        } catch (ConstraintViolationException cex) {
            throw new InvalidInputException();
        }
    }

    @Override
    public boolean deleteBook(Book book) {
        try {
            remove(new BookEntity(book));
            return true;
        } catch (PersistenceException ex) {
            return false;
        }
    }

    @Override
    public BookEntity getBook(long id) throws BookNotFoundException {
        try {
            return retrieve(id);
        } catch (PersistenceException ex) {
            throw new BookNotFoundException("No such book with id: " + id);
        }
    }
}
