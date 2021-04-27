package main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import orthoEngine.Cube3D;

public class PolyCubeContainer extends Cube3D implements KeyListener {

    Cubelet[][][] voxels;

    int[] xOrder;

    int[] yOrder;

    int[] zOrder;

    static int score;

    PolyCube tempPolyCube;

    double xTilt;

    double rotSpeed;

    double netRotation;

    boolean CW;

    boolean CCW;

    boolean LEFT;

    boolean RIGHT;

    boolean UP;

    boolean DOWN;

    boolean DROP;

    boolean FALL;

    boolean XCW;

    boolean YCW;

    boolean ZCW;

    boolean XCCW;

    boolean YCCW;

    boolean ZCCW;

    boolean gameOver = false;

    public PolyCubeContainer() {
        super(new double[]{200.0D, 480.0D, 200.0D}, new double[]{(Tetris.WIDTH / 2), (Tetris.HEIGHT / 2), 0.0D}, new double[]{(Tetris.WIDTH / 2), (Tetris.HEIGHT / 2), 0.0D}, new Color(0, 0, 0, 0));
        score = 0;
        this.voxels = new Cubelet[10][24][10];
        this.xOrder = new int[10];
        this.yOrder = new int[24];
        this.zOrder = new int[10];
        this.xTilt = 0.20943951023931953D;
        this.rotSpeed = 0.06283185307179587D;
        this.netRotation = 0.0D;
        rotateX(this.xTilt);
        this.CW = false;
        this.CCW = false;
        int i;
        for (i = 0; i < this.voxels.length; i++) {
            for (int j = 0; j < (this.voxels[0]).length; j++) {
                for (int k = 0; k < (this.voxels[0][0]).length; k++) {
                    this.voxels[i][j][k] = new Cubelet(false, new double[]{20.0D, 20.0D, 20.0D}, new double[]{(i * 20 + Tetris.WIDTH / 2 - 90), (j * 20 + Tetris.HEIGHT / 2 - 230), (k * 20 - 90)}, new double[]{(Tetris.WIDTH / 2), (Tetris.HEIGHT / 2), 0.0D}, Color.BLUE);
                    this.voxels[i][j][k].rotateX(this.xTilt);
                    this.voxels[i][j][k].sortOrder();
                }
            }
        }
        for (i = this.yOrder.length - 1; i >= 0; i--) {
            this.yOrder[this.yOrder.length - 1 - i] = i;
        }
        this.tempPolyCube = new PolyCube();
    }

    public void render(Graphics g) {
        draw(g, false);
        for (int i = 0; i < this.voxels.length; i++) {
            for (int j = 0; j < (this.voxels[0]).length; j++) {
                for (int k = 0; k < (this.voxels[0][0]).length; k++) {
                    if ((this.voxels[this.xOrder[i]][this.yOrder[j]][this.zOrder[k]]).isOccupied || (this.voxels[this.xOrder[i]][this.yOrder[j]][this.zOrder[k]]).hasPolyCube) {
                        this.voxels[this.xOrder[i]][this.yOrder[j]][this.zOrder[k]].draw(g);
                    }
                }
            }
        }
        draw(g, true);
        if (this.gameOver) {
            graphicsFont.drawString(g, "game over", 100, 250, 16, 6);
            graphicsFont.drawString(g, "your score " + score, 120, 350, 10, 4);
            g.fillRect(420, 360, 4, 4);
            g.fillRect(420, 380, 4, 4);
        }
    }

