package javaSHA_256;

public final class BinaryOperations {
    private BinaryOperations() {
    }

    public static int[] getBinarySet(int num, int size) {
        String value = Integer.toBinaryString(num);
        int[] binaryRecord = new int[size];

        for (int i = 0; i < value.length(); i++) {
            binaryRecord[binaryRecord.length - i - 1] =
                    Integer.parseInt(String.valueOf(value.charAt(value.length() - i - 1)));
        }

        return binaryRecord;
    }

    public static int[][] convertFrom8_ByteWordTo32_ByteWord(int[][] byte_8Words) {
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

    public static int[] sum(int[] firstArr, int[] secondArr) {
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
}
