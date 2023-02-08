package entity;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Entity {
    
    public int worldX,worldY;
    public int speed, maxSpeed, initialSpeed, jumpStrength;
    public float fallingSpeed, speedX, speedY, accX, accY;
    
    public BufferedImage left1, left2, right1, right2, idle1, idle2;
    public BufferedImage flowerSpawn, flower, flowerStem;
    public String direction,directionForCollision;
    
    public int stepCount = 0;
    public int spriteCounter = 0;
    public int spriteNum = 1;
    
    public Rectangle hitbox, jumpBox, headBox;
    public int hitboxDefaultX, hitboxDefaultY;
    
    public int heldSprouts;
    
    public boolean collisionOn = false;
    public boolean isGrounded = false;
    public boolean isBelowCeiling = false;
}
