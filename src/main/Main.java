package main;

import java.util.ArrayList;
import processing.core.PApplet;

/*
 * An offbeat clock displaying the progress of time in a non-traditional way:
 * - the number of ray circles indicates the current second.
 * - the number of fishes indicates the current minute.
 * - the number of trees indicates the current hour.
 * - The size of the cloud represents progress of seconds.
 * - The swaying of the trees also represents progress of seconds.
 * - The number of branches in the right-most tree represents progress of
 *   minutes.
 * - The background will be a daytime background from 6:00 AM to 5:59 PM.
 * - The background will be a nighttime background from 6:00 PM to 5:59 AM.
 * - The cloud and the trees are fractals which are drawn using recursive
 *   functions.
 * - The movement of the cloud makes use of Perlin noise.
 * - The swaying of the trees also makes use of Perlin noise.
 */
public class Main extends PApplet {
  SecondCloud scloud;
  ArrayList<SecondRayCircle> srclist;
  ArrayList<MinuteFish> mflist;
  ArrayList<HourTree> htlist;
  int second, minute, hour; // used to store current second, minute, hour

  public void setup()
  {
    size(1200, 700);
    smooth();

    scloud = new SecondCloud(this);
    srclist = new ArrayList<SecondRayCircle>();
    srclist.add(new SecondRayCircle(this, 0));

    mflist = new ArrayList<MinuteFish>();
    mflist.add(new MinuteFish(this, 0));

    htlist = new ArrayList<HourTree>();
    htlist.add(new HourTree(this, 0, false));
  }

  public void draw() {
    // store current second, minute, hour
    second = second();
    minute = minute();
    hour = hour();
    drawBackground(hour);
    hour %= 12; // a number from 0 to 11

    scloud.update();
    scloud.draw();

    if (minute == 0) {
      mflist = new ArrayList<MinuteFish>();
      mflist.add(new MinuteFish(this, 0));
    }

    // update/draw fishes
    for (int i = 0; i < mflist.size(); i++) {
      MinuteFish mf = mflist.get(i);
      mf.update();
      mf.draw();
    }

    // reset trees when hour goes back to 0
    if (hour == 0) {
      htlist = new ArrayList<HourTree>();
      htlist.add(new HourTree(this, 0, false));
    }

    // update/draw trees
    for (int i = 0; i < htlist.size(); ++i) {
      HourTree ht = htlist.get(i);
      ht.update();
      ht.draw();
    }
  }

  /* Returns whether it is currently daytime. */
  boolean isDaytime() {
    if (hour >= 6 && hour <= 17) {
      return true;
    }
    return false;
  }

  /* Draws background for the given hour of the day. */
  void drawBackground(int hour) {
    if (isDaytime()) { // daytime
      background(135, 206, 235); // bright sky
      fill(255, 255, 0); // yellow (sun's color)
    } else { // nighttime
      background(0, 0, 25); // dark sky
      fill(255); // white (moon's color)
    }
    noStroke();
    ellipse(100, 100, 100, 100); // sun or moon

    if (second == 0) {
      srclist = new ArrayList<SecondRayCircle>();
      srclist.add(new SecondRayCircle(this, 0));
    }

    // update/draw ray circles
    for (int i = 0; i < srclist.size(); i++) {
      SecondRayCircle src = srclist.get(i);
      src.update();
      src.draw();
    }
    SecondRayCircle.theta += 0.0002;

    noStroke();
    fill(0, 40, 0);
    rect(0, 3 * height / 6, width, height / 3); // field
    fill(0, 255, 255); // cyan
    rect(0, 5*height/6, width, height/6); // pond
  }
}
