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

public class Player extends Entity{
    GamePanel gp;
    KeyHandler kh;
    
    public final int stepCountInterval = 20;
    public final int stepCountReset = 15;
    
    public int dashCounter = 0;
    public final long dashCooldown = 800;//in milliseconds
    public long lastDash = 0;
    
    public final int screenX,screenY;
    
    BufferedImage sprout;
    
    public Player(GamePanel gp, KeyHandler kh){
        this.gp = gp;
        this.kh = kh;
        
        screenX = gp.gameWidth/2 - (gp.actualTileSize/2);
        screenY = gp.gameHeight/2 - (gp.actualTileSize/2);
        
        hitbox = new Rectangle(8*gp.scale,17*gp.scale,16*gp.scale-1,15*gp.scale);
        jumpBox = new Rectangle(9*gp.scale,31*gp.scale,13*gp.scale,1*gp.scale);
        headBox = new Rectangle(9*gp.scale,16*gp.scale,13*gp.scale,1*gp.scale);
        
        hitboxDefaultX = hitbox.x;
        hitboxDefaultY = hitbox.y;
        
        setDefaultValues();
        getPlayerImage();
    }
    
    public void setDefaultValues(){
        worldX = gp.actualTileSize*11;
        worldY = gp.actualTileSize*43-1;
        initialSpeed = 4;
        speed = initialSpeed;
        fallingSpeed = 0.3f;
        speedY = 0;
        maxSpeed = 15;
        direction = "idle";
        directionForCollision = "left";
        jumpStrength = 6;
        heldSprouts = 0;
        kh.superJump = false;
    }

    public void getPlayerImage(){
        
        left1 = setup("playerLeft");
        right1 = setup("playerRight");
        idle1 = setup("playerIdle");
        left2 = setup("playerLeftJump");
        right2 = setup("playerRightJump");
        idle2 = setup("playerIdleJump");
        try {
            sprout = ImageIO.read(getClass().getResourceAsStream("/Objects/sprout.png"));
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
        
        int tileX = (worldX+32)/gp.actualTileSize;
        int tileY = (worldY+32)/gp.actualTileSize;
        
        if(gp.tileM.mapTileNum[tileX][tileY] == 48){
            gp.won = true;
        }
        
        //Check Collision
        collisionOn = false;
        gp.cChecker.checkTile(this);
        
        //Check object collision
        int objIndex = gp.cChecker.checkObject(this, true);
        pickupObject(objIndex);
        
        //Left & Right Movement
        if(kh.leftPressed==true){
            direction = "left";
            directionForCollision = "left";
            if(isGrounded == true){
                stepCount++;
                if(stepCount > stepCountInterval){
                    gp.playSE(2);
                    stepCount = 0;
                }
            }
        }
        if(kh.rightPressed==true){
            direction = "right";
            directionForCollision = "right";
            if(isGrounded == true){
                stepCount++;
                if(stepCount > stepCountInterval){
                    gp.playSE(2);
                    stepCount = 0;
                }
            }
        }
        if(kh.leftPressed==false && kh.rightPressed==false){
            direction = "idle";
            stepCount = stepCountReset;
        }
        
        //Jumping
        if(kh.jumping == true && isGrounded == true){
            kh.jumping = false;
            gp.playSE(1);
            stepCount = stepCountReset;
            speedY=-jumpStrength;
            kh.superJump = false;
        }
        if(kh.superJump == true) jumpStrength = 12;
        else jumpStrength = 6;

        if(isBelowCeiling==true){
            for(int i = 0; i < maxSpeed; i++){
                speedY++;
                gp.cChecker.checkTile(this);
                if(isBelowCeiling == false){
                    worldY += speedY;
                    speedY = 0;
                    break;
                }
            }
        }
        
        //Gravity
        if(isGrounded == false){
            if(speedY < maxSpeed){
                speedY+=fallingSpeed;
            }
        }
        if(isGrounded==true && kh.jumping==false){
            if(speedY > 0){
                for(int i = 0; i < speedY; i++){
                    speedY--;
                    gp.cChecker.checkTile(this);
                    if(isGrounded == false){
                        worldY+=speedY+1;
                        speedY = 0;
                        break;
                    }
                }
            }
        }
        else{
            worldY+=speedY;
        }
        
        //Do collision with tiles in direction player is moving
        if(collisionOn == false){
            switch(direction){
                case "left":
                    worldX-=speed;
                    speed = initialSpeed;
                    break;
                case "right":
                    worldX+=speed;
                    speed = initialSpeed;
                    break;
            }
        }
        else{
            if(speed > 0){
                speed--;
                gp.cChecker.checkTile(this);
            }
        }
        
        if(kh.giving == true &&
        (gp.player2.centeredX > gp.gameWidth/2 - 64) && (gp.player2.centeredX < gp.gameWidth/2 + 64) &&
        (gp.player2.centeredY > gp.gameHeight/2 - 64) && (gp.player2.centeredY < gp.gameHeight/2 + 64)){
            
            kh.giving = false;
            
            if(heldSprouts > 0){
                gp.playSE(8);
                heldSprouts--;
                gp.player2.heldSprouts++;
            }
        }

        long time = System.currentTimeMillis();
        if(time > lastDash + dashCooldown){
            if(kh.dashing == true){
                speed = 20;
                dashCounter++;
            }
            if(dashCounter > 3){
                kh.dashing = false;
                dashCounter = 0;
                lastDash = time;
            }
        }
    }
    
    public void pickupObject(int i){
        
        if(i != 999){
            
            String objectName = gp.obj[i].name;
            
            switch(objectName){
                case "Sprout":
                    heldSprouts++;
                    gp.obj[i] = null;
                    gp.playSE(7);
                    break;
            }
        }
    }

    public void draw(Graphics2D g2){
        
        BufferedImage image = null;
        
        switch(direction){
        case "left":
            if(kh.superJump == true) image = left2;
            else image = left1;
            break;
        case "right":
            if(kh.superJump == true) image = right2;
            else image = right1;
            break;
        case "idle":
            if(kh.superJump == true) image = idle2;
            else image = idle1;
            break;
        }
        g2.drawImage(image, screenX, screenY, null);
        
        
        g2.setColor(Color.WHITE);
        g2.setFont(new Font("serif",Font.PLAIN,20));
        g2.drawString("x " + heldSprouts, screenX + 26, screenY + 25);
        g2.drawImage(sprout, screenX + 6, screenY + 10, 16, 16, null);
    }
}
