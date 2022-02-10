package nj.samples;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import nj.samples.day22.Board;

//Got this answer for Part 2 in reddit
public class Day21 {
  public static void main(String[] args) throws IOException {
    List<String> lst =
        Files.readAllLines(Paths.get("C:\\TNPS\\samples\\adventOfCode\\src\\main\\resources\\day21.txt"));
    int p1pos = Integer.parseInt(lst.get(0).split(":")[1].trim());
    int p2pos = Integer.parseInt(lst.get(1).split(":")[1].trim());
    Board board = new Board(p1pos, p2pos);
    board.play();

    day2(p1pos, p2pos);
  }

  static final int MAX_SCORE = 21;
  static final int[] diceSums = new int[10];

  public static void day2(int p1pos, int p2pos) {
    List<Integer> players = new ArrayList<>();
    players.add(p1pos);
    players.add(p2pos);

    for(int i=1; i<=3; i++) {
      for(int j=1; j<=3; j++) {
        for(int k=1; k<=3; k++) {
          diceSums[i+j+k]++; //i,j,k are the 3 rolls. index of diceSums is the sum of rolls.
          //if there are 6 combos of i,j,k where sum of i,j,k is 5, then a triple dice roll summing to 5 can occur in 6 different universes.
        }
      }
    }

    List<Long> playerWins = rollDice(players.get(0),0,players.get(1),0);
    System.out.println(playerWins);
    System.out.println("Answer 2: " + Math.max(playerWins.get(0), playerWins.get(1)));
  }

  private static List<Long> rollDice(int player1Pos, int score1, int player2Pos, int score2) {
    if(score2 >= MAX_SCORE) { //Player 1 is to roll. Hence player 2's score is checked before the roll.
      List<Long> result = new ArrayList<>();
      result.add(0l);
      result.add(1l);
      return result;
    }

    long win1 = 0, win2 = 0;
    for(int i=3; i<=9; i++) { //sum of 3 rolls ranges from 3 to 9
      int tempPos = (player1Pos + i)%10 == 0?10:(player1Pos + i)%10;

      //Player 2 is rolling the Dice here. Which is why its position in the method arguments is swapped with player 1
      //so that Player 1's score can be checked before player 2 rolls
      List<Long> result = rollDice(player2Pos, score2, tempPos, score1 + tempPos);

      win1 += diceSums[i]*result.get(1); //result.second because in the above call player2's position was swapped with player 1.
      win2 += diceSums[i]*result.get(0);
    }

    List<Long> result = new ArrayList<>();
    result.add(win1);
    result.add(win2);
    return result;
  }
}
