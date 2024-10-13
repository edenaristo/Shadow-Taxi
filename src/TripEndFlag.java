import bagel.Image;
import bagel.Input;
import bagel.Keys;

import java.util.Properties;

/**
 * A class representing the trip end flag in the game play.
 * Objects of this class will only move up and down based on the keyboard input. No other functionalities needed.
 */
public class TripEndFlag extends GameObject {

    public TripEndFlag(int x, int y, Properties props) {
        this.x = x;
        this.y = y;
        this.moveY = 0;

        this.speedY = Integer.parseInt(props.getProperty("gameObjects.taxi.speedY"));
        this.radius = Float.parseFloat(props.getProperty("gameObjects.tripEndFlag.radius"));
        this.image = new Image(props.getProperty("gameObjects.tripEndFlag.image"));
    }

    /**
     * Move the object in y direction according to the keyboard input, and render the trip flag image.
     * @param input The current mouse/keyboard input.
     */
    public void update(Input input) {
        if(input != null) {
            adjustToInputMovement(input);
        }

        move();
        draw();
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
}
