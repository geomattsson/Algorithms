import java.util.*;
import java.io.*;
import java.math.BigInteger;

public class StringSender implements Runnable
{
    private Scanner scan; private PrintWriter out;
	private BigInteger keyE, keyN;
	private RSA rsa;
    boolean cont = true;
    
    public StringSender(PrintWriter out, RSA rsa, BigInteger keyE, BigInteger keyN) {
	this.out = out; scan = new Scanner(System.in);
	this.keyE = keyE; this.keyN = keyN;
	this.rsa = rsa;
    }
    
    public void run() {
	while(cont==true) {
	    System.out.print("Send > "); String str = scan.nextLine();
            if(str.length() != 0) {
                out.println(rsa.encrypt(keyE, keyN, str)); out.flush();
            }
	}
    }

    public void stop(){ cont = false; }
}

