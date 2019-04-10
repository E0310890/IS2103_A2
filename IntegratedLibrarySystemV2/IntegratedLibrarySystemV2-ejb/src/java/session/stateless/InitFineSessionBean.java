package session.stateless;

import session.stateless.remote.InitFineSessionBeanRemote;
import entity.LendingEntity;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.LocalBean;
import javax.ejb.Remote;
import session.stateless.local.InitFineSessionBeanLocal;
import javax.ejb.Stateless;
import session.stateless.local.BookEntityControllerLocal;
import session.stateless.local.LendEntityControllerLocal;
import session.stateless.local.MemberEntityControllerLocal;
import session.stateless.local.PaymentEntityControllerLocal;
import session.stateless.local.ReservationEntityControllerLocal;

@Stateless
@LocalBean
@Remote(InitFineSessionBeanRemote.class)
@Local(InitFineSessionBeanLocal.class)
public class InitFineSessionBean implements InitFineSessionBeanRemote, InitFineSessionBeanLocal {

    @EJB
    private MemberEntityControllerLocal MEC;
    @EJB
    private BookEntityControllerLocal BEC;
    @EJB
    private PaymentEntityControllerLocal PEC;
    @EJB
    private LendEntityControllerLocal LEC;
    @EJB
    private ReservationEntityControllerLocal REC;
    
    @Override
    public void setUpFine() {
        List lendingEntity = LEC.retrieveAll();
        System.out.println(lendingEntity.size());
        for (Object o : lendingEntity) {
            LendingEntity le = (LendingEntity) o;
            boolean checkDue = LEC.isOverDue(le);
                if (checkDue) {
                    if (PEC.initFine(le)) {
                        PEC.updateFine(le);
                    }
                    else {
                        PEC.createFine(le);
                    }
                }
        }
    }
}
