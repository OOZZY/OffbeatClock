package main;

import processing.core.PVector;

public class SecondCloud extends TimeObject {
  Main p;
  PVector pos, vel; // position, velocity
  static final int maxRecursionCount = 10;
  int recursionCount = 1; // current recursion count
  float xoff = 0; // coordinate used to get Perlin noise values

  SecondCloud(Main parent) {
    p = parent;
    // x-coordinate determined by Perlin noise
    this.pos = new PVector(p.noise(xoff) * p.width, p.height / 5);
  }

  /* Updates this cloud. */
  @Override public void update() {
    recursionCount = p.second%maxRecursionCount + 1;
    xoff += 0.002;
    pos.x = p.noise(xoff) * p.width;
  }

  /* Recursive helper function for draw(). */
  public void spread(float x, float y, float width, float height,
                     int recursionDepth) {
    p.noStroke();
    p.fill(150, 240);
    p.ellipse(x, y, width, height);

    final float shrinkFactor = 0.90f;

    // conditionally make recursive calls
    if (recursionDepth + 2 <= recursionCount) {
      spread(x - width/2, y, width * shrinkFactor, height * shrinkFactor,
             recursionDepth + 1);
      spread(x + width/2, y, width * shrinkFactor, height * shrinkFactor,
             recursionDepth + 1);
    }
  }

  /* Draws this cloud. */
  @Override public void draw() {
    spread(pos.x, pos.y, 200, 100, 0);
  }
}
