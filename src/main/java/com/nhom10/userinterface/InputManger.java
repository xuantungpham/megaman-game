package com.nhom10.userinterface;

import java.awt.event.KeyEvent;

import com.nhom10.gameobject.GameWorld;

public class InputManger {

    private GameWorld gameWorld;
    public InputManger(GameWorld gameWorld) {
        this.gameWorld = gameWorld;
    }

    public void processKeyPressed (int keyCode) {
        switch (keyCode) {
            case KeyEvent.VK_UP:
                gameWorld.megaman.jump();
                break;
            case KeyEvent.VK_DOWN:
                gameWorld.megaman.dick();
                break;
            case KeyEvent.VK_LEFT:
                gameWorld.megaman.setDirection(gameWorld.megaman.LEFT_DIR);
                gameWorld.megaman.run(); 
                break;
            case KeyEvent.VK_RIGHT:
                gameWorld.megaman.setDirection(gameWorld.megaman.RIGHT_DIR);
                gameWorld.megaman.run(); 
                break;
            case KeyEvent.VK_ENTER:
                if (gameWorld.state == gameWorld.PAUSEGAME) {
                    if (gameWorld.previousState == gameWorld.GAMEPLAY) {
                        gameWorld.switchState(gameWorld.GAMEPLAY);
                    }
                    else gameWorld.switchState(gameWorld.TUTORIAL);
                }
                if (gameWorld.state == gameWorld.TUTORIAL && gameWorld.storyTutorial >= 1) {
                    if (gameWorld.storyTutorial <= 3) {
                        gameWorld.storyTutorial++;
                        gameWorld.currentSize = 1;
                        gameWorld.textTutorial = gameWorld.texts1[gameWorld.storyTutorial-1];
                    }
                    else {
                        gameWorld.switchState(gameWorld.GAMEPLAY);
                    }
                }
                break;
            case KeyEvent.VK_SPACE:
                gameWorld.megaman.attack();
                break;
        }
    }

    public void processKeyReleased (int keyCode) {
        switch (keyCode) {
            case KeyEvent.VK_UP:
                break;
            case KeyEvent.VK_DOWN:
                gameWorld.megaman.standUp();
                break;
            case KeyEvent.VK_LEFT:
                if (gameWorld.megaman.getSpeedX() < 0) {
                    gameWorld.megaman.stopRun();
                }
                break;
            case KeyEvent.VK_RIGHT:
                if (gameWorld.megaman.getSpeedX() > 0) {
                    gameWorld.megaman.stopRun();
                }
                break;
            case KeyEvent.VK_ENTER:
                break;
            case KeyEvent.VK_SPACE:
                break;
        }
    }
}
