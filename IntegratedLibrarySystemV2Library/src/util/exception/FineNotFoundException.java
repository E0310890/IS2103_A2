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
public class FineNotFoundException extends Exception{

    public FineNotFoundException() {
    }

    public FineNotFoundException(String message) {
        super(message);
    }
    
}