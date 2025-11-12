package xyz.schnorxoborx.base.numbers;

public class TestBigRational2 {

    // test client
    public static void main(String[] args) {
        BigRational x, y, z;

        // 1/2 + 1/3 = 5/6
        x = new BigRational(1, 2);
        y = new BigRational(1, 3);
        z = x.add(y);
        System.out.println(z);

        // 8/9 + 1/9 = 1
        x = new BigRational(8, 9);
        y = new BigRational(1, 9);
        z = x.add(y);
        System.out.println(z);

        // 1/200000000 + 1/300000000 = 1/120000000
        x = new BigRational(1, 200000000);
        y = new BigRational(1, 300000000);
        z = x.add(y);
        System.out.println(z);

        // 1073741789/20 + 1073741789/30 = 1073741789/12
        x = new BigRational(1073741789, 20);
        y = new BigRational(1073741789, 30);
        z = x.add(y);
        System.out.println(z);

        //  4/17 * 17/4 = 1
        x = new BigRational(4, 17);
        y = new BigRational(17, 4);
        z = x.multiplyBy(y);
        System.out.println(z);

        // 3037141/3247033 * 3037547/3246599 = 841/961
        x = new BigRational(3037141, 3247033);
        y = new BigRational(3037547, 3246599);
        z = x.multiplyBy(y);
        System.out.println(z);

        // 1/6 - -4/-8 = -1/3
        x = new BigRational( 1,  6);
        y = new BigRational(-4, -8);
        z = x.multiplyBy(y);
        System.out.println(z);

        // 0
        x = new BigRational(0, 5);
        System.out.println(x);
        System.out.println(x.add(x).compareTo(x) == 0);
        /// StdOut.println(x.reciprocal());   // divide-by-zero

        // -1/200000000 + 1/300000000 = 1/120000000
        x = new BigRational(-1, 200000000);
        y = new BigRational(1, 300000000);
        z = x.add(y);
        System.out.println(z);

    }

}
