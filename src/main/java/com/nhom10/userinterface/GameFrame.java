package com.nhom10.userinterface;

import javax.swing.JFrame;
import java.awt.*;

/**
 *
 * @author luumi
 */
public class GameFrame extends JFrame {

    public  static final int SCRREN_WIDTH = 1000;
    public  static final int SCRREN_HEIGHT = 600;

    GamePanel gamePanel;
    public GameFrame() {
        Toolkit tookit = this.getToolkit();
        Dimension dimension = tookit.getScreenSize();
        this.setBounds((dimension.width-SCRREN_WIDTH)/2, (dimension.height-SCRREN_HEIGHT)/2, SCRREN_WIDTH, SCRREN_HEIGHT);


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
