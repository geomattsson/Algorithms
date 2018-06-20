package kom_final;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Yousif on 2017-09-22.
 */
public class Calling extends PhoneState {
    private final Socket socket;
    private final Timeout timeout;

    public Calling(Socket socket, Timeout timeout) {
        this.socket = socket;
        this.timeout = timeout;
        this.timeout.startTimer();
    }

    @Override
    public PhoneState Tro(Handler handler, AudioStreamUDP audio) {
        // Stop timeout
        timeout.stopTimer();
        try {
            // Received TRO sending ACK
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            out.println("ACK");

            // Return inTalk()
            return new inTalk(handler, socket, audio);
        } catch (IOException ex) {
            Logger.getLogger(Calling.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new Waiting();
    }
    
    @Override
    public PhoneState Busy(Handler handler){
        System.out.println("ERROR: Phone is busy.");
        return Error(handler);
    }

    @Override
    public PhoneState Error(Handler handler) {
        handler.reset();
        return new Waiting();
    }

    @Override
    public void printState() {
        System.out.println("Calling.");
    }
}
