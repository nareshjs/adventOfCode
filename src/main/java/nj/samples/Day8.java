package nj.samples;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Day8 {
  public static void main(String []args) throws IOException {
    List<String> lst = Files.readAllLines(Paths.get("C:\\TNPS\\samples\\adventOfCode\\src\\main\\resources\\day8.txt"));
    List<Integer> uniqueSegmentSizes = new ArrayList<>();
    uniqueSegmentSizes.add(2);
    uniqueSegmentSizes.add(3);
    uniqueSegmentSizes.add(4);
    uniqueSegmentSizes.add(7);

    int sum1 = 0;
    long sum2 = 0;
    for(String eachLine : lst) {
      String[] lineParts = eachLine.split("\\|");
      Map<String, Integer> finalDigitMap = getDigitMap(lineParts[0].trim());

      StringBuffer sb = new StringBuffer();
      String[] digits = lineParts[1].trim().split("\\ ");
      for(String digit : digits) {
        if(uniqueSegmentSizes.contains(digit.length())) sum1++;

        List<Integer> integersOfDigit = new ArrayList<>();
        for(String s : digit.split("")) {
          integersOfDigit.add(finalDigitMap.get(s));
        }
        Collections.sort(integersOfDigit);
        for(Map.Entry<Integer,List<Integer>> eachEntry : getIntegerVsLocationsMap().entrySet()) {
          if(eachEntry.getValue().size() == integersOfDigit.size()) {
            boolean isSame = true;
            for(int i = 0; i < integersOfDigit.size(); i++) {
              if(!integersOfDigit.get(i).equals(eachEntry.getValue().get(i))) {
                isSame = false;
                break;
              }
            }
            if(isSame) {
              sb.append(eachEntry.getKey());
              break;
            }
          }
        }
      }
      sum2 += Integer.parseInt(sb.toString());
    }
    System.out.println("Answer 1: " + sum1);
    System.out.println("Answer 2: " + sum2);


  }

  private static Map<String, Integer> getDigitMap(String signalStr) {
    String[] signals = signalStr.split("\\ ");
    Arrays.sort(signals, new Comparator<String>() {
      @Override
      public int compare(String o1, String o2) {
        if(o1.length() < o2.length()) return -1;
        else if(o1.length() == o2.length()) return 0;
        else return 1;
      }
    });

    Map<Integer, List<Integer>> integerVsLocations = getIntegerVsLocationsMap();
    Map<Integer, List<String>> digitMap = getDefaultDigitMap();

    String sig1 = signals[0];
    for(String s : sig1.split("")) {
      for(Integer loc : integerVsLocations.get(1)) {
        digitMap.get(loc).add(s);
      }
    }

    String sig7 = signals[1];
    List<String> disjointWith1 = disjointChars(sig7, sig1);
    digitMap.get(1).add(disjointWith1.iterator().next());

    String sig4 = signals[2];
    disjointWith1 = disjointChars(sig4, sig1);
    List<Integer> tempList = new ArrayList<>(integerVsLocations.get(4));
    tempList.removeAll(integerVsLocations.get(1));
    for(String s : disjointWith1) {
      for(Integer loc : tempList) {
        digitMap.get(loc).add(s);
      }
    }

    String sig8 = signals[9];
    List<String> disjointWith7 = disjointChars(sig8, sig7);
    List<String> disjointWith4 = disjointChars(sig8, sig4);
    List<String> toBeRemoved = new ArrayList<>();
    for(String toBeRem : disjointWith7) {
      if(!disjointWith4.contains(toBeRem)) toBeRemoved.add(toBeRem);
    }
    disjointWith7.removeAll(toBeRemoved);
    tempList = new ArrayList<>(integerVsLocations.get(8));
    tempList.removeAll(integerVsLocations.get(7));
    tempList.removeAll(integerVsLocations.get(4));
    for(String s : disjointWith7) {
      for(Integer loc : tempList) {
        digitMap.get(loc).add(s);
      }
    }

    List<String> strsWithLength5 = new ArrayList<>();
    strsWithLength5.add(signals[3]);
    strsWithLength5.add(signals[4]);
    strsWithLength5.add(signals[5]);
    String sig3 = getSig3(strsWithLength5, sig1);
    disjointWith7 = disjointChars(sig3, sig7);
    for(String s : disjointWith7) {
      if(digitMap.get(4).contains(s)) {
        digitMap.get(4).clear();
        digitMap.get(4).add(s);
        digitMap.get(2).remove(s);
      } else {
        digitMap.get(7).clear();
        digitMap.get(7).add(s);
        digitMap.get(5).remove(s);
      }
    }

    String sig5;
    strsWithLength5.remove(sig3);
    if(strsWithLength5.get(0).contains(digitMap.get(2).iterator().next())) {
      sig5 = strsWithLength5.get(0);
    } else {
      sig5 = strsWithLength5.get(1);
    }
    String[] sig5Parts = sig5.split("");
    List<String> sig5PartsList = new ArrayList<>(Arrays.asList(sig5Parts));
    sig5PartsList.removeAll(digitMap.get(1));
    sig5PartsList.removeAll(digitMap.get(2));
    sig5PartsList.removeAll(digitMap.get(4));
    sig5PartsList.removeAll(digitMap.get(7));
    digitMap.get(6).clear();
    digitMap.get(6).add(sig5PartsList.get(0));
    digitMap.get(3).remove(sig5PartsList.get(0));

    Map<String, Integer> finalMap = new LinkedHashMap<>();
    for(Map.Entry<Integer, List<String>> entry : digitMap.entrySet()) {
      finalMap.put(entry.getValue().iterator().next(), entry.getKey());
    }
    return finalMap;
  }

  private static String getSig3(List<String> strsWithLength5, String sig1) {
    for(String str : strsWithLength5) {
      if(disjointChars(str, sig1).size() == 3) return str;
    }
    return null;
  }

  private static List<String> disjointChars(String sig1, String sig2) {
    String str1 = sig1.length()>sig2.length() ? sig1 : sig2;
    String str2 = sig1.length()<sig2.length() ? sig1 : sig2;

    String[] sig1Parts = str1.split("");
    String[] sig2Parts = str2.split("");
    List<String> retVal = new ArrayList<>(Arrays.asList(sig1Parts));
    retVal.removeAll(Arrays.asList(sig2Parts));
    return retVal;
  }

  public static Map<Integer, List<String>> getDefaultDigitMap() {
    Map<Integer, List<String>> digitMap = new LinkedHashMap<>();
    for(int i = 1; i <= 7; i++) digitMap.put(i, new ArrayList<>());
    return digitMap;
  }

  public static Map<Integer, List<Integer>> getIntegerVsLocationsMap() {
    Map<Integer, List<Integer>> integerVsLocations = new LinkedHashMap<>();
    integerVsLocations.put(0, new ArrayList<>());
    integerVsLocations.get(0).add(1);
    integerVsLocations.get(0).add(2);
    integerVsLocations.get(0).add(3);
    integerVsLocations.get(0).add(5);
    integerVsLocations.get(0).add(6);
    integerVsLocations.get(0).add(7);

    integerVsLocations.put(1, new ArrayList<>());
    integerVsLocations.get(1).add(3);
    integerVsLocations.get(1).add(6);

    integerVsLocations.put(2, new ArrayList<>());
    integerVsLocations.get(2).add(1);
    integerVsLocations.get(2).add(3);
    integerVsLocations.get(2).add(4);
    integerVsLocations.get(2).add(5);
    integerVsLocations.get(2).add(7);

    integerVsLocations.put(3, new ArrayList<>());
    integerVsLocations.get(3).add(1);
    integerVsLocations.get(3).add(3);
    integerVsLocations.get(3).add(4);
    integerVsLocations.get(3).add(6);
    integerVsLocations.get(3).add(7);

    integerVsLocations.put(4, new ArrayList<>());
    integerVsLocations.get(4).add(2);
    integerVsLocations.get(4).add(3);
    integerVsLocations.get(4).add(4);
    integerVsLocations.get(4).add(6);

    integerVsLocations.put(5, new ArrayList<>());
    integerVsLocations.get(5).add(1);
    integerVsLocations.get(5).add(2);
    integerVsLocations.get(5).add(4);
    integerVsLocations.get(5).add(6);
    integerVsLocations.get(5).add(7);

    integerVsLocations.put(6, new ArrayList<>());
    integerVsLocations.get(6).add(1);
    integerVsLocations.get(6).add(2);
    integerVsLocations.get(6).add(4);
    integerVsLocations.get(6).add(5);
    integerVsLocations.get(6).add(6);
    integerVsLocations.get(6).add(7);

    integerVsLocations.put(7, new ArrayList<>());
    integerVsLocations.get(7).add(1);
    integerVsLocations.get(7).add(3);
    integerVsLocations.get(7).add(6);

    integerVsLocations.put(8, new ArrayList<>());
    integerVsLocations.get(8).add(1);
    integerVsLocations.get(8).add(2);
    integerVsLocations.get(8).add(3);
    integerVsLocations.get(8).add(4);
    integerVsLocations.get(8).add(5);
    integerVsLocations.get(8).add(6);
    integerVsLocations.get(8).add(7);

    integerVsLocations.put(9, new ArrayList<>());
    integerVsLocations.get(9).add(1);
    integerVsLocations.get(9).add(2);
    integerVsLocations.get(9).add(3);
    integerVsLocations.get(9).add(4);
    integerVsLocations.get(9).add(6);
    integerVsLocations.get(9).add(7);

    return integerVsLocations;
  }
}
