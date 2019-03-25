package util.exception;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author lester
 */
public class InvalidLoginCredentialException extends Exception{
    
    public InvalidLoginCredentialException(){}
    
    public InvalidLoginCredentialException(String msg)
    {
        super(msg);
    }
    
}
