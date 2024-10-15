import bagel.*;

import java.util.Properties;

public abstract class GameObject {
    protected int x;
    protected int y;
    protected int moveY;

    protected int speedY;
    protected float radius;
    protected Image image;

    protected float health;
    protected float damage;
    protected boolean isAlive = true;
    protected boolean hasCollided = false;

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

    public void collide(GameObject object) {
        if (isAlive && object.isAlive() && this.hasCollidedWith(object) && object != this){
            object.hit(damage);
            this.hasCollided = true;
        }
    }

    public boolean hasCollidedWith(GameObject object) {
        float collisionDistance = radius + object.getRadius();
        float currDistance = (float) Math.sqrt(Math.pow(x - object.getX(), 2) + Math.pow(y - object.getY(), 2));
        return currDistance <= collisionDistance;
    }

    public void hit(float damage) {
        this.health -= damage;
    }

    public boolean hasCollided() {
        return hasCollided;
    }

    public void checkLife() {
        if (health <= 0) {
            isAlive = false;
        }
    }

    public boolean isAlive() {
        return isAlive;
    }

    public float getHealth() {
        return health;
    }
}
