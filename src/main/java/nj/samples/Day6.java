package nj.samples;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

//Good interview question
public class Day6 {
  public static void main(String []args) throws IOException {
    List<String> lst = Files.readAllLines(Paths.get("C:\\TNPS\\samples\\adventOfCode\\src\\main\\resources\\day6.txt"));
    int age = 8;
    long[] counts = new long[age+1];

    //Init with input
    for(int i = 0; i <= age; i++) counts[i] = 0;
    String[] initAges = lst.iterator().next().split(",");
    for(String eachAge : initAges) {
      int ageIndex = Integer.parseInt(eachAge);
      long curVal = counts[ageIndex];
      counts[ageIndex] = curVal + 1;
    }

    long[] countsPart2 = new long[age+1];
    for(int i = 0; i <= age; i++) countsPart2[i] = counts[i];

    //Age after days
    calculate(age, counts, 80);
    calculate(age, countsPart2, 256);

  }

  private static void calculate(int age, long[] counts, int days) {
    for(int i = 0; i < days; i++) {
      long temp = counts[0];
      for(int j = 0; j < age; j++) {
        counts[j] = counts[j+1];
      }
      counts[age] = temp;
      counts[6] = counts[6] + temp;
    }

    //Final count
    long sum = 0;
    for(int i = 0; i <= 8; i++) {
      sum += counts[i];
    }
    System.out.println("Total count: " + sum);
  }
}
