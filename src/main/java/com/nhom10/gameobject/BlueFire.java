package com.nhom10.gameobject;

import java.awt.Graphics2D;
import java.awt.Rectangle;

import com.nhom10.effect.Animation;
import com.nhom10.effect.CacheDataLoader;

public class BlueFire extends Bullet{
    private Animation forwarBullerAnim, backBulletAnim;

    public BlueFire (float x, float y, GameWorld gameWorld) {
        super(x, y, 60, 30, 1.0f, 10, gameWorld);
        forwarBullerAnim = CacheDataLoader.getInstance().getAnimation("bluefire");
        backBulletAnim = CacheDataLoader.getInstance().getAnimation("bluefire");
        backBulletAnim.flipAllImage(); // daor nguoc hinh anh
    }

    @Override
    public Rectangle getBoundForCollisionWithEnemy() {
        return getBoundForCollisionWithMap();
    }

    @Override
    public void draw(Graphics2D g2) {
        if (getSpeedX() > 0) {
            if (!forwarBullerAnim.isIgnoreFrame(0) && forwarBullerAnim.getCurrentFrame() == 3) {
                forwarBullerAnim.setIgnoreFrame(0);
                forwarBullerAnim.setIgnoreFrame(1);
                forwarBullerAnim.setIgnoreFrame(2);
            }

            forwarBullerAnim.Update(System.nanoTime());
            forwarBullerAnim.draw((int) (getPosX() - getGameWorld().camera.getPosX()), (int) getPosY() - (int) getGameWorld().camera.getPosY(), g2);
        }
        else {
            if (!backBulletAnim.isIgnoreFrame(0) && backBulletAnim.getCurrentFrame() == 3) {
                backBulletAnim.setIgnoreFrame(0);
                backBulletAnim.setIgnoreFrame(1);
                backBulletAnim.setIgnoreFrame(2);
            }
            backBulletAnim.Update(System.nanoTime());
            backBulletAnim.draw((int) (getPosX() - getGameWorld().camera.getPosX()), (int) getPosY() - (int) getGameWorld().camera.getPosY(), g2);
        }
    }

    @Override
    public void Update() {
        super.Update();
        // if (forwarBullerAnim.isIgnoreFrame(0) || backBulletAnim.isIgnoreFrame(0)) {
        //     setPosX(getPosX() + getSpeedX());
        // }
    }
    @Override 
    public void attack(){};
}
