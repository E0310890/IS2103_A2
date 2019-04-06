package session.stateless.remote;

import java.util.List;
import javax.persistence.PersistenceException;
import model.Book;
import util.exception.BookNotFoundException;
import util.exception.InvalidInputException;

public interface BookEntityControllerRemote {
    
    public boolean registerBook(Book Book) throws InvalidInputException;
    public Book viewBook (long BookID) throws BookNotFoundException;
    public List<Book> viewBook();
    public boolean updateBook(Book Book) throws InvalidInputException;
    public void deleteBook(long BookID) throws BookNotFoundException;
}