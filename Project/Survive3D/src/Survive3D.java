/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author shazam
 */
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;

public class Survive3D extends JFrame implements Runnable {

    private static final long serialVersionUID = 1L;
    private static final String gameTitle = "Survive3D";
    private final int width = 1280;
    private final int height = 720;
    private Screen screen;
    public static int fps = 0;
    public final int mapWidth = 24;
    public final int mapHeight = 24;
    Controls playerControl;
    private Thread thread;
    private boolean running;
    private BufferedImage image;
    public int[] pixels;
    public static final int[][] map
            = {
                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
                {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
                {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
                {1, 0, 0, 0, 0, 0, 2, 2, 2, 2, 2, 0, 0, 0, 0, 3, 0, 3, 0, 3, 0, 0, 0, 1},
                {1, 0, 0, 0, 0, 0, 2, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
                {1, 0, 0, 0, 0, 0, 2, 0, 0, 0, 2, 0, 0, 0, 0, 3, 0, 0, 0, 3, 0, 0, 0, 1},
                {1, 0, 0, 0, 0, 0, 2, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
                {1, 0, 0, 0, 0, 0, 2, 2, 0, 2, 2, 0, 0, 0, 0, 3, 0, 3, 0, 3, 0, 0, 0, 1},
                {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
                {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
                {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
                {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
                {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
                {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
                {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
                {1, 4, 4, 4, 4, 4, 4, 4, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
                {1, 4, 0, 4, 0, 0, 0, 0, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
                {1, 4, 0, 0, 0, 0, 5, 0, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
                {1, 4, 0, 4, 0, 0, 0, 0, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
                {1, 4, 0, 4, 4, 4, 4, 4, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
                {1, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
                {1, 4, 4, 4, 4, 4, 4, 4, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}
            };

    public Survive3D() {
        thread = new Thread(this);
        image = new BufferedImage(this.width, this.height, BufferedImage.TYPE_INT_RGB);
        pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
        setSize(this.width, this.height);
        setResizable(false);
        screen = new Screen(image.getWidth(), image.getHeight());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle(gameTitle);
        setBackground(Color.black);
        setLocationRelativeTo(null);
        setVisible(true);
        playerControl = new Controls();
        this.addKeyListener(playerControl);
    }

    public void run() {
        long lastTime = System.nanoTime();
        long timer = System.currentTimeMillis();
        final double ns = 1000000000.0 / 60.0;
        double delta = 0;
        int frames = 0;
        requestFocus();
        while (running) {
            long now = System.nanoTime();
            delta = delta + ((now - lastTime) / ns);
            lastTime = now;
            while (delta >= 1) {
                this.update(map);
                screen.clearImage(pixels);
                screen.drawImage(pixels, map);
                ++frames;
                delta--;
            }
            render();
            if (System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
                this.setTitle(gameTitle + " | " + "fps : " + frames);
                frames = 0;
            }
        }
    }

    public synchronized void start() {
        this.running = true;
        thread.start();
    }

    public synchronized void stop() {
        this.running = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void render() {
        BufferStrategy buff = getBufferStrategy();
        if (buff == null) {
            createBufferStrategy(3);
            return;
        }
        Graphics g = buff.getDrawGraphics();
        g.drawImage(image, 0, 0, image.getWidth(), image.getHeight(), null);
        buff.show();

    }

    public void update(int[][] map) {
        if (playerControl.up) {
            if (map[(int) (screen.xPos + screen.xDir * playerControl.MOVE_SPEED)][(int) screen.yPos] == 0) {
                screen.xPos += screen.xDir * playerControl.MOVE_SPEED;
            }
            if (map[(int) screen.xPos][(int) (screen.yPos + screen.yDir * playerControl.MOVE_SPEED)] == 0) {
                screen.yPos += screen.yDir * playerControl.MOVE_SPEED;
            }
        }
        if (playerControl.down) {
            if (map[(int) (screen.xPos - screen.xDir * playerControl.MOVE_SPEED)][(int) screen.yPos] == 0) {
                screen.xPos -= screen.xDir * playerControl.MOVE_SPEED;
            }
            if (map[(int) screen.xPos][(int) (screen.yPos - screen.yDir * playerControl.MOVE_SPEED)] == 0) {
                screen.yPos -= screen.yDir * playerControl.MOVE_SPEED;
            }
        }
        if (playerControl.right) {
            double oldxDir = screen.xDir;
            screen.xDir = screen.xDir * Math.cos(-playerControl.ROTATION_SPEED) - screen.yDir * Math.sin(-playerControl.ROTATION_SPEED);
            screen.yDir = oldxDir * Math.sin(-playerControl.ROTATION_SPEED) + screen.yDir * Math.cos(-playerControl.ROTATION_SPEED);
            double oldxPlane = screen.xPlane;
            screen.xPlane = screen.xPlane * Math.cos(-playerControl.ROTATION_SPEED) - screen.yPlane * Math.sin(-playerControl.ROTATION_SPEED);
            screen.yPlane = oldxPlane * Math.sin(-playerControl.ROTATION_SPEED) + screen.yPlane * Math.cos(-playerControl.ROTATION_SPEED);
        }
        if (playerControl.left) {
            double oldxDir = screen.xDir;
            screen.xDir = screen.xDir * Math.cos(playerControl.ROTATION_SPEED) - screen.yDir * Math.sin(playerControl.ROTATION_SPEED);
            screen.yDir = oldxDir * Math.sin(playerControl.ROTATION_SPEED) + screen.yDir * Math.cos(playerControl.ROTATION_SPEED);
            double oldxPlane = screen.xPlane;
            screen.xPlane = screen.xPlane * Math.cos(playerControl.ROTATION_SPEED) - screen.yPlane * Math.sin(playerControl.ROTATION_SPEED);
            screen.yPlane = oldxPlane * Math.sin(playerControl.ROTATION_SPEED) + screen.yPlane * Math.cos(playerControl.ROTATION_SPEED);
        }
        System.out.println(screen.xPos + " | " + screen.yPos);
        if (playerControl.quit) {
            this.setVisible(false);
            this.dispose();
            System.exit(0);
        }
        System.out.println(screen.xPos + " | " + screen.yPos);
    }

    public static void main(String[] args) {
        Survive3D game = new Survive3D();
        game.start();

    }
}
