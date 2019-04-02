package libraryadminterminalclient;

import rootOperationModule.LATRootModule;
import session.stateless.remote.BookEntityControllerRemote;
import session.stateless.remote.LendEntityControllerRemote;
import session.stateless.remote.MemberEntityControllerRemote;
import session.stateless.remote.StaffEntityControllerRemote;
import util.exception.FineNotFoundException;
import util.exception.FineNotPaidException;
import util.exception.MemberNotFoundException;
import util.exception.ReservedByOthersException;

public class MainApp {

    private StaffEntityControllerRemote SEC;
    private MemberEntityControllerRemote MEC;
    private BookEntityControllerRemote BEC;
    private LendEntityControllerRemote LEC;

    private LATRootModule rootModule;

    public MainApp(StaffEntityControllerRemote SEC, MemberEntityControllerRemote MEC, BookEntityControllerRemote BEC, LendEntityControllerRemote LEC) {
        this.SEC = SEC;
        this.MEC = MEC;
        this.BEC = BEC;
        this.LEC = LEC;
        rootModule = new LATRootModule(SEC, MEC, BEC, LEC);
    }

    public void runApp() {
        while (true) {
            rootModule.startRoot();
        }
    }

}
