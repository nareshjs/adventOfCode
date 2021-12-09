package nj.samples;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Day2 {
  public static void main(String[] args) throws IOException {
    List<String> lst = Files.readAllLines(Paths.get("C:\\TNPS\\samples\\adventOfCode\\src\\main\\resources\\day2.txt"));
    calculate(lst);
  }

  private static void calculate(List<String> lst) {
    int forward = 0;
    int depth = 0;
    int aim = 0;
    for(String entry : lst) {
      String[] parts = entry.split(" ");
      int value = Integer.parseInt(parts[1]);
      switch (parts[0]) {
        case "forward":
          forward += value;
          depth += (aim*value);
          break;
        case "up":
          aim -= value;
          break;
        case "down":
          aim += value;
          break;
      }
    }
    System.out.println(forward * depth);
  }
}
