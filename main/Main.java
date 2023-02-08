package main;

import javax.swing.JFrame;

public class Main extends JFrame{
    
    public static JFrame window;
    
    public Main(){
        
        window = new JFrame();
        GamePanel gamePanel = new GamePanel();
        window.add(gamePanel);
        //window.setUndecorated(true);
        window.pack();
        
        window.setTitle("Frightening Friendship");
        window.setResizable(false);
        window.setDefaultCloseOperation(EXIT_ON_CLOSE);
        window.setLocationRelativeTo(null);
        window.setVisible(true);
        
        gamePanel.setupGame();
        gamePanel.startGameThread();
    }
   
    public static void main(String[] args) {
        new Main();
    }
    
}
