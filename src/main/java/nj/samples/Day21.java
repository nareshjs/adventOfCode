package nj.samples;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

//Follow this order Day 21,22,24,19,23
public class Day21 {
  public static void main(String[] args) throws IOException {
    List<String> lst =
        Files.readAllLines(Paths.get("C:\\TNPS\\samples\\adventOfCode\\src\\main\\resources\\test.txt"));
  }
}
