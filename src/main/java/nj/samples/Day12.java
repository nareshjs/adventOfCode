package nj.samples;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import nj.samples.util.GraphNode;

public class Day12 {
  public static void main(String []args) throws IOException {
    List<String> lst =
        Files.readAllLines(Paths.get("C:\\TNPS\\samples\\adventOfCode\\src\\main\\resources\\day12.txt"));
    Map<String, GraphNode> graph = populateGraph(lst);
    List<String> paths = new ArrayList<>();
    List<String> visited = new ArrayList<>();
    dfs(graph, graph.get("start"), visited, paths, false);
    System.out.println("Answer 1: " + paths.size());

    paths = new ArrayList<>();
    visited = new ArrayList<>();
    dfs(graph, graph.get("start"), visited, paths, true);
    System.out.println("Answer 2: " + paths.size());
  }

  private static void dfs(Map<String, GraphNode> graph, GraphNode node, List<String> visited, List<String> paths,
                          boolean isPart2) {
    visited.add(node.name);
    if(node.name.equals("end")) {
      String path = getPath(visited);
      if(!paths.contains(path)) paths.add(path);
      return;
    }
    for(String nextNode : node.neighbours) {
      if (!visited.contains(nextNode) || nextNode.toUpperCase().equals(nextNode) ||
          checkLowerCaseConditions(isPart2, visited, nextNode)) {
        List<String> visitedClone = new ArrayList<>(visited);
        dfs(graph, graph.get(nextNode), visitedClone, paths, isPart2);
      }
    }
  }

  private static boolean checkLowerCaseConditions(boolean isPart2, List<String> visited, String nextNode) {
    if(isPart2 && !nextNode.equals("start") && !nextNode.equals("end") && nextNode.toLowerCase().equals(nextNode)) {
      Map<String, Integer> countMap = new LinkedHashMap<>();
      visited.stream().filter(y -> y.toLowerCase().equals(y)).forEach(x -> {
        if(!countMap.containsKey(x)) {
          countMap.put(x, 0);
        }
        countMap.put(x, countMap.get(x) + 1);
      });
      if(countMap.values().stream().filter(y -> y>1).count() == 0) return true;
    }
    return false;
  }

  private static String getPath(List<String> newVisited) {
    StringBuilder sb = new StringBuilder();
    newVisited.stream().forEach(x -> {
      sb.append(x);
      sb.append(",");
    });
    sb.deleteCharAt(sb.length() - 1);
    return sb.toString();
  }

  private static Map<String, GraphNode> populateGraph(List<String> lst) {
    Map<String, GraphNode> graph = new LinkedHashMap<>();
    for(String s : lst) {
      String[] parts = s.split("-");
      String nodea = parts[0];
      String nodez = parts[1];
      GraphNode nodeA = getNode(nodea, graph);
      GraphNode nodeZ = getNode(nodez, graph);
      nodeA.neighbours.add(nodez);
      nodeZ.neighbours.add(nodea);
    }
    return graph;
  }

  private static GraphNode getNode(String nodeName, Map<String, GraphNode> graph) {
    if(!graph.containsKey(nodeName)) {
      GraphNode newNode = new GraphNode();
      newNode.name = nodeName;
      graph.put(nodeName, newNode);
    }
    return graph.get(nodeName);
  }
}
