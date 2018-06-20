package kom_final;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import static java.lang.Thread.sleep;
import java.net.Socket;

/**
 * @author Gustav Mattsson
 */
public class Listener implements Runnable {
    private Thread t;
    private String threadName;
    private boolean running = true;
    
    private Socket socket;
    private Handler handler;
    
    public Listener(Socket socket, Handler handler) {
        this.threadName = "Listener";
        this.socket = socket;
        this.handler = handler;
        
        System.out.println("Listener started.");
    }
    
    private void processNext(String message) {
        String s[] = message.split(" ");
        switch(s[0]) {
            case "INVITE": {
                handler.setEvent(PhoneEvent.RECEIVEDINVITE);
                handler.setRemotePort(s[1]);
            } break;
            case "TRO": {
                handler.setEvent(PhoneEvent.TRO);
                handler.setRemotePort(s[1]);
            } break;
            case "ACK": {
                handler.setEvent(PhoneEvent.ACK);
            } break;
            case "BYE": {
                handler.setEvent(PhoneEvent.RECEIVEDBYE);
            } break;
            case "OK": {
                handler.setEvent(PhoneEvent.OK);
            } break;
            case "BUSY": {
                handler.setEvent(PhoneEvent.BUSY);
            } break;
            default: {
                handler.setEvent(PhoneEvent.ERROR);
            } break;
        }
    }

    @Override
    public void run() {
        while(running) {
            BufferedReader in;
            try {
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String string = in.readLine();
                if(string != null) {
                    processNext(string);
                }
            } catch (IOException ex) {}
            try {
                sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
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