    public void tick() {
        if (this.CW) {
            rotateY(-Math.cos(this.xTilt) * this.rotSpeed);
            rotateZ(Math.sin(this.xTilt) * this.rotSpeed);
            this.netRotation += this.rotSpeed;
            for (int i = 0; i < this.voxels.length; i++) {
                for (int j = 0; j < (this.voxels[0]).length; j++) {
                    for (int k = 0; k < (this.voxels[0][0]).length; k++) {
                        this.voxels[i][j][k].rotateY(-Math.cos(this.xTilt) * this.rotSpeed);
                        this.voxels[i][j][k].rotateZ(Math.sin(this.xTilt) * this.rotSpeed);
                        this.voxels[i][j][k].sortOrder();
                    }
                }
            }
        } else if (this.CCW) {
            rotateZ(-Math.sin(this.xTilt) * this.rotSpeed);
            rotateY(Math.cos(this.xTilt) * this.rotSpeed);
            this.netRotation -= this.rotSpeed;
            for (int i = 0; i < this.voxels.length; i++) {
                for (int j = 0; j < (this.voxels[0]).length; j++) {
                    for (int k = 0; k < (this.voxels[0][0]).length; k++) {
                        this.voxels[i][j][k].rotateZ(-Math.sin(this.xTilt) * this.rotSpeed);
                        this.voxels[i][j][k].rotateY(Math.cos(this.xTilt) * this.rotSpeed);
                        this.voxels[i][j][k].sortOrder();
                    }
                }
            }
        }
        if (this.netRotation < 0.0D) {
            this.netRotation += 6.283185307179586D;
        }
        if (this.netRotation > 6.283185307179586D) {
            this.netRotation -= 6.283185307179586D;
        }
        if (this.netRotation % 6.283185307179586D <= 1.5707963267948966D) {
            int i;
            for (i = 0; i < this.xOrder.length; i++) {
                this.xOrder[i] = i;
            }
            for (i = this.zOrder.length - 1; i >= 0; i--) {
                this.zOrder[this.zOrder.length - 1 - i] = i;
            }
        } else if (this.netRotation % 6.283185307179586D <= Math.PI) {
            int i;
            for (i = 0; i < this.xOrder.length; i++) {
                this.xOrder[i] = i;
            }
            for (i = 0; i < this.zOrder.length; i++) {
                this.zOrder[i] = i;
            }
        } else if (this.netRotation % 6.283185307179586D <= 4.71238898038469D) {
            int i;
            for (i = this.xOrder.length - 1; i >= 0; i--) {
                this.xOrder[this.xOrder.length - 1 - i] = i;
            }
            for (i = 0; i < this.zOrder.length; i++) {
                this.zOrder[i] = i;
            }
        } else if (this.netRotation % 6.283185307179586D <= 6.283185307179586D) {
            int i;
            for (i = this.xOrder.length - 1; i >= 0; i--) {
                this.xOrder[this.xOrder.length - 1 - i] = i;
            }
            for (i = this.zOrder.length - 1; i >= 0; i--) {
                this.zOrder[this.zOrder.length - 1 - i] = i;
            }
        }
        sortOrder();
        if (!this.gameOver) {
            moveTimer();
            rotateTimer();
            dropTimer();
        }
    }

    private void updatefield() {
        for (int i = 0; i < (this.voxels[0]).length; i++) {
            if ((this.voxels[0][i][0]).isOccupied && (this.voxels[0][i][1]).isOccupied && (this.voxels[0][i][2]).isOccupied && (this.voxels[0][i][3]).isOccupied && (this.voxels[0][i][4]).isOccupied
                    && (this.voxels[1][i][0]).isOccupied && (this.voxels[1][i][1]).isOccupied && (this.voxels[1][i][2]).isOccupied && (this.voxels[1][i][3]).isOccupied && (this.voxels[1][i][4]).isOccupied
                    && (this.voxels[2][i][0]).isOccupied && (this.voxels[2][i][1]).isOccupied && (this.voxels[2][i][2]).isOccupied && (this.voxels[2][i][3]).isOccupied && (this.voxels[2][i][4]).isOccupied
                    && (this.voxels[3][i][0]).isOccupied && (this.voxels[3][i][1]).isOccupied && (this.voxels[3][i][2]).isOccupied && (this.voxels[3][i][3]).isOccupied && (this.voxels[3][i][4]).isOccupied
                    && (this.voxels[4][i][0]).isOccupied && (this.voxels[4][i][1]).isOccupied && (this.voxels[4][i][2]).isOccupied && (this.voxels[4][i][3]).isOccupied && (this.voxels[4][i][4]).isOccupied) {
                for (int b = i; b > 0; b--) {
                    for (int a = 0; a < this.voxels.length; a++) {
                        for (int c = 0; c < (this.voxels[0][0]).length; c++) {
                            (this.voxels[a][b][c]).isOccupied = (this.voxels[a][b - 1][c]).isOccupied;
                            this.voxels[a][b][c].setColor(this.tempPolyCube.c);
                        }
                    }
                }
                score++;
            }
        }
    }

