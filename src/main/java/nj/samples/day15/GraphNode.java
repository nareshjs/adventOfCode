package nj.samples.day15;

import java.util.LinkedHashSet;
import java.util.Set;

public class GraphNode {
  public String name;
  public Set<GraphLink> links = new LinkedHashSet<>();
  public int curCost = Integer.MAX_VALUE;
}
