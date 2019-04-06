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
    
    @Override
    public Book retrieve(long id) throws BookNotFoundException{
        String jpql = "SELECT s FROM BookEntity s WHERE s.bookID = :id";
        Query query = em.createQuery(jpql);
        query.setParameter("id", id);
        BookEntity bookE = new BookEntity();
        try {
            bookE = (BookEntity) query.getSingleResult();
        } catch (PersistenceException ex) {
            throw new BookNotFoundException("No such Book with ID: " + id);
        }
        Book book = bookE.toBook();
        return book;
    }
    
    @Override
    public List<Book> retrieveAll() throws PersistenceException {
        String jpql = "SELECT s FROM BookEntity s";
        Query query = em.createQuery(jpql);
        List<BookEntity> bookEntityList;
        try {
            bookEntityList = query.getResultList();
        } catch (PersistenceException ex) {
            throw ex;
        }
        List<Book> bookList = bookEntityList.stream()
                                                .map(s -> s.toBook())
                                                .collect(Collectors.toList());
        return bookList;
    }    

    @Override
    public Book updateBook(Book book) throws InvalidInputException {
        BookEntity bookE = new BookEntity(book);
        try {
            em.merge(bookE);
        }catch (PersistenceException ex) {
            throw ex;
        }catch(Exception ex){
            throw new InvalidInputException("Username used.");
        }  
        return book;
    }

    @Override
    public Book deleteBook(Long id) throws BookNotFoundException {
        Book book = retrieve(id);
        String jpql = "DELETE FROM BookEntity s WHERE s.bookID =: id";
        Query query = em.createQuery(jpql);
        query.setParameter("id", id);
        query.executeUpdate();
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
}