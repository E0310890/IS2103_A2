package bookdropmachineclient;

import LoginOperationModule.LoginModule;


public class MainApp {


    public MainApp() {
    }

    public void runApp() {
        while (true) {
            new LoginModule().startRoot();
        }
    }

}
