/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session.stateless.local;

import entity.MemberEntity;
import util.exception.MemberNotFoundException;

/**
 *
 * @author lester
 */
public interface MemberEntityControllerLocal {
    public MemberEntity viewMember(String identityNumber) throws MemberNotFoundException;
//    public int numBookLended(MemberEntity memberE);
}
