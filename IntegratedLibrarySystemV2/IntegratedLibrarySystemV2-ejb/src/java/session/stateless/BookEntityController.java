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
public class BookEntityController implements BookEntityControllerRemote, BookEntityControllerLocal {

    @PersistenceContext
    private EntityManager em;
    
    @Override
    public boolean registerBook(Book Book) throws InvalidInputException{
        BookEntity me = new BookEntity(Book);
        try {
            if (me.getBookID() == null) {
                em.persist(me);
                em.flush();
            }
            return true;
        } catch (Exception ex) {
            throw new InvalidInputException("Please input correct personal details");
        }
    }

    @Override
    public Book viewBook(long BookID) throws BookNotFoundException {
        Book Book = new Book();
        try {
            BookEntity me = retrieve(BookID);
            Book = me.toBook();
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
                    .map(m -> m.toBook())
                    .collect(Collectors.toList());
            return Books;
        } catch (PersistenceException ex) {
            return new ArrayList();
        }
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
     
   @Override
   public BookEntity viewBook(String identityNumber) throws BookNotFoundException{
        try {
            return retrieve(identityNumber);
        } catch (PersistenceException ex) {
            throw new BookNotFoundException("No such Book with identity number: " + identityNumber);
        }
   }
   
    public void remove(BookEntity me) throws PersistenceException {
        try {
            me = em.find(BookEntity.class, me.getBookID());
            em.remove(me);
        } catch (PersistenceException ex) {
            throw ex;
        }
    }

    public void update(BookEntity me) throws PersistenceException {
        try {
            if (me.getBookID() != null) {
                em.merge(me);
            }
        } catch (PersistenceException ex) {
            throw ex;
        }
    }

    public BookEntity retrieve(long id) throws PersistenceException {
        String jpql = "SELECT m FROM BookEntity m WHERE m.BookID = :id";
        Query query = em.createQuery(jpql);
        query.setParameter("id", id);
        BookEntity BookE = new BookEntity();
        try {
            BookE = (BookEntity) query.getSingleResult();
        } catch (PersistenceException ex) {
            throw ex;
        }
        return BookE;
    }

    public BookEntity retrieve(String identityNumber) throws PersistenceException {
        String jpql = "SELECT m FROM BookEntity m WHERE m.identityNumber = :idn";
        TypedQuery query = em.createQuery(jpql, BookEntity.class);
        query.setParameter("idn", identityNumber);
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
        String jpql = "SELECT m FROM BookEntity m";
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