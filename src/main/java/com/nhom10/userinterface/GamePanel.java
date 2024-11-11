package com.nhom10.userinterface;

import javax.swing.*;

import com.nhom10.gameobject.GameWorld;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GamePanel extends JPanel implements Runnable, KeyListener {
    
    GameWorld gameWorld;
    InputManger inputManger;
    Thread gameThread;
    
    public boolean isRunning = true;

    public GamePanel() {
        gameWorld = new GameWorld();
        inputManger = new InputManger(gameWorld);
    }

    public void StartGame() {
        gameThread = new Thread(this);
        gameThread.start();
    }
    @Override
    public void run() {
        long previousTime = System.nanoTime();
        long currentTime;
        long sleepTime;
        long period = 1000000000/80;
        while (isRunning) {
            gameWorld.Update();
            gameWorld.Render();
            repaint();

            currentTime = System.nanoTime();
            sleepTime = period - (currentTime - previousTime);

            try {
                if (sleepTime > 0) {
                    Thread.sleep(sleepTime/1000000);
                }
                else Thread.sleep(period/2000000);
            } catch (Exception e) {
                // TODO: handle exception
            }
            previousTime = System.nanoTime();
        }
    }

    public void paint(Graphics g) {
        g.drawImage(gameWorld.getBufferedImage(), 0, 0, this);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }
    
    @Override
    public void keyPressed(KeyEvent e) {
        inputManger.processKeyPressed(e.getKeyCode());
    }

    @Override
    public void keyReleased(KeyEvent e) {
        inputManger.processKeyReleased(e.getKeyCode());
    }
}
