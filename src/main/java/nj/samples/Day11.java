package nj.samples;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Day11 {
  public static void main(String []args) throws IOException {
    List<String> lst =
        Files.readAllLines(Paths.get("C:\\TNPS\\samples\\adventOfCode\\src\\main\\resources\\day11.txt"));
    int rows = lst.size();
    int cols = lst.get(0).split("").length;
    int[][] matrix = new int[rows][cols];

    for(int i = 0; i < lst.size(); i++) {
      String[] parts = lst.get(i).split("");
      for(int j = 0; j < cols; j++) {
        matrix[i][j] = Integer.parseInt(parts[j]);
      }
    }
    print(matrix);
    part1(matrix);
  }

  private static void part1(int[][] matrix) {
    long totalFlashedCount = 0;
    int rows = matrix.length;
    int cols = matrix[0].length;
    int syncFlashIndex = -1;

    for(int step = 0; step < 100 || syncFlashIndex == -1; step++) {
      //increase all elements by 1
      incrementBy1(matrix, rows, cols);

      //init flashed
      int[][] flashed = getInitializedFlashedMatrix(rows, cols);

      //increase input and mark flashed
      boolean performStep = true;
      while(performStep) {
        performStep = false;
        for (int i = 0; i < rows; i++) {
          for (int j = 0; j < cols; j++) {
            if (matrix[i][j] > 9) {
              performStep = true;
              matrix[i][j] = 0;
              flashed[i][j] = 1;
              incrementAdjacent(matrix, rows, cols, i, j, flashed);
            }
          }
        }
      }

      //count flashed
      if(step < 100) {
        int flashedCount = getFlashedCount(rows, cols, flashed);
        totalFlashedCount += flashedCount;
      }

      //is sync flashed
      if(isSyncFlashed(matrix, rows, cols)) {
        syncFlashIndex = step + 1;
        break;
      }
    }
    System.out.println("Answer 1: " + totalFlashedCount);
    System.out.println("Answer 2: " + syncFlashIndex);
  }

  private static boolean isSyncFlashed(int[][] matrix, int rows, int cols) {
    boolean retValue = true;
    for (int i = 0; i < rows && retValue; i++) {
      for (int j = 0; j < cols; j++) {
        if(matrix[i][j] != 0) {
          retValue = false;
          break;
        }
      }
    }
    return retValue;
  }

  private static void incrementBy1(int[][] matrix, int rows, int cols) {
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < cols; j++) {
        matrix[i][j] += 1;
      }
    }
  }

  private static void incrementAdjacent(int[][] matrix, int rows, int cols, int i, int j, int[][] flashed) {
    for(int ii = i - 1; ii < i+2; ii++) {
      for(int jj = j - 1; jj < j+2; jj++) {
        if(!(ii == i && jj == j)) {
          if (isValid(ii, jj, rows, cols)) {
            if (flashed[ii][jj] != 1) matrix[ii][jj] += 1;
          }
        }
      }
    }
  }

  private static boolean isValid(int ii, int jj, int rows, int cols) {
    boolean isValid = true;
    if(ii < 0 || jj < 0 || ii > rows - 1 || jj > cols - 1) isValid = false;
    return isValid;
  }

  private static int[][] getInitializedFlashedMatrix(int rows, int cols) {
    int[][] flashed = new int[rows][cols];
    for(int i = 0; i < rows; i++) {
      for (int j = 0; j < cols; j++) {
        flashed[i][j] = 0;
      }
    }
    return flashed;
  }

  private static int getFlashedCount(int rows, int cols, int[][] flashed) {
    int flashedCount = 0;
    for(int i = 0; i < rows; i++) {
      for (int j = 0; j < cols; j++) {
        if(flashed[i][j] == 1) flashedCount++;
      }
    }
    return flashedCount;
  }

  public static void print(int[][] matrix) {
    int cols = matrix[0].length;
    for(int i = 0; i < matrix.length; i++) {
      for(int j = 0; j < cols; j++) {
        System.out.print(matrix[i][j] + " ");
      }
      System.out.println("");
    }
  }
}
