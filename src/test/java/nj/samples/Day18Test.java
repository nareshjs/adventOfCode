package nj.samples;

import nj.samples.day18.SfNum;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class Day18Test {
  @Test
  void sfNumCreation() {
    SfNum sfNum = new SfNum("1");
    Assertions.assertEquals(sfNum.getZ(), 1);
    Assertions.assertEquals(0, sfNum.getDepth());
    sfNum = new SfNum("[1,2]");
    Assertions.assertTrue(sfNum.getX() != null && sfNum.getY() != null && sfNum.getZ() == -1);
    Assertions.assertEquals(1, sfNum.getDepth());
    sfNum = new SfNum("[[1,2],3]");
    Assertions.assertTrue(sfNum.getY().getZ() == 3 && sfNum.getX() != null && sfNum.getY().getX() == null);
    Assertions.assertEquals(2, sfNum.getDepth());
    sfNum = new SfNum("[9,[8,7]]");
    Assertions.assertTrue(sfNum.getX().getZ() == 9 && sfNum.getY() != null && sfNum.getX().getX() == null);
    Assertions.assertEquals(2, sfNum.getDepth());
    sfNum = new SfNum("[[1,9],[8,5]]");
    Assertions.assertTrue(sfNum.getZ() == -1 && sfNum.getX().getZ() == -1 && sfNum.getY().getX().getZ() == 8);
    Assertions.assertEquals(2, sfNum.getDepth());
    sfNum = new SfNum("[[[[1,2],[3,4]],[[5,6],[7,8]]],9]");
    Assertions.assertTrue(sfNum.getY().getZ() == 9);
    Assertions.assertEquals(4, sfNum.getDepth());
  }

  @Test
  void sfDepth() {
    SfNum sfNum = new SfNum("[[[[[9,8],1],2],3],4]");
    Assertions.assertEquals(5, sfNum.getDepth());
    Assertions.assertEquals(true, sfNum.needToExplode());
    sfNum = new SfNum("[7,[6,[5,[4,[3,2]]]]]");
    Assertions.assertEquals(5, sfNum.getDepth());
    sfNum = new SfNum("[[6,[5,[4,[3,2]]]],1]");
    Assertions.assertEquals(5, sfNum.getDepth());
    sfNum = new SfNum("[[3,[2,[1,[7,3]]]],[6,[5,[4,[3,2]]]]]");
    Assertions.assertEquals(5, sfNum.getDepth());
    sfNum = new SfNum("[[3,[2,[8,0]]],[9,[5,[4,[3,2]]]]]");
    Assertions.assertEquals(5, sfNum.getDepth());
  }

  @Test
  void sfAtDepth() {
    SfNum sfNum = new SfNum("[[[[[9,8],1],2],3],4]");
    SfNum result = sfNum.identifySfForExplode(4);
    Assertions.assertEquals(9, result.getX().getZ());
    Assertions.assertEquals(8, result.getY().getZ());
    sfNum = new SfNum("[7,[6,[5,[4,[3,2]]]]]");
    result = sfNum.identifySfForExplode(4);
    Assertions.assertEquals(3, result.getX().getZ());
    Assertions.assertEquals(2, result.getY().getZ());
    sfNum = new SfNum("[[6,[5,[4,[3,2]]]],1]");
    result = sfNum.identifySfForExplode(4);
    Assertions.assertEquals(3, result.getX().getZ());
    sfNum = new SfNum("[[3,[2,[1,[7,3]]]],[6,[5,[4,[3,2]]]]]");
    result = sfNum.identifySfForExplode(4);
    Assertions.assertEquals(7, result.getX().getZ());
    sfNum = new SfNum("[[3,[2,[8,0]]],[9,[5,[4,[3,2]]]]]");
    result = sfNum.identifySfForExplode(4);
    Assertions.assertEquals(3, result.getX().getZ());
  }

  @Test
  void explodeTest() {
    SfNum sfNum = new SfNum("[[[[[9,8],1],2],3],4]");
    Assertions.assertTrue(sfNum.needToExplode());
    sfNum.explode();
    Assertions.assertEquals(4, sfNum.getDepth());
    Assertions.assertEquals("[[[[0,9],2],3],4]", sfNum.toString());
    sfNum = new SfNum("[7,[6,[5,[4,[3,2]]]]]");
    Assertions.assertTrue(sfNum.needToExplode());
    sfNum.explode();
    Assertions.assertEquals(4, sfNum.getDepth());
    Assertions.assertEquals("[7,[6,[5,[7,0]]]]", sfNum.toString());
    sfNum = new SfNum("[[6,[5,[4,[3,2]]]],1]");
    Assertions.assertTrue(sfNum.needToExplode());
    sfNum.explode();
    Assertions.assertEquals(4, sfNum.getDepth());
    Assertions.assertEquals(3, sfNum.getY().getZ());
    Assertions.assertEquals("[[6,[5,[7,0]]],3]", sfNum.toString());
    sfNum = new SfNum("[[3,[2,[1,[7,3]]]],[6,[5,[4,[3,2]]]]]");
    Assertions.assertTrue(sfNum.needToExplode());
    sfNum.explode();
    Assertions.assertEquals(5, sfNum.getDepth());
    Assertions.assertEquals("[[3,[2,[8,0]]],[9,[5,[4,[3,2]]]]]", sfNum.toString());
    sfNum = new SfNum("[[3,[2,[8,0]]],[9,[5,[4,[3,2]]]]]");
    Assertions.assertTrue(sfNum.needToExplode());
    sfNum.explode();
    Assertions.assertEquals(4, sfNum.getDepth());
    Assertions.assertEquals("[[3,[2,[8,0]]],[9,[5,[7,0]]]]", sfNum.toString());
  }

  @Test
  void getParentTest() {
    SfNum sfNum = new SfNum("[[[[[9,8],1],2],3],4]");
    SfNum result = sfNum.identifySfForExplode(4);
    SfNum parent = sfNum.getParent(result);
    Assertions.assertEquals(1, parent.getY().getZ());
    sfNum = new SfNum("[7,[6,[5,[4,[3,2]]]]]");
    result = sfNum.identifySfForExplode(4);
    parent = sfNum.getParent(result);
    Assertions.assertEquals(4, parent.getX().getZ());
    sfNum = new SfNum("[[6,[5,[4,[3,2]]]],1]");
    result = sfNum.identifySfForExplode(4);
    parent = sfNum.getParent(result);
    Assertions.assertEquals(4, parent.getX().getZ());
    sfNum = new SfNum("[[3,[2,[1,[7,3]]]],[6,[5,[4,[3,2]]]]]");
    result = sfNum.identifySfForExplode(4);
    parent = sfNum.getParent(result);
    Assertions.assertEquals(1, parent.getX().getZ());
    sfNum = new SfNum("[[3,[2,[8,0]]],[9,[5,[4,[3,2]]]]]");
    result = sfNum.identifySfForExplode(4);
    parent = sfNum.getParent(result);
    Assertions.assertEquals(4, parent.getX().getZ());
  }

  @Test
  void sumTest() {
    SfNum a = new SfNum("[1,2]");
    SfNum b = new SfNum("[[3,4],5]");
    a.add(b);
    Assertions.assertTrue(a.getX().getX() != null && a.getX().getY() != null);
    Assertions.assertEquals("[[1,2],[[3,4],5]]", a.toString());

    a = new SfNum("[[[[4,3],4],4],[7,[[8,4],9]]]");
    b = new SfNum("[1,1]");
    a.add(b);
    Assertions.assertEquals("[[[[0,7],4],[[7,8],[6,0]]],[8,1]]", a.toString());

    a = new SfNum("[[[[7,0],[7,7]],[[7,7],[7,8]]],[[[7,7],[8,8]],[[7,7],[8,7]]]]");
    b = new SfNum("[7,[5,[[3,8],[1,4]]]]");
    a.add(b);
    Assertions.assertEquals("[[[[7,7],[7,8]],[[9,5],[8,7]]],[[[6,8],[0,8]],[[9,9],[9,0]]]]", a.toString());
  }

  @Test
  void splitCheckTest() {
    SfNum sfNum = new SfNum("9");
    Assertions.assertFalse(sfNum.needToSplit());
    sfNum.setZ(10);
    Assertions.assertTrue(sfNum.needToSplit());
    sfNum = new SfNum("[5,[[0,1],[1,1]]]");
    sfNum.getX().setZ(15);
    Assertions.assertTrue(sfNum.needToSplit() && sfNum.getX().getX() == null && sfNum.getX().getY() == null);
    sfNum.getX().setZ(5);
    sfNum.getY().getX().getX().setZ(10);
    Assertions.assertTrue(
        sfNum.needToSplit() && sfNum.getY().getX().getX().getX() == null && sfNum.getY().getX().getX().getY() == null);
  }

  @Test
  void magnitudeTest() {
    SfNum sfNum = new SfNum("9");
    Assertions.assertEquals(9, sfNum.magnitude());
    sfNum = new SfNum("[9,1]");
    Assertions.assertEquals(29, sfNum.magnitude());
    sfNum = new SfNum("[[9,1],[1,9]]");
    Assertions.assertEquals(129, sfNum.magnitude());
    sfNum = new SfNum("[[1,2],[[3,4],5]]");
    Assertions.assertEquals(143, sfNum.magnitude());
    sfNum = new SfNum("[[[[0,7],4],[[7,8],[6,0]]],[8,1]]");
    Assertions.assertEquals(1384, sfNum.magnitude());
    sfNum = new SfNum("[[[[1,1],[2,2]],[3,3]],[4,4]]");
    Assertions.assertEquals(445, sfNum.magnitude());
    sfNum = new SfNum("[[[[3,0],[5,3]],[4,4]],[5,5]]");
    Assertions.assertEquals(791, sfNum.magnitude());
    sfNum = new SfNum("[[[[5,0],[7,4]],[5,5]],[6,6]]");
    Assertions.assertEquals(1137, sfNum.magnitude());
    sfNum = new SfNum("[[[[8,7],[7,7]],[[8,6],[7,7]]],[[[0,7],[6,6]],[8,7]]]");
    Assertions.assertEquals(3488, sfNum.magnitude());
  }

  @Test
  void splitTest() {
    SfNum sfNum = new SfNum("[[[9,10],20],[8,[9,0]]]");
    Assertions.assertTrue(sfNum.needToSplit());
    sfNum.split();
    Assertions.assertEquals("[[[9,[5,5]],20],[8,[9,0]]]", sfNum.toString());
    sfNum = new SfNum("1");
    sfNum.setZ(10);
    int origSize = sfNum.getSize();
    sfNum.split();
    Assertions.assertEquals(2, sfNum.getSize() - origSize);
    sfNum = new SfNum("[5,[[0,1],[1,1]]]");
    sfNum.getX().setZ(15);
    origSize = sfNum.getSize();
    sfNum.split();
    Assertions.assertEquals(2, sfNum.getSize() - origSize);
    Assertions.assertTrue(sfNum.getX().getZ() == -1 && sfNum.getX().getX().getZ() == 7);
    sfNum = new SfNum("[[[0,1],[1,1]],5]");
    sfNum.getY().setZ(15);
    origSize = sfNum.getSize();
    sfNum.split();
    Assertions.assertEquals(2, sfNum.getSize() - origSize);
    Assertions.assertTrue(sfNum.getY().getZ() == -1 && sfNum.getY().getX().getZ() == 7);
    sfNum = new SfNum("[[[[0,7],4],[5,[0,1]]],[1,1]]");
    sfNum.getX().getY().getX().setZ(15);
    sfNum.getX().getY().getY().getY().setZ(13);
    Assertions.assertTrue(sfNum.needToSplit());
    origSize = sfNum.getSize();
    sfNum.split();
    Assertions.assertEquals(2, sfNum.getSize() - origSize);
    Assertions.assertTrue(sfNum.needToSplit());
    origSize = sfNum.getSize();
    sfNum.split();
    Assertions.assertEquals(2, sfNum.getSize() - origSize);
    Assertions.assertFalse(sfNum.needToSplit());
  }

  @Test
  void sizeTest() {
    SfNum sfNum = new SfNum("1");
    Assertions.assertEquals(1, sfNum.getSize());
    sfNum = new SfNum("[1,2]");
    Assertions.assertEquals(3, sfNum.getSize());
    sfNum = new SfNum("[4,[1,2]]");
    Assertions.assertEquals(5, sfNum.getSize());
  }

  @Test
  void toStringTest() {
    SfNum sfNum = new SfNum("1");
    Assertions.assertEquals("1", sfNum.toString());
    sfNum = new SfNum("[1,2]");
    Assertions.assertEquals("[1,2]", sfNum.toString());
    sfNum = new SfNum("[4,[1,2]]");
    Assertions.assertEquals("[4,[1,2]]", sfNum.toString());
    sfNum = new SfNum("[[[[0,7],4],[5,[0,1]]],[1,1]]");
    Assertions.assertEquals("[[[[0,7],4],[5,[0,1]]],[1,1]]", sfNum.toString());
    sfNum = new SfNum("[[[[8,7],[7,7]],[[8,6],[7,7]]],[[[0,7],[6,6]],[8,7]]]");
    Assertions.assertEquals("[[[[8,7],[7,7]],[[8,6],[7,7]]],[[[0,7],[6,6]],[8,7]]]", sfNum.toString());
  }
}
