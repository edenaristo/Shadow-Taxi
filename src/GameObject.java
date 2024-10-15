import bagel.*;

/**
 * The GameObject class is an abstract base class for all game objects in the game.
 * It provides common attributes such as position, health, damage, and collision detection,
 * and handles basic functionalities like movement, drawing, and checking life status.
 */
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

    protected int invincibilityFrames = 0;

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

    /**
     * check for collision and do the collision logic
     * @param object object of collision
     */
    public void collide(GameObject object) {
        if (isAlive && object.isAlive() && this.hasCollidedWith(object) && object != this && !object.isInvincible()){
            object.hit(damage);
            this.hasCollided = true;
        }
    }

    /**
     * check whether the collision has occured based on the distance between the objects.
     */
    public boolean hasCollidedWith(GameObject object) {
        float collisionDistance = radius + object.getRadius();
        float currDistance = (float) Math.sqrt(Math.pow(x - object.getX(), 2) + Math.pow(y - object.getY(), 2));
        return currDistance <= collisionDistance;
    }

    /**
     * hit the object and take damage
     */
    public void hit(float damage) {
        this.health -= damage;
    }

    /**
     * getter for hascollided
     */
    public boolean hasCollided() {
        return hasCollided;
    }

    /**
     * check and updates the life
     */
    public void checkLife() {
        if (health <= 0) {
            isAlive = false;
        }
    }

    /**
     * getter for isalive
     */
    public boolean isAlive() {
        return isAlive;
    }

    /**
     * getter for health
     */
    public float getHealth() {
        return health;
    }

    /**
     * getter for isinvincible
     */
    public boolean isInvincible() {
        return invincibilityFrames > 0;
    }
}