    public void dropTimer() {
        if (Tetris.timer % (100 / (1 + score)) == 0L) {
            try {
                if (this.tempPolyCube.isCollidingFloor()) {
                    int i;
                    for (i = 0; i < 6; i++) {
                        (this.voxels[this.tempPolyCube.data[i][0]][this.tempPolyCube.data[i][1]][this.tempPolyCube.data[i][2]]).hasPolyCube = false;
                        (this.voxels[this.tempPolyCube.data[i][0]][this.tempPolyCube.data[i][1]][this.tempPolyCube.data[i][2]]).isOccupied = true;
                        this.voxels[this.tempPolyCube.data[i][0]][this.tempPolyCube.data[i][1]][this.tempPolyCube.data[i][2]].setColor(this.tempPolyCube.c);
                    }
                    for (i = 0; i < this.voxels.length; i++) {
                        for (int j = 0; j < this.voxels.length; j++) {
                            if ((this.voxels[i][1][j]).isOccupied) {
                                this.gameOver = true;
                            }
                        }
                    }
                    if (!this.gameOver) {
                        updatefield();
                        this.tempPolyCube = new PolyCube();
                    }
                }
            } catch (Exception exception) {
            }
            try {
                int i;
                for (i = 0; i < 6; i++) {
                    (this.voxels[this.tempPolyCube.data[i][0]][this.tempPolyCube.data[i][1]][this.tempPolyCube.data[i][2]]).hasPolyCube = false;
                }
                this.tempPolyCube.translate(0, 1, 0);
                for (i = 0; i < 6; i++) {
                    (this.voxels[this.tempPolyCube.data[i][0]][this.tempPolyCube.data[i][1]][this.tempPolyCube.data[i][2]]).hasPolyCube = true;
                    this.voxels[this.tempPolyCube.data[i][0]][this.tempPolyCube.data[i][1]][this.tempPolyCube.data[i][2]].setColor(this.tempPolyCube.c);
                }
            } catch (Exception exception) {
            }
        }
    }

