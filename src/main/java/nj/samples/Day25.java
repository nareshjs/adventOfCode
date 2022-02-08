package nj.samples;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Day25 {
  public static void main(String[] args) throws IOException {
    List<String> lst =
        Files.readAllLines(Paths.get("C:\\TNPS\\samples\\adventOfCode\\src\\main\\resources\\day25.txt"));
    char[][] matrix = populateMatrix(lst);
    print(matrix);
    performStep(matrix);
  }

  private static char[][] populateMatrix(List<String> lst) {
    int colCount = lst.get(0).length();
    char[][] matrix = new char[lst.size()][colCount];
    for(int i = 0; i < lst.size(); i++) {
      String eachLine = lst.get(i);
      for(int j = 0; j < colCount; j++) {
        matrix[i][j] = eachLine.charAt(j);
      }
    }
    return matrix;
  }

  private static void performStep(char[][] matrix) {
    boolean isModified = true;
    int stepCount = 0;
    while (isModified) {
      boolean movedRight = false;
      for(int i = 0; i < matrix.length; i++) {
        movedRight |= moveRight(matrix, i);
      }
      //print(matrix);
      boolean movedDown = false;
      for(int j = 0; j < matrix[0].length; j++) {
        movedDown |= moveDown(matrix, j);
      }
      isModified &= (movedRight | movedDown);
      stepCount++;
      //print(matrix);
    }
    System.out.println("Answer 1: " + stepCount);
  }

  private static boolean moveRight(char[][] matrix, int row) {
    List<Integer> indicesImpacted = new ArrayList<>();
    int colCount = matrix[row].length;
    char initialValue = matrix[row][0];
    for(int j = 0; j < colCount - 1; j++) {
      if(matrix[row][j] == '>' && matrix[row][j+1] == '.') {
        indicesImpacted.add(j);
      }
    }
    if(matrix[row][colCount - 1] =='>' && initialValue == '.') {
      indicesImpacted.add(colCount - 1);
    }
    for(Integer eachIndex : indicesImpacted) {
      char temp = matrix[row][eachIndex];
      if(eachIndex == colCount - 1) {
        matrix[row][eachIndex] = matrix[row][0];
        matrix[row][0] = temp;
      } else {
        matrix[row][eachIndex] = matrix[row][eachIndex+1];
        matrix[row][eachIndex+1] = temp;
      }
    }
    return indicesImpacted.size() > 0;
  }


  private static boolean moveDown(char[][] matrix, int col) {
    List<Integer> indicesImpacted = new ArrayList<>();
    int rowCount = matrix.length;
    char initialValue = matrix[0][col];
    for(int i = 0; i < rowCount - 1; i++) {
      if(matrix[i][col] == 'v' && matrix[i+1][col] == '.') {
        indicesImpacted.add(i);
      }
    }
    if(matrix[rowCount - 1][col] =='v' && initialValue == '.') {
      indicesImpacted.add(rowCount - 1);
    }
    for(Integer eachIndex : indicesImpacted) {
      char temp = matrix[eachIndex][col];
      if(eachIndex == rowCount - 1) {
        matrix[eachIndex][col] = matrix[0][col];
        matrix[0][col] = temp;
      } else {
        matrix[eachIndex][col] = matrix[eachIndex+1][col];
        matrix[eachIndex+1][col] = temp;
      }
    }
    return indicesImpacted.size() > 0;
  }

  public static void print(char[][] matrix) {
    int cols = matrix[0].length;
    for(int i = 0; i < matrix.length; i++) {
      for(int j = 0; j < cols; j++) {
        System.out.print(matrix[i][j] + " ");
      }
      System.out.println("");
    }
    System.out.println("----------------------");
  }
}