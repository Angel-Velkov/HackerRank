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

    public static String toHex(int[] binarySet) {
        StringBuilder hex = new StringBuilder();

        int start = 0;
        int end;
        for (int i = 0; i < binarySet.length / 4; i++) {
            end = start + 3;
            int[] piece = new int[4];
            System.arraycopy(binarySet, start, piece, 0, piece.length);
            start = end + 1;

            hex.append(getHexDigit(piece));
        }
        return hex.toString();
    }

    private static char getHexDigit(int[] binarySet) {
        if (binarySet.length != 4) {
            throw new IllegalArgumentException("Binary set length should be 4");
        }

        byte result = 0;
        for (int i = binarySet.length - 1; i >= 0; i--) {
            result += binarySet[i] * Math.pow(2, binarySet.length - 1 - i);
        }

        switch (result) {
            case 10:
                return 'A';
            case 11:
                return 'B';
            case 12:
                return 'C';
            case 13:
                return 'D';
            case 14:
                return 'E';
            case 15:
                return 'F';
            default:
                return String.valueOf(result).charAt(0);
        }
    }

    public static int[][] to32_ByteWord(int[][] byte_8Words) {
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
        if (firstArr.length > secondArr.length) {
            secondArr = extendArrayInFront(secondArr, firstArr.length);

        } else if (firstArr.length < secondArr.length) {
            firstArr = extendArrayInFront(firstArr, secondArr.length);
        }

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

    private static int[] extendArrayInFront(int[] arr, int newLength) {
        int[] extendedArr = new int[newLength];
        int pointer = arr.length - 1;

        for (int i = extendedArr.length - 1; i >= 0; i--) {
            extendedArr[i] = arr[pointer--];
        }
        arr = extendedArr;
        return arr;
    }
}
