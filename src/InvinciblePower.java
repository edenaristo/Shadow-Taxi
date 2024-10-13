import bagel.Image;
import bagel.Input;
import bagel.Keys;

import java.util.Properties;

public class InvinciblePower extends GameObject{
    private final int MAX_FRAMES;
    private boolean isCollided;

    public InvinciblePower(int x, int y, Properties props) {
        this.x = x;
        this.y = y;
        this.moveY = 0;

        this.speedY = Integer.parseInt(props.getProperty("gameObjects.taxi.speedY"));
        this.radius = Float.parseFloat(props.getProperty("gameObjects.invinciblePower.radius"));
        this.image = new Image(props.getProperty("gameObjects.invinciblePower.image"));
        this.MAX_FRAMES = Integer.parseInt(props.getProperty("gameObjects.invinciblePower.maxFrames"));
    }

    /**
     * Move the object in y direction according to the keyboard input, and render the coin image, before collision
     * happens with PowerCollectable objects. Once the collision happens, the coin active time will be increased.
     * @param input The current mouse/keyboard input.
     */
    public void update(Input input) {
        if(isCollided) {
            //framesActive++;
        } else {
            if(input != null) {
                adjustToInputMovement(input);
            }

            move();
            draw();
        }
    }

    /**
     * Move the GameObject object in the y-direction based on the speedY attribute.
     */
    public void move() {
        this.y += speedY * moveY;
    }

    /**
     * Draw the GameObject object into the screen.
     */
    public void draw() {
        image.draw(x, y);
    }

    /**
     * Adjust the movement direction in y-axis of the GameObject based on the keyboard input.
     * @param input The current mouse/keyboard input.
     */
    public void adjustToInputMovement(Input input) {
        if (input.wasPressed(Keys.UP)) {
            moveY = 1;
        }  else if(input.wasReleased(Keys.UP)) {
            moveY = 0;
        }
    }

    /**
     * Check if the coin has collided with any PowerCollectable objects, and power will be collected by PowerCollectable
     * object that is collided with.
     */
    public void collide(Taxi taxi) {
        if(hasCollidedWith(taxi)) {
            //taxi.collectPower(this);
            setIsCollided();
        }
    }
    /**
     * Check if the object is collided with another object based on the radius of the two objects.
     * @param taxi The taxi object to be checked.
     * @return True if the two objects are collided, false otherwise.
     */
    public boolean hasCollidedWith(Taxi taxi) {
        // if the distance between the two objects is less than the sum of their radius, they are collided
        float collisionDistance = radius + taxi.getRadius();
        float currDistance = (float) Math.sqrt(Math.pow(x - taxi.getX(), 2) + Math.pow(y - taxi.getY(), 2));
        return currDistance <= collisionDistance;
    }

    /**
     * Mark the status of the object as collided when it's collided with another object.
     * This will initiate the collision timeout.
     */
    public void setIsCollided() {
        this.isCollided = true;
    }
}
