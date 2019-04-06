package session.stateless.local;

import entity.BookEntity;
import util.exception.BookNotFoundException;

public interface BookEntityControllerLocal {
    public BookEntity viewBook(String isbn) throws BookNotFoundException;
//    public int numBookLended(MemberEntity memberE);
}