    public void rotateTimer() {
        if (Tetris.timer % 10L == 0L) {
            try {
                if (this.XCW) {
                    int i;
                    for (i = 0; i < 6; i++) {
                        (this.voxels[this.tempPolyCube.data[i][0]][this.tempPolyCube.data[i][1]][this.tempPolyCube.data[i][2]]).hasPolyCube = false;
                    }
                    this.tempPolyCube.rotate(0, 0);
                    for (i = 0; i < 6; i++) {
                        (this.voxels[this.tempPolyCube.data[i][0]][this.tempPolyCube.data[i][1]][this.tempPolyCube.data[i][2]]).hasPolyCube = true;
                        this.voxels[this.tempPolyCube.data[i][0]][this.tempPolyCube.data[i][1]][this.tempPolyCube.data[i][2]].setColor(this.tempPolyCube.c);
                    }
                }
            } catch (Exception exception) {
            }
            try {
                if (this.XCCW) {
                    int i;
                    for (i = 0; i < 6; i++) {
                        (this.voxels[this.tempPolyCube.data[i][0]][this.tempPolyCube.data[i][1]][this.tempPolyCube.data[i][2]]).hasPolyCube = false;
                    }
                    this.tempPolyCube.rotate(0, 1);
                    for (i = 0; i < 6; i++) {
                        (this.voxels[this.tempPolyCube.data[i][0]][this.tempPolyCube.data[i][1]][this.tempPolyCube.data[i][2]]).hasPolyCube = true;
                        this.voxels[this.tempPolyCube.data[i][0]][this.tempPolyCube.data[i][1]][this.tempPolyCube.data[i][2]].setColor(this.tempPolyCube.c);
                    }
                }
            } catch (Exception exception) {
            }
            try {
                if (this.YCW) {
                    int i;
                    for (i = 0; i < 6; i++) {
                        (this.voxels[this.tempPolyCube.data[i][0]][this.tempPolyCube.data[i][1]][this.tempPolyCube.data[i][2]]).hasPolyCube = false;
                    }
                    this.tempPolyCube.rotate(1, 0);
                    for (i = 0; i < 6; i++) {
                        (this.voxels[this.tempPolyCube.data[i][0]][this.tempPolyCube.data[i][1]][this.tempPolyCube.data[i][2]]).hasPolyCube = true;
                        this.voxels[this.tempPolyCube.data[i][0]][this.tempPolyCube.data[i][1]][this.tempPolyCube.data[i][2]].setColor(this.tempPolyCube.c);
                    }
                }
            } catch (Exception exception) {
            }
            try {
                if (this.YCCW) {
                    int i;
                    for (i = 0; i < 6; i++) {
                        (this.voxels[this.tempPolyCube.data[i][0]][this.tempPolyCube.data[i][1]][this.tempPolyCube.data[i][2]]).hasPolyCube = false;
                    }
                    this.tempPolyCube.rotate(1, 1);
                    for (i = 0; i < 6; i++) {
                        (this.voxels[this.tempPolyCube.data[i][0]][this.tempPolyCube.data[i][1]][this.tempPolyCube.data[i][2]]).hasPolyCube = true;
                        this.voxels[this.tempPolyCube.data[i][0]][this.tempPolyCube.data[i][1]][this.tempPolyCube.data[i][2]].setColor(this.tempPolyCube.c);
                    }
                }
            } catch (Exception exception) {
            }
            try {
                if (this.ZCW) {
                    int i;
                    for (i = 0; i < 6; i++) {
                        (this.voxels[this.tempPolyCube.data[i][0]][this.tempPolyCube.data[i][1]][this.tempPolyCube.data[i][2]]).hasPolyCube = false;
                    }
                    this.tempPolyCube.rotate(2, 0);
                    for (i = 0; i < 6; i++) {
                        (this.voxels[this.tempPolyCube.data[i][0]][this.tempPolyCube.data[i][1]][this.tempPolyCube.data[i][2]]).hasPolyCube = true;
                        this.voxels[this.tempPolyCube.data[i][0]][this.tempPolyCube.data[i][1]][this.tempPolyCube.data[i][2]].setColor(this.tempPolyCube.c);
                    }
                }
            } catch (Exception exception) {
            }
            try {
                if (this.ZCCW) {
                    int i;
                    for (i = 0; i < 6; i++) {
                        (this.voxels[this.tempPolyCube.data[i][0]][this.tempPolyCube.data[i][1]][this.tempPolyCube.data[i][2]]).hasPolyCube = false;
                    }
                    this.tempPolyCube.rotate(2, 1);
                    for (i = 0; i < 6; i++) {
                        (this.voxels[this.tempPolyCube.data[i][0]][this.tempPolyCube.data[i][1]][this.tempPolyCube.data[i][2]]).hasPolyCube = true;
                        this.voxels[this.tempPolyCube.data[i][0]][this.tempPolyCube.data[i][1]][this.tempPolyCube.data[i][2]].setColor(this.tempPolyCube.c);
                    }
                }
            } catch (Exception exception) {
            }
        }
    }

