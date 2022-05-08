package com.game.src.main;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Timer;
import java.util.TimerTask;

public class Egg extends GameObject {


    private Assets assets;
    Timer timer;
    private Controller controller;
    public BufferedImage img;
    public Egg(double x, double y, Assets assets, ID type, Controller controller) {
        super(x, y, type);
        this.assets = assets;
        this.controller = controller;
        img = assets.goldenEgg;
        timer = new Timer();
    }

    public void tick(){
        y += 4;
        if(y > 500)
            controller.removeObj(this);
        collides();

    }
    public Rectangle getBorders(){
        return(new Rectangle((int)x, (int)y, 19, 22));
    }

    public void render(Graphics graphics){

            graphics.drawImage(img, (int)x, (int)y, null);
//        Graphics2D graphics2D = (Graphics2D) graphics;
//        graphics2D.setColor(Color.green);
//        graphics2D.draw(getBorders());
    }

    public double getY() {
        return y;
    }


    public double getX() {
        return x;
    }
    public void collides() {
        for(int i = 0; i < controller.objects.size(); i++){
            if(controller.objects.get(i) != null) {
                GameObject temp = controller.objects.get(i);
                if (temp.getType() == ID.Cat) {
                    if (getBorders().intersects(temp.getBorders())) {
                        setImg(assets.eggCracked);
                        timer.schedule(new TimerTask() {
                            public void run() {
                                controller.removeObj(Egg.this);
                            }
                        }, 70);
                    }
                }
            }
        }
    }

    public void setImg(BufferedImage image){
        this.img = image;

    }


}
