package com.nhom10.gameobject;

import java.awt.Graphics2D;
import java.awt.Rectangle;

import com.nhom10.effect.Animation;
import com.nhom10.effect.CacheDataLoader;

public class RobotRBullet extends Bullet{
    
    private Animation forwarBullerAnim, backBullerAnim;

    public RobotRBullet (float x, float y, GameWorld gameWorld) {
        super(x, y, 60, 30, 1.0f, 10, gameWorld);
        forwarBullerAnim = CacheDataLoader.getInstance().getAnimation("robotRbullet");
        backBullerAnim = CacheDataLoader.getInstance().getAnimation("robotRbullet");
        backBullerAnim.flipAllImage();
    }

    @Override
    public Rectangle getBoundForCollisionWithEnemy() {
        return getBoundForCollisionWithMap();
    }

    @Override
    public void draw (Graphics2D g2) {
        if (getSpeedX() > 0) {
            forwarBullerAnim.Update(System.nanoTime());
            forwarBullerAnim.draw((int) (getPosX() - getGameWorld().camera.getPosX()), (int) (getPosY() - getGameWorld().camera.getPosY()), g2);
        }
        else {
            backBullerAnim.Update(System.nanoTime());
            backBullerAnim.draw((int) (getPosX() - getGameWorld().camera.getPosX()), (int) (getPosY() - getGameWorld().camera.getPosY()), g2);
        }
    }

    @Override
    public void Update() {
        super.Update(); 
    }

    @Override
    public void attack() {}
}
