package javaSHA_256;

import static javaSHA_256.BinaryOperations.*;
import static javaSHA_256.BitwiseOperators.*;
import static javaSHA_256.Matrix.*;

//The algorithm is taken from:
//https://qvault.io/cryptography/how-sha-2-works-step-by-step-sha-256/

public class JavaSHA_256 {

    //Hash values
    //First 32 bits of the fractional parts of the
    // square roots of the first 8 primes: 2, 3, 5, 7, 11, 13, 17, 19
    private static int[] h7_binary;
    private final static int h0 = 0x6a09e667;
    private final static int h1 = 0xbb67ae85;
    private final static int h2 = 0x3c6ef372;
    private final static int h3 = 0xa54ff53a;
    private final static int h4 = 0x510e527f;
    private final static int h5 = 0x9b05688c;
    private final static int h6 = 0x1f83d9ab;
    private final static int h7 = 0x5be0cd19;

    private static int[] h0_binary;
    private static int[] h1_binary;
    private static int[] h2_binary;
    private static int[] h3_binary;
    private static int[] h4_binary;
    private static int[] h5_binary;
    private static int[] h6_binary;

    //Each value (0-63) is the first 32 bits of the fractional
    // parts of the cube roots of the first 64 primes (2 – 311).
    private static final int[] constants = {
            0x428a2f98, 0x71374491, 0xb5c0fbcf, 0xe9b5dba5, 0x3956c25b, 0x59f111f1, 0x923f82a4, 0xab1c5ed5,
            0xd807aa98, 0x12835b01, 0x243185be, 0x550c7dc3, 0x72be5d74, 0x80deb1fe, 0x9bdc06a7, 0xc19bf174,
            0xe49b69c1, 0xefbe4786, 0x0fc19dc6, 0x240ca1cc, 0x2de92c6f, 0x4a7484aa, 0x5cb0a9dc, 0x76f988da,
            0x983e5152, 0xa831c66d, 0xb00327c8, 0xbf597fc7, 0xc6e00bf3, 0xd5a79147, 0x06ca6351, 0x14292967,
            0x27b70a85, 0x2e1b2138, 0x4d2c6dfc, 0x53380d13, 0x650a7354, 0x766a0abb, 0x81c2c92e, 0x92722c85,
            0xa2bfe8a1, 0xa81a664b, 0xc24b8b70, 0xc76c51a3, 0xd192e819, 0xd6990624, 0xf40e3585, 0x106aa070,
            0x19a4c116, 0x1e376c08, 0x2748774c, 0x34b0bcb5, 0x391c0cb3, 0x4ed8aa4a, 0x5b9cca4f, 0x682e6ff3,
            0x748f82ee, 0x78a5636f, 0x84c87814, 0x8cc70208, 0x90befffa, 0xa4506ceb, 0xbef9a3f7, 0xc67178f2
    };

    /**
     * @param line
     * @return unique hash value as String in Hex
     */

    public static String getSHA_256(String line) {
        int[][] binaryCharacters = new int[64][Byte.SIZE];

        for (int i = 0; i < line.length(); i++) {
            binaryCharacters[i] = getBinarySet(line.charAt(i), Byte.SIZE);
        }

        binaryCharacters[line.length()][0] = 1;

        binaryCharacters[binaryCharacters.length - 1] = getBinarySet(line.length() * Byte.SIZE, Byte.SIZE);

        int[][] byte_32Words = to32_ByteWord(binaryCharacters);
        byte_32Words = addPadding(byte_32Words, Integer.SIZE, 48);

        bitFormula(byte_32Words);
        compression(byte_32Words);

        //Concatenate Final Hash
        return toHex(h0_binary)
                + toHex(h1_binary)
                + toHex(h2_binary)
                + toHex(h3_binary)
                + toHex(h4_binary)
                + toHex(h5_binary)
                + toHex(h6_binary)
                + toHex(h7_binary);
    }

    private static void bitFormula(int[][] byte_32Words) {
        for (int i = 16; i < 64; i++) {

            int[] s0 = XOR(
                    XOR(
                            rightRotate(byte_32Words[i - 15], 7),
                            rightRotate(byte_32Words[i - 15], 18)),
                    rightShift(byte_32Words[i - 15], 3));

            int[] s1 = XOR(
                    XOR(
                            rightRotate(byte_32Words[i - 2], 17),
                            rightRotate(byte_32Words[i - 2], 19)),
                    rightShift(byte_32Words[i - 2], 10));

            byte_32Words[i] = sum(sum(byte_32Words[i - 16], s0), sum(byte_32Words[i - 7], s1));
        }
    }

    private static void compression(int[][] byte_32Word) {
        int[] a = getBinarySet(h0, Integer.SIZE);
        int[] b = getBinarySet(h1, Integer.SIZE);
        int[] c = getBinarySet(h2, Integer.SIZE);
        int[] d = getBinarySet(h3, Integer.SIZE);
        int[] e = getBinarySet(h4, Integer.SIZE);
        int[] f = getBinarySet(h5, Integer.SIZE);
        int[] g = getBinarySet(h6, Integer.SIZE);
        int[] h = getBinarySet(h7, Integer.SIZE);

        for (int i = 0; i < 64; i++) {
            int[] S1 = XOR(
                    XOR(
                            rightRotate(e, 6),
                            rightRotate(e, 11)),
                    rightRotate(e, 25)
            );

            int[] ch = XOR(
                    AND(e, f),
                    AND(NOT(e), g)
            );

            int[] temp1 = sum(
                    sum(
                            sum(h, S1),
                            sum(ch, getBinarySet(constants[i], Integer.SIZE))),
                    byte_32Word[i]
            );

            int[] S0 = XOR(
                    XOR(
                            rightRotate(a, 2),
                            rightRotate(a, 13)),
                    rightRotate(a, 22)
            );

            int[] maj = XOR(
                    XOR(
                            AND(a, b), AND(a, c)),
                    AND(b, c)
            );

            int[] temp2 = sum(S0, maj);

            h = g;
            g = f;
            f = e;
            e = sum(d, temp1);
            d = c;
            c = b;
            b = a;
            a = sum(temp1, temp2);
        }
        modifyHashValues(a, b, c, d, e, f, g, h);
    }

    private static void modifyHashValues(int[] a, int[] b, int[] c, int[] d, int[] e, int[] f, int[] g, int[] h) {
        h0_binary = sum(getBinarySet(h0, Integer.SIZE), a);
        h1_binary = sum(getBinarySet(h1, Integer.SIZE), b);
        h2_binary = sum(getBinarySet(h2, Integer.SIZE), c);
        h3_binary = sum(getBinarySet(h3, Integer.SIZE), d);
        h4_binary = sum(getBinarySet(h4, Integer.SIZE), e);
        h5_binary = sum(getBinarySet(h5, Integer.SIZE), f);
        h6_binary = sum(getBinarySet(h6, Integer.SIZE), g);
        h7_binary = sum(getBinarySet(h7, Integer.SIZE), h);
    }
}
