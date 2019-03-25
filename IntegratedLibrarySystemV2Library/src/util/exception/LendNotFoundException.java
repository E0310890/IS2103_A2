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
public class LendNotFoundException extends Exception{

    public LendNotFoundException() {
    }

    public LendNotFoundException(String message) {
        super(message);
    }
}
