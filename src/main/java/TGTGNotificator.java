import controller.AsyncController;
import controller.AuthController;
import properties.Properties;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class TGTGNotificator {

    private static final AuthController authController = new AuthController();
    private static final AsyncController asyncController = new AsyncController();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws IOException, InterruptedException {
        Properties.loadConfig();
        Properties.loadUserCredentials();

        System.out.println("Welcome to TGTG Notificator app.");
        printInitialSettings();
        while(true) {
            System.out.println("Options:");
            System.out.println("\t1. Register");
            System.out.println("\t2. Login and run");
            System.out.println("\t0. Exit");
            System.out.print("Choose option: ");


            int option = -1;
            try {
                option = scanner.nextInt();
            } catch (InputMismatchException e) {
                System.err.println("[ERROR] Undefined option.");
                System.exit(-1);
            }

            if (option == 1)
                register();
            else if (option == 2) {
                login();
                break;
            }
            else if(option == 0){
                System.out.println("[INFO] Application is shutting down...");
                TimeUnit.SECONDS.sleep(3);
                System.exit(0);
            }
            else
                System.err.println("[ERROR] Undefined option. Try again");
        }
        asyncController.run();
    }

    private static void printInitialSettings(){
        System.out.println("Your initial settings:");
        System.out.println("\tLatitude: " + System.getProperty("latitude"));
        System.out.println("\tLongitude: " + System.getProperty("longitude"));
        System.out.println("\tRadius: " + System.getProperty("radius"));
        System.out.println("\tWatcher interval: " + System.getProperty("min_watcher_interval") + "-" + System.getProperty("max_watcher_interval"));
        System.out.println("\tConsole print: " + System.getProperty("console_print"));
        System.out.println("\tWindows notifications: " + System.getProperty("windows_notifications"));
        System.out.println("You can change the settings in the file: config.properties\n");
    }

    private static void register() throws IOException {
        authController.register();
    }

    private static void login() throws IOException {
        authController.login();
    }
}
