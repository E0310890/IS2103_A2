package session.stateless.remote;

import java.util.List;
import model.Book;
import model.Member;
import util.exception.BookNotFoundException;
import util.exception.InvalidInputException;

public interface BookEntityControllerRemote {
    
    public boolean registerBook(Book Book) throws InvalidInputException;
    public Book viewBook (long BookID) throws BookNotFoundException;
    public List<Book> viewBook();
    public List<Book> viewBook (String title) throws BookNotFoundException;
    public boolean updateBook(Book Book) throws InvalidInputException;
    public void deleteBook(long BookID) throws BookNotFoundException;
    public List<Book> searchBook (String title, Member member) throws BookNotFoundException;
}