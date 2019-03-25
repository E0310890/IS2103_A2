/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.exception;

/**
 *
 * @author lester
 */
public class BookAlreadyLendedException extends Exception{

    public BookAlreadyLendedException() {
    }

    public BookAlreadyLendedException(String message) {
        super(message);
    }
    
}
