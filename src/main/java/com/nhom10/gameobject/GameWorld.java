package com.nhom10.gameobject;

import java.awt.Graphics2D;

public class GameWorld {

    Megaman megaman;
    PhysicalMap physicalMap;

    public GameWorld(){
        megaman = new Megaman(300,300,100, 100, 0.1f);
        physicalMap = new PhysicalMap(0,0);
    }

    public void Update(){

        megaman.update();

    }

    public void Render(Graphics2D g2){

        megaman.draw(g2);
        PhysicalMap.draw(g2);
    }
}
