/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.enumeration;

/**
 *
 * @author lester
 */
public enum Gender {
    MALE, FEMALE;

    public static Gender getEnumGender(String gen) {
        if(gen.equalsIgnoreCase("Male")){
            return MALE;
        }else if(gen.equalsIgnoreCase("Female")){
            return FEMALE;
        }
        return null;
    }
}
