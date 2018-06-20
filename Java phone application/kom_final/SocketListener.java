package kom_final;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Gustav Mattsson
 */
public class SocketListener implements Runnable {

    private Thread t;
    private String threadName;

    private Handler handler;
    private ServerSocket serverSocket;
    private Socket socket;

    private boolean running;

    public SocketListener(Handler handler, int port) throws IOException {
        threadName = "SocketListener";
        running = true;
        serverSocket = new ServerSocket(port);
        this.handler = handler;

        System.out.println("SocketListener started.");
    }

    @Override
    public void run() {
        while (running) {
            try {
                socket = serverSocket.accept();
                if (!handler.isBusy()) {
                    BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    String string = in.readLine();
                    if (string.contains("INVITE")) {
                        String s[] = string.split(" ");
                        handler.setSocket(socket);
                        handler.setRemotePort(s[1]);
                        handler.setEvent(PhoneEvent.RECEIVEDINVITE);
                    } else {
                        socket.close();
                    }
                } else {
                    try { // Sleep needed for listener on caller side to launch.
                        Thread.sleep(100);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Timeout.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    PrintWriter out = null;
                    out = new PrintWriter(socket.getOutputStream(), true);
                    out.println("BUSY");
                    out.close();
                    socket.close();
                }
            } catch (IOException ex) {
                System.out.println("ServerSocket exception");
            }
        }
    }

    public void start() {
        if (t == null) {
            t = new Thread(this, threadName);
            t.start();
        }
    }

    public void clear() {
        running = false;
    }

}
