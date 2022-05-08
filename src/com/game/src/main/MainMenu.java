package com.game.src.main;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import com.game.src.main.MyGame.GAME_STATE;

public class MainMenu extends MouseAdapter {
    private BImgLoader loader = new BImgLoader();
    private BufferedImage menuBackground = null;
    private BufferedImage levelLostbg = null;
    private BufferedImage levelWonbg = null;
    private String backgroundPath = "resources/menuBackground.png";
    private String lostPath = "resources/bgHARD.png";
    private String wonPath = "resources/menuBackground.png";
    private int x = MyGame.GAME_WIDTH, y = MyGame.GAME_HEIGHT,
            scale = MyGame.SCALE, initialY = 70, offset = 50;
    private MyGame game;
    private Controller controller;
    private Assets assets;
    private Chicken chicken;
    public MainMenu(MyGame game, Controller controller, Assets assets, Chicken chicken){

        try {
            menuBackground = loader.loadImage(backgroundPath);
            levelLostbg = loader.loadImage(lostPath);
            levelWonbg = loader.loadImage(wonPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.game = game;
        this.controller = controller;
        this.assets = assets;
        this.chicken = chicken;
    }

    
    public void render(Graphics graphics, String screen){
        graphics.drawImage(menuBackground, 0, 0, x * scale, y * scale, null);

        if(screen == "menu") {
            graphics.setColor(Color.black);
            graphics.setFont(new Font("Default", Font.PLAIN, 38));
            graphics.drawString("Call of Duty Chicken Warfare", 200, 40);
            graphics.setColor(new Color(100, 120, 120, 200));
            graphics.fillRoundRect(10, initialY - 30, 80, 40, 20, 20);
            graphics.fillRoundRect(10, initialY - 30 + offset, 80, 40, 20, 20);
            graphics.fillRoundRect(10, initialY - 30 + 2 * offset, 80, 40, 20, 20);
            graphics.fillRoundRect(10, initialY - 30 + 3 * offset, 40, 40, 20, 20);
            graphics.setColor(Color.yellow);
            graphics.setFont(new Font("Default", Font.PLAIN, 24));
            graphics.drawString("Level 1", 10, initialY); // lvl 1
            graphics.drawString("Level 2", 10, initialY + offset); // lvl 2
            graphics.drawString("Level 3", 10, initialY + 2 * offset); // lvl 3
            graphics.drawString("Exit", 10, initialY + 3 * offset);
        }else if(screen == "end"){
            graphics.setColor(Color.white);
            graphics.drawImage(levelLostbg, 0, 0, x * scale, y * scale, null);
            graphics.setFont(new Font("Default", Font.PLAIN, 40));
            graphics.drawString("Game Over!", 240, 200);
            graphics.setColor(new Color(100, 120, 120, 200));

            graphics.fillRoundRect(200, 260, 120, 40, 20, 20);
            graphics.fillRoundRect(340, 260, 120, 40, 20, 20);
            graphics.setFont(new Font("Default", Font.PLAIN, 24));
            graphics.setColor(Color.white);
            graphics.drawString("Quit", 235, 290);
            graphics.drawString("Menu", 370, 290);
        }else if(screen == "won"){
            graphics.setColor(Color.white);
            graphics.drawImage(levelWonbg, 0, 0, x * scale, y * scale, null);
            graphics.setFont(new Font("Default", Font.PLAIN, 40));
            graphics.drawString("Level Complete!", 200, 200);
            graphics.setColor(new Color(100, 120, 120, 200));

            graphics.fillRoundRect(200, 260, 120, 40, 20, 20);
            graphics.fillRoundRect(340, 260, 120, 40, 20, 20);
            graphics.setFont(new Font("Default", Font.PLAIN, 24));
            graphics.setColor(Color.white);
            graphics.drawString("Quit", 220, 290);
            graphics.drawString("Menu", 370, 290);
        }else if(screen == "paused"){
            graphics.setColor(new Color(0x3F575757, true));
            graphics.fillRect(0, 0, game.getWidth(), game.getHeight());
            graphics.setColor(Color.black);
            graphics.setFont(new Font("Default", Font.PLAIN, 40));
            graphics.drawString("Paused", 270, 100);
            graphics.setFont(new Font("Default", Font.PLAIN, 20));
            graphics.drawString("Press Esc again to continue", 210, 200);
        }
    }
    public void tick(){

    }
    public void mouseClicked(MouseEvent e){
        int mx = e.getX();
        int my = e.getY();
        if(game.state == GAME_STATE.Menu) {
            if (hovering(mx, my, 10, 40, 80, 40)) { // lvl 1
//                System.out.println("Level 1");
                game.level = 1;
                MyGame.EGG_LIMIT = 5;
                game.ENEMY_COUNT = 14;
                game.init_game(10);
                game.state = GAME_STATE.level1;
            }
            if (hovering(mx, my, 10, 90, 80, 40)) { // lvl 2
//                System.out.println("Level 2");
                game.level = 2;
                MyGame.EGG_LIMIT = 4;
                game.ENEMY_COUNT = 18;
                game.init_game(15);
                game.state = GAME_STATE.level2;

            }
            if (hovering(mx, my, 10, 140, 80, 40)) { // lvl 3
//                System.out.println("Level 3");
                game.level = 3;
                MyGame.EGG_LIMIT = 3;
                game.ENEMY_COUNT = 28;
                game.init_game(28);
                game.state = GAME_STATE.level3;
            }
            if (hovering(mx, my, 10, 190, 80, 40)) {
//                System.out.println("exit");
                System.exit(0);
            }
        }

        else if (game.state == GAME_STATE.levelLost || game.state == GAME_STATE.levelWon) { // lost/won screen
            game.reset();
            if (hovering(mx, my, 200, 260, 120, 40)) {
//                System.out.println("Exiting..");
                System.exit(0);
            }
            if (hovering(mx, my, 340, 260, 120, 40)) { // MM button
//                System.out.println("Main Menu..");
                game.level = 1;
                MyGame.EGG_LIMIT = 4;
                game.ENEMY_COUNT = 15;
                MyGame.KILL_COUNT = 0;
                game.state = GAME_STATE.Menu;

            }
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
