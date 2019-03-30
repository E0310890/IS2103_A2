package session.stateless;

import dao.BookEntityManager;
import entity.BookEntity;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.ejb.Remote;
import javax.persistence.PersistenceException;
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

    private final BookEntityManager bem = new BookEntityManager();

    @Override
    public boolean registerBook(Book book) throws InvalidInputException {
        try {
            bem.create(new BookEntity(book));
            return true;
        } catch (PersistenceException ex) {
            return false;
        } catch (ConstraintViolationException cex) {
            throw new InvalidInputException();
        }
    }

    @Override
    public Book viewBook(long id) throws BookNotFoundException {
        Book book = new Book();
        try {
            BookEntity be = bem.retrieve(id);
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
            books = bem.retrieveAll()
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
            bem.update(new BookEntity(book));
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
            bem.remove(new BookEntity(book));
            return true;
        } catch (PersistenceException ex) {
            return false;
        }
    }

    @Override
    public BookEntity getBook(long id) throws BookNotFoundException {
        try {
            return bem.retrieve(id);
        } catch (PersistenceException ex) {
            throw new BookNotFoundException("No such book with id: " + id);
        }
    }
}
