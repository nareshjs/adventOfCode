package nj.samples;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

//Easy interview question
public class Day10 {
  public static void main(String []args) throws IOException {
    List<String> lst = Files.readAllLines(Paths.get("C:\\TNPS\\samples\\adventOfCode\\src\\main\\resources\\day10.txt"));
    long cost = 0;
    List<String> autoCompleted = new ArrayList<>();
    for(String s : lst) {
      String[] parts = s.split("");
      LinkedList<String> stack = new LinkedList<>();
      boolean autoComplete = true;
      for(String brace : parts) {
        if(stack.isEmpty()) {
          if(!isClosingBrace(brace)) {
            stack.addLast(brace);
          } else {
            cost *= getCost(brace);
          }
        } else {
          String openBrace = getOpenBrace(brace);
          if (openBrace != null && stack.getLast().equals(openBrace)) {
            stack.removeLast();
          } else if (isClosingBrace(brace)) {
            cost += getCost(brace);
            autoComplete = false;
            break;
          } else {
            stack.addLast(brace);
          }
        }
      }
      //Auto complete
      if(autoComplete) {
        addAutoCompleteString(autoCompleted, stack);
      }
    }
    System.out.println("Answer 1: " + cost);
    part2(autoCompleted);
  }

  private static void addAutoCompleteString(List<String> autoCompleted, LinkedList<String> stack) {
    StringBuilder sb = new StringBuilder();
    for(String ss : stack) {
      sb.append(getCloseBrace(ss));
    }
    autoCompleted.add(sb.reverse().toString());
  }

  private static void part2(List<String> autoCompleted) {
    long multiplier = 5;
    List<Long> completionScores = new ArrayList<>();
    for(String s : autoCompleted) {
      long score = 0;
      for(int i = 0; i < s.length(); i++) {
        score *= multiplier;
        score += getCompletionCost(s.charAt(i));
      }
      completionScores.add(score);
    }
    Collections.sort(completionScores);
    System.out.println("Answer 2: " + completionScores.get(completionScores.size()/2));
  }

  private static int getCompletionCost(char charAt) {
    switch (charAt) {
      case ')':
        return 1;
      case ']':
        return 2;
      case '}':
        return 3;
      case '>':
        return 4;
      default:
        return 0;
    }
  }

  private static boolean isClosingBrace(String brace) {
    String allClosingBraces = "]})>";
    return allClosingBraces.contains(brace);
  }

  private static String getOpenBrace(String s) {
    String retVal;
    switch (s) {
      case "}":
        retVal = "{";
        break;
      case ">":
        retVal = "<";
        break;
      case ")":
        retVal = "(";
        break;
      case "]":
        retVal = "[";
        break;
      default:
        retVal = null;
        break;
    }
    return retVal;
  }

  private static String getCloseBrace(String s) {
    String retVal;
    switch (s) {
      case "{":
        retVal = "}";
        break;
      case "<":
        retVal = ">";
        break;
      case "(":
        retVal = ")";
        break;
      case "[":
        retVal = "]";
        break;
      default:
        retVal = null;
        break;
    }
    return retVal;
  }

  private static long getCost(String s) {
    long retVal;
    switch (s) {
      case "}":
        retVal = 1197;
        break;
      case ">":
        retVal = 25137;
        break;
      case ")":
        retVal = 3;
        break;
      case "]":
        retVal = 57;
        break;
      default:
        retVal = 1;
        break;
    }
    return retVal;
  }
}
