package com.game.src.main;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.LinkedList;

public abstract class GameObject {
    public double x, y;
    private ID objectType;
    LinkedList<GameObject> objects = new LinkedList<GameObject>(); // TODO: check if empty and add "level complete" splash screen if true.

    public Rectangle getBorders() {
        return (new Rectangle((int)x, (int)y + 19, 96, 56));
    }

    public GameObject(double x, double y, ID objectType) {
        this.x = x;
        this.y = y;
        this.objectType = objectType;
    }

    public void render(Graphics graphics) {
    }

    public void tick() {
    }

    public ID getType() {
        return objectType;
    }

    public void setImg(BufferedImage image) {

    }
}
