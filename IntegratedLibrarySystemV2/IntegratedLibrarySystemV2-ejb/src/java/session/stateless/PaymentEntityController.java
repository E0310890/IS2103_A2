/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session.stateless;

import dao.PaymentEntityManager;
import entity.LendingEntity;
import entity.MemberEntity;
import entity.PaymentEntity;
import java.util.List;
import java.util.stream.Collectors;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.ejb.Remote;
import javax.persistence.PersistenceException;
import model.Fine;
import model.Member;
import session.stateless.local.LendEntityControllerLocal;
import session.stateless.local.MemberEntityControllerLocal;
import session.stateless.remote.PaymentEntityControllerRemote;
import util.exception.FineNotFoundException;
import util.exception.LendNotFoundException;
import util.exception.MemberNotFoundException;

/**
 *
 * @author lester
 */
@Stateless
@LocalBean
@Remote(PaymentEntityControllerRemote.class)
public class PaymentEntityController implements PaymentEntityControllerRemote {

    @EJB
    private MemberEntityControllerLocal MEC;
    @EJB
    private LendEntityControllerLocal LEC;

    private final PaymentEntityManager pem = new PaymentEntityManager();

    @Override
    public boolean payFine(Member member, Long lendId) throws MemberNotFoundException, LendNotFoundException, FineNotFoundException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean payFine(String identityNumber, Long lendId) throws MemberNotFoundException, LendNotFoundException, FineNotFoundException {
        try {
            MemberEntity memberE = MEC.viewMember(identityNumber);
            LendingEntity currentLendCtx = LEC.getMemberLendCtx(memberE, lendId);

            return pay(currentLendCtx.getPayment());
            
        } catch (MemberNotFoundException | LendNotFoundException ex) {
            throw ex;
        }
    }

    @Override
    public List<Fine> ViewFine(Member member) throws MemberNotFoundException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Fine> ViewFine(String identityNumber) throws MemberNotFoundException {
        try {
            MemberEntity memberE = MEC.viewMember(identityNumber);
            List<PaymentEntity> paymentList = getAllFines(memberE);

            return paymentList.stream()
                    .map(p -> p.toFine())
                    .collect(Collectors.toList());

        } catch (MemberNotFoundException ex) {
            throw ex;
        }
    }

    private List<PaymentEntity> getAllFines(MemberEntity memberE) {
        List<LendingEntity> lendingList = memberE.getLending();
        return lendingList.stream()
                .filter(le -> le.getPayment().fineExist())
                .map(le -> le.getPayment())
                .collect(Collectors.toList());
    }

    private boolean pay(PaymentEntity payment) throws FineNotFoundException {
        try {
            if (payment.fineExist()) {
                payment.setPaid(true);
                pem.update(payment);
            } else {
                throw new FineNotFoundException("No Fine Found.");
            }
            return true;
        } catch(PersistenceException ex){
            return false;
        }
    }

}
