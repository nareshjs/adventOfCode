package nj.samples;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//Spent a lot of time in Part 2 since in Part 1 I had used int and did not completely change to long in getDecimalValue when I started Part 2.
public class Day16 {

  public static int versionSum = 0;
  public static int index = 0;
  public static void main(String []args) throws IOException {
    List<String> lst =
        Files.readAllLines(Paths.get("C:\\TNPS\\samples\\adventOfCode\\src\\main\\resources\\day16.txt"));
    process(lst.get(0));
  }

  private static void process(String input) {
    Map<Character, String> hex2Bin = getHexToBinaryMapper();
    List<Character> convertedInput = new ArrayList<>();
    Arrays.stream(input.split("")).forEach(x -> {
      Arrays.stream(hex2Bin.get(x.charAt(0)).split("")).forEach(y -> convertedInput.add(y.charAt(0)));
    });
    List<Long> operands = new ArrayList<>();
    processPacket(convertedInput, operands);
    System.out.println("Answer 1: " + versionSum);
    System.out.println("Answer 2: " + operands.get(0));
  }

  public static void processPacket(List<Character> convertedInput, List<Long> operands) {
    index += 3;
    List<Character> versionPart = convertedInput.subList(index - 3, index);
    versionSum += getDecimalValue(versionPart);

    index += 3;
    List<Character> typePart = convertedInput.subList(index - 3, index);
    long type = getDecimalValue(typePart);
    if(type == 4) {
      //literal
      List<Character> binFormat = new ArrayList<>();
      do {
        index += 5;
        binFormat.addAll(convertedInput.subList(index-4, index));
      } while(convertedInput.get(index-5) != '0');
      operands.add(getDecimalValue(binFormat));
    } else {
      //operator
      index++;
      int length = convertedInput.get(index - 1) == '1' ? 11 : 15;
      List<Long> opersForOp = new ArrayList<>();
      index += length;
      List<Character> lengthPart = convertedInput.subList(index - length, index);
      long subPackets = getDecimalValue(lengthPart);
      if (length == 15) {
        long packetEndIndex = index+subPackets;
        while (index < packetEndIndex) {
          processPacket(convertedInput, opersForOp);
        }
      } else {
        for(int i = 0; i < subPackets; i++) {
          processPacket(convertedInput, opersForOp);
        }
      }
      operands.add(getOperationValue(type, opersForOp));
    }
  }

  private static Long getOperationValue(long type, List<Long> opersForOp) {
    Long retValue;
    switch ((int) type) {
      case 0:
        retValue = opersForOp.stream().reduce(Long::sum).get();
        break;
      case 1:
        retValue = opersForOp.stream().reduce(1L, (a, b) -> a * b);
        break;
      case 2:
        retValue = opersForOp.stream().min((o1, o2) -> Long.compare(o1, o2)).get();
        break;
      case 3:
        retValue = opersForOp.stream().max((o1, o2) -> Long.compare(o1, o2)).get();
        break;
      case 5:
        retValue = opersForOp.get(0) > opersForOp.get(1) ? 1L : 0L;
        break;
      case 6:
        retValue = opersForOp.get(0) < opersForOp.get(1) ? 1L : 0L;
        break;
      case 7:
        retValue = opersForOp.get(0).equals(opersForOp.get(1)) ? 1L : 0L;
        break;
      default:
        retValue = -1L;
        break;
    }
    return retValue;
  }

  private static long getDecimalValue(List<Character> binaryFormat) {
    long value = 0;
    int length = binaryFormat.size();
    for(int i  = 0; i < length; i++) {
      if(binaryFormat.get(length - (1 + i)) == '1') {
        value += Math.pow(2, i);
      }
    }
    return value;
  }

  public static Map<Character, String> getHexToBinaryMapper() {
    Map<Character, String> hashMap = new HashMap<>();

    // storing the key value pairs
    hashMap.put('0', "0000");
    hashMap.put('1', "0001");
    hashMap.put('2', "0010");
    hashMap.put('3', "0011");
    hashMap.put('4', "0100");
    hashMap.put('5', "0101");
    hashMap.put('6', "0110");
    hashMap.put('7', "0111");
    hashMap.put('8', "1000");
    hashMap.put('9', "1001");
    hashMap.put('A', "1010");
    hashMap.put('B', "1011");
    hashMap.put('C', "1100");
    hashMap.put('D', "1101");
    hashMap.put('E', "1110");
    hashMap.put('F', "1111");
    return hashMap;
  }
}
