package javaSHA_256;

import static javaSHA_256.BinaryOperations.*;
import static javaSHA_256.BitwiseOperators.*;
import static javaSHA_256.Matrix.*;

public class JavaSHA_256 {
    private final static int h0 = 0x6a09e667;
    private final static int h1 = 0xbb67ae85;
    private final static int h2 = 0x3c6ef372;
    private final static int h3 = 0xa54ff53a;
    private final static int h4 = 0x510e527f;
    private final static int h5 = 0x9b05688c;
    private final static int h6 = 0x1f83d9ab;
    private final static int h7 = 0x5be0cd19;

    private static int[] h0_b = null;
    private static int[] h1_b = null;
    private static int[] h2_b = null;
    private static int[] h3_b = null;
    private static int[] h4_b = null;
    private static int[] h5_b = null;
    private static int[] h6_b = null;
    private static int[] h7_b = null;

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

    public static String getSHA_256(String line) {
        validateString(line);

        int[][] binaryCharacters = new int[64][Byte.SIZE];

        for (int i = 0; i < line.length(); i++) {
            binaryCharacters[i] = getBinarySet(line.charAt(i), Byte.SIZE);
        }

        binaryCharacters[line.length()][0] = 1;

        binaryCharacters[binaryCharacters.length - 1] = getBinarySet(88, Byte.SIZE);

        int[][] byte_32Words = convertFrom8_ByteWordTo32_ByteWord(binaryCharacters);
        byte_32Words = addPadding(byte_32Words, Integer.SIZE, 48);
        compression(byte_32Words);

        bitFormula(byte_32Words);

        //printMatrix(binaryCharacters, 8);
        printMatrix(byte_32Words, 2);
        return "";
    }

    private static void bitFormula(int[][] byte_32Words) {
        for (int i = 16; i < byte_32Words.length; i++) {

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
        //TODO: Wrong!!!
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
        modifyFinalValues(a, b, c, d, e, f, g, h);
    }

    private static void modifyFinalValues(int[] a, int[] b, int[] c, int[] d, int[] e, int[] f, int[] g, int[] h) {
        h0_b = sum(getBinarySet(h0, Integer.SIZE), a);
        h1_b = sum(getBinarySet(h0, Integer.SIZE), b);
        h2_b = sum(getBinarySet(h0, Integer.SIZE), c);
        h3_b = sum(getBinarySet(h0, Integer.SIZE), d);
        h4_b = sum(getBinarySet(h0, Integer.SIZE), e);
        h5_b = sum(getBinarySet(h0, Integer.SIZE), f);
        h6_b = sum(getBinarySet(h0, Integer.SIZE), g);
        h7_b = sum(getBinarySet(h0, Integer.SIZE), h);
    }

    private static void validateString(String line) {
        if (6 > line.length() || line.length() > 20) {
            throw new IllegalArgumentException("The String is out of bounds. " +
                    "It must be between 6 (inclusive) and 20 (inclusive)!");
        }
//        Pattern pattern = Pattern.compile("^[A-Za-z0-9]+$");
//        Matcher matcher = pattern.matcher(line);
//        if (!matcher.find()) {
//            throw new IllegalArgumentException("The String contains invalid characters!");
//        }
    }
}
