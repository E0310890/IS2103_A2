/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session.stateless.local;

import entity.BookEntity;
import util.exception.BookNotFoundException;

/**
 *
 * @author lester
 */
public interface BookEntityControllerLocal {
    public BookEntity getBook(long id) throws BookNotFoundException;
}
