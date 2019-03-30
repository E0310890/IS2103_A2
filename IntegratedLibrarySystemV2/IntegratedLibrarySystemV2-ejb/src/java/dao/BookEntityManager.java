package dao;

import entity.BookEntity;
import entity.MemberEntity;
import java.util.List;
import javax.ejb.Remove;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

public class BookEntityManager {

    private final EntityManagerFactory emf = Persistence.createEntityManagerFactory("IntegratedLibrarySystemV2-ejbPU");
    private final EntityManager em = emf.createEntityManager();

    public BookEntityManager() {
    }

    public void create(BookEntity be) throws PersistenceException {
        try {
            if (be.getBookID() == null) {
                em.joinTransaction();
                em.persist(be);
            }
        } catch (PersistenceException ex) {
            throw ex;
        }
    }

    public void remove(BookEntity be) throws PersistenceException {
        try {
            em.joinTransaction();
            be = em.find(BookEntity.class, be.getBookID());
            em.remove(be);
        } catch (PersistenceException ex) {
            throw ex;
        }
    }

    public void update(BookEntity be) throws PersistenceException {
        try {
            if (be.getBookID() != null) {
                em.joinTransaction();
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
}
