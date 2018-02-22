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
        private Screen screen;
        public static int fps = 0;
	public int mapWidth = 15;
	public int mapHeight = 15;
	private Thread thread;
	private boolean running;
	private BufferedImage image;
	public int[] pixels;
	public static int[][] map = 
		{
			{1,1,1,1,1,1,1,1,2,2,2,2,2,2,2},
			{1,0,0,0,0,0,0,0,2,0,0,0,0,0,2},
			{1,0,3,3,3,3,3,0,0,0,0,0,0,0,2},
			{1,0,3,0,0,0,3,0,2,0,0,0,0,0,2},
			{1,0,3,0,0,0,3,0,2,2,2,0,2,2,2},
			{1,0,3,0,0,0,3,0,2,0,0,0,0,0,2},
			{1,0,3,3,0,3,3,0,2,0,0,0,0,0,2},
			{1,0,0,0,0,0,0,0,2,0,0,0,0,0,2},
			{1,1,1,1,1,1,1,1,4,4,4,0,4,4,4},
			{1,0,0,0,0,0,1,4,0,0,0,0,0,0,4},
			{1,0,0,0,0,0,1,4,0,0,0,0,0,0,4},
			{1,0,0,2,0,0,1,4,0,3,3,3,3,0,4},
			{1,0,0,0,0,0,1,4,0,3,3,3,3,0,4},
			{1,0,0,0,0,0,0,0,0,0,0,0,0,0,4},
			{1,1,1,1,1,1,1,4,4,4,4,4,4,4,4}
		};

    public Survive3D()
    {
                thread = new Thread(this);
		image = new BufferedImage(640, 480, BufferedImage.TYPE_INT_RGB);
		pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();
		setSize(640, 480);
		setResizable(false);
                screen = new Screen(640 , 480);
		setTitle("3D Engine");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBackground(Color.black);
		setLocationRelativeTo(null);
		setVisible(true);
		start();
    }
    
public void run() {
	long lastTime = System.nanoTime();
	final double ns = 1000000000.0 / 60.0;
	double delta = 0;
	requestFocus();
	while(running) {
		long now = System.nanoTime();
		delta = delta + ((now-lastTime) / ns);
		lastTime = now;
		while (delta >= 1)
		{
			delta--;
		}
		render();
	}
}
    
    public synchronized void start()
    {
        this.running = true;
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Survive3D.class.getName()).log(Level.SEVERE, null, ex);
                }
        thread.start();
    }
    
    public synchronized void stop()
    {
        this.running = false;
        try{
            thread.join();
        }
        catch(InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    public void render ()
    {
        ++fps;
        BufferStrategy buff = getBufferStrategy(); 
        if(buff == null){
            createBufferStrategy(3);
            return;
        }
        screen.drawImage(pixels);
        Graphics g = buff.getDrawGraphics();
        g.drawImage(image , 0 , 0 , image.getWidth() , image.getHeight() , null);
        g.dispose();
        buff.show();
        
    }
    
    public static void main(String[] args) {
        Survive3D game = new Survive3D();
    }
    
}
