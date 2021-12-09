package nj.samples;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Day7 {
  public static void main(String []args) throws IOException {
    List<String> lst = Files.readAllLines(Paths.get("C:\\TNPS\\samples\\adventOfCode\\src\\main\\resources\\day7.txt"));
    List<Integer> positions = new ArrayList<>();
    long max = 0;
    String[] positionsStr = lst.iterator().next().split(",");
    for(String position : positionsStr){
      int pos = Integer.parseInt(position);
      if(pos > max) max = pos;
      positions.add(pos);
    }

    long minSum = 0;
    for(long i = 0; i < max; i++) {
      long sum = 0;
      for(int j = 0; j < positions.size(); j++) {
        sum += Math.abs(positions.get(j) - i);
      }
      if(minSum == 0 || sum < minSum) minSum = sum;
    }

    System.out.println("Min Sum: " + minSum);

    minSum = 0;
    for(long i = 0; i < max; i++) {
      long sum = 0;
      for(int j = 0; j < positions.size(); j++) {
        long jump = Math.abs(positions.get(j) - i);
        sum += (jump*(jump+1))/2;
      }
      if(minSum == 0 || sum < minSum) minSum = sum;
    }

    System.out.println("Min Sum2: " + minSum);
  }
}
