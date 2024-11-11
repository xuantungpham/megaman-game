package com.nhom10.gameobject;

import com.nhom10.effect.Animation;
import java.awt.*;

public abstract class ParticularObject extends GameObject {
    
    public static final int LEAGUE_TEAM = 1; // trong game có 2 phe 
    public static final int ENEMY_TEAM = 2;

    // hướng nhân vật
    public static final int LEFT_DIR = 0;
    public static final int RIGHT_DIR = 1;

    // trạng thái
    public static final int ALIVE = 0; // sống
    public static final int BEHURT = 1; // bị đau
    public static final int FEY = 2; // sắp chết
    public static final int DEATH = 3; // chết
    public static final int NOBEHURT = 4; // vô địch
    private int state = ALIVE;

    private float width;
    private float height;
    private float mass;
    private float speedX;
    private float speedY;
    private int blood;

    private int damage;

    private int direction; // hướng 

    protected Animation behurtForwardAnim, behurtBackAnim;

    private int teamType;

    private long startTimeNoBeHurt;
    private long timeForNoBeHurt;

    public ParticularObject(float x, float y, float width, float height, float mass, int blood, GameWorld gameWorld) {
        super(x, y, gameWorld);
        setWidth(width);
        setHeight(height);
        setMass(mass);
        setBlood(blood);
    }
    public void setTimeForNoBehurt(long time) {
        timeForNoBeHurt = time;
    }
    public long getTimeForNoBeHurt() {
        return timeForNoBeHurt;
    }
    public void setState(int state) {
        this.state = state;
    }
    public int getState() {
        return state;
    }
    public void setDamage(int damage) {
        this.damage = damage;
    }
    public int getDamage() {
        return damage;
    }
    public void setTeamType(int team) {
        teamType = team;
    }
    public int getTeamType() {
        return teamType;
    }
    public void setMass(float mass) {
        this.mass = mass;
    }
    public float getMass() {
        return mass;
    }
    public void setSpeedX(float speedX) {
        this.speedX = speedX;
    }
    public float getSpeedX() {
        return speedX;
    }
    public void setSpeedY(float speedY) {
        this.speedY = speedY;
    }
    public float getSpeedY() {
        return speedY;
    }

    public void setBlood(int blood) {
        if(blood>=0)
            this.blood = blood;
        else this.blood = 0;
    }
    public int getBlood() {
        return blood;
    }
    public void setWidth(float width) {
        this.width = width;
    }
    public float getWidth() {
        return width;
    }
    public void setHeight(float height) {
        this.height = height;
    }
    public float getHeight() {
        return height;
    }
    public void setDirection(int dir) {
        direction = dir;
    }
    public int getDirection() {
        return direction;
    }
    public abstract void attack();//viết hàm abstract để sau này định nghĩa sau

    public boolean isObjectOutOfCameraView() {
        if (getPosX() - getGameWorld().camera.getPosX() > getGameWorld().camera.getWidthView() ||
            getPosX() - getGameWorld().camera.getPosX() < -50 ||
            getPosY() - getGameWorld().camera.getPosY() > getGameWorld().camera.getHeightView() ||
            getPosY() - getGameWorld().camera.getPosY() < -50) return true;
        return false;
    }
    public Rectangle getBoundForCollisionWithMap() {
        Rectangle bound = new Rectangle();
        bound.x = (int) (getPosX() - (getWidth()/2));
        bound.y = (int) (getPosY() - (getHeight()/2));
        bound.width = (int) getWidth();
        bound.height = (int) getHeight();
        return bound;
    }

    public void beHurt(int damgeEat) {
        setBlood(getBlood() - damgeEat);
        state = BEHURT;
        hurtingCallback();
    }

    @Override
    public void Update() {
        switch (state) {
            case ALIVE:
                ParticularObject object = getGameWorld().particularObjectManager.getCollisionWidthEnemyObject(this);
                if (object != null) {
                    if (object.getDamage() > 0) {
                        beHurt(object.getDamage());
                    }
                }
                break;
            
            case BEHURT:
                if (behurtBackAnim == null) {
                    state = NOBEHURT;
                    startTimeNoBeHurt = System.nanoTime();
                    if (getBlood() == 0) {
                        state = DEATH;
                    }
                }
                else {
                    behurtForwardAnim.Update(System.nanoTime());
                    if (behurtForwardAnim.isLastFrame()) {
                        behurtForwardAnim.reset();
                        state = NOBEHURT;
                        if (getBlood() == 0) {
                            state = DEATH;
                        }
                        startTimeNoBeHurt = System.nanoTime();
                    }
                }
                break;

            case FEY:
                state = DEATH;
                break;
            case NOBEHURT:
                // System.out.println("state = nobehurt, Blood: " + getBlood());
                if (System.nanoTime() - startTimeNoBeHurt > timeForNoBeHurt) {
                    state = ALIVE;
                }
                break;
        }
    }
    public void drawBoundForCollisionWithMap (Graphics2D g2) {
        Rectangle rect = getBoundForCollisionWithMap();
        g2.setColor(Color.BLUE);
        g2.drawRect(rect.x - (int) getGameWorld().camera.getPosX(), rect.y - (int) getGameWorld().camera.getPosY(), rect.width, rect.height);
    }
    public void drawBoundForCollisionWithEnemy(Graphics2D g2) {
        Rectangle rect = getBoundForCollisionWithEnemy();
        g2.setColor(Color.RED);
        g2.drawRect(rect.x - (int) getGameWorld().camera.getPosX(), rect.y - (int) getGameWorld().camera.getPosY(), rect.width, rect.height);
    }

    public abstract Rectangle getBoundForCollisionWithEnemy();
    public abstract void draw(Graphics2D g2);
    public void hurtingCallback(){};
}
