package com.game.src.main;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.logging.Handler;

public class Input extends KeyAdapter {
    MyGame game;

    public Input(MyGame game) {
        this.game = game;
    }

    public void keyPressed(KeyEvent e){
        game.pressed(e);
    }
    public void keyReleased(KeyEvent e){
        game.released(e);
    }
}
