package com.game.src.main;

import com.sun.tools.javac.Main;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import com.game.src.main.MyGame.GAME_STATE;

import javax.rmi.ssl.SslRMIClientSocketFactory;

public class MainMenu extends MouseAdapter {
    private BImgLoader loader = new BImgLoader();
    private BufferedImage menuBackground = null;
    private String backgroundPath = "resources/menuBackground.png";
    private MyGame game;
    private Controller controller;
    private Assets assets;
    private Chicken chicken;
    public MainMenu(MyGame game, Controller controller, Assets assets, Chicken chicken){

        try {
            menuBackground = loader.loadImage(backgroundPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.game = game;
        this.controller = controller;
        this.assets = assets;
        this.chicken = chicken;
    }
    private int x = MyGame.GAME_WIDTH, y = MyGame.GAME_HEIGHT,
            scale = MyGame.SCALE, initialY = 70, offset = 50;
    public void render(Graphics graphics){
        graphics.drawImage(menuBackground, 0, 0, x * scale, y * scale, null);
        graphics.setColor(new Color(100, 120, 120, 200));
        graphics.fillRoundRect(10, initialY - 30, 120, 40, 20, 20);
        graphics.fillRoundRect(10, initialY - 30 + offset, 140, 40, 20, 20);
        graphics.fillRoundRect(10, initialY - 30 +  2 * offset, 40, 40, 20, 20);
        graphics.setColor(Color.yellow);
        graphics.setFont(new Font("Default", Font.PLAIN, 24));
        String start = "Start game";
        String selectLevel = "Choose level";
        String exit = "Exit";
        graphics.drawString(start, 10, initialY);
        graphics.drawString(selectLevel, 10, initialY + offset);
        graphics.drawString(exit, 10, initialY + 2 * offset);
    }
    public void tick(){

    }
    public void mouseClicked(MouseEvent e){
        int mx = e.getX();
        int my = e.getY();
        if(hovering(mx, my, 10, 40, 120, 40)){
            game.state = GAME_STATE.Game;

        }
        if(hovering(mx, my, 10, 140, 120, 40)) {
            System.out.println("exiting..");
            System.exit(0);
        }
        if(hovering(mx, my, 10, 80, 120, 40)) {
            System.out.println("changing level..");
        }

    }
    private boolean hovering(int mx, int my, int x, int y, int width, int height){
        if(mx > x && mx < x + width){
            if(my > y && my < y + height){
                return true;
            }else{
                return false;
            }
        }else return false;
    }
    public void mouseUnclicked(MouseEvent e){

    }

}
