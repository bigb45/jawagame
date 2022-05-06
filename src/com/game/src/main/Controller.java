package com.game.src.main;

import java.awt.*;
import java.util.Iterator;
import java.util.LinkedList;
import java.lang.Math;

public class Controller {
    public LinkedList<GameObject> objects = new LinkedList<>();

    MyGame game;
    Assets assets;
    GameObject obj;
    //private Random rand;
    public Controller(MyGame game, Assets assets) {

        this.game = game;
        this.assets = assets;
//        rand = new Random();

//        for(int i = 0; i < 768; i += 128){
//            addObj(new Cat(i, 470, assets, ID.Cat, this));
//        } for(int i = 0; i < 576; i += 64){
//            addObj(new Cat(i, 560, assets, ID.Cat, this));
//        }

    }

    public void tick(){
        for (int i = 0; i < objects.size(); i++) {
            GameObject object = objects.get(i);
            obj = object;
            obj.tick();
        }
        int size = getCount(ID.Cat);
        MyGame.KILL_COUNT = MyGame.ENEMY_COUNT - size;
    }
    public void render(Graphics graphics){

        for (int i = 0; i < objects.size(); i++) {
            obj = objects.get(i);
            if (obj != null)
                obj.render(graphics);
        }


    }
    public void addObj(GameObject o){
        objects.add(o);
//        System.out.println("added object " + o.getType());
    }
    public void removeObj(GameObject o){
        objects.remove(o);
//        System.out.println("removed object " + o.getType());
        if(o.getType() == ID.Cat){
            MyGame.KILL_COUNT++;
            // System.out.println("body count is " + MyGame.KILL_COUNT);
        }

    }
    public int getCount(ID type){
        int count = 0;

        for(int i = 0; i < objects.size(); i++){
            GameObject temp = objects.get(i);
            if(temp.getType() == type){
                count++;
            }
        }
        return count;
    }
    public void addEnemy(int count){
        for (int i = 0; i < count; i++){
            addObj(new Cat((getRand(1, 12)) * 48, (getRand(10, 18))* 48, assets, ID.Cat, this));
        }
    }

    public int getRand(int a, int b){
            return (int) ((Math.random() * (a - b)) + b);
        }

    public void runAnim(){
        addObj(new Cat(400, 300, assets, ID.Cat, this));
        addObj(new Cat(470, 350, assets, ID.Cat, this));
        for(int i = 0; i < objects.size(); i++){
            GameObject temp = objects.get(i);


        }
    }



}
