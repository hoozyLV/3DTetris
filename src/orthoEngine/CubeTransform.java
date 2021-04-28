package orthoEngine;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class CubeTransform {

    public Node[] nodes = new Node[8];

    public ZGetter[] stuff = new ZGetter[18];

    public int[] order = new int[this.stuff.length];

    public double[] size;

    public double[] translation;

    public double[] COR;

    public Color cl;

    public CubeTransform(double[] size, double[] translation, double[] COR, Color cl) {
        int c = 0;
        this.size = size;
        this.translation = translation;
        this.COR = COR;
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                for (int k = 0; k < 2; k++) {
                    this.nodes[c] = new Node(i * size[0] - size[0] / 2.0D + translation[0] - COR[0], j * size[1] - size[1] / 2.0D + translation[1] - COR[1], k * size[2] - size[2] / 2.0D + translation[2] - COR[2], 2);
                    c++;
                }
            }
        }
        this.stuff[0] = new Edge(this.nodes[0], this.nodes[1], 2);
        this.stuff[1] = new Edge(this.nodes[1], this.nodes[3], 2);
        this.stuff[2] = new Edge(this.nodes[3], this.nodes[2], 2);
        this.stuff[3] = new Edge(this.nodes[2], this.nodes[0], 2);
        this.stuff[4] = new Edge(this.nodes[4], this.nodes[5], 2);
        this.stuff[5] = new Edge(this.nodes[5], this.nodes[7], 2);
        this.stuff[6] = new Edge(this.nodes[7], this.nodes[6], 2);
        this.stuff[7] = new Edge(this.nodes[6], this.nodes[4], 2);
        this.stuff[8] = new Edge(this.nodes[0], this.nodes[4], 2);
        this.stuff[9] = new Edge(this.nodes[1], this.nodes[5], 2);
        this.stuff[10] = new Edge(this.nodes[2], this.nodes[6], 2);
        this.stuff[11] = new Edge(this.nodes[3], this.nodes[7], 2);
        this.stuff[12] = new Face(new Node[]{this.nodes[0], this.nodes[1], this.nodes[3], this.nodes[2]}, cl);
        this.stuff[13] = new Face(new Node[]{this.nodes[1], this.nodes[3], this.nodes[7], this.nodes[5]}, cl);
        this.stuff[14] = new Face(new Node[]{this.nodes[0], this.nodes[1], this.nodes[5], this.nodes[4]}, cl);
        this.stuff[15] = new Face(new Node[]{this.nodes[4], this.nodes[5], this.nodes[7], this.nodes[6]}, cl);
        this.stuff[16] = new Face(new Node[]{this.nodes[0], this.nodes[2], this.nodes[6], this.nodes[4]}, cl);
        this.stuff[17] = new Face(new Node[]{this.nodes[2], this.nodes[3], this.nodes[7], this.nodes[6]}, cl);
        sortOrder();
    }

    public void setColor(Color c) {
        for (int i = 0; i < 6; i++) {
            this.stuff[i + 12].setColor(c);
        }
    }

    public void rotateZ(double theta) {
        for (int i = 0; i < this.nodes.length; i++) {
            Node node = this.nodes[i];
            double x = node.x;
            double y = node.y;
            node.x = x * Math.cos(theta) - y * Math.sin(theta);
            node.y = y * Math.cos(theta) + x * Math.sin(theta);
        }
    }

    public void rotateY(double theta) {
        for (int i = 0; i < this.nodes.length; i++) {
            Node node = this.nodes[i];
            double x = node.x;
            double z = node.z;
            node.x = x * Math.cos(theta) - z * Math.sin(theta);
            node.z = z * Math.cos(theta) + x * Math.sin(theta);
        }
    }

    public void rotateX(double theta) {
        for (int i = 0; i < this.nodes.length; i++) {
            Node node = this.nodes[i];
            double y = node.y;
            double z = node.z;
            node.y = y * Math.cos(theta) - z * Math.sin(theta);
            node.z = z * Math.cos(theta) + y * Math.sin(theta);
        }
    }

    public void sortOrder() {
        double[] k = new double[this.stuff.length];
        for (int i = 0; i < this.stuff.length; i++) {
            this.order[i] = i;
            k[i] = this.stuff[i].averageZ();
        }
        for (int j = 0; j < this.stuff.length - 1; j++) {
            for (int m = 0; m < this.stuff.length - 1; m++) {
                if (k[m] < k[m + 1]) {
                    double t2 = k[m];
                    int t = this.order[m];
                    this.order[m] = this.order[m + 1];
                    k[m] = k[m + 1];
                    this.order[m + 1] = t;
                    k[m + 1] = t2;
                }
            }
        }
    }

    public void draw(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.translate((int) this.COR[0], (int) this.COR[1]);
        for (int i = 0; i < this.stuff.length; i++) {
            this.stuff[this.order[i]].draw(g2);
        }
        g2.translate((int) -this.COR[0], (int) -this.COR[1]);
    }
}
