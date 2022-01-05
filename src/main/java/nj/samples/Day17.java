package nj.samples;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import nj.samples.util.Coordinate;

public class Day17 {
  public static void main(String[] args) throws IOException {
    List<String> lst =
        Files.readAllLines(Paths.get("C:\\TNPS\\samples\\adventOfCode\\src\\main\\resources\\day17.txt"));
    String[] parts = lst.get(0).split("=");
    String[] xparts = (parts[1].split(","))[0].split("\\.\\.");
    String[] yparts = parts[2].split("\\.\\.");
    probeLaunch(Integer.parseInt(xparts[0]), Integer.parseInt(xparts[1]), Integer.parseInt(yparts[0]),
        Integer.parseInt(yparts[1]));
  }

  private static void probeLaunch(int xmin, int xmax, int ymin, int ymax) {
    Collection<Integer> maxY = new ArrayList<>();
    for (int xv = 1; xv <= xmax; xv++) {
      for(int yv = ymin; yv <= Math.abs(ymin); yv++) {
        probeLaunch(xmin, xmax, ymin, ymax, xv, yv, maxY);
      }
    }
    System.out.println("Answer 1: " + maxY.stream().sorted().max(Integer::compareTo).get());
    System.out.println("Answer 2: " + maxY.size());
  }

  private static void probeLaunch(int xmin, int xmax, int ymin, int ymax, int xv, int yv, Collection<Integer> maxY) {
    Coordinate start = new Coordinate(xv, yv);
    Set<Integer> allys = new LinkedHashSet<>();

    while(coordinateWithinLimits(start, xmax, ymin)) {
      allys.add(start.y);
      if(coordinateReachedDestination(start, xmin, xmax, ymin, ymax)) {
        Optional<Integer> maxy = allys.stream().sorted().max(Integer::compareTo);
        if(maxy.isPresent()) {
          maxY.add(maxy.get());
        }
        break;
      }
      xv = xv == 0 ? 0 : xv-1;
      yv--;
      start = new Coordinate(start.x + xv, start.y + yv);
    }
  }

  private static boolean coordinateReachedDestination(Coordinate pt, int xmin, int xmax, int ymin, int ymax) {
    if(pt.x >=xmin && pt.x <= xmax && pt.y >= ymin && pt.y <= ymax) return true;
    return false;
  }

  private static boolean coordinateWithinLimits(Coordinate pt, int xmax, int ymin) {
    if(pt.x > xmax || pt.y < ymin) return false;
    return true;
  }
}
