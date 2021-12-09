package nj.samples;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Day3 {
  public static void main(String[] args) throws IOException {
    List<String> lst = Files.readAllLines(Paths.get("C:\\TNPS\\samples\\adventOfCode\\src\\main\\resources\\day3.txt"));
    calculate(lst);
    calculate2(lst);
  }

  private static void calculate2(List<String> lst) {
    List<String> tempList = new ArrayList<>(lst);
    int position = 0;
    while (tempList.size() > 1) {
      //O2
      char common = findCommon(tempList, position, true);
      tempList = getSubList(tempList, position, common);
      position++;
    }
    String o2 = tempList.get(0);

    //CO2
    tempList = new ArrayList<>(lst);
    position = 0;
    while (tempList.size() > 1) {
      char common = findCommon(tempList, position, false);
      tempList = getSubList(tempList, position, common);
      position++;
    }
    String co2 = tempList.get(0);
    System.out.println(o2);
    System.out.println(co2);
    System.out.println(Integer.parseInt(o2, 2) * Integer.parseInt(co2, 2));
  }

  private static List<String> getSubList(List<String> lst, int position, char c) {
    List<String> subList = new ArrayList<>();
    for(String s: lst) {
      if(s.charAt(position) == c) {
        subList.add(s);
      }
    }
    return subList;
  }

  private static char findCommon(List<String> lst, int position, boolean mostCommon) {
    int count0 = 0;
    int count1 = 0;
    for (int i = 0; i < lst.size(); i++) {
      String input = lst.get(i);
      switch (input.charAt(position)) {
        case '0':
          count0++;
          break;
        default:
          count1++;
          break;
      }
    }
    if(mostCommon) {
      return count1 >= count0 ? '1' : '0';
    } else {
      return count0 <= count1 ? '0' : '1';
    }
  }

  private static void calculate(List<String> lst) {
    int charCount = lst.iterator().next().length();
    List<Integer> bin0 = new ArrayList<>(charCount);
    List<Integer> bin1 = new ArrayList<>(charCount);
    for(int j = 0; j < charCount; j++) {
      bin0.add(j, 0);
      bin1.add(j, 0);
    }
    for(int i = 0; i < lst.size(); i++) {
      String input = lst.get(i);
      for(int j = 0; j < charCount; j++) {
        switch (input.charAt(j)) {
          case '0':
            bin0.add(j, bin0.remove(j)+1);
            break;
          case '1':
            bin1.add(j, bin1.remove(j)+1);
            break;
        }
      }
    }
    System.out.println(bin0);
    System.out.println(bin1);
    String epsilon = "";
    String gamma = "";
    for(int j = 0; j < charCount; j++) {
      if(bin0.get(j) > bin1.get(j)) {
        epsilon += "0";
        gamma += "1";
      } else {
        epsilon += "1";
        gamma += "0";
      }
    }
    System.out.println(epsilon);
    System.out.println(gamma);
    System.out.println(Integer.parseInt(epsilon, 2) * Integer.parseInt(gamma, 2));
  }
}
