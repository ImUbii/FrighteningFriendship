package object;

import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
import main.GamePanel;

public class obj_InstructionsARROWS extends SuperObject{
    
    GamePanel gp;
    
    public obj_InstructionsARROWS(GamePanel gp){
        this.gp=gp;
        
        name = "wasd";
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/Objects/instructionsARROWS.png"));
            image = uTool.scaleImage(image, image.getWidth()*2, image.getHeight()*2);
        } catch (IOException ex) {System.out.println("obj_InstructionsARROWS obj_InstructionsARROWS "+ex);}
    }
}
