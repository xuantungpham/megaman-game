package com.nhom10.gameobject;

import java.awt.Graphics2D;
import java.awt.Rectangle;

import javax.sound.sampled.Clip;

import com.nhom10.effect.Animation;
import com.nhom10.effect.CacheDataLoader;

public class RobotR extends ParticularObject {
    private Animation forwardAnim, backAnim;

    private long startTimeToShoot;
    private float x1, x2, y1, y2;
    Clip shooting;

    public RobotR (float x, float y, GameWorld gameWorld) {
        super(x, y, 130, 90, 1.0f, 100, gameWorld);
        backAnim = CacheDataLoader.getInstance().getAnimation("robotR");
        forwardAnim = CacheDataLoader.getInstance().getAnimation("robotR");
        forwardAnim.flipAllImage();
        startTimeToShoot = 0;
        x1 = x - 300;
        x2 = x + 100;
        y1 = y - 50;
        y2 = y + 20;
        setSpeedX(1);
        setSpeedY(1);
        setDamage(10);
        setTimeForNoBehurt(3000*10000000);
        shooting = CacheDataLoader.getInstance().getSound("robotRshooting");
    }

    @Override
    public void attack() {  
        shooting.setFramePosition(0);
        shooting.start();
        Bullet bullet = new RobotRBullet(getPosX(), getPosY(), getGameWorld());
        
        if(getDirection()==LEFT_DIR)
            bullet.setSpeedX(5);
        else bullet.setSpeedX(-5);
        bullet.setTeamType(getTeamType());
        getGameWorld().bulletManager.addObject(bullet);

    }

    
    public void Update(){
        super.Update();
        
        if(getPosX() - getGameWorld().megaman.getPosX() > 0) setDirection(ParticularObject.RIGHT_DIR);
        else setDirection(ParticularObject.LEFT_DIR);
        
        if(getPosX() < x1)
            setSpeedX((float) (0.5 + Math.random() * 0.5));
        else if(getPosX() > x2)
            setSpeedX((float) (0.5 + Math.random() * 0.5)*(-1));

        if(getPosY() < y1)
            setSpeedY((float) (0.5 + Math.random() * 0.5));
        else if(getPosX() > y2)
            setSpeedY((float) (0.5 + Math.random() * 0.5)*(-1));
        
        setPosX(getPosX() + getSpeedX());
        setPosY(getPosY() + getSpeedY());
        if(System.nanoTime() - startTimeToShoot > 1000*10000000*1.5){
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
        if(!isObjectOutOfCameraView()){
            if(getState() == NOBEHURT && (System.nanoTime()/10000000)%2!=1){
                // plash...
            }else{
                if(getDirection() == LEFT_DIR){
                    backAnim.Update(System.nanoTime());
                    backAnim.draw((int) (getPosX() - getGameWorld().camera.getPosX()), 
                            (int)(getPosY() - getGameWorld().camera.getPosY()), g2);
                }else{
                    forwardAnim.Update(System.nanoTime());
                    forwardAnim.draw((int) (getPosX() - getGameWorld().camera.getPosX()), 
                            (int)(getPosY() - getGameWorld().camera.getPosY()), g2);
                }
            }
        }
        // drawBoundForCollisionWithEnemy(g2);
    }
}
