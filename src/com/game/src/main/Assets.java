package com.game.src.main;

import com.game.src.main.MyGame;
import com.game.src.main.Sprites;

import java.awt.image.BufferedImage;

public class Assets {
    public BufferedImage chicken, goldenEgg, silverEgg, cat, eggCracked, eggedCat, halfHeart, fullHeart, emptyHeart;
    private Sprites sprites;

    public Assets(MyGame game) {
        sprites = new Sprites(game.getSprites());
        getTextures();
    }
    public void getTextures(){
        chicken = sprites.fetch(2,2, 96, 96);
        goldenEgg  = sprites.fetch(2,1, 48, 48);
       // silverEgg  = sprites.fetch(3,1, 48, 48);
        cat = sprites.fetch(4,1, 48, 48);
        eggCracked = sprites.fetch(1, 2, 48, 48);
        eggedCat = sprites.fetch(5,1, 48, 48);
        fullHeart = sprites.fetch(1,4, 48, 48);
        halfHeart = sprites.fetch(2,4, 48, 48);
        emptyHeart = sprites.fetch(3,4, 48, 48);
    }
}
