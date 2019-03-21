package dao;

import Entity.BookEntity;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.Persistence;
import javax.persistence.Query;
import util.exception.BookNotFoundException;


public class BookEntityManager {
    
    //Code when need to use Entity manager outside of container
    private static final EntityManagerFactory factory = Persistence.createEntityManagerFactory("IntegratedLibrarySystemV2-ejbPU");
    private final EntityManager entityManager = factory.createEntityManager();

    public BookEntityManager() {
    }
    
    public BookEntity createNewBook(BookEntity newBookEntity){
        entityManager.persist(newBookEntity);
        entityManager.flush();
        
        return newBookEntity;
    }
    
    
    public List<BookEntity> retrieveAllBooks(){
        Query query = entityManager.createQuery("SELECT b FROM BookEntity b");
        
        return query.getResultList();
    }
    
    
    public BookEntity retrieveBookByBookId(Long bookId) throws BookNotFoundException{
        BookEntity bookEntity = entityManager.find(BookEntity.class, bookId);
        
        if(bookEntity != null){
            return bookEntity;
        }else{
            throw new BookNotFoundException("Book ID " + bookId + " does not exist!");
        }
    }
    
    public BookEntity retrieveBookByTitle(String title) throws BookNotFoundException{
        Query query = entityManager.createQuery("SELECT b FROM BookEntity b WHERE s.title = :inTitle");
        query.setParameter("inTitle", title);
        
        try{
            return (BookEntity)query.getSingleResult();
        }catch(NoResultException | NonUniqueResultException ex){
            throw new BookNotFoundException("Book Title " + title + " does not exist!");
        }
    }
    
        public void updateBook(BookEntity bookEntity){
        entityManager.merge(bookEntity);
    }
    
    
    public void deleteBook(Long bookId) throws BookNotFoundException{
        BookEntity bookEntityToRemove = retrieveBookByBookId(bookId);
        entityManager.remove(bookEntityToRemove);
    }
    
}
