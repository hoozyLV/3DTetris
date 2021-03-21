package renderer;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import renderer.entity.EntityManager;
import renderer.input.ClickType;
import renderer.input.Mouse;
import renderer.point.MyPoint;
import renderer.point.PointConverter;
import renderer.shapes.MyPolygon;
import renderer.shapes.Tetrahedron;

public class Display extends Canvas implements Runnable {
    private Thread thread;
    private JFrame frame;
    private static String title = "3D Tetris";
    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;
    private static boolean running = false;
    
    private EntityManager entityManager;
    
    private Mouse mouse;
    
    public Display(){
        this.frame = new JFrame();
        
        Dimension size = new Dimension(WIDTH, HEIGHT);
        this.setPreferredSize(size);
        
        this.mouse = new Mouse();
        
        this.entityManager = new EntityManager();
        
        this.addMouseListener(this.mouse);
        this.addMouseMotionListener(this.mouse);
        this.addMouseWheelListener(this.mouse);
    }
    
    public static void main(String[] args){
        Display display = new Display();
        display.frame.setTitle(title);
        display.frame.add(display);
        display.frame.pack();
        display.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        display.frame.setLocationRelativeTo(null);
        display.frame.setResizable(false);
        display.frame.setVisible(true);
        display.start();
    }
    
    public synchronized void start(){
        running = true;
        this.thread = new Thread(this, "Display");
        this.thread.start();
    }
    
    public synchronized void stop() throws InterruptedException {
        running = false;
        this.thread.join();
    }
    
    @Override
    public void run() {
        long lastTime = System.nanoTime();
        long timer = System.currentTimeMillis();
        final double ns = 1000000000.0 / 60; //60 updates per second
        double delta = 0;
        int frames = 0;
        
        this.entityManager.init();
        
        while(running){
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            
            while(delta >= 1){ //fixedUpdate
                update();
                delta--;
            }
            
            render();
            frames++;
            
            if(System.currentTimeMillis() - timer > 1000){
                timer += 1000;
                this.frame.setTitle(title + " | FPS: " + frames);
                frames = 0;
            }
        }
        try {
            stop();
        } catch (InterruptedException ex) {
            Logger.getLogger(Display.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void render(){
        BufferStrategy bs = this.getBufferStrategy();
        if(bs == null){
            this.createBufferStrategy(3);
            return;
        }
        
        Graphics g = bs.getDrawGraphics();
        
        g.setColor(new Color(15, 15, 15));
        g.fillRect(0,0, WIDTH, HEIGHT);
        
        this.entityManager.render(g);

        g.dispose();
        bs.show();
    }
    
    private void update(){
        this.entityManager.update(this.mouse);

    }
}
