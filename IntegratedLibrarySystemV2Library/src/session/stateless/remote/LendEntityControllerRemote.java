/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session.stateless.remote;

import java.util.Date;
import java.util.List;
import model.Book;
import model.Lend;
import model.Member;
import util.exception.BookAlreadyLendedException;
import util.exception.BookNotFoundException;
import util.exception.BookOverDueException;
import util.exception.LendNotFoundException;
import util.exception.MemberNotFoundException;

/**
 *
 * @author lester
 */
public interface LendEntityControllerRemote {
    
    public boolean lendBook(Member member, Long bookId) throws MemberNotFoundException, BookNotFoundException, BookAlreadyLendedException;
    public boolean lendBook(String identityNumber, Long bookId) throws MemberNotFoundException, BookNotFoundException, BookAlreadyLendedException;
    public List<Lend> ViewLendBooks(Member member) throws MemberNotFoundException;
    public List<Lend> ViewLendBooks(String identityNumber) throws MemberNotFoundException;
    public boolean ReturnLendBook(Member member, Long lendId) throws MemberNotFoundException, LendNotFoundException;
    public boolean ReturnLendBook(String identityNumber, Long lendId) throws MemberNotFoundException, LendNotFoundException;
    public Date ExtendLendBook(Member member, Long lendId) throws MemberNotFoundException, LendNotFoundException, BookOverDueException;
    public Date ExtendLendBook(String identityNumber, Long lendId) throws MemberNotFoundException, LendNotFoundException, BookOverDueException;
    
}
