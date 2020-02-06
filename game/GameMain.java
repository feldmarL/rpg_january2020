import java.io.*;
import javax.sound.sampled.*;

public class GameMain {
    private static GameMain instance;

    public static GameMain getInstance() {
        return instance;
    }
    public static void main(String[] args) {
        new Thread(() -> { // Start a new thread so the music can be played while the game is running too
            try {
                AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("sounds/foreststroll.wav").getAbsoluteFile());
                Clip clip = AudioSystem.getClip();
                clip.open(audioInputStream);
                clip.start();
            } catch(Exception ex) {
                System.out.println("Error with playing sound.");
                ex.printStackTrace();
            }
        }).start();
        (instance = new GameMain()).start(); // Launch the game
    }

    private Sgame game;
    private Window window;
    
    public void start(){
        window = new Window();
    }

    public Window getWindow() {
        return window;
    }

    public void setGame(Sgame game) {
        this.game = game;
    }

    public Sgame getGame() {
        return game;
    }
}