package kom_final;

import java.io.IOException;
/**
 * @author Gustav Mattsson
 */
public class Main {

    public static void main(String[] args) {
        System.out.println("Version 1.1");
        int port = 16700;
        boolean faulty = false;
        if (args.length == 1) {
            port = Integer.parseInt(args[0]);
        } else if (args.length == 2) {
            port = Integer.parseInt(args[0]);
            faulty = true;
        }
        Phone phone;
        try {
            phone = new Phone(faulty, port);
            phone.run();
        } catch (IOException ex) {
            System.out.println("Failed to initializer AudioStream. Exiting...");
        }
    }
}
