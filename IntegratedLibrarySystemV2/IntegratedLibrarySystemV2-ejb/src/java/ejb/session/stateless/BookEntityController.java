package ejb.session.stateless;

import Entity.BookEntity;
import dao.BookEntityManager;
import javax.ejb.Stateless;


@Stateless
public class BookEntityController implements BookEntityControllerRemote, BookEntityControllerLocal {
    
    private final BookEntityManager bookEntityManager;

    public BookEntityController() {
        this.bookEntityManager = new BookEntityManager();
    }
    
    @Override
    public BookEntity createNewBook(BookEntity bookEntity) {
        return bookEntityManager.createNewBook(bookEntity);
    }

}
