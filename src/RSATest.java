import org.junit.Assert;
import org.junit.Test;

import java.math.BigInteger;
import java.util.Random;

import static org.junit.Assert.*;

public class RSATest {

    @Test
    public void testSimple() {
        RSA.initialize();

        BigInteger m = new BigInteger("2");
        RSA.checkMessage(m);
        Assert.assertEquals(m, RSA.decrypt(RSA.encrypt(m)));
    }

    @Test
    public void testRandom() {
        RSA.initialize();

        for (int i = 1; i < 57; ++i) {
            BigInteger m = new BigInteger(randomM(i * 4));

            if (!RSA.checkMessage(m)) {
                continue;
            }

            Assert.assertEquals(m, RSA.decrypt(RSA.encrypt(m)));
        }

        System.out.println("\n57 tests passed.");
    }

    private String randomM(int len) {
        StringBuilder ret = new StringBuilder();
        Random random = new Random();

        for (int i = 0; i < len; ++i) {
            int digit = random.nextInt();
            if (digit < 0) {
                digit *= -1;
            }

            if (i == 0) {
                ret.append(Integer.toString(digit % 9 + 1));
            } else {
                ret.append(Integer.toString(digit % 10));
            }
        }

        return ret.toString();
    }
}