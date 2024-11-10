package com.nhom10.gameobject;

public abstract class GameObject {
    private float posX;
    private float posY;// tọa độ vị trí của object trong không gian 2D
    private GameWorld gameWorld;

    public GameObject(float posX, float posY, GameObject gameObject) {
        this.posX = posX;
        this.posY = posY;
        this.gameWorld = gameWorld;
    }
    public float getPosX() {
        return posX;
    }
    public void setPosX(float posX) {
        this.posX = posX;
    }
    public float getPosY() {
        return posY;
    }
    public void setPosY(float posY) {
        this.posY = posY;
    }
    public GameWorld getGameWorld() {
        return gameWorld;
    }
    public void setGameWorld(GameWorld gameWorld) {
        this.gameWorld = gameWorld;
    }

    public abstract void Update(); // abstract để khi kế thừa thì phải định nghĩa lại hàm Update vì mỗi đối tượng tượng có những hành động riêng

}
