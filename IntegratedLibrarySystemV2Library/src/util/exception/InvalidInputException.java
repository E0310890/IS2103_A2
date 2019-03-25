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
public class InvalidInputException extends Exception {

    public InvalidInputException() {
    }

    public InvalidInputException(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return "Invalid input occur.";
    }
    
    
    
}
