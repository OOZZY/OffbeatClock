package main;

public class HourTree extends TimeObject {
  MinuteBranch tree;
  boolean up; // stores whether this tree is in the upper row
  static final int maxRecursionCount = 10;

  public HourTree(Main parent, int storedTime, boolean up) {
    p = parent;
    this.storedTime = storedTime;
    this.up = up;
    tree = new MinuteBranch(p);
  }

  /* Updates this tree. Also may generate more trees. */
  @Override public void update() {
    if (p.hour > storedTime && addedRecursiveObjects == false) {
      addedRecursiveObjects = true;
      p.htlist.add(new HourTree(p, storedTime + 1, p.htlist.size()%2 == 0));
    }

    if (addedRecursiveObjects) { // for all trees except for the last one
      tree.recursionCount = maxRecursionCount;
    } else { // last tree's branching level depend on the minute%5
      tree.update();
    }
  }

  /* Draws this tree. */
  @Override public void draw() {
    p.pushMatrix();

    if (up) {
      p.translate(0, -p.height/5); // translate up
    }
    p.translate(storedTime * p.width/12 - p.width/24, 4*p.height/5);
    tree.draw();

    p.popMatrix();
  }
}
