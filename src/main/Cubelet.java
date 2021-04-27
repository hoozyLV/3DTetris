package main;

import java.awt.Color;
import orthoEngine.Cube3D;

public class Cubelet extends Cube3D {

    public boolean isOccupied;

    public boolean hasPolyCube;

    public Cubelet(boolean b, double[] size, double[] pos, double[] COR, Color c) {
        super(size, pos, COR, c);
        this.isOccupied = b;
        this.hasPolyCube = false;
    }
}
