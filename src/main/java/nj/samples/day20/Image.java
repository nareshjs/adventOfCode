package nj.samples.day20;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Image {
  List<BitSet> matrix;
  boolean isImageBorderDark;
  int iterationNumber;
  int rows;
  int cols;

  public Image() {
    matrix = new ArrayList<>();
    isImageBorderDark = true;
    iterationNumber = 0;
    rows = 0;
    cols = 0;
  }

  public Image(Image prev, boolean isBorderDark) {
    matrix = new ArrayList<>();
    iterationNumber = prev.iterationNumber + 1;
    if (isBorderDark && iterationNumber % 2 == 1) {
      isImageBorderDark = false;
    } else {
      isImageBorderDark = true;
    }
    for (int i = 0; i < prev.getMatrix().size() + 2; i++) {
      matrix.add(new BitSet());
    }
    rows = prev.getRows() + 2;
    cols = prev.getCols() + 2;
  }

  public Image transform(BitSet alg) {
    Image newImage = new Image(this, alg.get(0));
    for (int i = 0; i < newImage.rows; i++) {
      for (int j = 0; j < newImage.cols; j++) {
        int algIndex = getAlgIndex(i, j);
        if (alg.get(algIndex)) {
          newImage.setImageIndex(i, j);
        }
      }
    }
    return newImage;
  }

  private void setImageIndex(int i, int j) {
    matrix.get(i).set(j);
  }

  private int getAlgIndex(int i, int j) {
    i = i - 1;
    j = j - 1;
    StringBuilder sb = new StringBuilder();
    sb.append(getBit(i - 1, j - 1));
    sb.append(getBit(i - 1, j));
    sb.append(getBit(i - 1, j + 1));
    sb.append(getBit(i, j - 1));
    sb.append(getBit(i, j));
    sb.append(getBit(i, j + 1));
    sb.append(getBit(i + 1, j - 1));
    sb.append(getBit(i + 1, j));
    sb.append(getBit(i + 1, j + 1));
    return Integer.parseInt(sb.toString(), 2);
  }

  private String getBit(int i, int j) {
    if(i < 0 || j < 0 || i > rows - 1 || j > cols - 1) {
      return isImageBorderDark ? "0" : "1";
    } else {
      return matrix.get(i).get(j) ? "1" : "0";
    }
  }

  public int getBrightSpots() {
    int count = 0;
    for (int i = 0; i < matrix.size(); i++) {
      BitSet row = matrix.get(i);
      for (int j = 0; j < row.length(); j++) {
        if (row.get(j)) {
          count++;
        }
      }
    }
    return count;
  }

  public void print() {
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < cols; j++) {
        System.out.print(matrix.get(i).get(j) ? "# " : ". ");
      }
      System.out.println("");
    }
    System.out.println("-----------------------------");
  }
}
