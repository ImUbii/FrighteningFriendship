package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.text.DecimalFormat;

public class UI {
    
    GamePanel gp;
    Graphics2D g2;
    Font rag_40;
    public int optionNum = 0;
    
    double timer;
    DecimalFormat dFormat = new DecimalFormat("#0.00");
    
    public UI(GamePanel gp){
        
        this.gp = gp;
        
        rag_40 = new Font("Power Red and Green", Font.PLAIN, 40);
    }
    
    public void draw(Graphics2D g2){
        
        this.g2 = g2;
        
        g2.setFont(rag_40);
        
        if(gp.gameState == gp.titleState){
            drawTitleScreen();
        }
        if(gp.gameState == gp.playState){
            drawPlayScreen();
        }
        if(gp.gameState == gp.pauseState){
            drawPauseScreen();
        }
        if(gp.gameState == gp.optionsState){
            drawOptionsScreen();
        }
        if(gp.gameState == gp.titleOptionsState){
            drawOptionsScreen();
        }
    }
    
    public void drawTitleScreen(){
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN,40F));
        
        int x = 564;
        int y = 450;
        g2.setColor(new Color(250, 143, 142));
        String text = "CONTROLS:";
        g2.drawString(text, x, y);
        g2.setColor(new Color(186, 186, 186));
        text = "WASD to move";
        g2.drawString(text, x, y+50);
        text = "SPACE to jump/select";
        g2.drawString(text, x, y+100);
        text = "More controls will be explained";
        g2.drawString(text, x, y+150);
        text = "throughout the game!";
        g2.drawString(text, x, y+200);
        
        text = "New Game";
        x = 75;
        y = 450;
        if(optionNum == 0){
            g2.setColor(new Color(250, 143, 142));
            g2.drawString(text, x+30, y+100);
            g2.drawString(">", x, y+100);
            g2.setColor(new Color(186, 186, 186));
        }
        else{
            g2.drawString(text, x, y+100);
        }
        
        text = "Options";
        if(optionNum == 1){
            g2.setColor(new Color(250, 143, 142));
            g2.drawString(text, x+30, y+150);
            g2.drawString(">", x, y+150);
            g2.setColor(new Color(186, 186, 186));
        }
        else{
            g2.drawString(text, x, y+150);
        }
        
        text = "Quit";
        if(optionNum == 2){
            g2.setColor(new Color(250, 143, 142));
            g2.drawString(text, x+30, y+200);
            g2.drawString(">", x, y+200);
            g2.setColor(new Color(186, 186, 186));
        }
        else{
            g2.drawString(text, x, y+200);
        }
        
        g2.drawImage(gp.player.idle1, 500, 70, gp.actualTileSize*4, gp.actualTileSize*4, null);
        g2.drawImage(gp.player2.left1, 800, 102, gp.actualTileSize*4, gp.actualTileSize*4, null);
        
    }
    
    public void drawPlayScreen(){
        if(gp.won == true){
            
            g2.setColor(new Color(0,0,0,200));
            g2.fillRect(0, 0, gp.gameWidth, gp.gameHeight);
            
            g2.setFont(g2.getFont().deriveFont(Font.PLAIN,100F));
            g2.setColor(new Color(250, 143, 142));
            String text = "You made it!";
            int x = getXforCenteredText(text);
            int y = (gp.gameHeight/5)*2;
            g2.drawString(text, x, y);
            
            g2.setFont(g2.getFont().deriveFont(Font.PLAIN,50F));
            text = "But can you make it any faster?";
            x = getXforCenteredText(text);
            y += 50;
            g2.drawString(text, x, y);
            
            text = "ALT-F4 to try again!";
            x = getXforCenteredText(text);
            y += 100;
            g2.drawString(text, x, y);
        }
        else timer += (double)1/60;
        g2.drawString("" + dFormat.format(timer), 50, 50);
    }
    
    public void drawPauseScreen(){
        
        g2.setColor(new Color(0,0,0,200));
        g2.fillRect(0, 0, gp.gameWidth, gp.gameHeight);
        
        g2.setColor(new Color(250, 143, 142));
        g2.setFont(g2.getFont().deriveFont(Font.BOLD,72F));
        String text = "PAUSED";
        int x = getXforCenteredText(text);
        int y = (gp.gameHeight/5)*1;
        g2.drawString(text, x, y);
        
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN,40F));
        text = "Unpause";
        x = getXforCenteredText(text);
        g2.drawString(text, x, y+70);
        if(optionNum == 0){
            g2.drawString(">", x-30, y+70);
        }
        
        text = "Options";
        x = getXforCenteredText(text);
        g2.drawString(text, x, y+120);
        if(optionNum == 1){
            g2.drawString(">", x-30, y+120);
        }
        
        text = "Quit to Title (Restart)";
        x = getXforCenteredText(text);
        g2.drawString(text, x, y+170);
        if(optionNum == 2){
            g2.drawString(">", x-30, y+170);
        }
        
        text = "Quit to Desktop";
        x = getXforCenteredText(text);
        g2.drawString(text, x, y+220);
        if(optionNum == 3){
            g2.drawString(">", x-30, y+220);
        }
    }
    
    public void drawOptionsScreen(){
        
        g2.setColor(new Color(0,0,0,200));
        g2.fillRect(0, 0, gp.gameWidth, gp.gameHeight);
        
        g2.setColor(new Color(250, 143, 142));
        g2.setFont(g2.getFont().deriveFont(Font.BOLD,72F));
        String text = "OPTIONS";
        int x = 450;
        int y = (gp.gameHeight/5)*1;
        g2.drawString(text, x, y);
        
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN,40F));
        text = "Back";
        y += 70;
        g2.drawString(text, x, y);
        if(optionNum == 0){
            g2.drawString(">", x-30, y);
        }
        
        text = "Fullscreen:";
        y += 50;
        g2.drawString(text, x, y);
        if(optionNum == 1){
            g2.drawString(">", x-30, y);
        }
        x = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        text = ""+ gp.fullscreenOn;
        g2.drawString(text, x+460, y);
        
        text = "Sound Effects:";
        x = 450;
        y += 50;
        g2.drawString(text, x, y);
        if(optionNum == 2){
            g2.drawString(">", x-30, y);
        }
        x = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        text = ""+ gp.sound.volumeScale;
        g2.drawString(text, x+460, y);
        
        text = "Music:";
        x = 450;
        y += 50;
        g2.drawString(text, x, y);
        if(optionNum == 3){
            g2.drawString(">", x-30, y);
        }
        x = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        text = ""+ gp.music.volumeScale;
        g2.drawString(text, x+460, y);
    }
    
    public int getXforCenteredText(String text){
        int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int x = gp.gameWidth/2 - length/2;
        
        return x;
    }
}
