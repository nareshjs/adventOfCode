package nj.samples.util;

import java.util.LinkedHashSet;
import java.util.Set;

public class GraphNode {
  public String name;
  public Set<String> neighbours = new LinkedHashSet<>();
}
