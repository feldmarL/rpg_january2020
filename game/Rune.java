import java.awt.*;

public class Rune {
    private int x,
                y;

    public int getX()
    {
		return this.x;
	}

    public void setX(int x)
    {
		this.x = x;
	}

    public int getY() {
        return this.y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Rectangle getRectRune() {
        return new Rectangle(x, y, 20, 20);
    }

}