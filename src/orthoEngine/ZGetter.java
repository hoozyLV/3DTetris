package orthoEngine;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public abstract class ZGetter {
  public abstract double averageZ();
  
  public abstract void draw(Graphics2D paramGraphics2D);
  
  public abstract void draw(Graphics paramGraphics);
  
  public abstract void setColor(Color paramColor);
}
