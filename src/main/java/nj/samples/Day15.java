package nj.samples;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import nj.samples.day15.GraphLink;
import nj.samples.day15.GraphNode;


//Checked online to do dijkstra, also was able to put nodeCost on node itself since the node was my implementation,
//if it was some other impl, need to see how to do that. Also only cost was required and not the path.
public class Day15 {
  public static void main(String []args) throws IOException {
    List<String> lst =
        Files.readAllLines(Paths.get("C:\\TNPS\\samples\\adventOfCode\\src\\main\\resources\\day15.txt"));
    int rows = lst.size();
    int cols = lst.get(0).length();
    //Part 1
    Map<String, GraphNode> graph = getGraph(lst);
    GraphNode startNode = graph.get(getGraphNodeName(0, 0));
    startNode.curCost = 0;
    Set<String> visited = new LinkedHashSet<>();
    List<GraphNode> queue = new ArrayList<>();
    queue.add(startNode);
    dijkstra(graph, visited, graph.get(getGraphNodeName(rows - 1, cols - 1)), queue);

    //Part 2
    graph = getPart2Graph(lst);
    startNode = graph.get(getGraphNodeName(0, 0));
    startNode.curCost = 0;
    visited = new LinkedHashSet<>();
    queue = new ArrayList<>();
    queue.add(startNode);
    dijkstra(graph, visited, graph.get(getGraphNodeName((rows*5) - 1, (cols*5) - 1)), queue);
  }

  private static void dijkstra(Map<String, GraphNode> graph, Set<String> visited,
                               GraphNode endNode, List<GraphNode> queue) {
    while (!queue.isEmpty()) {
      GraphNode node = queue.get(0);
      if (node.name == endNode.name) {
        System.out.println("Answer " + endNode.curCost);
        break;
      }
      queue.remove(node);
      visited.add(node.name);
      for (GraphLink link : node.links) {
        if(!visited.contains(link.dest)) {
          GraphNode nbr = graph.get(link.dest);
          if (nbr.curCost > node.curCost + link.cost) {
            nbr.curCost = node.curCost + link.cost;
          }
          if(queue.contains(nbr)) {
            queue.remove(nbr);
          }
          queue.add(nbr);
        }
      }
      Collections.sort(queue, (o1, o2) -> Integer.compare(o1.curCost, o2.curCost));
    }
  }

  private static Map<String, GraphNode> getPart2Graph(List<String> lst) {
    Map<String, GraphNode> graph = new LinkedHashMap<>();
    int rows = lst.size();
    int cols = lst.get(0).length();
    int[][] costMatrix = new int[rows][cols];
    for(int i = 0; i < rows; i++) {
      String[] parts = lst.get(i).split("");
      for(int j = 0; j < cols; j++) {
        costMatrix[i][j] = Integer.parseInt(parts[j]);
      }
    }
    int[][] newCostMatrix = new int[rows*5][cols*5];
    for(int i = 0; i < rows*5; i++) {
      for (int j = 0; j < cols * 5; j++) {
        if(i<rows && j < rows) newCostMatrix[i][j] = costMatrix[i][j];
        int origVal = costMatrix[i%rows][j%cols];
        int incr = (i/rows) + (j/cols);
        int proposed = origVal + incr;
        newCostMatrix[i][j] = proposed > 9 ? proposed - 9 : proposed;
      }
    }
    for(int i = 0; i < rows*5; i++) {
      for(int j = 0; j < cols*5; j++) {
        GraphNode newNode = new GraphNode();
        newNode.name = getGraphNodeName(i, j);
        graph.put(newNode.name, newNode);
      }
    }
    for(int i = 0; i < rows*5; i++) {
      for(int j = 0; j < cols*5; j++) {
        addLinks(graph, newCostMatrix, rows*5, cols*5, i, j);
      }
    }
    return graph;
  }

  private static Map<String, GraphNode> getGraph(List<String> lst) {
    Map<String, GraphNode> graph = new LinkedHashMap<>();
    int rows = lst.size();
    int cols = lst.get(0).length();
    int[][] costMatrix = new int[rows][cols];
    for(int i = 0; i < rows; i++) {
      String[] parts = lst.get(i).split("");
      for(int j = 0; j < cols; j++) {
        GraphNode newNode = new GraphNode();
        newNode.name = getGraphNodeName(i, j);
        graph.put(newNode.name, newNode);
        costMatrix[i][j] = Integer.parseInt(parts[j]);
      }
    }
    for(int i = 0; i < rows; i++) {
      for(int j = 0; j < cols; j++) {
        addLinks(graph, costMatrix, rows, cols, i, j);
      }
    }
    return graph;
  }

  private static void addLinks(Map<String, GraphNode> graph, int[][] costMatrix, int rows, int cols, int i, int j) {
    GraphNode node = graph.get(getGraphNodeName(i, j));
    if(Day11.isValid(i-1, j, rows, cols)) {
      addLink(costMatrix, i-1, j, node);
    }
    if(Day11.isValid(i+1, j, rows, cols)) {
      addLink(costMatrix, i+1, j, node);
    }
    if(Day11.isValid(i, j-1, rows, cols)) {
      addLink(costMatrix, i, j-1, node);
    }
    if(Day11.isValid(i, j+1, rows, cols)) {
      addLink(costMatrix, i, j+1, node);
    }
  }

  private static void addLink(int[][] costMatrix, int ii, int jj, GraphNode node) {
    GraphLink link = new GraphLink();
    link.src = node.name;
    link.dest = getGraphNodeName(ii, jj);
    link.cost = costMatrix[ii][jj];
    node.links.add(link);
  }

  private static String getGraphNodeName(int i, int j) {
    return i + "," + j;
  }
}
