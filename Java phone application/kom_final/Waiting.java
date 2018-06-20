package kom_final;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by Yousif on 2017-09-22.
 */
public class Waiting extends PhoneState {

    public Waiting() {
    }

    @Override
    public PhoneState SendInvite(Handler handler, Timeout timeout) {
        PrintWriter out = null;
        Socket socket = null;
        try {
            socket = new Socket(handler.info.connectIP, handler.info.connectPort);
            handler.setSocket(socket);
            out = new PrintWriter(socket.getOutputStream(), true);
            out.println("INVITE " + handler.info.localAudioPort);
        } catch (IOException ex) {
            System.out.println("Failed to send INVITE + port in Waiting");
            return new Waiting();
        }
        return new Calling(socket, timeout);
    }

    @Override
    public PhoneState ReceivedInvite(Handler handler,
            Socket socket, Timeout timeout) {
        return new Ringing(socket, timeout);
    }

    @Override
    public PhoneState FaultCall(Handler handler, Timeout timeout) {
        PrintWriter out = null;
        Socket socket = null;
        try {
            socket = new Socket(handler.info.connectIP, handler.info.connectPort);
            handler.setSocket(socket);

            out = new PrintWriter(socket.getOutputStream(), true);
            out.println("INV " + handler.info.localAudioPort);
        } catch (IOException ex) {
            System.out.println("Failed to send INV + port in Waiting");
            return new Waiting();
        }
        return new Calling(socket, timeout);
    }

    @Override
    public PhoneState Busy(Handler handler) {
        System.out.println("Phone is busy.");
        return Error(handler);
    }

    @Override
    public PhoneState Error(Handler handler) {
        handler.reset();
        return new Waiting();
    }

    @Override
    public boolean isBusy() {
        return false;
    }

    @Override
    public void printState() {
        System.out.println("Waiting for a call.");
    }
}
