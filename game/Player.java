import java.util.List;
import java.awt.*;

public class Player extends Entities {

    public void damagePlayer(Player player, List<Monster> monsterlist) { // Damage player if a monster is at the same place
        int hp = player.getHp();
        Rectangle rp = player.getRectEntities();
        for (int i = 0; i < monsterlist.size(); i++) {
            if (monsterlist.get(i).getRectEntities().intersects(rp))
                hp -= 3;
            player.setHp(hp);
        }
    }

    public void useItem(Player player, Item item) { // Apply changes according to the type of the item the player is using
        if (item instanceof Bonus)
            player.setHp(player.getHp() + 20);
        else if (item instanceof Trap)
            player.setHp(player.getHp() - 20);
    }
}