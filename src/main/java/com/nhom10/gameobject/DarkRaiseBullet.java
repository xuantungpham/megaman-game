package com.nhom10.gameobject;

import java.awt.Graphics2D;
import java.awt.Rectangle;

import com.nhom10.effect.Animation;
import com.nhom10.effect.CacheDataLoader;

public class DarkRaiseBullet extends Bullet {
    private float startTimeToShoot;
    private Animation forwarBulletAnim, backBulletAnim;

    public DarkRaiseBullet (float x, float y, GameWorld gameWorld) {
        super(x, y, 30, 30, 1.0f, 10, gameWorld);
        forwarBulletAnim = CacheDataLoader.getInstance().getAnimation("darkraisebullet");
        backBulletAnim = CacheDataLoader.getInstance().getAnimation("darkraisebullet");
        backBulletAnim.flipAllImage(); // lat anh
        startTimeToShoot = 0;
        setDamage(10);
        setTimeForNoBehurt(3000*10000000);
    }

    @Override
    public Rectangle getBoundForCollisionWithEnemy() {
        return getBoundForCollisionWithMap();
    }

    @Override
    public void draw(Graphics2D g2) {
        if (getSpeedX() > 0) {
            forwarBulletAnim.Update(System.nanoTime());
            forwarBulletAnim.draw((int) (getPosX() - getGameWorld().camera.getPosX()), (int) (getPosY() - getGameWorld().camera.getPosY()), g2);
        }
        else {
            backBulletAnim.Update(System.nanoTime());
            backBulletAnim.draw((int) (getPosX() - getGameWorld().camera.getPosX()), (int) (getPosY() - getGameWorld().camera.getPosY()), g2);
        }
    }

    @Override
    public void Update() {
        if (System.nanoTime() - startTimeToShoot > 9000*10000000) {
            super.Update();
        }
    }

    @Override
    public void attack() {}
}
