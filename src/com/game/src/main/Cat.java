package com.game.src.main;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class Cat extends GameObject{
    private double velX, velY;
    private Assets assets;
    private Controller controller;

    private Random rand;

    BufferedImage img;
    Timer timer;
    private double auxiliary;
    public Cat(double x, double y, Assets assets, ID type, Controller controller) {
        super(x, y, type);
        this.assets = assets;
        this.velY = MyGame.FALLING_SPEED;
        this.controller = controller;
        this.img = assets.cat;
        timer = new Timer();
        this.rand = new Random();
        this.auxiliary = 0.0;
    }
    public void tick(){
        if(y < -40) {
            x = (getRand(1, 10)) * 48;
            y = 480;
        }
        y -= velY;
        collides();
        //zigzagMVMT();
    }
    public void render(Graphics graphics){
        graphics.drawImage(img, (int)x, (int)y, null);
//        Graphics2D graphics2D = (Graphics2D) graphics;
//        graphics2D.setColor(Color.green); // hitbox for enemies
//        graphics2D.draw(getBorders());
    }
    public Rectangle getBorders(){
        return(new Rectangle((int)x, (int)y, 40, 44));
    }
    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void collides() {
        for(int i = 0; i < controller.objects.size(); i++){
            GameObject temp  = controller.objects.get(i);
            if (temp != null && temp.getType() == ID.Egg) {
                if (getBorders().intersects(temp.getBorders())) {
                    setImg(assets.eggedCat);
                    timer.schedule(new TimerTask() {
                        public void run() {
                            controller.removeObj(Cat.this);
                        }
                    }, 100);

                }
            }
        }
    }
    public void zigzagMVMT(){
        if (auxiliary >= Math.PI * 2) {
            auxiliary = 0.0;
        }

        if (auxiliary < Math.PI * 2) {
            int posX = (int) (Math.sin(auxiliary) * 5);// zigzag amplitude
            int posY = (int) (Math.asin(auxiliary) * 1);// ?????????????????
            setX(getX() + posX);
            setY(getY() + posY); // controls the speed of enemies
            auxiliary += 0.1;
        }
    }
    public void setImg(BufferedImage image){
        this.img = image;
    }
    public int getRand(int a, int b){
        return (int) ((Math.random() * (a - b)) + b);
    }
}
