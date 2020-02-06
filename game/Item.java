import java.awt.*;

public class Item {
    private int x = 0;
    private int y = 0;
    private boolean used = false;

    public int getX() {
        return this.x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return this.y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Rectangle getRectItem() {
        return new Rectangle(x, y, 15, 15);
    }

    public boolean isUsed() {
        return this.used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }
}