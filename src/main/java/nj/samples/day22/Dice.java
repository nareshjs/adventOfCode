package nj.samples.day22;

import lombok.Data;

@Data
public class Dice {
  int curIndex = 0;
  int numOfRolls = 0;

  public int getSumOfNextThreeRolls() {
    return getNext() + getNext() + getNext();
  }

  private int getNext() {
    numOfRolls++;
    curIndex++;
    if(curIndex == 101) curIndex = 1;
    return curIndex;
  }
}
