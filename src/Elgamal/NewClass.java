package Elgamal;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class NewClass {

    public static void main(String args[]) {
        Elgamal elgamal = new Elgamal();
        List<BigInteger> list = new ArrayList<>();
        List<BigInteger> list2 = new ArrayList<>();
        list = elgamal.Encrypt(new BigInteger("1"));
        list2 = elgamal.Encrypt(new BigInteger("2"));

        BigInteger m = elgamal.Decrypt(list);
        System.out.println("Decrypt:" + m);
        m = elgamal.Decrypt(list2);
        System.out.println("Decrypt:" + m);
        List<BigInteger> list3 = elgamal.Encrypt_Add_Homomorph(list, list2);
        m = elgamal.Decrypt(list3);
        System.out.println("Add_Homomorph:" + m);
        for(int i=0;i<2;i++){
            list2=elgamal.Encrypt_Add_Homomorph(list2, list2);
        }
        m=elgamal.Decrypt(list2);
        System.out.println(m);

    }
}
