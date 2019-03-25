/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package libraryadminterminalclient;

import java.util.Scanner;
import javax.ejb.EJB;
import rootOperationModule.LATRootModule;
import session.stateless.remote.BookEntityControllerRemote;
import session.stateless.remote.LendEntityControllerRemote;
import session.stateless.remote.MemberEntityControllerRemote;
import session.stateless.remote.StaffEntityControllerRemote;

/**
 *
 * @author lester
 */
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
