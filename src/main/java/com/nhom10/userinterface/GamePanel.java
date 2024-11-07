package com.nhom10.userinterface;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

public class GamePanel extends JPanel implements Runnable, KeyListener {
    private Thread thread;
    private boolean isrunning;
    private InputManger inputManger;
    BufferedImage image;
    BufferedImage subImage;


    public GamePanel() {
        inputManger = new InputManger();


    }
    @Override
    public void paint(Graphics g) {
        g.setColor(Color.white);
        g.fillRect(0, 0, GameFrame.SCRREN_WIDTH, GameFrame.SCRREN_HEIGHT);
    }

    public void StartGame() {
        if (thread == null) {
            isrunning = true;
            thread = new Thread(this);
            thread.start();
        }
    }
    @Override
    public void run() {
        long FPS = 80;
        long period = 1000*1000000 / FPS;
        long beginTime = System.nanoTime();
        long sleepTime;
        while (isrunning) {
            // Update game
            // Render game

            long detalTime = System.nanoTime() - beginTime;
            sleepTime = period - detalTime;
            try {
                if (sleepTime > 0) {
                    Thread.sleep(sleepTime/1000000);
                }
                else Thread.sleep(period/2000000);
            } catch (InterruptedException e) {}
            beginTime = System.nanoTime();
        }
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
