package main;

import processing.core.PApplet;

public class SecondRayCircle extends TimeObject {
  static float theta = 0; // for rotation

  public SecondRayCircle(Main p, int storedTime) {
    this.p = p;
    this.storedTime = storedTime;
  }

  /* Updates ray circle. May also add more ray circles. */
  @Override public void update() {
    if (p.second % 60 > storedTime && addedRecursiveObjects == false) {
      addedRecursiveObjects = true;
      p.srclist.add(new SecondRayCircle(p, storedTime + 1));
    }
  }

  /* Draws ray circle. */
  @Override public void draw() {
    p.strokeWeight(1);

    if (p.isDaytime()) {
      p.stroke(255, 255, 0); // yellow
    } else {
      p.stroke(255); // white
    }

    for (int a = 0; a < 360; a += 6) {
      p.pushMatrix();
      p.translate(100, 100); // translate to center of sun/moon
      p.rotate(PApplet.radians(a) + theta);
      // diameter changes based on storedTime
      float xstart = 60 + storedTime*(p.width - 200)/60;
      float rayLength = 4;
      p.line(xstart, 0, xstart + rayLength, 0);
      p.popMatrix();
    }
  }
}
