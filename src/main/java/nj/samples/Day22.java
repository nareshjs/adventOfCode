package nj.samples;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Day22 {
  public static void main(String[] args) throws IOException {
    List<String> lst =
        Files.readAllLines(Paths.get("C:\\TNPS\\samples\\adventOfCode\\src\\main\\resources\\day22.txt"));
    Map<String, String> onMap = new LinkedHashMap<>();
    for(String eachStr : lst) {
      String[] parts = eachStr.split(" ");
      List<List<Integer>> ranges = getRanges(parts[1]);
      updateMap(onMap, parts[0], ranges, false);
    }
    System.out.println("Answer: " + onMap.size());
  }

  private static void updateMap(Map<String, String> onMap, String part, List<List<Integer>> ranges, boolean isInit) {
    boolean isOn = part.equals("on");
    List<Integer> xRange = ranges.get(0);
    List<Integer> yRange = ranges.get(1);
    List<Integer> zRange = ranges.get(2);

    for(int i = 0 ;i < xRange.size(); i++) {
      if(!isInit || isInValidRange(xRange.get(i))) {
        for(int j = 0 ;j < yRange.size(); j++) {
          if(!isInit || isInValidRange(yRange.get(j))) {
            for(int k = 0 ;k < zRange.size(); k++) {
              if(!isInit || isInValidRange(zRange.get(k))) {
                String key = xRange.get(i)+","+yRange.get(j)+","+zRange.get(k);
                if(!isOn) {
                  if (onMap.containsKey(key)) onMap.remove(key);
                } else {
                  onMap.put(key, key);
                }
              }
            }
          }
        }
      }
    }
  }

  private static boolean isInValidRange(Integer integer) {
    return integer >= -50 && integer <= 50;
  }

  private static List<List<Integer>> getRanges(String part) {
    String[] parts = part.split(",");
    List<List<Integer>> ranges = new ArrayList<>();
    for(String eachPart : parts) {
      String[] rangeParts = eachPart.split("=");
      String[] rangesStr = rangeParts[1].split("\\.\\.");
      List<Integer> range = new ArrayList<>();
      for(int i = Integer.parseInt(rangesStr[0]); i <= Integer.parseInt(rangesStr[1]); i++) {
        range.add(i);
      }
      ranges.add(range);
    }
    return ranges;
  }
}
