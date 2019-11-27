import java.math.BigInteger;

class Primes {

    // p, q : 384
    private static BigInteger p = new BigInteger("33478071698956898786044169848212690817704794983713768568912431388982883793878002287614711652531743087737814467999489");
    private static BigInteger q = new BigInteger("36746043666799590428244633799627952632279158164343087642676032283815739666511279233373417143396810270092798736308917");
    // for e: Fermat number
    private static BigInteger fermat1 = new BigInteger("1238926361552897");
    private static BigInteger fermat2 = new BigInteger("93461639715357977769163558199606896584051237541638188580280321");

    static BigInteger getP() {
        return p;
    }

    static BigInteger getQ() {
        return q;
    }

    public static BigInteger getFermat1() {
        return fermat1;
    }

    public static BigInteger getFermat2() {
        return fermat2;
    }
}
