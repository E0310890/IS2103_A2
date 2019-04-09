package libraryadminterminalclient;

import javax.ejb.EJB;
import session.stateless.remote.BookEntityControllerRemote;
import session.stateless.remote.LendEntityControllerRemote;
import session.stateless.remote.MemberEntityControllerRemote;
import session.stateless.remote.PaymentEntityControllerRemote;
import session.stateless.remote.StaffEntityControllerRemote;
import session.stateless.remote.ReservationEntityControllerRemote;

public class Main {

    @EJB
    private static StaffEntityControllerRemote SEC;
    @EJB
    private static MemberEntityControllerRemote MEC;
    @EJB
    private static BookEntityControllerRemote BEC;
    @EJB
    private static LendEntityControllerRemote LEC;
    @EJB
    private static ReservationEntityControllerRemote REC;
    @EJB
    private static PaymentEntityControllerRemote PEC;

    public static void main(String[] args) {

        MainApp app = new MainApp(SEC, MEC, BEC, LEC, REC, PEC);
        app.runApp();
    }
}