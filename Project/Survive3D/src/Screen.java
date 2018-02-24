
import java.util.Arrays;

/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author shazam
 */
public class Screen {
    private int width ,  height;
    private int time = 0;
    public int[] pixels;
    public Screen(int width , int height){
        this.width = width;
        this.height = height;
    }
    public void drawImage(int[] pixels)
    {
        ++time;
        if(time > 500){
            time = 0;
        }
        for(int i = 0; i < height; ++i){
            for(int j = 0; j < width; ++j){
                pixels[time + i + time * width] = 0xff00ff;  
            }
        }
    }
    
    public void clearImage(int[] pixels){
        Arrays.fill(pixels , 0);
    }
        
}
