package com.nhom10.userinterface;

import javax.swing.JFrame;

import com.nhom10.effect.CacheDataLoader;

import java.awt.*;

/**
 *
 * @author luumi
 */
public class GameFrame extends JFrame {

    public  static final int SCREEN_WIDTH = 1000;
    public  static final int SCREEN_HEIGHT = 600;

    GamePanel gamePanel;
    public GameFrame() {
        Toolkit tookit = this.getToolkit();
        Dimension dimension = tookit.getScreenSize();
        try {
            CacheDataLoader.getInstance().LoadData();
        } catch (Exception e) {
            // TODO: handle exception
        }
        this.setBounds((dimension.width-SCREEN_WIDTH)/2, (dimension.height-SCREEN_HEIGHT)/2, SCREEN_WIDTH, SCREEN_HEIGHT);


        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        gamePanel = new GamePanel();
        add(gamePanel);
        this.addKeyListener(gamePanel);
    }
    public void StartGame() {
        gamePanel.StartGame();
    }

    public static void main(String[] args) {

        GameFrame gameFrame = new GameFrame();
        gameFrame.setVisible(true);
        gameFrame.StartGame();
    }
}
