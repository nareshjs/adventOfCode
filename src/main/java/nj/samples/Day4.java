package nj.samples;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//Good interview question
public class Day4 {
  public static void main(String[] args) throws IOException {
    List<String> lst = Files.readAllLines(Paths.get("C:\\TNPS\\samples\\adventOfCode\\src\\main\\resources\\day4.txt"));
    List<Integer> picks = new ArrayList<>();
    List<List<List<Integer>>> input = new ArrayList<>();
    List<List<List<Integer>>> marked = new ArrayList<>();
    parseInput(lst, picks, input, marked);
    System.out.println(picks);
    System.out.println(input);
    System.out.println(marked);
    calculate(picks, input, marked);
  }

  private static void calculate(List<Integer> picks,
                                List<List<List<Integer>>> input,
                                List<List<List<Integer>>> marked) {
    List<Integer> finished = new ArrayList<>();
    for(Integer eachPick : picks) {
      for(int k = 0; k < input.size(); k++) {
        List<List<Integer>> matrix = input.get(k);
        for(int i = 0; i < matrix.size(); i++) {
          List<Integer> row = matrix.get(i);
          for(int j = 0; j < row.size(); j++) {
            Integer entry = row.get(j);
            if(entry.equals(eachPick)) {
              marked.get(k).get(i).remove(j);
              marked.get(k).get(i).add(j, 1);
            }
          }
        }
      }
      //Spent some time here, since was assuming that with each pick only one bingo card will win
      List<Integer> bingoIndices = getBingoIndex(marked, finished);
      System.out.println(bingoIndices);
      if(!bingoIndices.isEmpty()) {
        for(Integer eachIndex : bingoIndices) {
          List<List<Integer>> matrix = input.get(eachIndex);
          int sum = getSum(marked, eachIndex, matrix);
          System.out.println("Answer = " + eachPick * sum + " for index " + eachIndex);
        }
        finished.addAll(bingoIndices);
      }
    }
  }

  private static int getSum(List<List<List<Integer>>> marked, int bingoIndex, List<List<Integer>> matrix) {
    int sum = 0;
    for(int i = 0; i < matrix.size(); i++) {
      List<Integer> row = matrix.get(i);
      for(int j = 0; j < row.size(); j++) {
        if(!marked.get(bingoIndex).get(i).get(j).equals(1)){
          sum += row.get(j);
        }
      }
    }
    return sum;
  }

  private static List<Integer> getBingoIndex(List<List<List<Integer>>> marked, List<Integer> finished) {
    List<Integer> allIndices = new ArrayList<>();
    for(int k = 0; k < marked.size(); k++) {
      if(finished.contains(k)) continue;
      List<List<Integer>> matrix = marked.get(k);
      for (int i = 0; i < matrix.size() && !allIndices.contains(k); i++) {
        List<Integer> row = matrix.get(i);
        for (int j = 0; j < row.size(); j++) {
          if(!row.contains(0)) {
            allIndices.add(k);
            break;
          }
        }
      }
      //Check column
      int columnSize = matrix.get(0).size();
      for (int j = 0; j < columnSize && !allIndices.contains(k); j++) {
        boolean isColumnMarked = true;
        for(int i = 0; i < matrix.size() && !allIndices.contains(k); i++) {
          isColumnMarked &= matrix.get(i).get(j).equals(1);
          if(!isColumnMarked) break;
        }
        if(isColumnMarked) {
          allIndices.add(k);
          break;
        }
      }
    }
    return allIndices;
  }

  private static void parseInput(List<String> lst, List<Integer> picks, List<List<List<Integer>>> input,
                                 List<List<List<Integer>>> marked) {
    String[] picksStr = lst.get(0).split(",");
    Arrays.stream(picksStr).forEach(x -> picks.add(Integer.parseInt(x)));

    int inputIdx = 0;
    for (int i = 2; i < lst.size(); i++) {
      String eachStr = lst.get(i);
      if (eachStr == null || eachStr.length() == 0) {
        inputIdx++;
        continue;
      }
      List<Integer> eachRowItems = new ArrayList<>();
      List<Integer> eachMarkedRowItems = new ArrayList<>();
      Arrays.stream(eachStr.split("\\s+")).filter(x -> !x.equals(""))
          .forEach(y -> {
            eachRowItems.add(Integer.parseInt(y));
            eachMarkedRowItems.add(0);
          });
      if (input.size() == inputIdx) {
        input.add(new ArrayList<>());
        marked.add(new ArrayList<>());
      }
      input.get(inputIdx).add(eachRowItems);
      marked.get(inputIdx).add(eachMarkedRowItems);
    }
  }
}
