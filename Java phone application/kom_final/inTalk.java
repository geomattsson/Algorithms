package kom_final;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created by Yousif on 2017-09-22.
 */
public class inTalk extends PhoneState {

    private Socket socket;
    private AudioStreamUDP audio;

    public inTalk(Handler handler, Socket socket, AudioStreamUDP audio) {
        this.socket = socket;
        this.audio = audio;
        
        try {
            System.out.println("ConnectIP: " + handler.info.connectIP);
            audio.connectTo(InetAddress.getByName(handler.info.connectIP),
                    handler.info.remoteAudioPort);
            audio.startStreaming();
        } catch (UnknownHostException ex) {
            System.out.println("Exception Audio");
        } catch (IOException ex) {
            System.out.println("Exception Audio IOException");
        }
    }

    @Override
    public PhoneState SendBye(Handler handler, Timeout timeout) {
        // Close AUDIO
        audio.stopStreaming();
        // SEND BYE
        try {
            PrintWriter out;
            out = new PrintWriter(socket.getOutputStream(), true);
            out.println("BYE");
        } catch (IOException ex) {
            System.out.println("Failed to send BYE in Talking");
            return new Waiting();
        }
        // Go to terminating and wait for OK.
        return new Terminating(socket, timeout);
    }

    @Override
    public PhoneState ReceivedBye(Handler handler) {
        // Close AUDIO
        audio.stopStreaming();
        
        // SEND OK!
        try {
            PrintWriter out;
            out = new PrintWriter(socket.getOutputStream(), true);
            out.println("OK");
        } catch (IOException ex) {
            handler.reset();
            System.out.println("Failed to send OK in Talking");
            return new Waiting();
        }
        handler.reset();
        
        // Return to waiting
        return new Waiting();
    }

    @Override
    public PhoneState FaultHangup(Handler handler, Timeout timeout) {
        // Close AUDIO
        audio.stopStreaming();
        // SEND BYE
        try {
            PrintWriter out;
            out = new PrintWriter(socket.getOutputStream(), true);
            out.println("B");
        } catch (IOException ex) {
            System.out.println("Failed to send BYE in FaultTalking");
            return new Waiting();
        }
        // Go to terminating and wait for OK.
        return new Terminating(socket, timeout);
    }
    
    @Override
    public PhoneState Tro(Handler handler, AudioStreamUDP audio) {
        return this;
    }
    
    @Override
    public PhoneState SendInvite(Handler handler, Timeout timeout) { 
        return this; 
    }
    
    @Override
    public PhoneState Error(Handler handler) {
        handler.reset();
        return new Waiting();
    }

    @Override
    public void printState() {
        System.out.println("Talking...");
    }
}
