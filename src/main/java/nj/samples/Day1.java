package nj.samples;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Day1 {
  public static void main(String[] args) throws IOException {
    List<String> lst = Files.readAllLines(Paths.get("C:\\TNPS\\samples\\adventOfCode\\src\\main\\resources\\day1.txt"));
    calculate(lst);
    calculate2(lst);
  }

  private static void calculate2(List<String> lst) {
    int count = 0;
    int prevValue = Integer.parseInt(lst.get(0));
    prevValue += Integer.parseInt(lst.get(1));
    prevValue += Integer.parseInt(lst.get(2));
    for(int i = 3; i < lst.size(); i++) {
      int curValue = prevValue;
      curValue -= Integer.parseInt(lst.get(i - 3));
      curValue += Integer.parseInt(lst.get(i));
      if(curValue > prevValue) count++;
      prevValue = curValue;
    }
    System.out.println(count);
  }

  private static void calculate(List<String> lst) {
    int count = 0;
    int prevValue = Integer.parseInt(lst.get(0));
    for(int i = 1; i < lst.size(); i++) {
      int curValue = Integer.parseInt(lst.get(i));
      if(curValue > prevValue) count++;
      prevValue = curValue;
    }
    System.out.println(count);
  }
}
