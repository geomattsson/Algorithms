package kom_final;

import java.io.IOException;
import java.net.Socket;
import static java.lang.Thread.sleep;

/**
 * @author Gustav Mattsson
 */
public class Handler implements Runnable {
    private Thread t;
    private String threadName;
    private boolean running = true;
    
    private PhoneState state;
    private AudioStreamUDP audio;
    private Timeout timeout;
    
    private SocketListener socketListener;
    private Listener listener;
    private Socket socket;
    public ConnectInfo info;
    
    private PhoneEvent userEvent;

    public Handler(String threadName, int serverPort) throws IOException {
        this.threadName = threadName;
        
        state = new Waiting();
        timeout = new Timeout(this);
        socketListener = new SocketListener(this, serverPort);
        info = new ConnectInfo();
        
        audio = new AudioStreamUDP();
        info.localAudioPort = audio.getLocalPort();
    }

    synchronized public void setEvent(PhoneEvent event) {
        this.userEvent = event;
    }

    synchronized public boolean isBusy() {
        return state.isBusy();
    }

    public void printState() {
        state.printState();
    }

    public void clearThreads() {
        running = false;
        listener.clear();
        socketListener.clear();
    }

    synchronized public void setPort(int port) {
        info.connectPort = port;
    }

    synchronized public void setIP(String ip) {
        info.connectIP = ip;
    }
    synchronized public void setRemotePort(String port) {
        info.remoteAudioPort = Integer.parseInt(port);
        System.out.println("RemotePort: " + info.remoteAudioPort);
    }
    
    synchronized public void setSocket(Socket socket) {
        this.socket = socket;
        startListener();
    }
    
    synchronized public void startListener() {
        listener = new Listener(socket, this);
        listener.start();
    }
    
    synchronized public void stopListener() {
        listener.clear();
    }

    @Override
    public void run() {
        running = true;
        while (running) {
            PhoneEvent event = userEvent;
            if (event != null) {
                switch (event) {
                    case SENDINVITE: {
                        state = state.SendInvite(this, timeout);
                    }
                    break;
                    case RECEIVEDINVITE: {
                        state = state.ReceivedInvite(this, socket, timeout);
                    }
                    break;
                    case TRO: {
                        state = state.Tro(this, audio);
                    }
                    break;
                    case ACK: {
                        state = state.Ack(this, audio);
                    }
                    break;
                    case SENDBYE: {
                        state = state.SendBye(this, timeout);
                    }
                    break;
                    case RECEIVEDBYE: {
                        state = state.ReceivedBye(this);
                    }
                    break;
                    case OK: {
                        state = state.Ok(this);
                    }
                    break;
                    case BUSY: {
                        state = state.Busy(this);
                    } break;
                    case ERROR: {
                        state = state.Error(this);
                    }
                    break;
                    case FAULT_CALL: {
                        state = state.FaultCall(this, timeout);
                    } break;
                    case FAULT_TRO: {
                        state = state.FaultAnswer(this);
                    } break;
                    case FAULT_HANG_UP: {
                        state = state.FaultHangup(this, timeout);
                    } break;
                }
                userEvent = null;
            }
            try {
                sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    
    public void start() {
        if(t == null) {
            t = new Thread(this, threadName);
            t.start();
        }
        timeout.start();
        socketListener.start();
    }
    
    synchronized public void reset() {
        try {
            this.stopListener();
            timeout.stopTimer();
            audio.stopStreaming();
            if(socket != null) socket.close();
        } catch (IOException ex) {
            System.out.println("Handler reset failed.");
        }
    }
}
