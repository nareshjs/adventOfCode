package nj.samples.day18;

public class SfNum {
  private SfNum x;
  private SfNum y;
  private int z;

  public SfNum(String val) {
    if(val.length() == 1|| val.length() == 2) {
      z = Integer.parseInt(val);
    } else {
      val = val.substring(1, val.length() - 1);
      int splitIndex = getSplitIndex(val);
      x = new SfNum(val.substring(0, splitIndex));
      y = new SfNum(val.substring(splitIndex + 1));
      resetZ();
    }
  }

  public SfNum(SfNum toCopy) {
    x = toCopy.getX();
    y = toCopy.getY();
    z = toCopy.getZ();
  }

  public SfNum(int val) {
    z = val;
  }

  public void add(SfNum toAdd) {
    addToSelf(toAdd);
    //System.out.println(this.toString());
    boolean process = true;
    while (process) {
      process = false;
      while (needToExplode()) {
        process = true;
        explode();
       // System.out.println(this.toString());
      }
      if(needToSplit()) {
        process = true;
        split();
        //System.out.println(this.toString());
      }
    }
  }

  public void explode() {
    SfNum sfNumToExplode = identifySfForExplode(4);
    int left = sfNumToExplode.getX().getZ();
    int right = sfNumToExplode.getY().getZ();

    SfNum parentForPredecessor = identifyParentPredecessor(sfNumToExplode);
    if(parentForPredecessor != null) {
      SfNum predecessor = parentForPredecessor.getX();
      while(predecessor.getZ() == -1) {
        predecessor = predecessor.getY();
      }
      predecessor.setZ(predecessor.getZ() + left);
    }

    SfNum parentForSuccessor = identifyParentSuccessor(sfNumToExplode);
    if(parentForSuccessor != null) {
      SfNum successor = parentForSuccessor.getY();
      while(successor.getZ() == -1) {
        successor = successor.getX();
      }
      successor.setZ(successor.getZ() + right);
    }

    SfNum parentOfExplodingNum = getParent(sfNumToExplode);
    if(parentOfExplodingNum.getX() == sfNumToExplode) {
      parentOfExplodingNum.setX(new SfNum(0));
    } else {
      parentOfExplodingNum.setY(new SfNum(0));
    }
  }

  private SfNum identifyParentSuccessor(SfNum child) {
    SfNum parent = getParent(child);
    while(parent != null && parent.getY() == child) {
      child = parent;
      parent = getParent(child);
    }
    return parent;
  }

  public SfNum identifyParentPredecessor(SfNum child) {
    SfNum parent = getParent(child);
    while(parent != null && parent.getX() == child) {
      child = parent;
      parent = getParent(child);
    }
    return parent;
  }

  public SfNum getParent(SfNum child) {
    if(x == child || y == child) return this;
    SfNum retVal = null;
    if(x != null) {
      retVal = x.getParent(child);
    }
    if(retVal == null && y != null) {
      retVal = y.getParent(child);
    }
    return retVal;
  }

  public SfNum identifySfForExplode(int level) {
    if(level == 0 && x!= null && y != null) return this;
    SfNum sfNum = null;
    if(x!=null) {
      sfNum = x.identifySfForExplode(level - 1);
    }
    if(sfNum == null && y != null) {
      sfNum = y.identifySfForExplode(level - 1);
    }
    return sfNum;
  }

  public void split() {
    if(z > 9) {
      splitLeaf();
      return;
    }
    if(x != null) {
      int origSize = x.getSize();
      x.split();
      int newSize = x.getSize();
      if(origSize + 2 == newSize) return;
    }
    if(y != null) {
      int origSize = y.getSize();
      y.split();
      int newSize = y.getSize();
      if(origSize + 2 == newSize) return;
    }
  }

  private void splitLeaf() {
    x = new SfNum(z/2);
    y = new SfNum(z % 2 == 1 ? (z/2)+1 : z/2);
    resetZ();
  }

  private void addToSelf(SfNum toAdd) {
    SfNum copy = new SfNum(this);
    setX(copy);
    setY(toAdd);
    resetZ();
  }

  private void resetZ() {
    z = -1;
  }

  public boolean needToExplode() {
    return getDepth() > 4;
  }

  public boolean needToSplit() {
    if(z > 9) return true;
    else if(x == null && y == null) return false;
    else {
      return (x != null ? x.needToSplit() : false) || (y != null ? y.needToSplit() : false);
    }
  }

  public int magnitude() {
    if(z != -1) return z;
    else {
      int xMagnitude = x!=null ? x.magnitude() : 0;
      int yMagnitude = y!=null ? y.magnitude() : 0;
      return (3*xMagnitude) + (2*yMagnitude);
    }
  }

  public int getSize() {
    if(z != -1 && x == null && y == null) return 1;
    else {
      int xSize = x != null ? x.getSize() : -1;
      int ySize = y != null ? y.getSize() : -1;
      return xSize + ySize + 1;
    }
  }

  public int getDepth() {
    if(z != -1 && x == null && y == null) return 0;
    else {
      int xDepth = x != null ? x.getDepth() : -1;
      int yDepth = y != null ? y.getDepth() : -1;
      return xDepth > yDepth ? xDepth + 1 : yDepth + 1;
    }
  }

  private int getSplitIndex(String val) {
    int index = -1;
    int countOpen = 0;
    int countClose = 0;
    for(int i = 0; i < val.length() && index == -1; i++) {
      char eachChar = val.charAt(i);
      switch (eachChar) {
        case '[':
          countOpen++;
          break;
        case ']':
          countClose++;
          break;
        case ',':
          if(countClose == countOpen) {
            index = i;
          }
          break;
        default:
          break;
      }
    }
    return index;
  }

  public SfNum getX() {
    return x;
  }

  public void setX(SfNum x) {
    this.x = x;
  }

  public SfNum getY() {
    return y;
  }

  public void setY(SfNum y) {
    this.y = y;
  }

  public int getZ() {
    return z;
  }

  public void setZ(int z) {
    this.z = z;
  }

  public String toString() {
    StringBuffer sb = new StringBuffer();
    fillPrintString(sb);
    return sb.toString();
  }

  private void fillPrintString(StringBuffer sb) {
    if(x == null && y == null) sb.append(z);
    if(x != null) {
      sb.append("[");
      x.fillPrintString(sb);
      sb.append(",");
    }
    if(y != null) {
      y.fillPrintString(sb);
      sb.append("]");
    }
  }
}
