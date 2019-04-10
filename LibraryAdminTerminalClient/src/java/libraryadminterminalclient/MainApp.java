package libraryadminterminalclient;

import java.time.*;
import java.util.*;
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

    public boolean checkDay() {
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
      
        if (dayOfWeek >= 1 && dayOfWeek <= 5) {
            return true;
        }
        return false;
    }
    
    public boolean checkTime() {
        int currentHour = LocalTime.now().getHour();
        if (currentHour >= 9 && currentHour <= 17) {
            return true;
        }
        return false;
    }
    
    public boolean isAvailable() {
        if (checkDay() && checkTime()) {
            return true;
        }
        return false;
    }
                
    public void runApp() {   
        if (isAvailable()) {
            rootModule.startRoot();
        }
        System.out.println("Library Operating Hours: Monday to Friday, from 09:00 to 17:00");
        System.exit(0);
    }
}