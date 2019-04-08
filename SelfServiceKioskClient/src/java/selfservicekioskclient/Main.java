package selfservicekioskclient;

import javax.ejb.EJB;
import session.stateless.remote.BookEntityControllerRemote;
import session.stateless.remote.LendEntityControllerRemote;
import session.stateless.remote.MemberEntityControllerRemote;
import session.stateless.remote.ReservationEntityControllerRemote;
import session.stateless.remote.StaffEntityControllerRemote;

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

    public static void main(String[] args){
        MainApp app = new MainApp(SEC, MEC, BEC, LEC, REC);
        app.runApp();
    }
}