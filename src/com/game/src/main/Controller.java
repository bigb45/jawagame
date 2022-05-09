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
//    private int objCount = 0;
    public Controller(MyGame game, Assets assets) {
        this.game = game;
        this.assets = assets;

    }

    public void tick(){
        for (int i = 0; i < objects.size(); i++) {
           try {
               objects.get(i).tick();
           }catch(NullPointerException e){
                System.out.println("null exception in controller");
                continue;
           }
        }
    }
    public void render(Graphics graphics){
        if(objects != null && objects.size() > 0) {
            for (int i = 0; i < objects.size(); i++) {
                try {
                    obj = objects.get(i);
                    obj.render(graphics);
                }catch (NullPointerException e){

                }
            }
        }
    }
    public void addObj(GameObject o){

//       objects.add(o);
        objCount++;
//        System.out.println("added object " + o.getType() + "#" + objCount);
    }
    public boolean removeObj(GameObject o){
//        objCount--;
//        System.out.println("removed object " + o.getType() + "#" + objCount);
        return objects.remove(o);
    }
//    public int getCount(ID type){
//        int count = 0;
//
//        for(int i = 0; i < objects.size(); i++){
//            GameObject temp = objects.get(i);
//            if(temp != null && temp.getType() == type){
//                count++;
//            }
//        }
//        return count;
//    }
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
//        for(int i = 0; i < objects.size(); i++){
//            GameObject temp = objects.get(i);
//        }
    }



}
