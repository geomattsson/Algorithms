package kom_final;

import java.net.Socket;

public class Terminating extends PhoneState {
    private Socket socket;
    private Timeout timeout;

    public Terminating(Socket socket, Timeout timeout) {
        this.socket = socket;
        this.timeout = timeout;
        this.timeout.startTimer();
    }
    
    @Override
    public PhoneState Ok(Handler handler) {
        // Received OK
        // Reset handler and return to waiting.
        handler.reset();
        return new Waiting(); 
    }
    
    @Override
    public PhoneState Error(Handler handler) {
        handler.reset();
        return new Waiting();
    }
}
