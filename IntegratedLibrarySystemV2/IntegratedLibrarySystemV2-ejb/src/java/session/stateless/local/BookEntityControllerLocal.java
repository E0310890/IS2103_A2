package session.stateless.local;

import entity.BookEntity;
import util.exception.BookNotFoundException;


public interface BookEntityControllerLocal {
   
    public BookEntity viewBookE (long BookID) throws BookNotFoundException;
}