    public void moveTimer() {
        if (Tetris.timer % 10L == 0L) {
            try {
                if (this.LEFT
                        && !this.tempPolyCube.isCollidingLeft()) {
                    int i;
                    for (i = 0; i < 6; i++) {
                        (this.voxels[this.tempPolyCube.data[i][0]][this.tempPolyCube.data[i][1]][this.tempPolyCube.data[i][2]]).hasPolyCube = false;
                    }
                    this.tempPolyCube.translate(-1, 0, 0);
                    for (i = 0; i < 6; i++) {
                        (this.voxels[this.tempPolyCube.data[i][0]][this.tempPolyCube.data[i][1]][this.tempPolyCube.data[i][2]]).hasPolyCube = true;
                        this.voxels[this.tempPolyCube.data[i][0]][this.tempPolyCube.data[i][1]][this.tempPolyCube.data[i][2]].setColor(this.tempPolyCube.c);
                    }
                }
            } catch (Exception exception) {
            }
            try {
                if (this.RIGHT
                        && !this.tempPolyCube.isCollidingRight()) {
                    int i;
                    for (i = 0; i < 6; i++) {
                        (this.voxels[this.tempPolyCube.data[i][0]][this.tempPolyCube.data[i][1]][this.tempPolyCube.data[i][2]]).hasPolyCube = false;
                    }
                    this.tempPolyCube.translate(1, 0, 0);
                    for (i = 0; i < 6; i++) {
                        (this.voxels[this.tempPolyCube.data[i][0]][this.tempPolyCube.data[i][1]][this.tempPolyCube.data[i][2]]).hasPolyCube = true;
                        this.voxels[this.tempPolyCube.data[i][0]][this.tempPolyCube.data[i][1]][this.tempPolyCube.data[i][2]].setColor(this.tempPolyCube.c);
                    }
                }
            } catch (Exception exception) {
            }
            try {
                if (this.UP
                        && !this.tempPolyCube.isCollidingUp()) {
                    int i;
                    for (i = 0; i < 6; i++) {
                        (this.voxels[this.tempPolyCube.data[i][0]][this.tempPolyCube.data[i][1]][this.tempPolyCube.data[i][2]]).hasPolyCube = false;
                    }
                    this.tempPolyCube.translate(0, 0, 1);
                    for (i = 0; i < 6; i++) {
                        (this.voxels[this.tempPolyCube.data[i][0]][this.tempPolyCube.data[i][1]][this.tempPolyCube.data[i][2]]).hasPolyCube = true;
                        this.voxels[this.tempPolyCube.data[i][0]][this.tempPolyCube.data[i][1]][this.tempPolyCube.data[i][2]].setColor(this.tempPolyCube.c);
                    }
                }
            } catch (Exception exception) {
            }
            try {
                if (this.DOWN
                        && !this.tempPolyCube.isCollidingDown()) {
                    int i;
                    for (i = 0; i < 6; i++) {
                        (this.voxels[this.tempPolyCube.data[i][0]][this.tempPolyCube.data[i][1]][this.tempPolyCube.data[i][2]]).hasPolyCube = false;
                    }
                    this.tempPolyCube.translate(0, 0, -1);
                    for (i = 0; i < 6; i++) {
                        (this.voxels[this.tempPolyCube.data[i][0]][this.tempPolyCube.data[i][1]][this.tempPolyCube.data[i][2]]).hasPolyCube = true;
                        this.voxels[this.tempPolyCube.data[i][0]][this.tempPolyCube.data[i][1]][this.tempPolyCube.data[i][2]].setColor(this.tempPolyCube.c);
                    }
                }
            } catch (Exception exception) {
            }
            try {
                if (this.FALL
                        && !this.tempPolyCube.isCollidingFloor()) {
                    int i;
                    for (i = 0; i < 6; i++) {
                        (this.voxels[this.tempPolyCube.data[i][0]][this.tempPolyCube.data[i][1]][this.tempPolyCube.data[i][2]]).hasPolyCube = false;
                    }
                    this.tempPolyCube.translate(0, 1, 0);
                    for (i = 0; i < 6; i++) {
                        (this.voxels[this.tempPolyCube.data[i][0]][this.tempPolyCube.data[i][1]][this.tempPolyCube.data[i][2]]).hasPolyCube = true;
                        this.voxels[this.tempPolyCube.data[i][0]][this.tempPolyCube.data[i][1]][this.tempPolyCube.data[i][2]].setColor(this.tempPolyCube.c);
                    }
                }
            } catch (Exception exception) {
            }
            try {
                if (this.DROP) {
                    while (!this.tempPolyCube.isCollidingFloor()) {
                        int i;
                        for (i = 0; i < 6; i++) {
                            (this.voxels[this.tempPolyCube.data[i][0]][this.tempPolyCube.data[i][1]][this.tempPolyCube.data[i][2]]).hasPolyCube = false;
                        }
                        this.tempPolyCube.translate(0, 1, 0);
                        for (i = 0; i < 6; i++) {
                            (this.voxels[this.tempPolyCube.data[i][0]][this.tempPolyCube.data[i][1]][this.tempPolyCube.data[i][2]]).hasPolyCube = true;
                            this.voxels[this.tempPolyCube.data[i][0]][this.tempPolyCube.data[i][1]][this.tempPolyCube.data[i][2]].setColor(this.tempPolyCube.c);
                        }
                    }
                }
            } catch (Exception exception) {
            }
        }
    }

