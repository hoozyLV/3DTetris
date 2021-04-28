package orthoEngine;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;

public class Face extends ZGetter {

    Node[] n;

    public Color c;

    public Face(Node[] n, Color c) {
        this.n = n;
        this.c = c;
    }

    @Override
    public void setColor(Color c) {
        this.c = c;
    }

    @Override
    public double averageZ() {
        double tmp = 0.0D;
        for (Node n1 : this.n) {
            tmp += (n1).z;
        }
        tmp /= this.n.length;
        return tmp;
    }

    @Override
    public void draw(Graphics2D g) {
        Polygon p = new Polygon();
        for (Node n1 : this.n) {
            p.addPoint((int) (n1).x, (int) (n1).y);
        }
        g.setColor(this.c);
        g.fill(p);
    }

    @Override
    public void draw(Graphics g) {
        Polygon p = new Polygon();
        for (Node n1 : this.n) {
            p.addPoint((int) (n1).x, (int) (n1).y);
        }
        g.setColor(this.c);
        g.drawPolygon(p);
    }
}
