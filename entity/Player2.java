package entity;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import main.GamePanel;
import main.KeyHandler;
import main.UtilityTool;

public class Player2 extends Entity{
    GamePanel gp;
    KeyHandler kh;
    int screenX;
    int screenY;
    public int centeredX, centeredY;
    
    BufferedImage sprout;
    BufferedImage tileImage, arrowUp, arrowRight, arrowDown, arrowLeft, arrowUpLeft, arrowUpRight, arrowDownLeft, arrowDownRight;
    
    public Player2(GamePanel gp, KeyHandler kh){
        this.gp = gp;
        this.kh = kh;
        
        worldX = gp.gameWidth/2 - (gp.actualTileSize/2);
        worldY = gp.gameHeight/2 - (gp.actualTileSize/2);
        
        hitbox = new Rectangle(screenX-16,screenY-16,96,96);
        
        setDefaultValues();
        getPlayerImage();
    }
    
    public void setDefaultValues(){
        screenX = worldX - gp.player.worldX + gp.player.screenX;
        screenY = worldY - gp.player.worldY + gp.player.screenY;
        worldX = gp.actualTileSize*11;
        worldY = gp.actualTileSize*42;
        accX = 0.2f;
        accY = 0.2f;
        speedX = 0;
        speedY = 0;
        maxSpeed = 5;
        direction = "left";
        heldSprouts = 0;
    }
    
    public void getPlayerImage(){
        
        left1 = setup("ghostLeft");
        right1 = setup("ghostRight");
        arrowUp = setup("arrowUp");
        arrowRight = setup("arrowRight");
        arrowDown = setup("arrowDown");
        arrowLeft = setup("arrowLeft");
        arrowUpLeft = setup("arrowUpLeft");
        arrowDownLeft = setup("arrowDownLeft");
        arrowUpRight = setup("arrowUpRight");
        arrowDownRight = setup("arrowDownRight");
        try {
            sprout = ImageIO.read(getClass().getResourceAsStream("/Objects/sprout.png"));
            tileImage = ImageIO.read(getClass().getResourceAsStream("/Tiles/49.png"));
        } catch (IOException ex) {}
    }
    
    public BufferedImage setup(String imageName){
        
        UtilityTool uTool = new UtilityTool();
        BufferedImage image = null;
        
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/Sprites/"+imageName+".png"));
            image = uTool.scaleImage(image,gp.actualTileSize, gp.actualTileSize);
        } catch (IOException ex) {System.out.println("Player setup "+ex);}
        
        return image;
    }
    
    public void update(){

        centeredX = screenX + 32;
        centeredY = screenY + 32;
        
        screenX = worldX - gp.player.worldX + gp.player.screenX;
        screenY = worldY - gp.player.worldY + gp.player.screenY;
        if(kh.left2Pressed == true){
            if(speedX <= maxSpeed && speedX >= -maxSpeed){
                direction = "left";
                speedX -= accX;
            }
        }
        else if(speedX < 0){
            speedX += accX;
        }
        if(kh.right2Pressed == true){
            if(speedX <= maxSpeed && speedX >= -maxSpeed){
                direction = "right";
                speedX += accX;
            }
        }
        else{
            if(speedX > 0){
                speedX -= accX;
            }
        }
        if(kh.upPressed == true){
            if(speedY <= maxSpeed && speedY >= -maxSpeed){
                speedY -= accY;
            }
        }
        else{
            if(speedY < 0){
                speedY += accY;
            }
        }
        if(kh.downPressed == true){
            if(speedY <= maxSpeed && speedY >= -maxSpeed){
                speedY += accY;
            }
        }
        else{
            if(speedY > 0){
                speedY -= accY;
            }
        }
        if (speedX > maxSpeed) speedX = maxSpeed;
        else if (speedY > maxSpeed) speedY = maxSpeed;
        if (speedX < -maxSpeed) speedX = -maxSpeed;
        else if (speedY < -maxSpeed) speedY = -maxSpeed;
        if (speedX > 0 && speedX < 0.2f) speedX = 0;
        if (speedX < 0 && speedX > -0.2f) speedX = 0;
        if (speedY > 0 && speedY < 0.2f) speedY = 0;
        if (speedY < 0 && speedY > -0.2f) speedY = 0;
        worldX += speedX;
        worldY += speedY;
        
        if(kh.placingTile == true && heldSprouts > 0){
            
            kh.placingTile = false;
            
            int tileX = (worldX+32)/gp.actualTileSize;
            int tileY = (worldY+32)/gp.actualTileSize;
            
            if(gp.tileM.mapTileNum[tileX][tileY] == 0){
                heldSprouts--;
                gp.tileM.mapTileNum[tileX][tileY] = 49;
            }
        }
    }
    
    public void draw(Graphics2D g2){
        
        BufferedImage image = null;
        
        g2.setColor(Color.WHITE);
//g2.setFont(new Font("serif",Font.PLAIN,20));
//g2.drawString("speedX: "+speedX, screenX, screenY-96);
//g2.drawString("speedY: "+speedY, screenX, screenY-96-64);
        
        switch(direction){
        case "left":
            image = left1;
            break;
        case "right":
            image = right1;
            break;
        case "idle":
                image = idle1;
            break;
        }
        
        g2.drawImage(image, screenX, screenY, null);
        
        g2.setColor(Color.WHITE);
        g2.setFont(new Font("serif",Font.PLAIN,20));
        g2.drawString("x " + heldSprouts, screenX + 26, screenY - 1);
        g2.drawImage(sprout, screenX + 6, screenY - 16, 16, 16, null);
        
        if(screenX < -image.getWidth() && screenY < -image.getHeight()){
            g2.drawImage(arrowUpLeft, 32, 32, 32, 32, null);
        }
        else if(screenX > gp.gameWidth + image.getWidth() && screenY < -image.getHeight()){
            g2.drawImage(arrowUpRight, gp.gameWidth - 64, 32, 32, 32, null);
        }
        else if(screenX > gp.gameWidth + image.getWidth() && screenY > gp.gameHeight + image.getHeight()){
            g2.drawImage(arrowDownRight, gp.gameWidth - 64, gp.gameHeight - 64, 32, 32, null);
        }
        else if(screenX < -image.getWidth() && screenY > gp.gameHeight + image.getHeight()){
            g2.drawImage(arrowDownLeft, 32, gp.gameHeight - 64, 32, 32, null);
        }
        else if(screenX < -image.getWidth()){
            g2.drawImage(arrowLeft, 32, screenY, 32, 32, null);
        }
        else if(screenX > gp.gameWidth + image.getWidth()){
            g2.drawImage(arrowRight, gp.gameWidth - 64, screenY, 32, 32, null);
        }
        else if(screenY < -image.getHeight()){
            g2.drawImage(arrowUp, screenX, 32, 32, 32, null);
        }
        else if(screenY > gp.gameHeight + image.getHeight()){
            g2.drawImage(arrowDown, screenX, gp.gameHeight - 64, 32, 32, null);
        }
    }
}
