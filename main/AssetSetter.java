package main;

import object.SuperObject;
import object.obj_InstructionsARROWS;
import object.obj_InstructionsE;
import object.obj_InstructionsQCtrl;
import object.obj_InstructionsStuck;
import object.obj_InstructionsWASD;
import object.obj_sprout;

public class AssetSetter {
    GamePanel gp;
    
    public AssetSetter(GamePanel gp){
        this.gp = gp;
    }
    
    public void setObject(){
        
        switch(gp.level+1){
            case 1:
                startingValues(0, new obj_sprout(gp), 20, 43);
                startingValues(1, new obj_sprout(gp), 28, 39);
                startingValues(2, new obj_sprout(gp), 33, 37);
                startingValues(3, new obj_sprout(gp), 39, 43);
                startingValues(4, new obj_sprout(gp), 33, 28);
                startingValues(5, new obj_sprout(gp), 23, 29);
                startingValues(6, new obj_sprout(gp), 23, 30);
                startingValues(7, new obj_sprout(gp), 23, 31);
                startingValues(8, new obj_sprout(gp), 14, 24);
                startingValues(9, new obj_sprout(gp), 10, 30);
                startingValues(10, new obj_sprout(gp), 10, 31);
                startingValues(11, new obj_sprout(gp), 21, 18);
                startingValues(12, new obj_sprout(gp), 31, 21);
                startingValues(13, new obj_sprout(gp), 38, 10);
                startingValues(14, new obj_sprout(gp), 33, 9);
                startingValues(15, new obj_sprout(gp), 23, 10);
                startingValues(16, new obj_sprout(gp), 24, 10);
                startingValues(17, new obj_sprout(gp), 25, 10);
                startingValues(18, new obj_sprout(gp), 10, 27);
                startingValues(19, new obj_sprout(gp), 10, 28);
                startingValues(20, new obj_sprout(gp), 10, 24);
                startingValues(21, new obj_sprout(gp), 10, 25);
                startingValues(22, new obj_sprout(gp), 10, 21);
                startingValues(23, new obj_sprout(gp), 10, 22);
                startingValues(24, new obj_sprout(gp), 15, 10);
                startingValues(25, new obj_sprout(gp), 16, 10);
                startingValues(26, new obj_sprout(gp), 17, 10);
                startingValues(27, new obj_sprout(gp), 29, 29);
                startingValues(28, new obj_sprout(gp), 20, 28);
                startingValues(29, new obj_InstructionsWASD(gp), 3, 45);
                startingValues(30, new obj_InstructionsARROWS(gp), 3, 46);
                startingValues(31, new obj_InstructionsE(gp), 17, 45);
                startingValues(32, new obj_InstructionsQCtrl(gp), 40, 34);
                startingValues(33, new obj_InstructionsStuck(gp), 1, 30);
                break;
        }
    }
    
    public void startingValues(int index, SuperObject obj, int x, int y){
        gp.obj[index] = obj;
        gp.obj[index].worldX = gp.actualTileSize * x;
        gp.obj[index].worldY = gp.actualTileSize * y;
        
        if(gp.obj[index].name.equals("Sprout")){
            gp.obj[index].worldX += 16;
            gp.obj[index].worldY  += 16;
        }
        if(gp.obj[index].name.equals("QCtrl")){
            gp.obj[index].worldX += 25;
        }
    }
}
