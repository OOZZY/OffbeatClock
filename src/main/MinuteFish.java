package main;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PVector;

public class MinuteFish extends TimeObject {
  PVector pos, vel; // position, velocity
  private float width = 50, height = 25; // width, height
  private float theta = 0; // variable used for tail animation/oscillation

  public MinuteFish(Main p, int storedTime) {
    this.p = p;
    this.storedTime = storedTime;

    float ylow = 5*p.height/6+height/2; // top edge
    float yhigh = p.height-height/2; // bottom edge

    if (p.random(0, 2) > 1) {
      // move in from let side
      pos = new PVector(+width / 2, p.random(ylow, yhigh));
      vel = new PVector(p.random(1, 3), p.random(-1, 1));
    } else {
      // move in from right side
      pos = new PVector(p.width - width / 2, p.random(ylow, yhigh));
      vel = new PVector(-p.random(1, 3), p.random(-1, 1));
    }
  }

  /* Updates fish. May also add more fishes. */
  @Override public void update() {
    pos.add(vel); // update position

    // left edge
    if (pos.x < width/2) {
      pos.x = width/2;
      vel.x *= -1;
    }
    // right edge
    if (pos.x > p.width - width/2) {
      pos.x = p.width - width/2;
      vel.x *= -1;
    }
    // top edge
    if (pos.y < 5*p.height/6+height/2) {
      pos.y = 5*p.height/6+height/2;
      vel.y *= -1;
    }
    // bottom edge
    if (pos.y > p.height - height/2) {
      pos.y = p.height - height/2;
      vel.y *= -1;
    }

    if (p.minute % 60 > storedTime && addedRecursiveObjects == false) {
      addedRecursiveObjects = true;
      p.mflist.add(new MinuteFish(p, storedTime + 1));
    }
  }

  /* Draws fish. */
  @Override public void draw() {
    p.pushMatrix();

    p.translate(pos.x, pos.y);

    // scale to width and height specified in constructor
    p.scale(width / 200, height / 100);

    if (vel.x < 0) { // if moving left
      p.scale(-1, 1); // flip horizontally
    }

    p.stroke(0);
    p.strokeWeight(1);
    float sinTheta = PApplet.sin(theta); // a float between -1, 1

    // tail
    p.fill(162, 121, 80);
    for (int i = 0; i < 4; ++i) {
      // top half
      p.triangle(-100, -32 + 8*i + sinTheta*3, -75, 0, -40, 0);
      // bottom half
      p.triangle(-100, 32 - 8*i - sinTheta*3, -75, 0, -40, 0);
    }

    // body fins
    p.fill(177, 163, 127);
    // top left
    p.curve(100, 100, -40, -20, -10, -30, 100, 100);
    p.curve(50, 50, -40, -20, -10, -30, 50, 50);
    // top right
    p.curve(-80, 130, -10, -30, 50, -30, -80, 130);
    p.curve(-40, 65, -10, -30, 50, -30, -40, 65);
    // bottom left
    p.curve(100, -100, -40, 20, -10, 30, 100, -100);
    p.curve(50, -50, -40, 20, -10, 30, 50, -50);
    // bottom right
    p.curve(175, -125, 10, 30, 40, 30, 175, -125);
    p.curve(87.5f, -62.5f, 10, 30, 40, 30, 87.5f, -62.5f);

    // body
    p.fill(130, 132, 76);
    p.curve(0, 260, -60, 1, 100, 1, 40, 260); // top half
    p.curve(0, -260, -60, -1, 100, -1, 40, -260); // bottom half

    // body scales
    for (int j = 0; j < 2; ++j) {
      p.pushMatrix();
      p.translate(-10 + j * 28, 0); // move center of grid
      p.rotate(PConstants.PI / 4); // rotate around center of grid
      // draw grid
      for (int i = 0; i < 40; i += 10) {
        p.line(i - 15, -20, i - 15, 20);
        p.line(-20, i - 15, 20, i - 15);
      }
      p.popMatrix();
    }

    // head
    p.fill(113, 94, 39);
    p.curve(-250, -50, 60, -28, 60, 28, -250, 50); // right curve
    p.curve(150, -50, 60, -28, 60, 28, 150, 50); // left curve (gill)
    p.noFill();
    p.curve(75, -30, 75, 0, 100, 0, 100, -30); // mouth/smile
    p.fill(0);
    p.ellipse(70, -10, 10, 10); // eye
    p.fill(255);
    p.ellipse(72, -11, 4, 4); // eye glint

    p.popMatrix();
  }
}
