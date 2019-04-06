package session.stateless.remote;

import java.util.List;
import javax.persistence.PersistenceException;
import model.Book;
import util.exception.BookNotFoundException;
import util.exception.InvalidInputException;

public interface BookEntityControllerRemote {
    
    public Book createBook(Book book) throws PersistenceException ;
    // public boolean registerBook(Book book) throws InvalidInputException;
    public Book viewBook (long id) throws BookNotFoundException;
    public List<Book> viewBook();
    public boolean updateBook(Book book) throws InvalidInputException;
    public boolean deleteBook(Book book);
}