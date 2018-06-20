package kom_final;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Gustav Mattsson
 */
public class Timeout implements Runnable {
    private Thread t;
    private String threadName;
    
    // Timeout duration in milliseconds
    private int DURATION = 10000;
    private long timer;
    private boolean timeout;
    private Handler handler;

    public Timeout(Handler handler) {
        timer = 0;
        timeout = false;
        this.handler = handler;
        threadName = "Timer";
    }
    
    synchronized public void startTimer() {
        timer = System.currentTimeMillis();
    }
    
    synchronized public void stopTimer() {
        timer = 0;
    }

    @Override
    public void run() {
        while (true) {
            if(timer != 0) {
                if(System.currentTimeMillis() > (timer + DURATION)) {
                    System.out.println("Timeout reached.");
                    timer = 0;
                    handler.setEvent(PhoneEvent.ERROR);
                }
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(Timeout.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public void start() {
        if(t == null) {
            t = new Thread(this, threadName);
            t.start();
        }
    }
    
    public void reset() {
        timeout = false;
    }
    
    public boolean isTimeout() {
        return timeout;
    }
}
