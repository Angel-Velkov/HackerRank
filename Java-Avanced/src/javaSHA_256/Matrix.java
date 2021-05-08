package javaSHA_256;

import java.util.Arrays;

public final class Matrix {

    private Matrix() {
    }

    public static int[][] addPadding(int[][] matrix, int columnSize, int extraSpace) {
        int totalLength = matrix.length + extraSpace;
        int[][] extendedMatrix = new int[totalLength][columnSize];

        System.arraycopy(matrix, 0, extendedMatrix, 0, matrix.length);
        return extendedMatrix;
    }

    public static void printMatrix(int[][] matrix, int columns) {
        StringBuilder sb = new StringBuilder();

        int counter = 0;
        for (int[] array : matrix) {
            Arrays.stream(array).forEach(sb::append);

            if (++counter % columns == 0) {
                sb.append(System.lineSeparator());
            } else {
                sb.append(" ");
            }
        }

        System.out.println(sb);
    }
}
