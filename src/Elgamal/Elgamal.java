package Elgamal;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

public class Elgamal {

    private BigInteger TWO = new BigInteger("2");
    private BigInteger p;
    private BigInteger order;
    private BigInteger g;
    private BigInteger x;
    private BigInteger y;

    public Elgamal() {
        genkey(10);

    }

    private void genkey(int nb_bits) {
        order = new BigInteger(nb_bits, 10, new SecureRandom());
        p = order.multiply(TWO).add(BigInteger.ONE);
        while (!p.isProbablePrime(10)) {
            order = new BigInteger(nb_bits, 10, new SecureRandom());
            p = order.multiply(TWO).add(BigInteger.ONE);
        }

        g = random_number(p);
        while (!g.modPow(order, p).equals(BigInteger.ONE)) {
            if (g.modPow(order.multiply(TWO), p).equals(BigInteger.ONE)) {
                g = g.modPow(TWO, p);
            } else {
                g = random_number(p);
            }
        }
        x = random_number(p);
        y = g.modPow(x, p);
    }

    public List Encrypt(BigInteger m) {
        List<BigInteger> list = new ArrayList<>();
        BigInteger r = random_number(order);
        BigInteger C1 = g.modPow(r, p);
        BigInteger temp = y.modPow(r, p);
        BigInteger C2 = g.modPow(m, p);
        C2 = temp.multiply(C2);
        list.add(C1);
        list.add(C2);
        return list;
    }

    public BigInteger Decrypt(List list) {
        BigInteger gr = (BigInteger) list.get(0);
        BigInteger hrgm = (BigInteger) list.get(1);
        BigInteger hr = gr.modPow(x, p);
        BigInteger gm = hrgm.multiply(hr.modInverse(p)).mod(p);

        BigInteger m = BigInteger.ONE;
        BigInteger gm_prime = g.modPow(m, p);

        while (!gm_prime.equals(gm)) {
            m = m.add(BigInteger.ONE);
            gm_prime = g.modPow(m, p);
        }

        return m;
    }

    public List Encrypt_Add_Homomorph(List list, List list2) {
        List<BigInteger> list3 = new ArrayList<>();
        BigInteger C1 = (BigInteger) list.get(0);
        BigInteger C2 = (BigInteger) list.get(1);
        BigInteger C3 = (BigInteger) list2.get(0);
        BigInteger C4 = (BigInteger) list2.get(1);
        C1 = C1.multiply(C3);
        C2 = C2.multiply(C4);
        list3.add(C1);
        list3.add(C2);
        return list3;
    }



    private BigInteger random_number(BigInteger n) {
        return new BigInteger(n.bitLength(), new SecureRandom()).mod(n);
    }

}
