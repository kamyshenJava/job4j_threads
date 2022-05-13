package ru.job4j.pools;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class RolColSum {
    public static class Sums {
        private int rowSum;
        private int colSum;

        public Sums(int rowSum, int colSum) {
            this.rowSum = rowSum;
            this.colSum = colSum;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            Sums sums = (Sums) o;
            return rowSum == sums.rowSum && colSum == sums.colSum;
        }

        @Override
        public int hashCode() {
            return Objects.hash(rowSum, colSum);
        }
    }

    public static Sums[] sum(int[][] matrix) {
        int l = matrix.length;
        Sums[] sums = new Sums[l];
        for (int i = 0; i < l; i++) {
            int sumRow = 0;
            int sumCol = 0;
            for (int j = 0; j < l; j++) {
                sumRow += matrix[i][j];
                sumCol += matrix[j][i];
            }
            sums[i] = new Sums(sumRow, sumCol);
        }
        return sums;
    }

    public static Sums[] asyncSum(int[][] matrix) throws ExecutionException, InterruptedException {
        int l = matrix.length;
        Sums[] sums = new Sums[l];
        for (int i = 0; i < l; i++) {
            int finalI = i;
            CompletableFuture<Integer> sumRow = CompletableFuture.supplyAsync(() -> {
                int row = 0;
                for (int j = 0; j < l; j++) {
                    row += matrix[finalI][j];
                }
                return row;
            });
            CompletableFuture<Integer> sumCol = CompletableFuture.supplyAsync(() -> {
                int col = 0;
                for (int j = 0; j < l; j++) {
                    col += matrix[j][finalI];
                }
                return col;
            });
            sums[i] = new Sums(sumRow.get(), sumCol.get());
        }
        return sums;
    }

}
