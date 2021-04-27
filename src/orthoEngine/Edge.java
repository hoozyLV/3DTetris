package orthoEngine;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class Edge extends ZGetter {
  Node n1;
  
  Node n2;
  
  int sz;
  
  Color c;
  
  public Edge(Node n1, Node n2, int sz, Color c) {
    this.n1 = n1;
    this.n2 = n2;
    this.sz = sz;
    this.c = c;
  }
  
  public Edge(Node n1, Node n2, int sz) {
    this.n1 = n1;
    this.n2 = n2;
    this.sz = sz;
    this.c = Color.WHITE;
  }
  
  public double averageZ() {
    return (this.n1.z + this.n2.z) / 2.0D;
  }
  
  public void draw(Graphics2D g) {
    g.setStroke(new BasicStroke(this.sz));
    g.setColor(this.c);
    g.drawLine((int)this.n1.x, (int)this.n1.y, (int)this.n2.x, (int)this.n2.y);
  }
  
  public void draw(Graphics g) {
    g.setColor(this.c);
    g.drawLine((int)this.n1.x, (int)this.n1.y, (int)this.n2.x, (int)this.n2.y);
  }
  
  public void setColor(Color c) {
    this.c = c;
  }
}