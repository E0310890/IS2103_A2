/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session.stateless.remote;

import java.util.List;
import model.Fine;
import model.Member;
import util.exception.FineNotFoundException;
import util.exception.LendNotFoundException;
import util.exception.MemberNotFoundException;

/**
 *
 * @author lester
 */
public interface PaymentEntityControllerRemote {

    public boolean payFine(Member member, Long lendId) throws MemberNotFoundException, LendNotFoundException, FineNotFoundException;
    public boolean payFine(String identityNumber, Long lendId) throws MemberNotFoundException, LendNotFoundException, FineNotFoundException;
    public List<Fine> ViewFine(Member member) throws MemberNotFoundException;
    public List<Fine> ViewFine(String identityNumber) throws MemberNotFoundException;

}
