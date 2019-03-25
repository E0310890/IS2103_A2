/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;

/**
 *
 * @author lester
 */
public class Fine implements Serializable {
    private Long lendID;
    private double fineAmount;

    public Fine() {
    }
    
    public Fine(Long lendID, double fineAmount) {
        this.lendID = lendID;
        this.fineAmount = fineAmount;
    }

    public Long getLendID() {
        return lendID;
    }

    public double getFineAmount() {
        return fineAmount;
    }
    
    
}
