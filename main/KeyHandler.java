package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashSet;
import java.util.Set;

public class KeyHandler implements KeyListener{
    
    GamePanel gp;
    
    //vars for player 1
    public boolean leftPressed,rightPressed, jumping, giving, dashing, superJump;
    public boolean spaceHeld, QHeld, EHeld, shiftHeld;
    
    //vars for player 2
    public boolean left2Pressed,right2Pressed, upPressed, downPressed, placingTile;
    public boolean rightControlHeld;
    
    public KeyHandler(GamePanel gp){
        this.gp = gp;
    }

    @Override
    public void keyTyped(KeyEvent e) {}
    
    @Override
    public void keyPressed(KeyEvent e) {
        
        if(gp.gameState  ==  gp.titleState){
            if(gp.won == false) doTitleState(e);
        }
        else if(gp.gameState  ==  gp.playState){
            if(gp.won == false) doPlayState(e);
        }
        else if(gp.gameState  ==  gp.pauseState){
            if(gp.won == false) doPauseState(e);
        }
        else if(gp.gameState  ==  gp.optionsState){
            if(gp.won == false) doOptionsState(e);
        }
        else if(gp.gameState  ==  gp.titleOptionsState){
            if(gp.won == false) doTitleOptionsState(e);
        }
        
    }

    @Override
    public void keyReleased(KeyEvent e) {
        
        if(e.getKeyCode() == KeyEvent.VK_A){
            leftPressed = false;
        }
        if(e.getKeyCode() == KeyEvent.VK_D){
            rightPressed = false;
        }
        if(e.getKeyCode() == KeyEvent.VK_SPACE){
            jumping = false;
            spaceHeld = false;
        }
        if(e.getKeyCode() == KeyEvent.VK_Q){
            giving = false;
            QHeld = false;
        }
        if(e.getKeyCode() == KeyEvent.VK_SHIFT && e.getKeyLocation() == KeyEvent.KEY_LOCATION_LEFT){
            dashing = false;
            shiftHeld = false;
        }
        
        if(e.getKeyCode() == KeyEvent.VK_LEFT){
            left2Pressed = false;
        }
        if(e.getKeyCode() == KeyEvent.VK_RIGHT){
            right2Pressed = false;
        }
        if(e.getKeyCode() == KeyEvent.VK_UP){
            upPressed = false;
        }
        if(e.getKeyCode() == KeyEvent.VK_DOWN){
            downPressed = false;
        }
        if(e.getKeyCode() == KeyEvent.VK_CONTROL && e.getKeyLocation() == KeyEvent.KEY_LOCATION_RIGHT){
            placingTile = false;
            rightControlHeld = false;
        }
    }
    
    public void doTitleState(KeyEvent e){
        if(e.getKeyCode() == KeyEvent.VK_SPACE){
            
            if(gp.ui.optionNum  ==  0){
                gp.gameState = gp.playState;
                gp.ui.optionNum = 0;
                gp.stopMusic();
                gp.playMusic(5);
            }
            else if(gp.ui.optionNum  ==  1){
                gp.gameState = gp.titleOptionsState;
                gp.ui.optionNum = 0;
            }
            else if(gp.ui.optionNum  ==  2){
                gp.playSE(6);
                System.exit(0);
            }
            
            gp.playSE(4);
        }

        if(e.getKeyCode() == KeyEvent.VK_W){
            
            gp.playSE(3);
            
            gp.ui.optionNum--;
            if(gp.ui.optionNum < 0){
                gp.ui.optionNum = 2;
            }
        }
        if(e.getKeyCode() == KeyEvent.VK_S){
            
            gp.playSE(3);
            
            gp.ui.optionNum++;
            if(gp.ui.optionNum > 2){
                gp.ui.optionNum = 0;
            }
        }
    }
    
