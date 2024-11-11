package com.nhom10.effect;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Hashtable;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class CacheDataLoader {
    private static CacheDataLoader instance;

    private String framefile = "data/frame.txt";
    private String animationfile = "data/animation.txt";
    private String physmapfile = "data/phys_map.txt";
    private String backgroundmapfile = "data/background_map.txt";
    private String soundfile = "data/sounds.txt";

    private Hashtable<String, FrameImage> frameImages; 
    private Hashtable<String, Animation> animations;
    private Hashtable<String, Clip> sounds;
    private int[][] phys_map; 
    private int[][] background_map;

    private CacheDataLoader(){
        
        
    }

    public static CacheDataLoader getInstance(){
        if(instance == null){
            instance = new CacheDataLoader();
        }
        return instance;
    }

    public void LoadData()throws IOException{
        LoadFrame();
        LoadAnimation();
        LoadPhysMap();
        LoadBackgroundMap();
        LoadSounds();
    }

    public int[][] getPhysicalMap() {
        return instance.phys_map;
    }
    public void LoadSounds() throws IOException {
        sounds = new Hashtable<String, Clip>();
        
        // Đọc file cấu hình âm thanh
        FileReader fr = new FileReader(soundfile);
        BufferedReader br = new BufferedReader(fr);
        
        String line = null;
        
        // Kiểm tra xem file có dữ liệu không
        if (br.readLine() == null) {
            System.out.println("No data");
            br.close();
            throw new IOException();
        } else {
            // Đọc lại file
            fr = new FileReader(soundfile);
            br = new BufferedReader(fr);
            
            // Đọc số lượng âm thanh
            while ((line = br.readLine()).equals("")) ;
            int n = Integer.parseInt(line);
            
            for (int i = 0; i < n; i++) {
                Clip clip = null;
                
                // Đọc tên và đường dẫn của từng file âm thanh
                while ((line = br.readLine()).equals("")) ;
                String[] str = line.split(" ");
                String name = str[0];
                String path = str[1];
                
                try {
                    // Đọc âm thanh từ file
                    File audioFile = new File(path);
                    AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
                    clip = AudioSystem.getClip();
                    clip.open(audioStream);
                    
                } catch (UnsupportedAudioFileException | LineUnavailableException | IOException ex) {
                    System.out.println("Error loading sound file: " + path);
                    ex.printStackTrace();
                }
                
                if (clip != null) {
                    sounds.put(name, clip); // Lưu vào Hashtable
                }
            }
        }
        
        br.close();
    }

    public void LoadPhysMap() throws IOException {
        FileReader fr = new FileReader(physmapfile);
        BufferedReader br = new BufferedReader(fr);

        String line = null;

        line = br.readLine();
        int numberOfRows = Integer.parseInt(line);
        line = br.readLine();
        int numberOfColumns = Integer.parseInt(line);
        
        instance.phys_map = new int[numberOfRows][numberOfColumns];

        for (int i = 0; i < numberOfRows; i++) {
            line = br.readLine();
            String[] str = line.split(" ");
            for (int j = 0; j < numberOfColumns; j++) {
                instance.phys_map[i][j] = Integer.parseInt(str[j]);
            }
        }

        for (int i = 0; i < numberOfRows; i++) {
            for (int j = 0; j < numberOfColumns; j++) {
                System.out.print(" " + instance.phys_map[i][j]);
            }
            System.out.println();
        }
        br.close();
    }

    public void LoadFrame() throws IOException{
        
        frameImages = new Hashtable<String, FrameImage>();
        
        FileReader fr = new FileReader(framefile);
        BufferedReader br = new BufferedReader(fr);
        
        String line = null; // lưu từng dòng trong file
        
        if(br.readLine()==null) {
            System.out.println("No data");
            br.close();
            throw new IOException();
        }
        else {
            // khai báo lại để đưa con trỏ về đầu file
            fr = new FileReader(framefile);
            br = new BufferedReader(fr);
            
            // đọc file để bỏ qua các dòng trống kco dữ liệu
            while((line = br.readLine()).equals(""));
            
            int n = Integer.parseInt(line);
            String path = null;
            BufferedImage imageData = null;
            // int i2 = 0;
            for(int i = 0;i < n; i ++){
                
                FrameImage frame = new FrameImage();
                while((line = br.readLine()).equals(""));
                frame.setName(line);
                
                while((line = br.readLine()).equals(""));
                String[] str = line.split(" ");
                
                boolean refreshImage = (path == null || !path.equals(str[1]));
                path = str[1];
                
                while((line = br.readLine()).equals(""));
                str = line.split(" ");
                int x = Integer.parseInt(str[1]);
                
                while((line = br.readLine()).equals(""));
                str = line.split(" ");
                int y = Integer.parseInt(str[1]);
                
                while((line = br.readLine()).equals(""));
                str = line.split(" ");
                int w = Integer.parseInt(str[1]);
                
                while((line = br.readLine()).equals(""));
                str = line.split(" ");
                int h = Integer.parseInt(str[1]);
                
                if(refreshImage) {
                    refreshImage = false;
                    imageData = ImageIO.read(new File(path));
                }
                if(imageData != null) {
                    BufferedImage image = imageData.getSubimage(x, y, w, h);
                    frame.setImage(image);
                }
                
                instance.frameImages.put(frame.getName(), frame);
            }
            
        }
        
        br.close();
        
    }

    public FrameImage getFrameImage(String name){

        FrameImage frameImage = new FrameImage(instance.frameImages.get(name));
        return frameImage;

    }

    public void LoadAnimation() throws IOException {
        animations = new Hashtable<String, Animation>();
        
        FileReader fr = new FileReader(animationfile);
        BufferedReader br = new BufferedReader(fr);
        
        String line = null;
        
        if(br.readLine()==null) {
            System.out.println("No data");
            br.close();
            throw new IOException();
        }
        else {
            
            fr = new FileReader(animationfile);
            br = new BufferedReader(fr);
            
            while((line = br.readLine()).equals(""));
            int n = Integer.parseInt(line);
            
            for(int i = 0;i < n; i ++){
                Animation animation = new Animation();
                
                while((line = br.readLine()).equals(""));
                animation.setName(line);
                
                while((line = br.readLine()).equals(""));
                String[] str = line.split(" ");
                
                for(int j = 0;j<str.length;j+=2)
                    animation.add(getFrameImage(str[j]), Double.parseDouble(str[j+1]));
                
                instance.animations.put(animation.getName(), animation);
                
            }
            
        }
        
        br.close();
    }

    public Clip getSound(String name) {
        Clip clip = null;
        if (instance != null && instance.sounds != null) {
            clip = instance.sounds.get(name);  // Lấy Clip từ bảng sounds
            if (clip != null) {
                return clip;  // Nếu tìm thấy âm thanh với tên đó, trả về Clip
            } else {
                System.out.println("Sound with name '" + name + "' not found.");
            }
        } else {
            System.out.println("Instance or sounds map is null.");
        }
        return null;
    }
    

    public Animation getAnimation(String name){
        Animation animation = null;
        if (instance != null && instance.animations != null) {
            Animation temp = instance.animations.get(name);
            if (temp != null) {
                animation = new Animation(temp);
                return animation;
            } else {
                for (String key : animations.keySet()) {
                    System.out.println("Key: " + key);
                }
                System.out.println("Animation with name " + name + " not found.");
            }
        } else {
            System.out.println("Instance or animations map is null.");
        }
        return null;
    }

    public int[][] getBackgroundMap() {
        return instance.background_map;
    }
    public void LoadBackgroundMap() throws IOException {
        FileReader fr = new FileReader(backgroundmapfile);
        BufferedReader br = new BufferedReader(fr);

        String line = null;

        line = br.readLine();
        int numberOfRows = Integer.parseInt(line);
        line = br.readLine();
        int numberOfColumns = Integer.parseInt(line);

        instance.background_map = new int[numberOfRows][numberOfColumns];
        
        for (int i = 0; i < numberOfRows; i++) {
            line = br.readLine();
            String [] str = line.split(" ");
            for (int j = 0; j < str.length; j++) {
                instance.background_map[i][j] = Integer.parseInt(str[j]);
            }
        }

        for (int i = 0; i < numberOfRows; i++) {
            for (int j = 0; j < numberOfColumns; j++) {
                System.out.print(" " + instance.background_map[i][j]);
            }
            System.out.println();
        }
        br.close();
    }
}
