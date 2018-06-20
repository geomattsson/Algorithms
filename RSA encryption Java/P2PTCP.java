import java.io.*;
import java.math.BigInteger;
import java.net.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class P2PTCP {
    private static BigInteger keyE = null;
    private static BigInteger keyN = null;
	
    private static boolean handshakeLeft(RSA rsa, Socket socket, PrintWriter out) {
        boolean ret = false;
        try {
            Scanner scan = new Scanner(socket.getInputStream());
            
            String key = scan.nextLine();
            String[] p = key.split(";");
            keyE = new BigInteger(p[0]);
            keyN = new BigInteger(p[1]);
            Random r = new Random();
            int test = r.nextInt(100) + 1;
            
            String send = rsa.encrypt(keyE, keyN, Integer.toString(test));
            out.println(send); out.flush();
			
            String result = scan.nextLine();
            if(Integer.compare(test, Integer.parseInt(result)) == 0) ret = true;
        } catch (IOException ex) {
            Logger.getLogger(P2PTCP.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return ret;
    }
    
    private static void handshakeRight(Socket peerConnectionSocket, PrintWriter out, RSA rsa){
        String msg = "";
        out.println(rsa.getPublic());
        out.flush();
       
        try {
            Scanner scan = new Scanner(peerConnectionSocket.getInputStream());
            msg = scan.nextLine();
        } catch (IOException ex) {
            Logger.getLogger(P2PTCP.class.getName()).log(Level.SEVERE, null, ex);
        }
        out.println(rsa.decrypt((new BigInteger(msg)).toByteArray()));
        out.flush();
    }
    
    public static void main(String[] args) {
        Scanner scan; Thread st = null;
        Socket peerConnectionSocket = null;
        RSA rsa = null;
        if(args[0].equals("server")) rsa = new RSA(Integer.parseInt(args[2]));
        else if(args[0].equals("client")) rsa = new RSA(Integer.parseInt(args[3]));
		
        if(args[0].equals("server")){
            try{
                ServerSocket ss = new ServerSocket(Integer.parseInt(args[1]));
                System.out.println("Waiting for connection...");
                peerConnectionSocket = ss.accept();

                handshakeRight(peerConnectionSocket, new PrintWriter(
                        peerConnectionSocket.getOutputStream()), rsa);

                if(handshakeLeft(rsa, peerConnectionSocket, new PrintWriter(
                        peerConnectionSocket.getOutputStream()))) {
                    st = new Thread(new StringSender(new PrintWriter(
                            peerConnectionSocket.getOutputStream()), rsa, keyE, keyN));
                    st.start();
                    
                    scan = new Scanner (peerConnectionSocket.getInputStream());
                    String fromSocket;
                    while((fromSocket = scan.nextLine()) != null) {
                        System.out.println(rsa.decrypt((new BigInteger(
                                fromSocket)).toByteArray()));
                    }
                }
            }catch(IOException e) {System.err.println("Server crash");}
            finally {st.stop();}
        }
        else if(args[0].equals("client")) {
            try{
                peerConnectionSocket = new Socket(args[1], Integer.parseInt(args[2]));
				
                if(handshakeLeft(rsa, peerConnectionSocket, new PrintWriter(
                        peerConnectionSocket.getOutputStream()))) {
                    handshakeRight(peerConnectionSocket, new PrintWriter(
                            peerConnectionSocket.getOutputStream()), rsa);
                    
                    st = new Thread(new StringSender(new PrintWriter(
                                    peerConnectionSocket.getOutputStream()), rsa, keyE, keyN));
                    st.start();
					
                    scan = new Scanner(peerConnectionSocket.getInputStream());
                    String fromSocket;
                    while((fromSocket = scan.nextLine())!=null) {
                        System.out.println(rsa.decrypt((new BigInteger(
                                fromSocket)).toByteArray()));
                    }
                }
            } catch(Exception e) {System.err.println("Client crash");}
            finally{st.stop();}
        }
    }
}