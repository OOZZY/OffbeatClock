package main;

public abstract class TimeObject {
  public Main p;
  public int storedTime;
  public boolean addedRecursiveObjects = false;
  public abstract void update();
  public abstract void draw();
}
