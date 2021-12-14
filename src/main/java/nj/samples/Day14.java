package nj.samples;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

//was able to do part 1 using LinkedList, but for Part 2 had to look at reddit for soln and rewrite the whole soln.
public class Day14 {
  public static void main(String []args) throws IOException {
    List<String> lst =
        Files.readAllLines(Paths.get("C:\\TNPS\\samples\\adventOfCode\\src\\main\\resources\\day14.txt"));
    String input = lst.get(0);
    Map<String, String> transformations = getTransformations(lst.subList(2, lst.size()));
    Map<String, Long> pairMap = new LinkedHashMap<>();
    for(int i = 0; i < input.length() - 1; i++) {
      String subStr = input.substring(i, i + 2);
      pairMap.putIfAbsent(subStr, 0L);
      pairMap.computeIfPresent(subStr, (k,v) -> v = v + 1);
    }
    for(int step = 0; step < 40; step++) {
      Set<String> keys = pairMap.keySet();
      Set<String> toBeRemoved = new LinkedHashSet<>();
      Map<String, Long> tempMap = new LinkedHashMap<>();
      for(String key : keys) {
        if(transformations.containsKey(key)){
          long curCount = pairMap.get(key);
          if(curCount > 0) {
            toBeRemoved.add(key);
            if(!tempMap.containsKey(key.substring(0, 1) + transformations.get(key))) {
              tempMap.put(key.substring(0, 1) + transformations.get(key), curCount);
            } else {
              tempMap.put(key.substring(0, 1) + transformations.get(key), curCount + tempMap.get(key.substring(0, 1) + transformations.get(key)));
            }
            if(!tempMap.containsKey(transformations.get(key) + key.substring(1, 2))) {
              tempMap.put(transformations.get(key) + key.substring(1, 2), curCount);
            } else {
              tempMap.put(transformations.get(key) + key.substring(1, 2), curCount + tempMap.get(transformations.get(key) + key.substring(1, 2)));
            }
          }
        }
      }
      toBeRemoved.stream().forEach(pairMap::remove);
      tempMap.entrySet().forEach(x -> {
        pairMap.putIfAbsent(x.getKey(), 0L);
        pairMap.computeIfPresent(x.getKey(), (k,v) -> v = v + tempMap.get(x.getKey()));
      });
    }
    Map<Character, Long> countMap = new LinkedHashMap<>();
    for(String key : pairMap.keySet()) {
      countMap.putIfAbsent(key.charAt(0), 0L);
      countMap.computeIfPresent(key.charAt(0), (k,v) -> v = v + pairMap.get(key));
    }
    countMap.put(input.charAt(input.length() - 1), countMap.get(input.charAt(input.length() - 1)) + 1);
    System.out.println("Answer : " + (countMap.values().stream().max(Long::compareTo).get() -
        countMap.values().stream().min(Long::compareTo).get()));
  }

  private static Map<String, String> getTransformations(List<String> subList) {
    Map<String, String> transformations = new LinkedHashMap<>();
    subList.stream().forEach(x -> {
      String[] parts = x.split("->");
      transformations.put(parts[0].trim(), parts[1].trim());
    });
    return transformations;
  }
}
