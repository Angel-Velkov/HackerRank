import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class JavaSHA_256 {
    /*
    public static void main(String[] args) throws NoSuchAlgorithmException {
        Scanner input = new Scanner(System.in);
        MessageDigest m = MessageDigest.getInstance("SHA-256");
        m.reset();
        m.update(input.nextLine().getBytes());
        for (byte i : m.digest()) {
            System.out.print(String.format("%02x", i));
        }
        System.out.println();
    }
     */

    private final static int h0 = 0x6a09e667;
    private final static int h1 = 0xbb67ae85;
    private final static int h2 = 0x3c6ef372;
    private final static int h3 = 0xa54ff53a;
    private final static int h4 = 0x510e527f;
    private final static int h5 = 0x9b05688c;
    private final static int h6 = 0x1f83d9ab;
    private final static int h7 = 0x5be0cd19;

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

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        String line = "hello world";

        getSHA_256(line);
    }

    private static String getSHA_256(String line) {
        validateString(line);

        int[][] binaryCharacters = new int[64][Byte.SIZE];

        for (int i = 0; i < line.length(); i++) {
            binaryCharacters[i] = getBinarySet(line.charAt(i));
        }

        binaryCharacters[line.length()][0] = 1;

        binaryCharacters[binaryCharacters.length - 1] = getBinarySet(88);

        int[][] byte_32Words = convertFrom8_ByteWordTo32_ByteWord(binaryCharacters);
        byte_32Words = extendMatrix(byte_32Words, Integer.SIZE, 48);

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

    private static int[] rightRotate(int[] arr, int times) {
        while (arr.length <= times) {
            times -= arr.length;
        }

        int[] rotatedArr = new int[arr.length];
        int j = 0;
        for (int i = 0; i < rotatedArr.length; i++) {
            if (times-- > 0) {
                rotatedArr[times] = arr[arr.length - i - 1];
            } else {
                rotatedArr[i] = arr[j++];
            }
        }

        return rotatedArr;
    }

    private static int[] sum(int[] firstArr, int[] secondArr) {
        //TODO: Ensure that they are the same length
        int[] sum = new int[firstArr.length];

        boolean hasAddition = false;
        for (int i = sum.length - 1; i >= 0; i--) {
            int f = firstArr[i];
            int s = secondArr[i];

            if (f == 1 && s == 1) {
                if (hasAddition) {
                    sum[i] = 1;
                } else {
                    sum[i] = 0;
                    hasAddition = true;
                }
            } else if (f == 1 || s == 1) {
                if (hasAddition) {
                    sum[i] = 0;
                } else {
                    sum[i] = 1;
                }
            } else {
                if (hasAddition) {
                    sum[i] = 1;
                    hasAddition = false;
                } else {
                    sum[i] = 0;
                }
            }
        }

        return sum;
    }

    private static int[] rightShift(int[] arr, int times) {
        int[] shiftedArr = new int[arr.length];

        if (times >= shiftedArr.length) {
            return shiftedArr;
        }

        int j = 0;
        for (int i = times; i < shiftedArr.length; i++) {
            shiftedArr[i] = arr[j++];
        }

        return shiftedArr;
    }

    private static int[] XOR(int[] firstArr, int[] secondArr) {
        int length = Math.min(firstArr.length, secondArr.length);
        int[] arr = new int[length];
        for (int i = 0; i < length; i++) {
            int f = firstArr[i];
            int s = secondArr[i];

            if (f == s) {
                arr[i] = 0;
            } else {
                arr[i] = 1;
            }
        }
        return arr;
    }

    private static int[][] extendMatrix(int[][] matrix, int columnSize, int extraSpace) {
        int totalLength = matrix.length + extraSpace;
        int[][] extendedMatrix = new int[totalLength][columnSize];

        System.arraycopy(matrix, 0, extendedMatrix, 0, matrix.length);
        return extendedMatrix;
    }

    private static int[][] convertFrom8_ByteWordTo32_ByteWord(int[][] byte_8Words) {
        int[][] byte_32Words = new int[16][Integer.SIZE];

        int r8 = 0;
        int c8 = 0;
        for (int r32 = 0; r32 < byte_32Words.length; r32++) {
            for (int c32 = 0; c32 < byte_32Words[r32].length; c32++) {
                byte_32Words[r32][c32] = byte_8Words[r8][c8];
                c8++;

                if (c8 % byte_8Words[r8].length == 0) {
                    c8 = 0;
                    r8++;
                }
            }
        }

        return byte_32Words;
    }

    private static int[] getBinarySet(int num) {
        String value = Integer.toBinaryString(num);
        int[] binaryRecord = new int[Byte.SIZE];

        for (int i = 0; i < value.length(); i++) {
            binaryRecord[binaryRecord.length - i - 1] =
                    Integer.parseInt(String.valueOf(value.charAt(value.length() - i - 1)));
        }

        return binaryRecord;
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

    private static void printMatrix(int[][] matrix, int columns) {
        int counter = 0;
        for (int[] array : matrix) {
            Arrays.stream(array).forEach(System.out::print);
            System.out.print(" ");
            if (++counter % columns == 0) {
                System.out.println();
            }
        }
    }
}
