package object;

import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
import main.GamePanel;

public class obj_InstructionsStuck extends SuperObject{
    
    GamePanel gp;
    
    public obj_InstructionsStuck(GamePanel gp){
        this.gp=gp;
        
        name = "wasd";
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/Objects/instructionsStuck.png"));
            image = uTool.scaleImage(image, image.getWidth()*2, image.getHeight()*2);
        } catch (IOException ex) {System.out.println("obj_InstructionsStuck obj_InstructionsStuck "+ex);}
    }
}
