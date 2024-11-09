package com.nhom10.effect;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
// tạo bức ảnh 
public class FrameImage {
    private String name;
    private BufferedImage image;

    public FrameImage(String name, BufferedImage image){
        this.name = name;
        this.image = image;
    }
    FrameImage(){
        image = null;
        name = null;
    }
    public FrameImage(FrameImage frameImage){
        image = new BufferedImage(frameImage.getImageWidth(), frameImage.getImageHeight(), frameImage.getImage().getType());

        Graphics g = image.getGraphics();
        g.drawImage(frameImage.getImage(),0,0,null);
    }

    // hàm vẽ
    public void draw(Graphics2D g2,int x, int y){
        g2.drawImage(image, x - image.getWidth()/2, y - image.getHeight()/2,null);
    }

    // lấy ra độ rộng của image
    public int getImageWidth(){
        return image.getWidth();
    }
    // lấy ra độ dài của image
    public int getImageHeight(){
        return image.getHeight();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BufferedImage getImage() {
        return image;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }

    
}