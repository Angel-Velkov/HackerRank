import java.util.Arrays;

public class Test {
    public static void main(String[] args) {
//        int[] firstArr = {0, 1, 1, 0, 1, 0, 0, 0, 0, 1, 1, 0, 0, 1, 0, 1, 0, 1, 1, 0, 1, 1, 0, 0, 0, 1, 1, 0, 1, 1, 0, 0};
//        int[] seconArr = {1, 1, 0, 0, 1, 1, 1, 0, 1, 1, 1, 0, 0, 0, 0, 1, 1, 0, 0, 1, 0, 1, 0, 1, 1, 1, 0, 0, 1, 0, 1, 1};
//        //                0  0  1  1  0  1  1  1  0  1  0  0  0  1  1  1  0  0  0  0  0  0  1  0  0  0  1  1  0  1  1  1
//
//        firstArr = sum(firstArr, seconArr);

        int[] arr = {0, 1, 1, 0, 1, 1, 1, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1};
        int[] a = rightShift(arr, 3);

        printArr(a);
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

    private static void printArr(int[] arr) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < arr.length; i++) {
            sb.append(arr[i]);
            if (i < arr.length - 1) {
                sb.append(" ");
            }
        }
        System.out.println(sb);
    }
}
