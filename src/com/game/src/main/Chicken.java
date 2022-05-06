package com.game.src.main;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Chicken extends GameObject {

    private double velX, velY;
    private boolean direction, moving;

    private BufferedImage chicken;
    private Assets assets;
    private Controller controller;

    private MyGame game;
    private Life health;
    private int heartCount;
    public Chicken(double x, double y, Assets assets, ID type, Controller controller, MyGame game){
        super(x, y, type);
        this.velX = 0;
        this.velY = 0;
        this.moving = false;
        this.assets = assets;
        this.controller = controller;
        this.game = game;
        health = new Life(10, 10, game, 6);
        heartCount = 6;

    }

    public boolean isDirection() {
        return direction;
    }

    public void tick(){
        //y += velY;
        if(direction) {
            x += velX;
        }else{
            x -= velX;
        }
        if(moving)
            velX += 0.1;
        if(velX > 3)//speed limit
            velX = 3;
        if(!moving)
            velX -= 0.2;//slippage factor
        if(velX < 0)
            velX = 0;
        if(x <= 0)
            x = 0;
        if(x >= 600)
            x = 600;
        collides();

    }

    public void render(Graphics graphics)
    {
        graphics.drawImage(assets.chicken, (int)x, (int)y, null);
        health.render(graphics);

//        Graphics2D graphics2D = (Graphics2D) graphics;
//        graphics2D.setColor(Color.green);
//        graphics2D.draw(getBorders());
    }
    public double getX() {
        return x;
    }
    public double getY() {
        return y;
    }
    public void setVelX(double velX) {
            this.velX = velX;
    }

    public void setDirection(boolean direction) {
        this.direction = direction;
    }
    public void setMoving(boolean moving) {
        this.moving = moving;
    }

    public void collides(){
        for(int i = 0; i < controller.objects.size(); i++){
            GameObject temp = controller.objects.get(i);
            if(temp != null &&temp.getType() == ID.Cat){
                if(getBorders().intersects(temp.getBorders())){
                    controller.removeObj(temp);

                        health.setHealth(--heartCount);
                        if(heartCount == 0)
                            System.exit(0); // replace with game end screen

                    }
                }
            }
        }
    public void runAnim(){
        this.velX = 3;
        this.direction = true;
        if(this.x < 400)
            tick();

    }
}
