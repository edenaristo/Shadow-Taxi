import bagel.Font;
import bagel.Image;
import bagel.Input;
import bagel.Keys;

import java.util.Properties;

public class Particle extends GameObject {

    protected int duration;
    protected int taxiSpeed;

    public Particle(int x, int y, Properties props) {
        this.x = x;
        this.y = y;
        taxiSpeed = Integer.parseInt(props.getProperty("gameObjects.taxi.speedY"));
    }

    /**
     * Move the GameObject object in the y-direction based on the speedY attribute.
     */
    public void move() {
        this.y +=  moveY*taxiSpeed;
    }

    /**
     * Adjust the movement direction in y-axis of the GameObject based on the keyboard input.
     * @param input The current mouse/keyboard input.
     */
    public void adjustToInputMovement(Input input) {
        if (input.isDown(Keys.UP)) {
            moveY = 1;
        }  else  {
            moveY = 0;
        }
    }

    /**
     * Draw the GameObject object into the screen.
     */
    public void draw() {
        image.draw(x, y);
    }

    /**
     * Update the GameObject object's movement states based on the input.
     * Render the game object into the screen.
     * @param input The current mouse/keyboard input.
     */
    public void update(Input input) {
        adjustToInputMovement(input);
        checkLife();
        if (isAlive) {
            draw();
            move();
            duration--;
        }
    }

    @Override
    public void checkLife() {
        // check for out of bounds
        if (duration <= 0) {
            isAlive = false;
        }
    }
}