    public void doPlayState(KeyEvent e){
        if(e.getKeyCode() == KeyEvent.VK_A){
            leftPressed = true;
        }
        if(e.getKeyCode() == KeyEvent.VK_D){
            rightPressed = true;
        }
        if(e.getKeyCode() == KeyEvent.VK_SPACE && spaceHeld  ==  false){
            jumping = true;
            spaceHeld = true;
        }
        if(e.getKeyCode() == KeyEvent.VK_Q && QHeld  ==  false){
            giving = true;
            QHeld = true;
        }
        if(e.getKeyCode() == KeyEvent.VK_SHIFT && e.getKeyLocation() == KeyEvent.KEY_LOCATION_LEFT && shiftHeld == false){
            dashing = true;
            shiftHeld = true;
        }
        if(e.getKeyCode() == KeyEvent.VK_E && superJump == false){
            if(gp.player.heldSprouts > 0){
                superJump = true;
                EHeld = true;
                gp.player.heldSprouts--;
            }
        }

        if(e.getKeyCode() == KeyEvent.VK_LEFT){
            left2Pressed = true;
        }
        if(e.getKeyCode() == KeyEvent.VK_RIGHT){
            right2Pressed = true;
        }
        if(e.getKeyCode() == KeyEvent.VK_UP){
            upPressed = true;
        }
        if(e.getKeyCode() == KeyEvent.VK_DOWN){
            downPressed = true;
        }
        if(e.getKeyCode() == KeyEvent.VK_CONTROL && e.getKeyLocation() == KeyEvent.KEY_LOCATION_RIGHT && placingTile  ==  false && rightControlHeld  ==  false){
            placingTile = true;
            rightControlHeld = true;
        }

        if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
            gp.gameState = gp.pauseState;
            gp.playSE(6);
        }
    }
    
    public void doPauseState(KeyEvent e){
        if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
            gp.gameState = gp.playState;
            gp.playSE(6);
        }
        if(e.getKeyCode() == KeyEvent.VK_SPACE){
            
            if(gp.ui.optionNum  ==  0){
                gp.gameState = gp.playState;
                gp.ui.optionNum = 0;
                gp.playSE(6);
            }
            else if(gp.ui.optionNum  ==  1){
                gp.gameState = gp.optionsState;
                gp.ui.optionNum = 0;
                gp.playSE(4);
            }
            else if(gp.ui.optionNum  ==  2){
                gp.gameState = gp.titleState;
                gp.player.setDefaultValues();
                gp.player2.setDefaultValues();
                gp.tileM.loadMap();
                gp.aSetter.setObject();
                gp.ui.timer = 0;
                gp.stopMusic();
                gp.playMusic(0);
                
                gp.playSE(4);

                gp.ui.optionNum = 0;
            }
            else if(gp.ui.optionNum  ==  3){
                gp.playSE(6);
                System.exit(0);
            }
        }
        if(e.getKeyCode() == KeyEvent.VK_W){
            
            gp.playSE(3);
            
            gp.ui.optionNum--;
            if(gp.ui.optionNum < 0){
                gp.ui.optionNum = 3;
            }
        }
        if(e.getKeyCode() == KeyEvent.VK_S){
            
            gp.playSE(3);
            
            gp.ui.optionNum++;
            if(gp.ui.optionNum > 3){
                gp.ui.optionNum = 0;
            }
        }
    }
    public void doOptionsState(KeyEvent e){
        if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
            gp.ui.optionNum = 0;
            gp.gameState = gp.pauseState;
            gp.playSE(6);
        }
        
        if(e.getKeyCode() == KeyEvent.VK_SPACE){
            
            if(gp.ui.optionNum  ==  0){
                gp.gameState = gp.pauseState;
                gp.ui.optionNum = 0;
                gp.playSE(6);
            }
            else if(gp.ui.optionNum  ==  1){
                if(gp.fullscreenOn  ==  false){
                    gp.setFullScreen();
                    gp.fullscreenOn = true;
                    gp.playSE(4);
                }
                else if(gp.fullscreenOn  ==  true){
                    gp.setWindowed();
                    gp.fullscreenOn = false;
                    gp.playSE(6);
                }
            }
            else if(gp.ui.optionNum  ==  2){
                if(e.getKeyCode() == KeyEvent.VK_A){
                    if(gp.music.volumeScale > 0){
                        gp.music.volumeScale--;
                        gp.music.checkVolume();
                    }
                }
                if(e.getKeyCode() == KeyEvent.VK_D){
                    if(gp.music.volumeScale < 11){
                        gp.music.volumeScale++;
                        gp.music.checkVolume();
                    }
                }
            }
        }
        
        if(e.getKeyCode() == KeyEvent.VK_A){
            if(gp.ui.optionNum  ==  2 && gp.sound.volumeScale > 0){
                gp.sound.volumeScale--;
                gp.playSE(3);
            }
            if(gp.ui.optionNum  ==  3 && gp.music.volumeScale > 0){
                gp.music.volumeScale--;
                gp.music.checkVolume();
                gp.playSE(3);
            }
        }
        if(e.getKeyCode() == KeyEvent.VK_D){
            if(gp.ui.optionNum  ==  2 && gp.sound.volumeScale < 10){
                gp.sound.volumeScale++;
                gp.playSE(3);
            }
            if(gp.ui.optionNum  ==  3 && gp.music.volumeScale < 10){
                gp.music.volumeScale++;
                gp.music.checkVolume();
                gp.playSE(3);
            }
        }

        if(e.getKeyCode() == KeyEvent.VK_W){
            
            gp.playSE(3);
            
            gp.ui.optionNum--;
            if(gp.ui.optionNum < 0){
                gp.ui.optionNum = 3;
            }
        }
        if(e.getKeyCode() == KeyEvent.VK_S){
            
            gp.playSE(3);
            
            gp.ui.optionNum++;
            if(gp.ui.optionNum > 3){
                gp.ui.optionNum = 0;
            }
        }
    }
    public void doTitleOptionsState(KeyEvent e){
        if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
            gp.ui.optionNum = 0;
            gp.gameState = gp.titleState;
            gp.playSE(6);
        }
        
        if(e.getKeyCode() == KeyEvent.VK_SPACE){
            
            if(gp.ui.optionNum  ==  0){
                gp.gameState = gp.titleState;
                gp.ui.optionNum = 0;
                gp.playSE(6);
            }
            else if(gp.ui.optionNum  ==  1){
                if(gp.fullscreenOn  ==  false){
                    gp.setFullScreen();
                    gp.fullscreenOn = true;
                    gp.playSE(4);
                }
                else if(gp.fullscreenOn  ==  true){
                    gp.setWindowed();
                    gp.fullscreenOn = false;
                    gp.playSE(6);
                }
            }
            else if(gp.ui.optionNum  ==  2){
                if(e.getKeyCode() == KeyEvent.VK_A){
                    if(gp.music.volumeScale > 0){
                        gp.music.volumeScale--;
                        gp.music.checkVolume();
                    }
                }
                if(e.getKeyCode() == KeyEvent.VK_D){
                    if(gp.music.volumeScale < 11){
                        gp.music.volumeScale++;
                        gp.music.checkVolume();
                    }
                }
            }
        }
        
        if(e.getKeyCode() == KeyEvent.VK_A){
            if(gp.ui.optionNum  ==  2 && gp.sound.volumeScale > 0){
                gp.sound.volumeScale--;
                gp.playSE(3);
            }
            if(gp.ui.optionNum  ==  3 && gp.music.volumeScale > 0){
                gp.music.volumeScale--;
                gp.music.checkVolume();
                gp.playSE(3);
            }
        }
        if(e.getKeyCode() == KeyEvent.VK_D){
            if(gp.ui.optionNum  ==  2 && gp.sound.volumeScale < 10){
                gp.sound.volumeScale++;
                gp.playSE(3);
            }
            if(gp.ui.optionNum  ==  3 && gp.music.volumeScale < 10){
                gp.music.volumeScale++;
                gp.music.checkVolume();
                gp.playSE(3);
            }
        }

        if(e.getKeyCode() == KeyEvent.VK_W){
            
            gp.playSE(3);
            
            gp.ui.optionNum--;
            if(gp.ui.optionNum < 0){
                gp.ui.optionNum = 3;
            }
        }
        if(e.getKeyCode() == KeyEvent.VK_S){
            
            gp.playSE(3);
            
            gp.ui.optionNum++;
            if(gp.ui.optionNum > 3){
                gp.ui.optionNum = 0;
            }
        }
    }
}
