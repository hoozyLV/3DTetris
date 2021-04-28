package main;

import java.awt.Color;

public class Hexamino {

    final int polyCubeSize = 6;

    int[] bounds = new int[]{10, 24, 10};

    int[][] data;

    Color c;

    public static int[][][] properties = new int[][][]{
        {{-2, 0, 0}, {-1, 0, 0}, {0, 0, 0}, {1, 0, 0}, {2, 0, 0}, {3, 0, 0}},
        {{0, 0, 0}, {0, 1, 0}, {-1, 1, 0}, {1, 1, 0}, {0, 1, -1}, {0, 1, 1}},
        {{1, 0, 0}, {1, 1, 0}, {-1, 1, 0}, {1, 2, 0}, {0, 2, 0}, {-1, 2, 0}},
        {{-1, 0, 0}, {0, 0, 0}, {1, 0, 0}, {-1, 1, 0}, {0, 1, 0}, {1, 1, 0}},
        {{2, 0, 0}, {1, 0, 0}, {0, 0, 0}, {0, 0, 1}, {0, 0, 2}, {0, 0, 3}},
        {{-2, 0, 0}, {-1, 0, 0}, {0, 0, 0}, {0, 0, 1}, {1, 1, 0}, {2, 1, 0}}
    };

    public Hexamino() {
        double tmp = Math.random() * 5;
        //double tmp = 3;
        //System.out.println(tmp);
        this.data = new int[][]{
            {properties[(int) tmp][0][0] + this.bounds[0] / 2, properties[(int) tmp][0][1], properties[(int) tmp][0][2] + this.bounds[2] / 2},
            {properties[(int) tmp][1][0] + this.bounds[0] / 2, properties[(int) tmp][1][1], properties[(int) tmp][1][2] + this.bounds[2] / 2},
            {properties[(int) tmp][2][0] + this.bounds[0] / 2, properties[(int) tmp][2][1], properties[(int) tmp][2][2] + this.bounds[2] / 2},
            {properties[(int) tmp][3][0] + this.bounds[0] / 2, properties[(int) tmp][3][1], properties[(int) tmp][3][2] + this.bounds[2] / 2},
            {properties[(int) tmp][4][0] + this.bounds[0] / 2, properties[(int) tmp][4][1], properties[(int) tmp][4][2] + this.bounds[2] / 2},
            {properties[(int) tmp][5][0] + this.bounds[0] / 2, properties[(int) tmp][5][1], properties[(int) tmp][5][2] + this.bounds[2] / 2}
        };
        switch ((int) tmp) {
            case 0:
                this.c = Color.BLUE;
                break;
            case 1:
                this.c = Color.RED;
                break;
            case 2:
                this.c = Color.GREEN;
                break;
            case 3:
                this.c = Color.MAGENTA;
                break;
            case 4:
                this.c = Color.ORANGE;
                break;
            case 5:
                this.c = Color.CYAN;
                break;
            default:
                this.c = Color.WHITE;
                break;
                   
        }
    }

    public void translate(int x, int y, int z) {
        for (int i = 0; i < 6; i++) {
            this.data[i][0] = this.data[i][0] + x;
            this.data[i][1] = this.data[i][1] + y;
            this.data[i][2] = this.data[i][2] + z;
        }
    }

    public void rotate(int axis, int dir) {
        if (rotateCheck(axis, dir)) {
            if (dir == 0) {
                if (axis == 0) {
                    for (int i = 0; i < 6; i++) {
                        int relY = this.data[i][1] - this.data[3][1];
                        int relZ = this.data[i][2] - this.data[3][2];
                        int tmp = relY;
                        relY = -relZ;
                        relZ = tmp;
                        this.data[i][1] = relY + this.data[3][1];
                        this.data[i][2] = relZ + this.data[3][2];
                    }
                } else if (axis == 1) {
                    for (int i = 0; i < 6; i++) {
                        int relZ = this.data[i][2] - this.data[3][2];
                        int relX = this.data[i][0] - this.data[3][0];
                        int tmp = relZ;
                        relZ = -relX;
                        relX = tmp;
                        this.data[i][2] = relZ + this.data[3][2];
                        this.data[i][0] = relX + this.data[3][0];
                    }
                } else {
                    for (int i = 0; i < 6; i++) {
                        int relY = this.data[i][1] - this.data[3][1];
                        int relX = this.data[i][0] - this.data[3][0];
                        int tmp = relY;
                        relY = -relX;
                        relX = tmp;
                        this.data[i][0] = relX + this.data[3][0];
                        this.data[i][1] = relY + this.data[3][1];
                    }
                }
            } else if (dir == 1) {
                if (axis == 0) {
                    for (int i = 0; i < 6; i++) {
                        int relY = this.data[i][1] - this.data[3][1];
                        int relZ = this.data[i][2] - this.data[3][2];
                        int tmp = relY;
                        relY = -relZ;
                        relZ = tmp;
                        this.data[i][1] = -relY + this.data[3][1];
                        this.data[i][2] = -relZ + this.data[3][2];
                    }
                } else if (axis == 1) {
                    for (int i = 0; i < 6; i++) {
                        int relZ = this.data[i][2] - this.data[3][2];
                        int relX = this.data[i][0] - this.data[3][0];
                        int tmp = relZ;
                        relZ = -relX;
                        relX = tmp;
                        this.data[i][2] = -relZ + this.data[3][2];
                        this.data[i][0] = -relX + this.data[3][0];
                    }
                } else {
                    for (int i = 0; i < 6; i++) {
                        int relY = this.data[i][1] - this.data[3][1];
                        int relX = this.data[i][0] - this.data[3][0];
                        int tmp = relY;
                        relY = -relX;
                        relX = tmp;
                        this.data[i][0] = -relX + this.data[3][0];
                        this.data[i][1] = -relY + this.data[3][1];
                    }
                }
            }
        }
    }

