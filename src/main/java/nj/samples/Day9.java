package nj.samples;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Day9 {
  public static void main(String []args) throws IOException {
    List<String> lst = Files.readAllLines(Paths.get("C:\\TNPS\\samples\\adventOfCode\\src\\main\\resources\\day9.txt"));
    int columns = lst.get(0).split("").length;
    int rows = lst.size();
    int[][] matrix = new int[rows][columns];
    for(int i = 0; i < rows; i++) {
      String[] parts = lst.get(i).split("");
      for(int j = 0; j < columns; j++) {
        matrix[i][j] = Integer.parseInt(parts[j]);
      }
    }
    List<Integer> lowPoints = new ArrayList<>();
    int sum = 0;
    for(int i = 0; i < matrix.length; i++) {
      for (int j = 0; j < matrix[0].length; j++) {
        int cur = matrix[i][j];
        if (isLower(matrix, i - 1, j, cur) &&
            isLower(matrix, i + 1, j, cur) &&
            isLower(matrix, i, j - 1, cur) &&
            isLower(matrix, i, j + 1, cur)) {
          sum += cur + 1;
          lowPoints.add(i);
          lowPoints.add(j);
        }
      }
    }
    System.out.println("Answer 1: " + sum);
    part2(matrix, lowPoints);
  }

  private static void part2(int[][] matrix, List<Integer> lowPoints) {
    List<Integer> basinSizes = new ArrayList<>();
    for(int i = 0; i < lowPoints.size(); i += 2) {
      List<String> surPtsStrs = new ArrayList<>();
      List<Integer> surPts = new ArrayList<>();
      surPts.add(lowPoints.get(i));
      surPts.add(lowPoints.get(i+1));
      surPtsStrs.add("" + lowPoints.get(i) + lowPoints.get(i+1));
      int basinSize = 0;
      while (!surPts.isEmpty()) {
        basinSize++;
        int li = surPts.remove(0);
        int lj = surPts.remove(0);
        int cur = matrix[li][lj];
        addIfPartOfBasin(matrix, li - 1, lj, surPts, cur, surPtsStrs);
        addIfPartOfBasin(matrix, li + 1, lj, surPts, cur, surPtsStrs);
        addIfPartOfBasin(matrix, li, lj - 1, surPts, cur, surPtsStrs);
        addIfPartOfBasin(matrix, li, lj + 1, surPts, cur, surPtsStrs);
      }
      basinSizes.add(basinSize);
    }
    Collections.sort(basinSizes);
    long result = 1;
    int len = basinSizes.size();
    for(int i = 0; i < 3; i++) {
      result *= basinSizes.get(len - (i+1));
    }
    System.out.println("Answer 2: " + result);
  }

  private static void addIfPartOfBasin(int[][] matrix, int li, int lj, List<Integer> surPts, int cur, List<String> surPtsStrs) {
    int rows = matrix.length;
    int columns = matrix[0].length;
    if(li >=0 && li < rows && lj >= 0 && lj < columns) {
      if(matrix[li][lj] > cur && matrix[li][lj] < 9) {
        if(!surPtsStrs.contains("" + li + lj)) {
          surPts.add(li);
          surPts.add(lj);
          surPtsStrs.add("" + li + lj);
        }
      }
    }
  }

  private static boolean isLower(int[][] matrix, int i, int j, int cur) {
    int rows = matrix.length;
    int columns = matrix[0].length;
    if(i >=0 && i < rows && j >= 0 && j < columns) {
      return cur < matrix[i][j] ? true : false;
    } else {
      return true;
    }
  }
}
