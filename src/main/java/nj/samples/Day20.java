package nj.samples;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.BitSet;
import java.util.List;
import nj.samples.day20.Image;

public class Day20 {
  public static void main(String[] args) throws IOException {
    List<String> lst =
        Files.readAllLines(Paths.get("C:\\TNPS\\samples\\adventOfCode\\src\\main\\resources\\day20.txt"));
    BitSet alg = getBitSet(lst.get(0));
    Image initialImage = getInitialImage(lst);
    Image temp = initialImage;
    for(int i = 0; i < 2; i++) {
      temp = temp.transform(alg);
    }
    System.out.println("Answer 1:" + temp.getBrightSpots());
    for(int i = 2; i < 50; i++) {
      temp = temp.transform(alg);
    }
    System.out.println("Answer 2:" + temp.getBrightSpots());
  }

  private static Image getInitialImage(List<String> lst) {
    Image initImage = new Image();
    for(int i = 2; i < lst.size(); i++) {
      initImage.getMatrix().add(getBitSet(lst.get(i)));
    }
    initImage.setRows(initImage.getMatrix().size());
    initImage.setCols(lst.get(2).length());
    return initImage;
  }

  private static BitSet getBitSet(String s) {
    BitSet alg = new BitSet();
    for(int i = 0; i < s.length(); i++) {
      if(s.charAt(i) == '#') {
        alg.set(i);
      }
    }
    return alg;
  }
}