    public void draw(Graphics g, boolean front) {
        Graphics2D g2 = (Graphics2D) g;
        g2.translate((int) this.COR[0], (int) this.COR[1]);
        if (front) {
            for (int i = 17; i > 12; i--) {
                this.stuff[this.order[i]].draw(g2);
            }
        } else {
            for (int i = 0; i < this.stuff.length; i++) {
                this.stuff[this.order[i]].draw(g2);
            }
        }
        g2.translate((int) -this.COR[0], (int) -this.COR[1]);
    }

    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == 90) {
            this.CW = true;
        }
        if (e.getKeyCode() == 88) {
            this.CCW = true;
        }
        if (e.getKeyCode() == 65) {
            this.LEFT = true;
        } else if (e.getKeyCode() == 87) {
            this.UP = true;
        } else if (e.getKeyCode() == 83) {
            this.DOWN = true;
        } else if (e.getKeyCode() == 68) {
            this.RIGHT = true;
        } else if (e.getKeyCode() == 81) {
            this.DROP = true;
        } else if (e.getKeyCode() == 69) {
            this.FALL = true;
        } else if (e.getKeyCode() == 82) {
            this.XCW = true;
        } else if (e.getKeyCode() == 70) {
            this.XCCW = true;
        } else if (e.getKeyCode() == 84) {
            this.YCW = true;
        } else if (e.getKeyCode() == 71) {
            this.YCCW = true;
        } else if (e.getKeyCode() == 89) {
            this.ZCW = true;
        } else if (e.getKeyCode() == 72) {
            this.ZCCW = true;
        }
    }

    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() != 88) {
            this.CW = false;
        }
        if (e.getKeyCode() != 90) {
            this.CCW = false;
        }
        if (e.getKeyCode() == 65) {
            this.LEFT = false;
        }
        if (e.getKeyCode() == 87) {
            this.UP = false;
        }
        if (e.getKeyCode() == 83) {
            this.DOWN = false;
        }
        if (e.getKeyCode() == 68) {
            this.RIGHT = false;
        }
        if (e.getKeyCode() == 81) {
            this.DROP = false;
        }
        if (e.getKeyCode() == 69) {
            this.FALL = false;
        }
        if (e.getKeyCode() == 82) {
            this.XCW = false;
        }
        if (e.getKeyCode() == 70) {
            this.XCCW = false;
        }
        if (e.getKeyCode() == 84) {
            this.YCW = false;
        }
        if (e.getKeyCode() == 71) {
            this.YCCW = false;
        }
        if (e.getKeyCode() == 89) {
            this.ZCW = false;
        }
        if (e.getKeyCode() == 72) {
            this.ZCCW = false;
        }
    }

    public void keyTyped(KeyEvent e) {
    }
}
