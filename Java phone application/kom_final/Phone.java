package kom_final;

import java.io.IOException;
import java.util.Scanner;

/**
 * @author Gustav Mattsson
 */
public class Phone {

    private Handler handler;
    private Menu menu;

    private int port;
    private boolean faulty;

    private int choice;

    public Phone(boolean faulty, int port) throws IOException {
        handler = new Handler("Handler", port);
        menu = new Menu(faulty);

        this.faulty = faulty;
        this.port = port;
        this.choice = 0;
    }

    public void run() {
        // Starts all needed threads.
        initialize();

        // Begin menu loop
        Scanner scan = new Scanner(System.in);
        do {
            // Print the UI.
            printMenu();
            // Wait for user choice.
            doChoice(scan);
            // Do user choice.
            switch (choice) {
                case 1:
                    if (!handler.isBusy()) {
                        System.out.println("IP to call:");
                        String ipToCall = scan.next();
                        handler.setIP(ipToCall);
                        System.out.println("Port:");
                        int portToCall = scan.nextInt();
                        handler.setPort(portToCall);
                        handler.setEvent(PhoneEvent.SENDINVITE);
                    } else {
                        System.out.println("Phone is busy. Hangup before calling!");
                    }
                    break;
                case 2:
                    // Answer the phone
                    handler.setEvent(PhoneEvent.TRO);
                    break;
                case 3:
                    // Hangup the phone
                    handler.setEvent(PhoneEvent.SENDBYE);
                    break;
                case 4:
                    break;
                case 5:
                    if (faulty && !handler.isBusy()) {
                        System.out.println("IP to call:");
                        String ipToCall = scan.next();
                        handler.setIP(ipToCall);
                        System.out.println("Port:");
                        int portToCall = scan.nextInt();
                        handler.setPort(portToCall);
                        handler.setEvent(PhoneEvent.FAULT_CALL);
                    }
                    break;
                case 6:
                    if (faulty) {
                        handler.setEvent(PhoneEvent.FAULT_TRO);
                    }
                    break;
                case 7:
                    if (faulty) {
                        handler.setEvent(PhoneEvent.FAULT_HANG_UP);
                    }
                    break;
            }
        } while (choice != 0);
        handler.clearThreads();
        System.exit(0);
    }

    private void doChoice(Scanner scan) {
        try {
            choice = scan.nextInt();
        } catch (Exception e) {
            System.out.println("Terminating...");
            handler.clearThreads();
            System.exit(0);
        }
    }

    private void printMenu() {
        menu.printMenu();
        handler.printState();
    }

    private void initialize() {
        handler.start();
    }
}
