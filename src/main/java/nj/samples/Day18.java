package nj.samples;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import nj.samples.day18.SfNum;

public class Day18 {
  public static void main(String[] args) throws IOException {
    List<String> lst =
        Files.readAllLines(Paths.get("C:\\TNPS\\samples\\adventOfCode\\src\\main\\resources\\day18.txt"));
    SfNum sfNum = new SfNum(lst.get(0));
    for(int i = 1; i < lst.size(); i++) {
      sfNum.add(new SfNum(lst.get(i)));
      System.out.println(sfNum);
    }
    System.out.println("Answer 1: " + sfNum.magnitude());
    int max = 0;
    for(int i = 0; i < lst.size() - 1; i++) {
      for(int j = i+1; j < lst.size(); j++) {
        int magnitude = getMagnitude(lst, max, i, j);
        if(max < magnitude) max = magnitude;

        magnitude = getMagnitude(lst, max, j, i);
        if(max < magnitude) max = magnitude;
      }
    }
    System.out.println("Answer 2: " + max);
  }

  private static int getMagnitude(List<String> lst, int max, int i, int j) {
    SfNum a = new SfNum(lst.get(i));
    SfNum b = new SfNum(lst.get(j));
    a.add(b);
    return a.magnitude();
  }
}
