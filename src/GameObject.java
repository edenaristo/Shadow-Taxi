import bagel.*;

import java.util.Properties;

public abstract class GameObject {
    protected int x;
    protected int y;
    protected int moveY;

    protected int speedY;
    protected float radius;
    protected Image image;

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public float getRadius() {
        return radius;
    }

    protected void draw() {
        image.draw(x, y);
    }
}
