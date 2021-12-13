package nj.samples;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Day13 {
  public static void main(String []args) throws IOException {
    List<String> lst =
        Files.readAllLines(Paths.get("C:\\TNPS\\samples\\adventOfCode\\src\\main\\resources\\day13.txt"));
    List<String> transformations = new ArrayList<>();
    int matrix[][] = getMatrixInput(lst, transformations);
    transform(matrix, transformations);
  }

  private static void transform(int[][] matrix, List<String> transformations) {
    List<Integer> matrixSize = new ArrayList<>(2);
    matrixSize.add(matrix.length);
    matrixSize.add(matrix[0].length);
    for(String s : transformations) {
      String [] parts = s.split("=");
      int index = Integer.parseInt(parts[1]);
      char axis = parts[0].charAt(parts[0].length() - 1);
      if(axis == 'x') {
        transformAlongX(matrix, index, matrixSize);
        matrixSize.remove(1);
        matrixSize.add(1, index);
      } else {
        transformAlongY(matrix, index, matrixSize);
        matrixSize.remove(0);
        matrixSize.add(0, index);
      }
      System.out.println(" ");
      printCount(matrix, matrixSize);
    }
    print(matrix, matrixSize); //Need to recognize the chars by sight, need to make this machine understandable
  }

  private static void transformAlongX(int[][] matrix, int index, List<Integer> matrixSize) {
    for(int i = 0; i < matrixSize.get(0); i++) {
      for(int j = index + 1; j < matrixSize.get(1); j++) {
        if(matrix[i][j] == 1) {
          matrix[i][index-(j-index)] = 1;
        }
      }
    }
  }

  private static void transformAlongY(int[][] matrix, int index, List<Integer> matrixSize) {
    for(int i = index + 1; i < matrixSize.get(0); i++) {
      for(int j = 0; j < matrixSize.get(1); j++) {
        if(matrix[i][j] == 1) {
          matrix[index-(i-index)][j] = 1;
        }
      }
    }
  }

  private static int[][] getMatrixInput(List<String> lst, List<String> transformations) {
    int cols = 0, rows = 0;
    List<List<Integer>> coordinatesInput = new ArrayList<>();
    for(String s : lst) {
      if(s.contains(",")) {
        String parts[] = s.split(",");
        int x = Integer.parseInt(parts[0]);
        int y = Integer.parseInt(parts[1]);
        if(x > cols) cols = x;
        if(y > rows) rows = y;
        List<Integer> coord = new ArrayList<>(2);
        coord.add(y);
        coord.add(x);
        coordinatesInput.add(coord);
      } else if(s.contains("=")) {
        transformations.add(s);
      }
    }
    int input[][] = new int[rows+1][cols+1];
    coordinatesInput.stream().forEach(x -> input[x.get(0)][x.get(1)] = 1);
    return input;
  }

  public static void print(int[][] matrix, List<Integer> matrixSize) {
    int space = matrixSize.get(1)/8;
    for(int i = 0; i < matrixSize.get(0); i++) {
      int printSpace = 0;
      for(int j = 0; j < matrixSize.get(1); j++) {
        System.out.print(matrix[i][j] + " ");
        if(printSpace++ == space - 1) {
          System.out.print(" ");
          printSpace = 0;
        }
        if(matrix[i][j] == 1) {
        }
      }
      System.out.println("");
    }
  }

  private static void printCount(int[][] matrix, List<Integer> matrixSize) {
    int sum = 0;
    for(int i = 0; i < matrixSize.get(0); i++) {
      for(int j = 0; j < matrixSize.get(1); j++) {
        if(matrix[i][j] == 1) {
          sum += 1;
        }
      }
    }
    System.out.println("Sum: " + sum);
  }
}
