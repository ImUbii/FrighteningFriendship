package object;

import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
import main.GamePanel;

public class obj_sprout extends SuperObject{
    
    GamePanel gp;
    
    public obj_sprout(GamePanel gp){
        this.gp=gp;
        
        name = "Sprout";
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/Objects/sprout.png"));
            image = uTool.scaleImage(image, gp.actualTileSize/2, gp.actualTileSize/2);
        } catch (IOException ex) {System.out.println("obj_sprout obj_sprout "+ex);}
    }
}
