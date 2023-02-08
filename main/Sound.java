package main;

import java.net.URL;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

public class Sound {
    Clip clip;
    URL soundURL[] = new URL[30];
    FloatControl fc;
    int volumeScale;
    float volume;
    
    public Sound(){
            soundURL[0] = getClass().getResource("/Sounds/titleTheme.wav");
            soundURL[1] = getClass().getResource("/Sounds/jump.wav");
            soundURL[2] = getClass().getResource("/Sounds/step.wav");
            soundURL[3] = getClass().getResource("/Sounds/menuNavigation.wav");
            soundURL[4] = getClass().getResource("/Sounds/menuConfirm.wav");
            soundURL[5] = getClass().getResource("/Sounds/levelTheme.wav");
            soundURL[6] = getClass().getResource("/Sounds/menuDeny.wav");
            soundURL[7] = getClass().getResource("/Sounds/pickup.wav");
            soundURL[8] = getClass().getResource("/Sounds/give.wav");
    }
    
    public void setFile(int i){
        
        try{
            AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
            clip = AudioSystem.getClip();
            clip.open(ais);
            fc = (FloatControl)clip.getControl(FloatControl.Type.MASTER_GAIN);
            checkVolume();
        }
        catch(Exception e){System.out.println(e);}
    }
    public void play(){
        
        clip.start();
    }
    public void loop(){

        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }
    public void stop(){

        clip.stop();
    }
    
    public void checkVolume(){
        
        switch(volumeScale){
            case 0: volume = -80f; break;
            case 1: volume = -21f; break;
            case 2: volume = -18f; break;
            case 3: volume = -15f; break;
            case 4: volume = -12f; break;
            case 5: volume = -9f; break;
            case 6: volume = -6f; break;
            case 7: volume = -3f; break;
            case 8: volume = 0f; break;
            case 9: volume = 3f; break;
            case 10: volume = 6f; break;
        }
        fc.setValue(volume);
    }
}
