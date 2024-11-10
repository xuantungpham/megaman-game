package com.nhom10.gameobject;

import java.awt.*;
public abstract class Human extends PaticularObject {
    private boolean isJumping;
    private boolean isDicking;

    private boolean isLanding; // đang quỳ xuống (rơi từ trên cao xuống khoảng tiếp đất)

    public Human(float x, float y, float width, float height, float mass, float blood, GameWorld gameWorld) {
        super(x, y, width, height, mass, blood, gameWorld);
        setState(ALIVE);
    }

    public abstract void run();

    public abstract void jump();

    public abstract void dick();

    public abstract void standUp();

    public abstract void stopRun();

    public boolean getIsJumping() {
        return isJumping;
    }

    public void setIsLanding(boolean b) {
        isLanding = b;
    }

    public boolean getIsLanding() {
        return isLanding;
    }

    public void setIsJumping(boolean isJumping) {
        this.isJumping = isJumping;
    }

    public boolean getIsDicking() {
        return isDicking;
    }

    public void setIsDicking(boolean isDicking) {
        this.isDicking = isDicking;
    }

    @Override
    public void Update() {
        super.Update();

        if (getState() == ALIVE || getState() == NOBEHURT) {
            if (!isLanding) {
                setPosX(getPosX() + getSpeedX());
                // kiểm tra xem nhân vật đã đi ra ngoài map chưa
                if (getDirection() == LEFT_DIR && getGameWorld().pysicalMap.haveCollisionWithLeftWall(getBoundForCollisionWithMap()) != null) {
                    Rectangle rectLeftWall = getGameWorld().physicalMap.haveCollisionWithLeftWall(getBoundForCollisionWithMap());
                    setPosX(rectLeftWall.x + rectLeftWall.width + getWidth()/2);
                }
    
                if (getDirection() == RIGHT_DIR && getGameWorld().pysicalMap.haveCollisionWithLeftWall(getBoundForCollisionWithMap()) != null) {
                    Rectangle rectLeftWall = getGameWorld().physicalMap.haveCollisionWithLeftWall(getBoundForCollisionWithMap());
                    setPosX(rectLeftWall.x - getWidth()/2);
                }

                Rectangle boundForCollisionWithMapFuture = getBoundForCollisionWithMap();
                // tiến hành di chuyển nhân vật 1 xíu tránh trường hợp nhân vật chưa chạm đất
                boundForCollisionWithMapFuture.y += (getSpeedY() != 0?getSpeedY():2);
                Rectangle rectLand = getGameWorld().pysicalMap.haveCollisionWithLand(boundForCollisionWithMapFuture);
                Rectangle rectTop = getGameWorld().pysicalMap.haveCollisionWithTop(boundForCollisionWithMapFuture);

                if (rectTop != null) { // chạm trần 
                    setSpeedY(0);
                    setPosY(rectTop.y + getGameWorld().pysicalMap.getTilesize() + getHeight()/2);
                }
                else if (rectLand != null) { // chuyển trạng thái
                    setIsJumping(false);
                    if (getSpeedY() > 0) {
                        setIsLanding(true);
                    }
                    setSpeedY(0);
                    setPosY(rectLand.y - getHeight()/2 - 1);
                }
                else { // trạng thái đang bay
                    setIsJumping(true);
                    setSpeedY(getSpeedY() + getMass());
                    setPosY(getPosY() + getSpeedY()); 
                }
            }
        }
    }
}
