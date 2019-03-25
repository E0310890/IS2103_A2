/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session.stateless.local;

import entity.LendingEntity;
import entity.MemberEntity;
import util.exception.LendNotFoundException;

/**
 *
 * @author lester
 */
public interface LendEntityControllerLocal {
    public LendingEntity getMemberLendCtx(MemberEntity memberE, Long lendId) throws LendNotFoundException;
}
