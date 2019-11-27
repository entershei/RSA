import java.math.BigInteger;
import java.util.Scanner;

import static java.lang.System.exit;

// (e, n) - public key
// (d, n) - private key
public class RSA {

    private static BigInteger n; // modulo
    private static BigInteger Fn;
    private static BigInteger e; // GCD(e, (p-1)*(q-1)) == 1
    private static BigInteger d; // d * e == 1 (mod Fn)

    static void initialize() {
        countN();
        countFn();
        countE();
        countD();
    }

    public static void main(String[] args) {
        initialize();

        while (true) {
            System.out.print("Enter message for decryption. It should be an integer from 0 to");
            System.out.println(n.subtract(BigInteger.ONE));

            Scanner scanner = new Scanner(System.in);
            String message = scanner.nextLine();
            BigInteger m = new BigInteger(message);

            if (!checkMessage(m)) {
                exit(1);
            }

            BigInteger encryptedMessage = encrypt(m);
            BigInteger originalMessage = decrypt(encryptedMessage);

            System.out.println("Encrypted message:");
            System.out.println(encryptedMessage);
            System.out.println("Decrypted message:");
            System.out.println(originalMessage);

            if (originalMessage.equals(m)) {
                System.out.println("SUCCESS");
            } else {
                System.out.println("FAILED");
            }

            System.out.println("\n========================================\n\n");
        }
    }

    static boolean checkMessage(BigInteger m) {
        if (m.compareTo(BigInteger.ZERO) < 0 || m.compareTo(n.subtract(BigInteger.ONE)) > 0) {
            System.err.println("Wrong message");
            return false;
        }
        return true;
    }

    static BigInteger encrypt(BigInteger m) {
        return quickPowModN(m, e);
    }

    static BigInteger decrypt(BigInteger c) {
        return quickPowModN(c, d);
    }

    private static BigInteger quickPowModN(BigInteger m, BigInteger pow) {
        if (pow.equals(BigInteger.ZERO)) {
            return BigInteger.ONE;
        }

        if (pow.mod(BigInteger.TWO).equals(BigInteger.ZERO)) {
            BigInteger ret = quickPowModN(m, pow.divide(BigInteger.TWO)).mod(n);
            return ret.multiply(ret).mod(n);
        }

        BigInteger ret = quickPowModN(m, pow.subtract(BigInteger.ONE)).mod(n);
        return ret.multiply(m).mod(n);
    }

    private static void countN() {
        n = Primes.getP().multiply(Primes.getQ());
    }

    private static void countFn() {
        BigInteger p_ = Primes.getP().subtract(BigInteger.ONE);
        BigInteger q_ = Primes.getQ().subtract(BigInteger.ONE);

        Fn = p_.multiply(q_);
    }

    private static void countE() {
        e = Primes.getFermat1().multiply(Primes.getFermat2());
        checkE();
    }

    private static void countD() {
        d = inverseModulo(e, Fn);
    }

    private static void checkE() {
        BigInteger p_ = Primes.getP().subtract(BigInteger.ONE);
        BigInteger q_ = Primes.getQ().subtract(BigInteger.ONE);

        if (p_.mod(Primes.getFermat1()).equals(BigInteger.ZERO)
                || p_.mod(Primes.getFermat2()).equals(BigInteger.ZERO)
                || q_.mod(Primes.getFermat1()).equals(BigInteger.ZERO)
                || q_.mod(Primes.getFermat2()).equals(BigInteger.ZERO)) {
            System.out.println("FAILED: Wrong e!!!");
            exit(1);
        }
    }

    static class GcdResult {
        GcdResult() {}
        BigInteger x;
        BigInteger y;
        BigInteger d;
    }

    // Advanced Euclidean algorithm.
    private static GcdResult gcd(BigInteger a, BigInteger b) {
        GcdResult result = new GcdResult();
        if (a.equals(BigInteger.ZERO)) {
            result.x = BigInteger.ZERO;
            result.y = BigInteger.ONE;
            result.d = b;
            return result;
        }
        result = gcd(b.mod(a), a);
        BigInteger x1 = result.x;
        BigInteger y1 = result.y;
        result.x = y1.subtract(b.divide(a).multiply(x1));
        result.y = x1;
        return result;
    }

    private static BigInteger inverseModulo(BigInteger e, BigInteger f) {
        GcdResult result = gcd (e, f);
        result.x = result.x.mod(f);
        return result.x;
    }
}
