package main;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferStrategy;
import java.awt.image.VolatileImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Initializer extends JFrame implements Runnable {

    private static final long serialVersionUID = 1L;
    public static int WIDTH = 1280;
    public static int HEIGHT = 720;
    public static Image icon;
    public static boolean isRunning = false;
    private long FPS = 100;
    public static long timer = 1L;
    private Thread thread;
    private VolatileImage screen;
    private static Image backgr;

    public static Font customFont;
    public static Font customFontLarge;
    File music = new File("resources/music.wav");
    static Game game;
    public Mouse mouse;

    public Initializer() {
        super("Hexis 3D");
        mouse = new Mouse();
        game = new Game(mouse);
    }

    public static void main(String[] args) throws FontFormatException {
        Initializer t = new Initializer();
        t.setDefaultCloseOperation(3);
        t.setSize(WIDTH, HEIGHT);
        t.setResizable(false);
        t.setLayout(new FlowLayout());

        try {
            t.setIconImage(ImageIO.read(new File("resources/logo.png")));
        } catch (IOException e) {
        }
        try {
            backgr = ImageIO.read(new File("resources/backgr.png"));
        } catch (IOException e) {
        }
        try {
            AudioInputStream stream = AudioSystem.getAudioInputStream(t.music);
            Clip clip = AudioSystem.getClip();
            clip.open(stream);
            clip.getControl(FloatControl.Type.MASTER_GAIN);
            FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(-20.0f); // Reduce volume by 10 decibels.
            clip.start();
            clip.loop(-1);
        } catch (IOException | LineUnavailableException | UnsupportedAudioFileException e) {
            System.out.println(e);
        }

        try {
            customFont = Font.createFont(Font.TRUETYPE_FONT, new File("resources/custom_font.ttf")).deriveFont(25f);
            customFontLarge = Font.createFont(Font.TRUETYPE_FONT, new File("resources/custom_font.ttf")).deriveFont(80f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(customFont);
            ge.registerFont(customFontLarge);
        } catch (IOException | FontFormatException e) {
        }

        t.addMouseListener(t.mouse);
        t.addMouseMotionListener(t.mouse);
        
        t.addKeyListener(game);

        t.setVisible(true);
        t.start();
    }

    public synchronized void start() {
        isRunning = true;
        this.thread = new Thread(this, "Display");
        this.thread.start();
    }

    @Override
    public void run() {
        while (isRunning) {
            tick();
            render();
        }
    }

    private void render() {
        BufferStrategy bs = this.getBufferStrategy();
        if (bs == null) {
            this.createBufferStrategy(3);
            return;
        }

        Graphics g = bs.getDrawGraphics();
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, WIDTH * 2, HEIGHT * 2);

        //g.drawImage(backgr, );
        g.drawImage(backgr, 0, 0, null);

        g.setColor(Color.WHITE);
        //g.setFont(new Font(customFont, Font.PLAIN, 18));
        g.setFont(customFont);
        g.drawString("HEXIS 3D", 20, 70);
        g.drawString("SCORE: " + Game.score, 20, 100);

        game.render(g);

        g.dispose();
        bs.show();
    }

    public void tick() {
        game.tick();
        timer++;
        timer %= this.FPS;
    }
}
