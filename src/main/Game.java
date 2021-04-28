package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashSet;
import java.util.Set;
import static main.Initializer.customFont;
import orthoEngine.CubeTransform;

public class Game extends CubeTransform implements KeyListener {

    Cube[][][] voxels;
    Hexamino tempPolyCube;

    int[] xOrder;
    int[] yOrder;
    int[] zOrder;
    static int score;

    //movements
    double xTilt;

    private int initialX, initialY;

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

    boolean ENTER;
    boolean CHEAT;

    boolean gameOver = false;

    private Mouse mouse;

    public Game(Mouse mouse) {
        super(new double[]{200.0D, 480.0D, 200.0D}, new double[]{(Initializer.WIDTH / 2), (Initializer.HEIGHT / 2), 0.0D}, new double[]{(Initializer.WIDTH / 2), (Initializer.HEIGHT / 2), 0.0D}, new Color(0, 0, 0, 0));
        score = 0;
        this.voxels = new Cube[10][24][10];
        this.xOrder = new int[10];
        this.yOrder = new int[24];
        this.zOrder = new int[10];
        this.xTilt = 0.20943951023931953D;

        this.mouse = mouse;

        this.rotSpeed = 0.12D;
        this.netRotation = 0.0D;
        rotateX(this.xTilt);
        this.CW = false;
        this.CCW = false;
        int i;
        for (i = 0; i < this.voxels.length; i++) {
            for (int j = 0; j < (this.voxels[0]).length; j++) {
                for (int k = 0; k < (this.voxels[0][0]).length; k++) {
                    this.voxels[i][j][k] = new Cube(false, new double[]{20.0D, 20.0D, 20.0D}, new double[]{(i * 20 + Initializer.WIDTH / 2 - 90), (j * 20 + Initializer.HEIGHT / 2 - 230), (k * 20 - 90)}, new double[]{(Initializer.WIDTH / 2), (Initializer.HEIGHT / 2), 0.0D}, Color.BLUE);
                    this.voxels[i][j][k].rotateX(this.xTilt);
                    this.voxels[i][j][k].sortOrder();
                }
            }
        }
        for (i = this.yOrder.length - 1; i >= 0; i--) {
            this.yOrder[this.yOrder.length - 1 - i] = i;
        }
        this.tempPolyCube = new Hexamino();
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
            g.setColor(Color.BLACK);
            g.fillRect(0, 0, 1280, 720);
            g.setColor(Color.RED);
            //g.setFont(new Font("serif", Font.PLAIN, 100));
            g.setFont(Initializer.customFontLarge);
            g.drawString("GAME OVER", 420, 320);
            g.drawString("SCORE: " + score, 440, 400);
            g.drawString("PRESS ENTER TO START OVER", 320, 480);

            if (this.ENTER) {
                score = 0;

                for (int i = 0; i < 10; i++) {
                    for (int j = 0; j < 24; j++) {
                        for (int k = 0; k < 10; k++) {
                            this.voxels[i][j][k].isOccupied = false;
                            this.voxels[i][j][k].setColor(this.tempPolyCube.c);

                        }
                    }
                }

                this.gameOver = false;
            }

        }
    }

    public void tick() {
        int x = mouse.getX();
        int y = mouse.getY();

        if (mouse.getButton() == ClickType.LeftClick) {
            double xDif = ((double) initialX - (double) x) / 300;
            xDif = ensureRange(xDif, -this.rotSpeed, this.rotSpeed);

//            double yDif = ((double)y - (double)initialY) / 100;
//            //yDif = ensureRange(yDif, 0, 1.5);
//            
//            xTilt += yDif;
//            xTilt = ensureRange(xTilt, 0, 20943951023931953D);
            //System.out.println(xTilt);
            rotateY(-Math.cos(this.xTilt) * xDif);
            rotateZ(Math.sin(this.xTilt) * xDif);
            this.netRotation += xDif;
            for (Cube[][] voxel : this.voxels) {
                for (int j = 0; j < (this.voxels[0]).length; j++) {
                    for (int k = 0; k < (this.voxels[0][0]).length; k++) {
                        voxel[j][k].rotateY(-Math.cos(this.xTilt) * xDif);
                        voxel[j][k].rotateZ(Math.sin(this.xTilt) * xDif);
                        voxel[j][k].sortOrder();
                    }
                }
            }
        }

        initialX = x;
        initialY = y;

        if (this.CW) {
            rotateY(-Math.cos(this.xTilt) * this.rotSpeed);
            rotateZ(Math.sin(this.xTilt) * this.rotSpeed);
            this.netRotation += this.rotSpeed;
            for (Cube[][] voxel : this.voxels) {
                for (int j = 0; j < (this.voxels[0]).length; j++) {
                    for (int k = 0; k < (this.voxels[0][0]).length; k++) {
                        voxel[j][k].rotateY(-Math.cos(this.xTilt) * this.rotSpeed);
                        voxel[j][k].rotateZ(Math.sin(this.xTilt) * this.rotSpeed);
                        voxel[j][k].sortOrder();
                    }
                }
            }
        } else if (this.CCW) {
            rotateZ(-Math.sin(this.xTilt) * this.rotSpeed);
            rotateY(Math.cos(this.xTilt) * this.rotSpeed);
            this.netRotation -= this.rotSpeed;
            for (Cube[][] voxel : this.voxels) {
                for (int j = 0; j < (this.voxels[0]).length; j++) {
                    for (int k = 0; k < (this.voxels[0][0]).length; k++) {
                        voxel[j][k].rotateZ(-Math.sin(this.xTilt) * this.rotSpeed);
                        voxel[j][k].rotateY(Math.cos(this.xTilt) * this.rotSpeed);
                        voxel[j][k].sortOrder();
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
                    for (Cube[][] voxel : this.voxels) {
                        for (int c = 0; c < (this.voxels[0][0]).length; c++) {
                            (voxel[b][c]).isOccupied = (voxel[b - 1][c]).isOccupied;
                            voxel[b][c].setColor(this.tempPolyCube.c);
                        }
                    }
                }
                score++;
            }
        }
    }

    public void dropTimer() {
        if (Initializer.timer % (100 / (1 + Math.min(score, 99))) == 0L) {
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
                        this.tempPolyCube = new Hexamino();
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
        if (Initializer.timer % 10L == 0L) {
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
        if (Initializer.timer % 10L == 0L) {
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
            
            try{
                if(this.CHEAT){
                    score++;
                }
            }catch(Exception e){}
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

    @Override
    public void keyPressed(KeyEvent e) {

        if (e.getKeyCode() == KeyEvent.VK_Z) {
            this.CW = true;
        }
        if (e.getKeyCode() == KeyEvent.VK_X) {
            this.CCW = true;
        }
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            this.ENTER = true;
        }
        switch (e.getKeyCode()) {
            case KeyEvent.VK_A:
                this.LEFT = true;
                break;
            case KeyEvent.VK_W:
                this.UP = true;
                break;
            case KeyEvent.VK_S:
                this.DOWN = true;
                break;
            case KeyEvent.VK_D:
                this.RIGHT = true;
                break;
            case KeyEvent.VK_Q:
                this.DROP = true;
                break;
            case KeyEvent.VK_E:
                this.FALL = true;
                break;
            case KeyEvent.VK_R:
                this.XCW = true;
                break;
            case KeyEvent.VK_F:
                this.XCCW = true;
                break;
            case KeyEvent.VK_T:
                this.YCW = true;
                break;
            case KeyEvent.VK_G:
                this.YCCW = true;
                break;
            case KeyEvent.VK_Y:
                this.ZCW = true;
                break;
            case KeyEvent.VK_H:
                this.ZCCW = true;
                break;
            case KeyEvent.VK_9:
                this.CHEAT = true;
                break;
            default:
                break;
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() != KeyEvent.VK_X) {
            this.CW = false;
        }
        if (e.getKeyCode() != KeyEvent.VK_Z) {
            this.CCW = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_A) {
            this.LEFT = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_W) {
            this.UP = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_S) {
            this.DOWN = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_D) {
            this.RIGHT = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_Q) {
            this.DROP = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_E) {
            this.FALL = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_R) {
            this.XCW = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_F) {
            this.XCCW = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_T) {
            this.YCW = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_G) {
            this.YCCW = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_Y) {
            this.ZCW = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_H) {
            this.ZCCW = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            this.ENTER = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_9) {
            this.CHEAT = false;
        }

    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    private double ensureRange(double value, double min, double max) {
        return Math.min(Math.max(value, min), max);
    }
}
