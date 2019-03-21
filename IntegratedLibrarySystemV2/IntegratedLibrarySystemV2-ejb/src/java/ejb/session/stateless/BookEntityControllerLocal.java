package ejb.session.stateless;

import Entity.BookEntity;
import javax.ejb.Local;


@Local
public interface BookEntityControllerLocal {
    
    BookEntity createNewBook(BookEntity bookEntity);
    
}
