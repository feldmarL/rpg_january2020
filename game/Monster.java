import java.awt.*;
import java.util.List;

public class Monster extends Entities {

    public void moveMonster(Monster monster, Player player) { // Move the monster in the player's direction
        int x1 = monster.getX(),
            y1 = monster.getY();
        if (!monster.isDead()) {
            if (x1 < player.getX()) {
                x1 += 1;
            }
            if (x1 > player.getX()) {
                x1 -= 1;
            }
            if (y1 < player.getY()) {
                y1 += 1;
            }
            if (y1 > player.getY()) {
                y1 -= 1;
            }
        }
        monster.setX(x1);
        monster.setY(y1);
    }

    public void damageMonster(Monster monster, Rune rune1, Rune rune2) { // Damage monster if it's on a rune
        int hp = monster.getHp();
        Rectangle rr1 = rune1.getRectRune();
        Rectangle rr2 = rune2.getRectRune();
        boolean runeused1 = false,
                runeused2 = false;
        if (monster.getRectEntities().intersects(rr1)){
            hp -= 34;
            runeused1 = true;
        }
        if (monster.getRectEntities().intersects(rr2)) {
            hp -= 34;
            runeused2 = true;
        }
        monster.setHp(hp);

        if(runeused1 == true) {
            rune1.setX(0);
            rune1.setY(0);
        }

        if (runeused2 == true){
            rune2.setX(0);
            rune2.setY(0);
        }
    }
}