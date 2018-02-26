package game.modules;


import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Mr.Robot
 */

public class Controls implements KeyListener {

    public boolean left , right , up , down , quit = false , doorOpen;
    public final double MOVE_SPEED = .065;
    public final double  ROTATION_SPEED = .035;
    
    @Override
    public void keyTyped(KeyEvent e) {
        return;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_LEFT){
            left = true;
        }
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            right = true;
        }
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            up = true;
        }
        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            down = true;
        }  
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            quit = true;
        }
        if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
            quit = true;
        }
        if(e.getKeyCode() == KeyEvent.VK_SPACE){
            doorOpen = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            left = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            right = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            up = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            down = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            doorOpen = false;
        }
        
    } 
    
}
