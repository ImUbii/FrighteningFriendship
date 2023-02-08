package object;

import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
import main.GamePanel;

public class obj_InstructionsQCtrl extends SuperObject{
    
    GamePanel gp;
    
    public obj_InstructionsQCtrl(GamePanel gp){
        this.gp=gp;
        
        name = "QCtrl";
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/Objects/instructionsQCtrl.png"));
            image = uTool.scaleImage(image, image.getWidth()*2, image.getHeight()*2);
        } catch (IOException ex) {System.out.println("obj_InstructionsQCtrl obj_InstructionsQCtrl "+ex);}
    }
}