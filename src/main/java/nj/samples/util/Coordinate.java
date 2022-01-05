package nj.samples.util;

public class Coordinate {
  public int x;
  public int y;

  public Coordinate(String coordinate) {
    String[] parts = coordinate.split(",");
    x = Integer.parseInt(parts[0]);
    y = Integer.parseInt(parts[1]);
  }

  public Coordinate(int xx, int yy) {
    x = xx;
    y = yy;
  }

  public static boolean areCoordinatesAlongSameAxis(Coordinate a, Coordinate b) {
    return a.x == b.x || a.y == b.y;
  }

  public static boolean areCoordinatesSameAlongxAxis(Coordinate a, Coordinate b) {
    return a.x == b.x;
  }

  public static boolean areCoordinatesSameAlongyAxis(Coordinate a, Coordinate b) {
    return a.y == b.y;
  }
}
