package com.nhom10.gameobject;

import java.awt.Graphics2D;
import java.awt.Rectangle;

import javax.sound.sampled.Clip;

import com.nhom10.effect.Animation;
import com.nhom10.effect.CacheDataLoader;

public class DarkRaise extends ParticularObject {
    private Animation forwardAnim, backAnim;

    private long startTimeToShoot;
    private float x1, x2, y1, y2;
    Clip shooting;

    public DarkRaise (float x, float y, GameWorld gameWorld) {
        super(x, y, 150, 60, 0, 100, gameWorld);
        backAnim = CacheDataLoader.getInstance().getAnimation("darkraise");
        forwardAnim = CacheDataLoader.getInstance().getAnimation("darkraise");
        forwardAnim.flipAllImage();
        startTimeToShoot = 0;
        x1 = x - 100;
        x2 = x + 100;
        y1 = y - 100;
        y2 = y + 100;

        setDamage(10);
        setTimeForNoBehurt(3000*10000000);
        shooting = CacheDataLoader.getInstance().getSound("redeyeshooting");
        setSpeedX(1);
        setSpeedY(1);
    }
    @Override
    public void attack() {
    
        float megaManX = getGameWorld().megaman.getPosX();
        float megaManY = getGameWorld().megaman.getPosY();
        
        float deltaX = megaManX - getPosX();
        float deltaY = megaManY - getPosY();
        
        float speed = 3;
        float a = Math.abs(deltaX/deltaY);
        
        float speedX = (float) Math.sqrt(speed*speed*a*a/(a*a + 1));
        float speedY = (float) Math.sqrt(speed*speed/(a*a + 1));
        
        
        
        Bullet bullet = new DarkRaiseBullet(getPosX(), getPosY(), getGameWorld());
        
        if(deltaX < 0)
            bullet.setSpeedX(-speedX);
        else bullet.setSpeedX(speedX);
        bullet.setSpeedY(speedY);
        bullet.setTeamType(getTeamType());
        getGameWorld().bulletManager.addObject(bullet);

    }
    public void Update(){
        super.Update();
        
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
