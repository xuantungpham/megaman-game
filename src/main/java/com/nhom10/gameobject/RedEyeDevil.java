package com.nhom10.gameobject;

import java.awt.Graphics2D;
import java.awt.Rectangle;

import javax.sound.sampled.Clip;

import com.nhom10.effect.Animation;
import com.nhom10.effect.CacheDataLoader;

public class RedEyeDevil extends ParticularObject {
    
    private Animation forwarAnim, backAnim;

    private long startTimeToShoot;
    Clip shooting;

    public RedEyeDevil(float x, float y, GameWorld gameWorld) {
        super(x, y, 127, 89, 0, 100, gameWorld);
        backAnim = CacheDataLoader.getInstance().getAnimation("redeye");
        forwarAnim = CacheDataLoader.getInstance().getAnimation("redeye");
        forwarAnim.flipAllImage();
        startTimeToShoot = 0;
        setDamage(10);
        setTimeForNoBehurt(3000*10000000);
        shooting = CacheDataLoader.getInstance().getSound("redeyeshooting");
    }

    @Override 
    public void attack() {
        shooting.setFramePosition(0);
        shooting.start();
        Bullet bullet = new RedEyeBullet(getPosX(), getPosY(), getGameWorld());
        if (getDirection() == LEFT_DIR) {
            bullet.setSpeedX((-8));
        }
        else {
            bullet.setSpeedX(8);
        }
        bullet.setTeamType(getTeamType());
        getGameWorld().bulletManager.addObject(bullet); 
    }

    public void Update() {
        super.Update();
        if (System.nanoTime() - startTimeToShoot > 1000*10000000) {
            attack();
            startTimeToShoot = System.nanoTime();
        }
    }

    @Override
    public Rectangle getBoundForCollisionWithEnemy() {
        Rectangle rect = getBoundForCollisionWithMap();
        rect.x += 20;
        rect.width -= 40;
        return rect;
    }

    @Override
    public void draw(Graphics2D g2) {
        if (!isObjectOutOfCameraView()) {
            // drawBoundForCollisionWithEnemy(g2);
            if (getState() == NOBEHURT && (System.nanoTime()/10000000)%2 != 1) {
                // plash..
            }
            else {
                if (getDirection() == LEFT_DIR) {
                    backAnim.Update(System.nanoTime());
                    backAnim.draw((int) (getPosX() - getGameWorld().camera.getPosX()), (int) (getPosY() - getGameWorld().camera.getPosY()), g2);
                }
                else {
                    forwarAnim.Update(System.nanoTime());
                    forwarAnim.draw((int) (getPosX() - getGameWorld().camera.getPosX()), (int) (getPosY() - getGameWorld().camera.getPosY()), g2);
                }
            }
        }
    }
}
