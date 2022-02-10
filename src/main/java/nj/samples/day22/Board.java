package nj.samples.day22;

import lombok.Data;

@Data
public class Board {
  int p1pos;
  int p2pos;
  Dice dice;
  int p1cost;
  int p2cost;
  int winningCost = 1000;

  public Board(int pos1, int pos2) {
    p1pos = pos1;
    p2pos = pos2;
    dice = new Dice();
    p1cost = 0;
    p2cost = 0;
  }

  public void play() {
    while (true) {
      int steps = dice.getSumOfNextThreeRolls();
      p1pos = (p1pos + steps) % 10;
      if(p1pos == 0) p1pos = 10;
      p1cost += p1pos;
      if(p1cost >= winningCost) break;
      steps = dice.getSumOfNextThreeRolls();
      p2pos = (p2pos + steps) % 10;
      if(p2pos == 0) p2pos = 10;
      p2cost += p2pos;
      if(p2cost >= winningCost) break;
    }
    int ans1 = p1cost < p2cost ? p1cost * dice.getNumOfRolls() : p2cost * dice.getNumOfRolls();
    System.out.println("Answer 1: " + ans1);
  }
}
