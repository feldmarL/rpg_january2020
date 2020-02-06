import java.awt.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class Canvas extends JPanel {
    Image character,
          enemys,
          attack,
          trap,
          bonus;

    public Canvas() {
        try { // Set images for each entities on the map
            character = ImageIO.read(new File("images/hero.png"));
            enemys = ImageIO.read(new File("images/monster.png"));
            attack = ImageIO.read(new File("images/rune.png"));
            trap = ImageIO.read(new File("images/trap.png"));
            bonus = ImageIO.read(new File("images/potion.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D)g;

        Rune rune1 = GameMain.getInstance().getGame().getRune1();
        if (rune1.getX() != 0 && rune1.getY() != 0) // Paint rune number one if it's coordinates are different than 0, 0
            g2d.drawImage(attack, rune1.getX(), rune1.getY(), 20, 20, null);

        Rune rune2 = GameMain.getInstance().getGame().getRune2();
        if (rune2.getX() != 0 && rune2.getY() != 0) // Paint rune number two if it's coordinates are different than 0, 0
            g2d.drawImage(attack, rune2.getX(), rune2.getY(), 20, 20, null);

        Player player = GameMain.getInstance().getGame().getPlayer();
        if (!player.isDead()) // Paint player if still alive
            g2d.drawImage(character, player.getX(), player.getY(), 25, 25, null);
        
        for (int i = 0; i < GameMain.getInstance().getGame().getItemlist().size(); i++) {
            Item item = GameMain.getInstance().getGame().getItemlist().get(i);
            if (!item.isUsed()) { // Paint items according to it's type and if not already used
                if (item instanceof Bonus) {
                    Bonus potion = (Bonus) item;
                    g2d.drawImage(bonus, potion.getX(), potion.getY(), 15, 15, null);
                } else if (item instanceof Trap) {
                    Trap skull = (Trap) item;
                    g2d.drawImage(trap, skull.getX(), skull.getY(), 15, 15, null);
                }
            }
        }

        for (int i = 0; i < GameMain.getInstance().getGame().getMonsterList().size(); i++) {
            Monster monster = GameMain.getInstance().getGame().getMonsterList().get(i);
            if (!monster.isDead()) // Paint monsters in the list if allive
                g2d.drawImage(enemys, monster.getX(), monster.getY(), 25, 25, null);
        }
    }
    
    public void updateGame(){  // Method to repaint map (to update for changes to be seen)
        this.repaint();
    }
}