import java.awt.*;

public class Entities {
    private int hp;
    private int y;
    private int x;

    public int getHp() {
        return this.hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getY() {
        return this.y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getX() {
        return this.x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public boolean isDead() {
        return hp <= 0;
    }

    public Rectangle getRectEntities() {
        return new Rectangle(x, y, 25, 25);
    }

    public void entityDelete() { // Set cooridnates to 0, 0 is the entity is dead
        if (isDead()) {
            setX(0);
            setY(0);
        }
    }
}