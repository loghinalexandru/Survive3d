package game.modules;

import java.awt.Color;
import java.util.Arrays;
import game.modules.Texture;

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

    private static int width, height;
    private int time = 0;
    public double xPos = 4.5, yPos = 4.5;
    public double xDir = 1, yDir = 0;
    public double xPlane = 0, yPlane = -0.66;
    public static Texture brick = new Texture("textures/brick_wall.png", 64);

    public Screen(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public void drawImage(int[] pixels, int[][] map) {

        for (int n = 0; n < pixels.length >> 1; n++) {
            if (pixels[n] != Color.DARK_GRAY.getRGB()) {
                pixels[n] = Color.DARK_GRAY.getRGB();
            }
        }
        for (int i = pixels.length >> 1; i < pixels.length; i++) {
            if (pixels[i] != Color.LIGHT_GRAY.getRGB()) {
                pixels[i] = (Color.LIGHT_GRAY.getRGB() >> 1) & 8355711;
            }
        }
        for (int x = 0; x < width; ++x) {
            double cameraX = (x << 1) / (double) width - 1;
            double rayDirX = xDir + xPlane * cameraX;
            double rayDirY = yDir + yPlane * cameraX;
            int mapX = (int) xPos;
            int mapY = (int) yPos;
            double nextRayX = Math.abs(1 / rayDirX);
            double nextRayY = Math.abs(1 / rayDirY);
            double sideDistX;
            double sideDistY;
            double perpWallDist;
            int side = 0;
            int stepX;
            int stepY;
            boolean wallHit = false;
            if (rayDirX < 0) {
                stepX = -1;
                sideDistX = (xPos - mapX) * nextRayX;
            } else {
                stepX = 1;
                sideDistX = (mapX + 1.0 - xPos) * nextRayX;
            }
            if (rayDirY < 0) {
                stepY = -1;
                sideDistY = (yPos - mapY) * nextRayY;
            } else {
                stepY = 1;
                sideDistY = (mapY + 1.0 - yPos) * nextRayY;
            }
            while (wallHit == false) {
                if (sideDistX < sideDistY) {
                    sideDistX += nextRayX;
                    mapX += stepX;
                    side = 0;
                } else {
                    sideDistY += nextRayY;
                    mapY += stepY;
                    side = 1;
                }
                if (map[mapX][mapY] > 0) {
                    wallHit = true;
                }
            }
            if (side == 0) {
                perpWallDist = Math.abs((mapX - xPos + (1 - stepX) / 2) / rayDirX);
            } else {
                perpWallDist = Math.abs((mapY - yPos + (1 - stepY) / 2) / rayDirY);
            }
            int lineHeight;
            if (perpWallDist > 0) {
                lineHeight = Math.abs((int) (height / perpWallDist));
            } else {
                lineHeight = height;
            }
            int drawStart = (-lineHeight >> 1) + (height >> 1);
            if (drawStart < 0) {
                drawStart = 0;
            }
            int drawEnd = (lineHeight >> 1) + (height >> 1);
            if (drawEnd >= height) {
                drawEnd = height - 1;
            }

            double wallHitX;
            if (side == 1) {
                wallHitX = (xPos + ((mapY - yPos + (1 - stepY) / 2) / rayDirY) * rayDirX);
            } else {
                wallHitX = (yPos + ((mapX - xPos + (1 - stepX) / 2) / rayDirX) * rayDirY);
            }
            wallHitX -= Math.floor(wallHitX);
            int textureX = (int) (wallHitX * brick.SIZE);
            if (side == 0 && rayDirX > 0) {
                textureX = brick.SIZE - textureX - 1;
            }
            if (side == 1 && rayDirY < 0) {
                textureX = brick.SIZE - textureX - 1;
            }
            for (int j = drawStart; j < drawEnd; ++j) {
                int d = j * 256 - height * 128 + lineHeight * 128;
                int textureY = ((d * brick.SIZE) / lineHeight) / 256;
                int color = brick.pixels[Math.abs(textureX + textureY * brick.SIZE)];
                if (side == 1) {
                    color = (brick.pixels[Math.abs(textureX + textureY * brick.SIZE)] >> 1) & 8355711;
                }
                pixels[x + j * width] = color;
            }
        }
    }

    public void clearImage(int[] pixels) {
        Arrays.fill(pixels, 0);
    }

}
