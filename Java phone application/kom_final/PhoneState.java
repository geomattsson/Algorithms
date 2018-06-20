package kom_final;

import java.net.Socket;

/**
 * Created by Yousif on 2017-09-22.
 */
public abstract class PhoneState {
    
    public PhoneState SendInvite(Handler handler, 
            Timeout timeout) { return Error(handler); };
    
    public PhoneState ReceivedInvite(Handler handler, Socket socket, 
            Timeout timeout) { return Error(handler); };
    
    public PhoneState Ack(Handler handler, AudioStreamUDP audio) 
            { return Error(handler); };
    
    public PhoneState Tro(Handler handler, AudioStreamUDP audio) 
            { return Error(handler); };
    
    public PhoneState Ok(Handler handler) { return Error(handler); };
    
    public PhoneState SendBye(Handler handler, Timeout timeout) 
            { return Error(handler); };
    
    public PhoneState ReceivedBye(Handler handler) { return Error(handler); };
    
    public PhoneState FaultCall(Handler handler, Timeout timeout) 
            { return Error(handler);};
    
    public PhoneState FaultAnswer(Handler handler) { return Error(handler); };
    
    public PhoneState FaultHangup(Handler handler, Timeout timeout) 
            { return Error(handler); };
    
    public PhoneState Error(Handler handler) { return this; };
    public PhoneState Busy(Handler handler) { return this; };
    
    public void printState() {};
    public boolean isBusy(){ return true; };
}