    public boolean isCollidingLeft() {
        for (int i = 0; i < 6; i++) {
            if (this.data[i][0] <= 0) {
                return true;
            }
            if ((Initializer.game.voxels[this.data[i][0] - 1][this.data[i][1]][this.data[i][2]]).isOccupied) {
                return true;
            }
        }
        return false;
    }

    public boolean isCollidingRight() {
        for (int i = 0; i < 6; i++) {
            if (this.data[i][0] >= this.bounds[0] - 1) {
                return true;
            }
            if ((Initializer.game.voxels[this.data[i][0] + 1][this.data[i][1]][this.data[i][2]]).isOccupied) {
                return true;
            }
        }
        return false;
    }

    public boolean isCollidingDown() {
        for (int i = 0; i < 6; i++) {
            if (this.data[i][2] <= 0) {
                return true;
            }
            if ((Initializer.game.voxels[this.data[i][0]][this.data[i][1]][this.data[i][2] - 1]).isOccupied) {
                return true;
            }
        }
        return false;
    }

    public boolean isCollidingUp() {
        for (int i = 0; i < 6; i++) {
            if (this.data[i][2] >= this.bounds[2] - 1) {
                return true;
            }
            if ((Initializer.game.voxels[this.data[i][0]][this.data[i][1]][this.data[i][2] + 1]).isOccupied) {
                return true;
            }
        }
        return false;
    }

    public boolean isCollidingFloor() {
        for (int i = 0; i < 6; i++) {
            if (this.bounds[1] - 1 - this.data[i][1] <= 0) {
                return true;
            }
            if ((Initializer.game.voxels[this.data[i][0]][this.data[i][1] + 1][this.data[i][2]]).isOccupied) {
                return true;
            }
        }
        return false;
    }

    public boolean rotateCheck(int axis, int dir) {
        int[][] testCube = new int[6][3];
        int i;
        for (i = 0; i < testCube.length; i++) {
            for (int j = 0; j < (testCube[0]).length; j++) {
                testCube[i][j] = this.data[i][j];
            }
        }
        if (dir == 0) {
            if (axis == 0) {
                for (i = 0; i < testCube.length; i++) {
                    int relY = testCube[i][1] - testCube[3][1];
                    int relZ = testCube[i][2] - testCube[3][2];
                    int tmp = relY;
                    relY = -relZ;
                    relZ = tmp;
                    testCube[i][1] = relY + testCube[3][1];
                    testCube[i][2] = relZ + testCube[3][2];
                }
            } else if (axis == 1) {
                for (i = 0; i < testCube.length; i++) {
                    int relZ = testCube[i][2] - testCube[3][2];
                    int relX = testCube[i][0] - testCube[3][0];
                    int tmp = relZ;
                    relZ = -relX;
                    relX = tmp;
                    testCube[i][2] = relZ + testCube[3][2];
                    testCube[i][0] = relX + testCube[3][0];
                }
            } else {
                for (i = 0; i < testCube.length; i++) {
                    int relY = testCube[i][1] - testCube[3][1];
                    int relX = testCube[i][0] - testCube[3][0];
                    int tmp = relY;
                    relY = -relX;
                    relX = tmp;
                    testCube[i][0] = relX + testCube[3][0];
                    testCube[i][1] = relY + testCube[3][1];
                }
            }
        } else if (dir == 1) {
            if (axis == 0) {
                for (i = 0; i < testCube.length; i++) {
                    int relY = testCube[i][1] - testCube[3][1];
                    int relZ = testCube[i][2] - testCube[3][2];
                    int tmp = relY;
                    relY = -relZ;
                    relZ = tmp;
                    testCube[i][1] = -relY + testCube[3][1];
                    testCube[i][2] = -relZ + testCube[3][2];
                }
            } else if (axis == 1) {
                for (i = 0; i < testCube.length; i++) {
                    int relZ = testCube[i][2] - testCube[3][2];
                    int relX = testCube[i][0] - testCube[3][0];
                    int tmp = relZ;
                    relZ = -relX;
                    relX = tmp;
                    testCube[i][2] = -relZ + testCube[3][2];
                    testCube[i][0] = -relX + testCube[3][0];
                }
            } else {
                for (i = 0; i < testCube.length; i++) {
                    int relY = testCube[i][1] - testCube[3][1];
                    int relX = testCube[i][0] - testCube[3][0];
                    int tmp = relY;
                    relY = -relX;
                    relX = tmp;
                    testCube[i][0] = -relX + testCube[3][0];
                    testCube[i][1] = -relY + testCube[3][1];
                }
            }
        }
        for (i = 0; i < testCube.length; i++) {
            if (testCube[i][0] <= 0) {
                return false;
            }
            if (testCube[i][0] > this.bounds[0] - 1) {
                return false;
            }
            if (testCube[i][1] <= 0) {
                return false;
            }
            if (testCube[i][1] > this.bounds[1] - 1) {
                return false;
            }
            if (testCube[i][2] <= 0) {
                return false;
            }
            if (testCube[i][2] > this.bounds[2] - 1) {
                return false;
            }
            if ((Initializer.game.voxels[testCube[i][0]][testCube[i][1]][testCube[i][2]]).isOccupied) {
                return false;
            }
        }
        return true;
    }
}
