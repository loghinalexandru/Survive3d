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
		image = new BufferedImage(this.width, this.height, BufferedImage.TYPE_INT_RGB);
		pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();
		setSize(this.width, this.height);
		setResizable(false);
                screen = new Screen(image.getWidth(), image.getHeight());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                setTitle(gameTitle);
		setBackground(Color.black);
		setLocationRelativeTo(null);
		setVisible(true);
    }
    
public void run() {
	long lastTime = System.nanoTime();
        long timer = System.currentTimeMillis();
	final double ns = 1000000000.0 / 60.0;
	double delta = 0;
        int frames = 0;
	requestFocus();
	while(running) {
		long now = System.nanoTime();
		delta = delta + ((now-lastTime) / ns);
		lastTime = now;
		while (delta >= 1)
		{
                        ++frames;
			delta--;
		}
		render();
                if(System.currentTimeMillis() - timer > 1000){
                    timer += 1000;
                    this.setTitle(gameTitle + " | " + "fps : " + frames);
                    frames = 0;
                }
	}
}
    
    public synchronized void start()
    {
        this.running = true;
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
       // screen.clearImage(pixels);
        screen.drawImage(pixels);
        Graphics g = buff.getDrawGraphics();
        g.drawImage(image , 0 , 0 , image.getWidth() , image.getHeight() , null);
        g.dispose();
        buff.show();
        
    }
    
    public static void main(String[] args) {
        Survive3D game = new Survive3D();
        game.start();
        System.out.println("I am the main thread , i created thread t1 and had it execute run method, which is currently looping for i to 1000000");
        
    }
    
}


class testThread implements Runnable{

 public void run()
 {
     for(int i=0;i<1000000;i++){} //a simple delay block to clarify.

     System.out.println("I am done executing run method of testThread");

 }  
}
