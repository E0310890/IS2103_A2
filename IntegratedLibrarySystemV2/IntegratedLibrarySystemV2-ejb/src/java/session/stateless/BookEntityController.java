package session.stateless;

import entity.BookEntity;
import entity.LendingEntity;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.ejb.EJB;
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
import model.Lend;
import model.Member;
import session.stateless.local.BookEntityControllerLocal;
import session.stateless.local.LendEntityControllerLocal;
import session.stateless.local.ReservationEntityControllerLocal;
import session.stateless.remote.BookEntityControllerRemote;
import util.exception.InvalidInputException;
import util.exception.InvalidLoginCredentialException;
import util.exception.BookNotFoundException;
import util.exception.MemberNotFoundException;
import util.exception.ReservedByOthersException;

@Stateless
@LocalBean
@Remote(BookEntityControllerRemote.class)
@Local(BookEntityControllerLocal.class)
public class BookEntityController implements BookEntityControllerRemote, BookEntityControllerLocal {

    @PersistenceContext
    private EntityManager em;

    @EJB
    private ReservationEntityControllerLocal REC;
    @EJB
    private LendEntityControllerLocal LEC;

    @Override
    public boolean registerBook(Book Book) throws InvalidInputException {
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
        return retrieve(BookID);
    }

    @Override
    public Book viewBook(long bookID) throws BookNotFoundException {
        Book book = new Book();
        try {
            book = retrieve(bookID).toBook();
        } catch (PersistenceException ex) {
            throw new BookNotFoundException("No such Book with ID: " + bookID);
        }
        return book;
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
    public List<Book> viewBook(String title) throws BookNotFoundException {
        return viewBook()
                .stream()
                .filter(b -> b.getTitle().contains(title))
                .collect(Collectors.toList());
    }

    @Override
    public boolean updateBook(Book Book) throws InvalidInputException {
        try {
            update(new BookEntity(Book));
            return true;
        } catch (PersistenceException ex) {
            return false;
        } catch (ConstraintViolationException cex) {
            throw new InvalidInputException();
        }
    }

    @Override
    public void deleteBook(long BookID) throws BookNotFoundException {
        try {
            BookEntity BookE = retrieve(BookID);
            remove(BookE);
        } catch (PersistenceException ex) {
            throw new BookNotFoundException("No such Book with ID: " + BookID);
        }
    }

    @Override
    public List<Book> searchBook(String title, Member member) throws BookNotFoundException {
        String jpql = "SELECT b from BookEntity as b WHERE b.title LIKE :title";
        Query query = em.createQuery(jpql);
        query.setParameter("title", "%" + title + "%");
        List<BookEntity> bookList = query.getResultList();

        List<Book> store = new ArrayList<>();

        for (BookEntity be : bookList) {
            // check reservation, set book stautes to reserved by other OR reserved by you
            boolean isReserveByOthers = REC.retrieveAll().stream().anyMatch(r -> (r.getBook().getBookID().equals(be.getBookID())) && !(r.getMember().getMemberID().equals(member.getMemberID())));
            if (isReserveByOthers) {
                Book b = be.toBook();
                b.setStatus("Reserved By Others");
                store.add(b);
                continue;
            }
            boolean isReserveBySelf = REC.retrieveAll().stream().anyMatch(r -> (r.getBook().getBookID().equals(be.getBookID())) && (r.getMember().getMemberID().equals(member.getMemberID())));
            if (isReserveBySelf) {
                Book b = be.toBook();
                b.setStatus("Reserved By You");
                store.add(b);
                continue;
            }
            String pattern = "yyyy-MM-dd";
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

            try {
                // check lending, set due day
                Optional<Lend> ole = LEC.ViewLendBooks().stream()
                        .filter(l -> l.getBook().getBookID().equals(be.getBookID()))
                        .findFirst();
                boolean isLendedBySomeone = ole.isPresent();
                if (isLendedBySomeone) {
                    //if self lend
                    if (ole.get().getMember().getMemberID().equals(member.getMemberID())) {
                        Book b = ole.get().getBook();
                        b.setStatus("Book Lended By You");
                        store.add(b);
                        continue;
                    }
                    Book b = ole.get().getBook();
                    b.setStatus("Due on: " + simpleDateFormat.format(ole.get().getDueDate()));
                    store.add(b);
                    continue;
                }

                // inside library, set avaliable 
                Book b = be.toBook();
                b.setStatus("Available");
                store.add(b);

            } catch (MemberNotFoundException ex) {
                Logger.getLogger(BookEntityController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        if (store.isEmpty()) {
            throw new BookNotFoundException("No Search Results..");
        }

        return store;
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

    public BookEntity retrieve(long bookID) throws BookNotFoundException {
        String jpql = "SELECT b FROM BookEntity b WHERE b.bookID = :bookID";
        Query query = em.createQuery(jpql);
        query.setParameter("bookID", bookID);
        BookEntity BookE = new BookEntity();
        try {
            BookE = (BookEntity) query.getSingleResult();
        } catch (PersistenceException ex) {
            throw new BookNotFoundException("This book does not exist.");
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

}
