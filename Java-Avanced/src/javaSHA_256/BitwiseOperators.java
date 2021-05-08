package javaSHA_256;

public final class BitwiseOperators {

    private BitwiseOperators() {
    }

    public static int[] AND(int[] firstArr, int[] secondArr) {
        int length = Math.min(firstArr.length, secondArr.length);
        int[] result = new int[length];

        for (int i = 0; i < result.length; i++) {
            int f = firstArr[i];
            int s = secondArr[i];

            if (f == 1 && s == 1) {
                result[i] = 1;
            } else {
                result[i] = 0;
            }
        }

        return result;
    }

    public static int[] NOT(int[] binarySet) {
        int[] result = new int[binarySet.length];

        for (int i = 0; i < result.length; i++) {
            int currentBit = binarySet[i];

            if (currentBit == 1) {
                result[i] = 0;
            } else {
                result[i] = 1;
            }
        }

        return result;
    }

    public static int[] XOR(int[] firstArr, int[] secondArr) {
        int length = Math.min(firstArr.length, secondArr.length);
        int[] result = new int[length];

        for (int i = 0; i < length; i++) {
            int f = firstArr[i];
            int s = secondArr[i];

            if (f == s) {
                result[i] = 0;
            } else {
                result[i] = 1;
            }
        }

        return result;
    }

    public static int[] rightShift(int[] arr, int times) {
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

    public static int[] rightRotate(int[] arr, int times) {
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
}
