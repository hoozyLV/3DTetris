package orthoEngine;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class Node extends ZGetter {
  public double x;
  
  public double y;
  
  public double z;
  
  public int sz;
  
  public Color c;
  
  public Node(double x, double y, double z, int sz, Color c) {
    this.x = x;
    this.y = y;
    this.z = z;
    this.sz = sz;
    this.c = c;
  }
  
  public Node(double x, double y, double z, int sz) {
    this.x = x;
    this.y = y;
    this.z = z;
    this.sz = sz;
    this.c = Color.WHITE;
  }
  
  public double averageZ() {
    return this.z;
  }
  
  public void draw(Graphics2D g) {
    g.setColor(this.c);
    g.fillOval((int)this.x - this.sz / 2, (int)this.y - this.sz / 2, this.sz, this.sz);
  }
  
  public void draw(Graphics g) {
    g.setColor(this.c);
    g.fillOval((int)this.x - this.sz / 2, (int)this.y - this.sz / 2, this.sz, this.sz);
  }
  
  public void setColor(Color c) {
    this.c = c;
  }
}
