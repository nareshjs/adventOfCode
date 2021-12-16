package nj.samples;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class Day16Test {
  @BeforeEach
  void init() {
    Day16.versionSum = 0;
    Day16.index = 0;
  }

  @Test
  void test1() {
    Map<Character, String> hex2Bin = Day16.getHexToBinaryMapper();
    List<Character> convertedInput = new ArrayList<>();
    Arrays.stream("C200B40A82".split("")).forEach(x -> {
      Arrays.stream(hex2Bin.get(x.charAt(0)).split("")).forEach(y -> convertedInput.add(y.charAt(0)));
    });
    List<Long> operands = new ArrayList<>();
    Day16.processPacket(convertedInput, operands);
    Assertions.assertEquals(3, operands.get(0));
    Assertions.assertEquals(1, operands.size());
  }

  @Test
  void test2() {
    Map<Character, String> hex2Bin = Day16.getHexToBinaryMapper();
    List<Character> convertedInput = new ArrayList<>();
    Arrays.stream("04005AC33890".split("")).forEach(x -> {
      Arrays.stream(hex2Bin.get(x.charAt(0)).split("")).forEach(y -> convertedInput.add(y.charAt(0)));
    });
    List<Long> operands = new ArrayList<>();
    Day16.processPacket(convertedInput, operands);
    Assertions.assertEquals(54, operands.get(0));
    Assertions.assertEquals(1, operands.size());
  }

  @Test
  void test3() {
    Map<Character, String> hex2Bin = Day16.getHexToBinaryMapper();
    List<Character> convertedInput = new ArrayList<>();
    Arrays.stream("880086C3E88112".split("")).forEach(x -> {
      Arrays.stream(hex2Bin.get(x.charAt(0)).split("")).forEach(y -> convertedInput.add(y.charAt(0)));
    });
    List<Long> operands = new ArrayList<>();
    Day16.processPacket(convertedInput, operands);
    Assertions.assertEquals(7, operands.get(0));
    Assertions.assertEquals(1, operands.size());
  }

  @Test
  void test4() {
    Map<Character, String> hex2Bin = Day16.getHexToBinaryMapper();
    List<Character> convertedInput = new ArrayList<>();
    Arrays.stream("CE00C43D881120".split("")).forEach(x -> {
      Arrays.stream(hex2Bin.get(x.charAt(0)).split("")).forEach(y -> convertedInput.add(y.charAt(0)));
    });
    List<Long> operands = new ArrayList<>();
    Day16.processPacket(convertedInput, operands);
    Assertions.assertEquals(9, operands.get(0));
    Assertions.assertEquals(1, operands.size());
  }

  @Test
  void test5() {
    Map<Character, String> hex2Bin = Day16.getHexToBinaryMapper();
    List<Character> convertedInput = new ArrayList<>();
    Arrays.stream("D8005AC2A8F0".split("")).forEach(x -> {
      Arrays.stream(hex2Bin.get(x.charAt(0)).split("")).forEach(y -> convertedInput.add(y.charAt(0)));
    });
    List<Long> operands = new ArrayList<>();
    Day16.processPacket(convertedInput, operands);
    Assertions.assertEquals(1, operands.get(0));
    Assertions.assertEquals(1, operands.size());
  }

  @Test
  void test6() {
    Map<Character, String> hex2Bin = Day16.getHexToBinaryMapper();
    List<Character> convertedInput = new ArrayList<>();
    Arrays.stream("F600BC2D8F".split("")).forEach(x -> {
      Arrays.stream(hex2Bin.get(x.charAt(0)).split("")).forEach(y -> convertedInput.add(y.charAt(0)));
    });
    List<Long> operands = new ArrayList<>();
    Day16.processPacket(convertedInput, operands);
    Assertions.assertEquals(0, operands.get(0));
    Assertions.assertEquals(1, operands.size());
  }

  @Test
  void test7() {
    Map<Character, String> hex2Bin = Day16.getHexToBinaryMapper();
    List<Character> convertedInput = new ArrayList<>();
    Arrays.stream("9C005AC2F8F0".split("")).forEach(x -> {
      Arrays.stream(hex2Bin.get(x.charAt(0)).split("")).forEach(y -> convertedInput.add(y.charAt(0)));
    });
    List<Long> operands = new ArrayList<>();
    Day16.processPacket(convertedInput, operands);
    Assertions.assertEquals(0, operands.get(0));
    Assertions.assertEquals(1, operands.size());
  }

  @Test
  void test8() {
    Map<Character, String> hex2Bin = Day16.getHexToBinaryMapper();
    List<Character> convertedInput = new ArrayList<>();
    Arrays.stream("9C0141080250320F1802104A08".split("")).forEach(x -> {
      Arrays.stream(hex2Bin.get(x.charAt(0)).split("")).forEach(y -> convertedInput.add(y.charAt(0)));
    });
    List<Long> operands = new ArrayList<>();
    Day16.processPacket(convertedInput, operands);
    Assertions.assertEquals(1, operands.get(0));
    Assertions.assertEquals(1, operands.size());
  }
}
