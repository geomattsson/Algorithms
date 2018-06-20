package kom_final;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by Yousif on 2017-09-22.
 */
public class Ringing extends PhoneState {

    private Socket socket;
    private Timeout timeout;

    public Ringing(Socket socket, Timeout timeout) {
        this.socket = socket;
        this.timeout = timeout;
        timeout.startTimer();
    }

    @Override
    public PhoneState Ack(Handler handler, AudioStreamUDP audio) {
        timeout.stopTimer();
        return new inTalk(handler, socket, audio);
    }

    @Override
    public PhoneState Tro(Handler handler, AudioStreamUDP audio) {
        timeout.stopTimer();
        timeout.startTimer();
        PrintWriter out = null;
        try {
            out = new PrintWriter(socket.getOutputStream(), true);
            out.println("TRO " + handler.info.localAudioPort);
        } catch (IOException ex) {
            System.out.println("Failed to send TRO in Ringing");
            return new Waiting();
        }
        return this;
    }
    
    @Override
    public PhoneState FaultAnswer(Handler handler) {
        timeout.stopTimer();
        timeout.startTimer();
        PrintWriter out = null;
        try {
            out = new PrintWriter(socket.getOutputStream(), true);
            out.println("T " + handler.info.localAudioPort);
            System.out.println("T " + handler.info.localAudioPort);
        } catch (IOException ex) {
            System.out.println("Failed to send TRO in Ringing");
            
            return new Waiting();
        }
        return this;
    }
    
    @Override
    public PhoneState Error(Handler handler) {
        handler.reset();
        return new Waiting();
    }

    @Override
    public void printState() {
        System.out.println("Ringing!!!");
    }
}
