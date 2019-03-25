/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package libraryadminterminalclient;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import model.Book;
import session.stateless.remote.BookEntityControllerRemote;
import session.stateless.remote.LendEntityControllerRemote;
import session.stateless.remote.MemberEntityControllerRemote;
import session.stateless.remote.StaffEntityControllerRemote;
import util.exception.BookAlreadyLendedException;
import util.exception.BookNotFoundException;
import util.exception.InvalidLoginCredentialException;
import util.exception.LendNotFoundException;
import util.exception.MemberNotFoundException;

/**
 *
 * @author lester
 */
public class Main {

    @EJB
    private static StaffEntityControllerRemote SEC;
    @EJB
    private static MemberEntityControllerRemote MEC;
    @EJB
    private static BookEntityControllerRemote BEC;
    @EJB
    private static LendEntityControllerRemote LEC;

    public static void main(String[] args) {

        MainApp app = new MainApp(SEC, MEC, BEC, LEC);
        app.runApp();
        
        
//        try {
//            System.out.println(SEC.staffLogin("dssd", "dssd"));
//        } catch (InvalidLoginCredentialException ex) {
//            System.err.print(ex.getMessage());
//        }
    }

}
