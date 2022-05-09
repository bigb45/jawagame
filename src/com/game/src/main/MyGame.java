package com.game.src.main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class MyGame extends Canvas implements Runnable {
    /// globals
    public static int BULLET_SPEED = -1;
    public static int EGG_LIMIT = 3;
    public static int FALLING_SPEED = 1;
    public static final int GAME_WIDTH = 350;
    public static final int GAME_HEIGHT = GAME_WIDTH/12 * 9 - 10;
    public static final int SCALE = 2;
    public static final  String TITLE = "COD: CW";
    public static int KILL_COUNT = 0;
    public int ENEMY_COUNT = 1;


    private boolean gameRunning = false;
    private Thread thread;
    private BufferedImage img = new BufferedImage(GAME_WIDTH, GAME_HEIGHT,
            BufferedImage.TYPE_INT_RGB);
    private BufferedImage sprite = null;
    private BufferedImage background = null;
    private BufferedImage menuBackground = null;
    private Chicken chicken;
    private Controller controller, animController;
    private Assets assets;
    private String backgroundPath = "resources/bgEZ.png";
    private String menuBackgroundPath = "resources/menuBackground.png";
    private MainMenu menu;
    private boolean paused = false;
    public int level = 1;
    public  enum GAME_STATE{

        level1,
        level2,
        level3,
        paused,
        Menu,
        levelWon,
        levelLost
    }
    public GAME_STATE state = GAME_STATE.Menu;  // Game state
    public GAME_STATE prevState = null;
    public void init(){
        requestFocus();
        BImgLoader loader = new BImgLoader();
        try{
            background = loader.loadImage(backgroundPath);
            menuBackground = loader.loadImage(menuBackgroundPath);
            sprite = loader.loadImage("resources/sprites.png");
        }catch(IOException e){
            e.printStackTrace();
        }
        addKeyListener(new Input(this));
        assets = new Assets(this);
        controller = new Controller(this, assets);
        animController = new Controller(this, assets);
        menu = new MainMenu(this, animController, assets, chicken);
        this.addMouseListener(menu);

        chicken = new Chicken(150, 20, assets, ID.Chicken, controller, this);

    }
    public void init_game(int eCount){
        controller.addEnemy(eCount);
    }
    public void reset(){
        for(int i = 0; i < controller.objects.size(); i++)

            controller.removeObj(controller.objects.get(i));
        ENEMY_COUNT = 10;
        EGG_LIMIT = 6;
        KILL_COUNT = 0;
        chicken.setHeartCount(6);

    }
    private synchronized void start(){
        if(gameRunning)
            return;
        gameRunning = true;
        thread = new Thread(this);
        thread.start();
    }
    private synchronized void stop(){
        if(!gameRunning)
            return;
        gameRunning = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.exit(1);
    }
    public void run(){
        init();
        long previousTime = System.nanoTime();
        double ticks = 60.0;
        double nano = 1000000000.0/ticks;
        double delta = 0;
        long millis = System.currentTimeMillis();
        int frameCount = 0;
        int updates = 0;
        while(gameRunning){
            long current = System.nanoTime();
            delta += (current - previousTime)/nano;
            previousTime = current;
            if(delta >= 1){
                tick();
                updates++;
                delta--;
            }
            render();
            frameCount++;
            if(System.currentTimeMillis() - millis > 1000){
                millis += 1000;
//                System.out.println(updates + " ticks, " + frameCount); // FPS counter
                frameCount = 0;
                updates = 0;
            }
        }
        stop();
    }
    private void tick(){
        if(!paused) {
            if (state == GAME_STATE.level1 || state == GAME_STATE.level2 || state == GAME_STATE.level3) {
                chicken.tick();
                controller.tick();
            } else if (state == GAME_STATE.Menu) {
                animController.runAnim();
                chicken.runAnim();
            }
            if (KILL_COUNT == ENEMY_COUNT - 3) {
                state = GAME_STATE.levelWon;
            }
            if(state == GAME_STATE.levelWon || state == GAME_STATE.levelLost)
                reset();
        }
    }
    public void setState(GAME_STATE newState){
        state = newState;
    }
    private void render(){
        BufferStrategy bStrat = this.getBufferStrategy();

        if(bStrat == null) {
            createBufferStrategy(3);
            return;
        }
         Graphics graphics = bStrat.getDrawGraphics();
        graphics.drawImage(img, 0, 0, getWidth(), getHeight(), this);
        graphics.drawImage(background, 0,0, null);
        if(!paused) {
            if (state == GAME_STATE.level1 || state == GAME_STATE.level2 || state == GAME_STATE.level3){
                chicken.render(graphics);
                controller.render(graphics);
                graphics.setColor(Color.black);
                graphics.setFont(new Font("Default", Font.BOLD, 14));
                graphics.drawString("Your score: " + (KILL_COUNT * 10), 10, 60);

            } else if (state == GAME_STATE.Menu) {
                menu.render(graphics, "menu");
                chicken.render(graphics);
                animController.render(graphics);
            } else if (state == GAME_STATE.levelLost) {
                menu.render(graphics, "end");
            } else if (state == GAME_STATE.levelWon) {
                menu.render(graphics, "won");
            }
        }else
            menu.render(graphics, "paused");
        graphics.dispose();
        bStrat.show();
    }
    public void pressed(KeyEvent e){
        int key = e.getKeyCode();
        if(key == KeyEvent.VK_RIGHT){
            chicken.setMoving(true);
            if(!chicken.isDirection())
                chicken.setVelX(0);
            chicken.setDirection(true);
        }else if(key == KeyEvent.VK_LEFT){
            chicken.setMoving(true);
            if(chicken.isDirection())
                chicken.setVelX(0);
            chicken.setDirection(false);
        }
        if(key == KeyEvent.VK_SPACE){
//            if(controller.getCount(ID.Egg) < EGG_LIMIT )
                controller.addObj(new Egg(chicken.getX()+38, chicken.getY()+70, assets, ID.Egg, controller));
        }
        if(key == KeyEvent.VK_ESCAPE && state != GAME_STATE.Menu && state != GAME_STATE.levelLost && state != GAME_STATE.levelWon ){
            prevState = state;
           paused = !paused;
        }

    }
    public void released(KeyEvent e){
        int key = e.getKeyCode();
        if(key == KeyEvent.VK_RIGHT){
            chicken.setMoving(false);
        }else if(key == KeyEvent.VK_LEFT){
            chicken.setMoving(false);
        }

    }
    public static void main(String args[]){
        MyGame game = new MyGame();
        game.setPreferredSize(new Dimension(GAME_WIDTH * SCALE, GAME_HEIGHT * SCALE));
        game.setMaximumSize(new Dimension(GAME_WIDTH * SCALE, GAME_HEIGHT * SCALE));
        game.setMinimumSize(new Dimension(GAME_WIDTH * SCALE, GAME_HEIGHT * SCALE));
        JFrame frame = new JFrame(game.TITLE);
        frame.add(game);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        game.start();
    }
    public BufferedImage getSprites(){
        return sprite;
    }
}
