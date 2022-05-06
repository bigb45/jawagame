package com.game.src.main;

import java.awt.image.BufferedImage;

public class Sprites {
    private BufferedImage img;
    public Sprites(BufferedImage img){
        this.img = img;
    }
    public BufferedImage fetch(int col, int row, int width, int height){
        BufferedImage subImg = img.getSubimage((col * 48) - 48,
                (row * 48) - 48, width, height);
        return subImg;
    }

}
