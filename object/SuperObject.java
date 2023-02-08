package object;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import main.GamePanel;

import main.UtilityTool;

public class SuperObject {
    
    public BufferedImage image;
    public String name;
    public boolean collision = false;
    public int worldX, worldY;
    public Rectangle hitbox = new Rectangle(0,0,48,48);
    public int hitboxDefaultX = 0;
    public int hitboxDefaultY = 0;
    UtilityTool uTool = new UtilityTool();
    
    public void draw(Graphics2D g2, GamePanel gp){
        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;
        if(worldX + gp.actualTileSize > gp.player.worldX - gp.player.screenX*2 && 
           worldX - gp.actualTileSize < gp.player.worldX + gp.player.screenX*2 && 
           worldY + gp.actualTileSize > gp.player.worldY - gp.player.screenY*2 && 
           worldY - gp.actualTileSize < gp.player.worldY + gp.player.screenY*2){

           g2.drawImage(image, screenX, screenY, image.getWidth(), image.getHeight(), null);
        }
    }
}
