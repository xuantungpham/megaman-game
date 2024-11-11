package com.nhom10.gameobject;

import java.awt.Graphics2D;
import java.awt.Rectangle;

import com.nhom10.effect.Animation;
import com.nhom10.effect.CacheDataLoader;

public class RedEyeBullet extends Bullet {
    private Animation forwarBulletAnim, backBulletAnim;

    public RedEyeBullet (float x, float y, GameWorld gameWorld) {
        super(x, y, 30, 30, 1.0f, 10, gameWorld);
        forwarBulletAnim = CacheDataLoader.getInstance().getAnimation("redeyebullet");
        backBulletAnim = CacheDataLoader.getInstance().getAnimation("redeyebullet");
        backBulletAnim.flipAllImage();
    }

    @Override
    public Rectangle getBoundForCollisionWithEnemy() {
        return getBoundForCollisionWithMap();
    }

    @Override
    public void draw(Graphics2D g2) {
        if (getSpeedX() > 0) {
            forwarBulletAnim.Update(System.nanoTime());
            forwarBulletAnim.draw((int) (getPosX() - getGameWorld().camera.getPosX()), (int) getPosY() - (int) getGameWorld().camera.getPosY(), g2);
        }
        else {
            backBulletAnim.Update(System.nanoTime());
            backBulletAnim.draw((int) (getPosX() - getGameWorld().camera.getPosX()), (int) getPosY() - (int) getGameWorld().camera.getPosY(), g2);
        }
    }
    @Override 
    public void Update() {
        super.Update();
    }

    @Override
    public void attack() {}
}
