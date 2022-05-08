package com.game.src.main;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Life extends GameObject{
    private Assets assets;
    public int remainingHealth;

    public Life(double x, double y, MyGame game, int remainingHealth) {
        super(x, y, ID.Life);
        assets = new Assets(game);
        this.remainingHealth = remainingHealth;
    }

    public void render(Graphics graphics){
        init(graphics);
        int i = 0, posX = 10;;

        while(i < remainingHealth) {
             graphics.drawImage(assets.halfHeart, posX, (int)y, null);
             i++;
             if(i < remainingHealth){
                 graphics.drawImage(assets.fullHeart, posX, (int)y, null);
             }
             i++;
             posX += 30;
        }

    }
//    public void tick(){}
    private void init(Graphics graphics){
        int i = 0;
        int posX = 10;
        while(i < 3){
            graphics.drawImage(assets.emptyHeart, posX, (int)y, null);
            i++;
            posX += 30;
        }
    }

    public void setHealth(int health) {
        this.remainingHealth = health;
    }
}
