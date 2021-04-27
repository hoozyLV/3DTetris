package main;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.VolatileImage;
import java.io.File;
import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JFrame;

public class Tetris extends JFrame implements Runnable {
    
    private static final long serialVersionUID = 1L;
    public static int WIDTH = 600;
    public static int HEIGHT = 600;
    public static Image icon;
    public static boolean isRunning = false;
    private long ping = 10L;
    private long FPS = 1000L / this.ping;
    public static long timer = 1L;
    private VolatileImage screen;

    File music = new File("resources/music.wav");

    static PolyCubeContainer game;

    public Tetris() {
        super("Tetris 3D 1.0.0");
        setDefaultCloseOperation(3);
        setSize(WIDTH, HEIGHT);
        setResizable(false);

        try {
            icon = ImageIO.read(new File("resources/logo.png"));
            setIconImage(icon);
        } catch (Exception exception) {
        }

        setLayout(new FlowLayout());
        try {
            AudioInputStream AIS = AudioSystem.getAudioInputStream(this.music);
            Clip clip = AudioSystem.getClip();
            clip.open(AIS);
            clip.start();
            clip.loop(-1);
        } catch (Exception e) {
            System.out.println("Audio Playback Error");
        }
        game = new PolyCubeContainer();
        addKeyListener(game);
    }

    public static void main(String[] args) {
        Tetris t = new Tetris();
        t.setVisible(true);
        isRunning = true;
        (new Thread(t)).start();
    }

    public void run() {
        this.screen = createVolatileImage(WIDTH, HEIGHT);
        while (isRunning) {
            tick();
            render();
            try {
                Thread.sleep(this.ping);
            } catch (Exception exception) {
            }
        }
    }

    public void render() {
        Graphics g = this.screen.getGraphics();
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, WIDTH, HEIGHT);
        for (int i = 510; i >= 0; i--) {
            g.setColor(new Color(0, i / 2, i / 2));
            g.fillRect(0, i + HEIGHT - 510, WIDTH, 1);
        }
        game.render(g);
        graphicsFont.drawString(g, "tetris 3d", 20, 50, 5, 2);
        graphicsFont.drawString(g, "your score " + PolyCubeContainer.score, 20, 80, 3, 1);
        g.fillRect(110, 83, 2, 2);
        g.fillRect(110, 87, 2, 2);
        graphicsFont.drawString(g, "developed by angel alvarez", 20, 580, 3, 1);
        Graphics g1 = getGraphics();
        g1.drawImage(this.screen, 0, 0, null);
        g1.dispose();
    }

    public void tick() {
        game.tick();
        timer++;
        timer %= this.FPS;
    }
}
