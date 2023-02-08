package tile;

import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.io.InputStream;
import java.io.InputStreamReader;
import javax.imageio.ImageIO;

import main.GamePanel;
import main.UtilityTool;

public class TileManager {
    
    GamePanel gp;
    public Tile[] tile;
    public int mapTileNum[][];
    
    public TileManager(GamePanel gp){
        
        this.gp = gp;
        
        tile = new Tile[50];
        mapTileNum = new int[gp.maxWorldCol][gp.maxWorldRow];
        
        getTileImage();
        loadMap();
    }
    
    public void getTileImage(){
        
            setup(0, null, false);//This is the tile for "no tile" so it is null
            setup(1, "01", true);
            setup(2, "02", true);
            setup(3, "03", true);
            setup(4, "04", true);
            setup(5, "05", true);
            setup(6, "06", true);
            setup(7, "07", true);
            setup(8, "08", true);
            setup(9, "09", true);
            setup(10, "10", true);
            setup(11, "11", true);
            setup(12, "12", true);
            setup(13, "13", true);
            setup(14, "14", true);
            setup(15, "15", true);
            setup(16, "16", true);
            setup(17, "17", true);
            setup(18, "18", true);
            setup(19, "19", true);
            setup(20, "20", true);
            setup(21, "21", true);
            setup(22, "22", true);
            setup(23, "23", true);
            setup(24, "24", true);
            setup(25, "25", true);
            setup(26, "26", true);
            setup(27, "27", true);
            setup(28, "28", true);
            setup(29, "29", true);
            setup(30, "30", true);
            setup(31, "31", true);
            setup(32, "32", true);
            setup(33, "33", true);
            setup(34, "34", true);
            setup(35, "35", true);
            setup(36, "36", true);
            setup(37, "37", true);
            setup(38, "38", true);
            setup(39, "39", true);
            setup(40, "40", true);
            setup(41, "41", true);
            setup(42, "42", true);
            setup(43, "43", true);
            setup(44, "44", true);
            setup(45, "45", true);
            setup(46, "46", true);
            setup(47, "47", true);
            setup(48, "48", false);
            setup(49, "49", true);
    }
    
    public void setup(int index, String imagePath, boolean collision){
        
        UtilityTool uTool = new UtilityTool();
        try {
            tile[index] = new Tile();
            if(index == 0){
                tile[index].image=null;
            }
            else{
                tile[index].image = ImageIO.read(getClass().getResourceAsStream("/Tiles/"+imagePath+".png"));
                tile[index].image = uTool.scaleImage(tile[index].image, gp.actualTileSize, gp.actualTileSize);
            }
            tile[index].collision = collision;
        } catch (IOException ex) {System.out.println("TileManager setup "+ex);}
    }
    
    public void loadMap(){
        
        try {
            InputStream is = getClass().getResourceAsStream("/Maps/realLevel.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            
            int col = 0;
            int row = 0;
        
            while(col < gp.maxWorldCol && row < gp.maxWorldRow){
            
                String line = br.readLine();
                
                while(col < gp.maxWorldCol){
                    String numbers[] = line.split(" ");
                    
                    int num = Integer.parseInt(numbers[col]);
                    
                    mapTileNum[col][row] = num;
                    col++;
                }
                if(col == gp.maxWorldCol){
                    col = 0;
                    row++;
                }
            }
            br.close();
        } catch (FileNotFoundException ex) {System.out.println("TileManager loadMap " + ex);} 
        catch (IOException ex) {System.out.println("Tilemanager loadMap " + ex);}
    }
    
    public void draw(Graphics2D g2){
        
        int worldCol = 0;
        int worldRow = 0;
        
        while(worldCol < gp.maxWorldCol && worldRow < gp.maxWorldRow){
            
            int tileNum = mapTileNum[worldCol][worldRow];
            
            int worldX = worldCol * gp.actualTileSize;
            int worldY = worldRow * gp.actualTileSize;
            int screenX = worldX - gp.player.worldX + gp.player.screenX;
            int screenY = worldY - gp.player.worldY + gp.player.screenY;
            if(worldX + gp.actualTileSize > gp.player.worldX - gp.player.screenX && 
               worldX - gp.actualTileSize < gp.player.worldX + gp.player.screenX && 
               worldY + gp.actualTileSize > gp.player.worldY - gp.player.screenY && 
               worldY - gp.actualTileSize < gp.player.worldY + gp.player.screenY){
                
                g2.drawImage(tile[tileNum].image, screenX, screenY, null);
            }
            worldCol++;
            
            if(worldCol == gp.maxWorldCol){
                worldCol = 0;
                worldRow++;
            }
        }
    }
}
