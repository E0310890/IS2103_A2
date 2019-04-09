/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LoginOperationModule;

import java.util.Scanner;

/**
 *
 * @author lester
 */
public class LoginModule {

    private Scanner sc = new Scanner(System.in);
    //API

    //modules
    private final LoginOperation loginOps;

    //fields
    private int input;

    public LoginModule() {
        loginOps = new LoginOperation();
    }

    private void displayMenu() {
        System.out.println("*** Welcome to Library Admin Terminal ***");
        System.out.println("1: Login\n" + "2: Exit");

        System.out.print("> ");
    }

    private void getInput() {
        while (true) {
            try {
                this.input = sc.nextInt();
                break;
            } catch (Exception ex) {
                System.err.println("please type a valid input");
            }
        }
    }

    public void startRoot() {
        displayMenu();
        getInput();

        setBackInstance();
        navigate(this.input);
    }

    private void setBackInstance() {
        loginOps.setLoginModIn(this);
    }

    private void navigate(int input) {
        switch (input) {
            case 1:
                loginOps.start();
            case 2:
                System.exit(0);
        }
    }

}