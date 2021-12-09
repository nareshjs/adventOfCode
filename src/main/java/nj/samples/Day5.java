package nj.samples;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import nj.samples.util.Coordinate;

public class Day5 {

  public static void main(String []args) throws IOException {
    List<String> lst = Files.readAllLines(Paths.get("C:\\TNPS\\samples\\adventOfCode\\src\\main\\resources\\day5.txt"));
    List<List<Integer>> matrix = new ArrayList<>();
    populate(matrix, lst);
    calculate(matrix);
  }

  private static void calculate(List<List<Integer>> matrix) {
    int count = 0;
    for(List<Integer> row : matrix) {
      for(Integer coordinate : row) {
        if(coordinate > 1) count++;
      }
    }
    System.out.println("Answer 1: " + count);
  }

  private static void populate(List<List<Integer>> matrix, List<String> lst) {
    for(String eachStr : lst) {
      String[] coordinates = eachStr.split(" -> ");
      Coordinate c1 = new Coordinate(coordinates[0]);
      Coordinate c2 = new Coordinate(coordinates[1]);
      populateMatrix(matrix, c1.x >= c2.x ? c1.x : c2.x, c1.y >= c2.y ? c1.y : c2.y);
      if(Coordinate.areCoordinatesAlongSameAxis(c1,c2)) {
        if(Coordinate.areCoordinatesSameAlongxAxis(c1, c2)) {
          populateWithCoordinateY(matrix, c1.x, Math.min(c1.y, c2.y), Math.max(c1.y, c2.y));
        } else {
          populateWithCoordinateX(matrix, c1.y, Math.min(c1.x, c2.x), Math.max(c1.x, c2.x));
        }
      } else {
        populateDiagonalCoordinates(matrix, c1, c2);
      }
    }
  }

  private static void populateDiagonalCoordinates(List<List<Integer>> matrix, Coordinate c1, Coordinate c2) {
    List<Integer> xCoords = new ArrayList<>();
    List<Integer> yCoords = new ArrayList<>();
    for(int i = Math.min(c1.x, c2.x); i <= Math.max(c1.x, c2.x); i++){
      xCoords.add(i);
    }
    for(int i = Math.min(c1.y, c2.y); i <= Math.max(c1.y, c2.y); i++){
      yCoords.add(i);
    }
    for(int i = 0; i < xCoords.size(); i++) {
      if((c1.x < c2.x && c1.y < c2.y) || (c1.x > c2.x && c1.y > c2.y)) {
        populateCoordinate(matrix, xCoords.get(i), yCoords.get(i));
      } else {
        populateCoordinate(matrix, xCoords.get(i), yCoords.get(yCoords.size() - (i + 1)));
      }
    }
  }

  private static void populateCoordinate(List<List<Integer>> matrix, Integer x, Integer y) {
    Integer curValue = matrix.get(x).remove(y.intValue());
    matrix.get(x).add(y, curValue+1);
  }

  private static void populateWithCoordinateX(List<List<Integer>> matrix, int y, int x, int x1) {
    for(int i = x; i <= x1; i++) {
      Integer curValue = matrix.get(i).remove(y);
      matrix.get(i).add(y, curValue+1);
    }
  }

  private static void populateWithCoordinateY(List<List<Integer>> matrix, int x, int y, int y1) {
    for(int i = y; i <= y1; i++) {
      Integer curValue = matrix.get(x).remove(i);
      matrix.get(x).add(i, curValue+1);
    }
  }

  private static void populateMatrix(List<List<Integer>> matrix, int x, int y) {
    if(matrix.isEmpty()) {
      for(int i = 0; i <= x; i++) {
        matrix.add(new ArrayList<>());
      }
    } else if(matrix.size() - 1 < x) {
      int oldSize = matrix.size() - 1;
      for(int i = 0; i < x - oldSize; i++) {
        matrix.add(new ArrayList<>());
      }
    }
    for(int row = 0; row <=x; row++) {
      List<Integer> rowX = matrix.get(row);
      //Size = 3, y = 5 -> Add 5-(3-1) = 3 elements
      //Size = 0, y = 1 -> Add 1 element
      if (rowX.isEmpty()) {
        for (int i = 0; i <= y; i++) {
          rowX.add(0);
        }
      } else if (rowX.size() - 1 < y) {
        int oldSize = rowX.size() - 1;
        for (int i = 0; i < y - oldSize; i++) {
          rowX.add(0);
        }
      }
    }
  }
}
