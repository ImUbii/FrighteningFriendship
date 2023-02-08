package main;

import entity.Entity;

public class CollisionChecker {
    
    GamePanel gp;
    
    public CollisionChecker(GamePanel gp){
        this.gp = gp;
    }
    
    public void checkTile(Entity entity){
        
        //Find world position of each side of the entity's hitboxes
        int entityLeftWorldX = entity.worldX + entity.hitbox.x;
        int entityRightWorldX = entity.worldX + entity.hitbox.x + entity.hitbox.width;
        int entityTopWorldY = entity.worldY + entity.hitbox.y;
        int entityBottomWorldY = entity.worldY + entity.hitbox.y + entity.hitbox.height;
        int entityLeftWorldJumpX = entity.worldX + entity.jumpBox.x;
        int entityRightWorldJumpX = entity.worldX + entity.jumpBox.x + entity.jumpBox.width;
        int entityBottomWorldJumpY = entity.worldY + entity.jumpBox.y + entity.jumpBox.height;
        int entityLeftWorldHeadX = entity.worldX + entity.headBox.x;
        int entityRightWorldHeadX = entity.worldX + entity.headBox.x + entity.headBox.width;
        int entityTopWorldHeadY = entity.worldY + entity.headBox.y;
        
        //Find what row and column each side is in, messed up because no switch statement?
        int entityLeftCol = entityLeftWorldX/gp.actualTileSize;
        int entityRightCol = entityRightWorldX/gp.actualTileSize;
        int entityTopRow = entityTopWorldY/gp.actualTileSize;
        int entityBottomRow = entityBottomWorldY/gp.actualTileSize;
        int entityLeftJumpCol = entityLeftWorldJumpX/gp.actualTileSize;
        int entityRightJumpCol = entityRightWorldJumpX/gp.actualTileSize;
        int entityBottomJumpRow = entityBottomWorldJumpY/gp.actualTileSize;
        int entityLeftHeadCol = entityLeftWorldHeadX/gp.actualTileSize;
        int entityRightHeadCol = entityRightWorldHeadX/gp.actualTileSize;
        int entityTopHeadRow = entityTopWorldHeadY/gp.actualTileSize;
        
        int horizTile1, horizTile2, vertTile1, vertTile2, vertTile3, vertTile4;//set to immediate tiles
        horizTile1 = 0;
        horizTile2 = 0;
        
        //Gravity (floor collision)
        entityBottomJumpRow = (entityBottomWorldJumpY + (int)(entity.speedY)+1)/gp.actualTileSize;
        vertTile1 = gp.tileM.mapTileNum[entityLeftJumpCol][entityBottomJumpRow];
        vertTile2 = gp.tileM.mapTileNum[entityRightJumpCol][entityBottomJumpRow];
        if(gp.tileM.tile[vertTile1].collision == true || gp.tileM.tile[vertTile2].collision == true){
            entity.isGrounded = true;
        }
        else{
            entity.isGrounded = false;
        }
        
        //Checking for ceiling above player
        entityTopHeadRow = (entityTopWorldHeadY + (int)(entity.speedY-1))/gp.actualTileSize;
        vertTile3 = gp.tileM.mapTileNum[entityLeftHeadCol][entityTopHeadRow];
        vertTile4 = gp.tileM.mapTileNum[entityRightHeadCol][entityTopHeadRow];
        if(gp.tileM.tile[vertTile3].collision == true || gp.tileM.tile[vertTile4].collision == true){
            entity.isBelowCeiling = true;
        }
        else{
            entity.isBelowCeiling = false;
        }
        
        switch(entity.directionForCollision){//only check for collision with tiles in the direction of the entity
            case"left":
                entityLeftCol = (entityLeftWorldX - entity.speed)/gp.actualTileSize;
                horizTile1 = gp.tileM.mapTileNum[entityLeftCol][entityTopRow];
                horizTile2 = gp.tileM.mapTileNum[entityLeftCol][entityBottomRow];
                if(gp.tileM.tile[horizTile1].collision == true || gp.tileM.tile[horizTile2].collision == true){
                    entity.collisionOn = true;
                }
                break;
            case"right":
                entityRightCol = (entityRightWorldX + entity.speed)/gp.actualTileSize;
                horizTile1 = gp.tileM.mapTileNum[entityRightCol][entityTopRow];
                horizTile2 = gp.tileM.mapTileNum[entityRightCol][entityBottomRow];
                if(gp.tileM.tile[horizTile1].collision == true || gp.tileM.tile[horizTile2].collision == true){
                    entity.collisionOn = true;
                }
                break;
            case"idle":
                if(gp.tileM.tile[horizTile1].collision == true || gp.tileM.tile[horizTile2].collision == true){
                    entity.collisionOn = true;
                }
                break;
        }
    }
    
    public int checkObject(Entity entity, boolean player){
        
        int index = 999;
        
        for(int i = 0; i < gp.obj.length; i++){
            if(gp.obj[i] != null){
                
                entity.hitbox.x = entity.worldX + entity.hitbox.x;
                entity.hitbox.y = entity.worldY + entity.hitbox.y;
                
                gp.obj[i].hitbox.x = gp.obj[i].worldX + gp.obj[i].hitbox.x;
                gp.obj[i].hitbox.y = gp.obj[i].worldY + gp.obj[i].hitbox.y;
                
                switch(entity.direction){
                    case "left":
                        entity.hitbox.x -= entity.speed;
                        if(entity.hitbox.intersects(gp.obj[i].hitbox)){
                            if(gp.obj[i].collision == true){
                                entity.collisionOn = true;
                            }
                            if(player == true){
                                index = i;
                            }
                        }
                        break;
                    case "right":
                        entity.hitbox.x += entity.speed;
                        if(entity.hitbox.intersects(gp.obj[i].hitbox)){
                            if(gp.obj[i].collision == true){
                                entity.collisionOn = true;
                            }
                            if(player == true){
                                index = i;
                            }
                        }
                        break;
                    case "idle":
                        if(entity.hitbox.intersects(gp.obj[i].hitbox)){
                            if(gp.obj[i].collision == true){
                                entity.collisionOn = true;
                            }
                            if(player == true){
                                index = i;
                            }
                        }
                }
                entity.hitbox.x = entity.hitboxDefaultX;
                entity.hitbox.y = entity.hitboxDefaultY;
                gp.obj[i].hitbox.x = gp.obj[i].hitboxDefaultX;
                gp.obj[i].hitbox.y = gp.obj[i].hitboxDefaultY;
            }
        }
        
        return index;
    }
}
