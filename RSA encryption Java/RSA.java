import java.math.BigInteger;
import java.util.Random;

/**
 * @author Gustav and Yousif
 */
public class RSA {
    private final BigInteger p;
    private final BigInteger q;
    private final BigInteger N;
    private final BigInteger phiN;
    private final BigInteger e;
    private final BigInteger d;
    private final Random r;
    
    private String publicKey;
    
    // Create a RSA key pair with the specified bit length.
    public RSA(int bitLength) {
        r = new Random(); // Random type object
        // Getting the prime for p and q
        p = BigInteger.probablePrime(bitLength, r);
        q = BigInteger.probablePrime(bitLength, r);
        
        // N = p*q;
        N = p.multiply(q);
        
        // phi(N) = (p-1)(q-1);
        phiN = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));
        
        // Getting the prime for e
        e = BigInteger.probablePrime(bitLength / 2, r);
        
        // Determines e as a coprime to phiN if probablePrime doesn't hit.
        while (phiN.gcd(e).compareTo(BigInteger.ONE) > 0 && e.compareTo(phiN) < 0)
            e.add(BigInteger.ONE);
        
        // d * e % phiN = 1 <=> the modular multiplicative inverse of e % phiN
        d = e.modInverse(phiN);
        
        // The public key to encrypt with for this key pair.
        publicKey = e.toString(10) + ";" + N.toString(10);
    }
    
    // Does encryption from non local e and N.
    public String encrypt(BigInteger e, BigInteger N, String msg) {
        return (new BigInteger(msg.getBytes())).modPow(e, N).toString(10);
    }
    
    // Does decryption with local d and N.
    public String decrypt(byte[] msg) {
        byte[] temp = (new BigInteger(msg)).modPow(d, N).toByteArray();
        return new String(temp);
    }
    
    // Return the public key for this RSA.
    public String getPublic() {
        return publicKey;
    }
}
