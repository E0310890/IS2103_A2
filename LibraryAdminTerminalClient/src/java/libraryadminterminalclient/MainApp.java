package libraryadminterminalclient;

import rootOperationModule.LATRootModule;
import session.stateless.remote.BookEntityControllerRemote;
import session.stateless.remote.LendEntityControllerRemote;
import session.stateless.remote.MemberEntityControllerRemote;
import session.stateless.remote.PaymentEntityControllerRemote;
import session.stateless.remote.ReservationEntityControllerRemote;
import session.stateless.remote.StaffEntityControllerRemote;

public class MainApp {

    private StaffEntityControllerRemote SEC;
    private MemberEntityControllerRemote MEC;
    private BookEntityControllerRemote BEC;
    private LendEntityControllerRemote LEC;
    private ReservationEntityControllerRemote REC;
    private PaymentEntityControllerRemote PEC;

    private LATRootModule rootModule;

    public MainApp(StaffEntityControllerRemote SEC, MemberEntityControllerRemote MEC, BookEntityControllerRemote BEC, LendEntityControllerRemote LEC, ReservationEntityControllerRemote REC, PaymentEntityControllerRemote PEC) {
        this.SEC = SEC;
        this.MEC = MEC;
        this.BEC = BEC;
        this.LEC = LEC;
        this.REC = REC;
        this.PEC = PEC;
        rootModule = new LATRootModule(SEC, MEC, BEC, LEC, REC, PEC);
    }

    public void runApp() {
        while (true) {
            rootModule.startRoot();
        }
    }

}
