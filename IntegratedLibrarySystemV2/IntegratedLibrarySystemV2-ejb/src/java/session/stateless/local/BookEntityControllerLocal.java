package session.stateless.local;

import java.util.List;
import javax.persistence.PersistenceException;
import model.Book;
import util.exception.BookNotFoundException;
import util.exception.InvalidInputException;

public interface BookEntityControllerLocal {
    
    /* public Book createBook(Book book) throws PersistenceException;
    public boolean registerBook(Book book) throws InvalidInputException;
    public Book viewBook (long id) throws BookNotFoundException;
    public List<Book> viewBook();
    public boolean updateBook(Book book) throws InvalidInputException;
    public boolean deleteBook(Book book); */
    
    public Book createBook(Book book) throws PersistenceException ;    
    public List<Book> retrieveAll() throws PersistenceException; 
    public Book retrieve(long id) throws BookNotFoundException;
}