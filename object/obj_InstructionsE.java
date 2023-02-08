package object;

import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
import main.GamePanel;

public class obj_InstructionsE extends SuperObject{
    
    GamePanel gp;
    
    public obj_InstructionsE(GamePanel gp){
        this.gp=gp;
        
        name = "e";
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/Objects/instructionsE.png"));
            image = uTool.scaleImage(image, image.getWidth()*2, image.getHeight()*2);
        } catch (IOException ex) {System.out.println("obj_InstructionsE obj_InstructionsE "+ex);}
    }
}