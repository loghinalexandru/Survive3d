/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game.modules;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author Mr.Robot
 */
public class Texture {
    private int width;
    private int height;
    public int SIZE ;
    public int pixels [];
    Texture(String path , int size){
        this.SIZE = size;
        pixels = new int[size * size];
        loadTextureSheet(path);
    }
    private static BufferedImage image;
    
    public void loadTextureSheet(String location){
        try{
            BufferedImage image = ImageIO.read(new File(location));
            this.height = image.getHeight();
            this.width = image.getWidth();
            image.getRGB(0, 0 , 64 , 64 ,  pixels , 0 , 64);
        }
        catch(IOException e){
            e.printStackTrace();
    }
}
    
    
}
