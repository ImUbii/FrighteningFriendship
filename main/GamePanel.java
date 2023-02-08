package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;

import entity.Player;
import entity.Player2;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferedImage;
import object.SuperObject;
import tile.TileManager;

public class GamePanel extends JPanel implements Runnable{
    
    //Screen Settings
    final int originalTileSize = 32;
    public final int scale = 2;
    public final int actualTileSize = originalTileSize * scale;
    public final int gameWidth = 1280;
    public final int gameHeight = 704;
    public final int maxScreenCol = gameWidth/actualTileSize;//40 tiles by
    public final int maxScreenRow = gameHeight/actualTileSize;//22 tiles
    
    //World Settings
    public final int maxWorldCol = 50;
    public final int maxWorldRow= 50;
    
    //Full Screen
    int gameWidth2 = gameWidth;
    int gameHeight2 = gameHeight;
    BufferedImage tempScreen;
    Graphics2D g2;
    public boolean fullscreenOn = false;
    
    //Frate Rate
    final int FPS = 60;
    
    //Things necessary to run game
    public TileManager tileM = new TileManager(this);
    public KeyHandler kh = new KeyHandler(this);
    public CollisionChecker cChecker = new CollisionChecker(this);
    public AssetSetter aSetter = new AssetSetter(this);
    public UI ui = new UI(this);
    Sound sound = new Sound();
    Sound music = new Sound();
    Thread gameThread;
    //Necessary entities/objects
    public Player player = new Player(this,kh);
    public Player2 player2 = new Player2(this,kh);
    public SuperObject obj[] = new SuperObject[34];
    
    public int level = 0;
    
    long passed = 0;
    
    public boolean won = false;
    
    //Game State
    public int gameState;
    public final int playState = 1;
    public final int pauseState = 2;
    public final int titleState = 3;
    public final int optionsState = 4;
    public final int titleOptionsState = 5;
    
    public GamePanel(){
        
        setPreferredSize(new Dimension(gameWidth,gameHeight));
        setDoubleBuffered(true);
        addKeyListener(kh);
        setFocusable(true);
    }

    public void setupGame(){
        
        aSetter.setObject();
        gameState = titleState;
        
        tempScreen = new BufferedImage(gameWidth, gameHeight, BufferedImage.TYPE_INT_ARGB);
        g2 = (Graphics2D)tempScreen.getGraphics();
        
        music.volumeScale = 6;
        sound.volumeScale = 8;
        
        playMusic(0);
    }
    
    public void startGameThread(){
        
        gameThread = new Thread(this);//this = gamePanel
        gameThread.start();
    }
    
    public void run() {
        
        double drawInterval = 1000000000/FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        
        while(gameThread!=null){
            
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime)/drawInterval;
            lastTime = currentTime;
            if(delta >=1){
                update();
                drawToTempScreen();
                drawToScreen();
                delta--;
            }
        }
    }
    
    public void update(){
        
        if(won == false){
            if(gameState == playState){
                player.update();
                player2.update();
            }
        }
    }
    
    public void drawToTempScreen(){
        
        //Background
        g2.setColor(new Color(63,63,116));
        g2.fillRect(0, 0, gameWidth, gameHeight);
        
        //Title Screen
        if(gameState == titleState || gameState == titleOptionsState){
            ui.draw(g2);
        }
        //Others
        else if(gameState == playState || gameState == pauseState || gameState == optionsState){
            //Tile
            tileM.draw(g2);

            //Objects
            for(int i = 0; i < obj.length; i++){
                if(obj[i] != null){
                    obj[i].draw(g2, this);
                }
            }

            //Player
            player.draw(g2);
            player2.draw(g2);

            //UI
            ui.draw(g2); 
        }
    }
    
    public void playMusic(int i){
        
        music.setFile(i);
        music.play();
        music.loop();
    }
    public void stopMusic(){
        
        music.stop();
    }
    public void playSE(int i){
        
        sound.setFile(i);
        sound.play();
    }
    
    public void drawToScreen(){
        Graphics g = getGraphics();
        g.drawImage(tempScreen, 0, 0, gameWidth2, gameHeight2, null);
        g.dispose();
    }
    
    public void setFullScreen(){
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice gd = ge.getDefaultScreenDevice();
        gd.setFullScreenWindow(Main.window);
        
        gameWidth2 = Main.window.getWidth();
        gameHeight2 = Main.window.getHeight();
    }
    
    public void setWindowed(){
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice gd = ge.getDefaultScreenDevice();
        gd.setFullScreenWindow(null);
        
        gameWidth2 = Main.window.getWidth();
        gameHeight2 = Main.window.getHeight();
    }
}