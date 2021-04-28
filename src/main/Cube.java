package main;

import java.awt.Color;
import orthoEngine.CubeTransform;

public class Cube extends CubeTransform {

    public boolean isOccupied;

    public boolean hasPolyCube;

    public Cube(boolean b, double[] size, double[] pos, double[] COR, Color c) {
        super(size, pos, COR, c);
        this.isOccupied = b;
        this.hasPolyCube = false;
    }
}
