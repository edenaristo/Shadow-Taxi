import bagel.Image;
import bagel.Input;
import bagel.Keys;

import java.util.Properties;

public class Driver extends GameObject{

    private final int SPEED_X;
    private boolean isMovingY;
    private boolean isMovingX;
    private boolean isOutside;


    public Driver(int x, int y, Properties props) {
        this.x = x;
        this.y = y;
        this.moveY = 0;
        isOutside = true;

        this.SPEED_X = Integer.parseInt(props.getProperty("gameObjects.driver.walkSpeedX"));
        this.speedY = Integer.parseInt(props.getProperty("gameObjects.driver.walkSpeedY"));
        this.image = new Image(props.getProperty("gameObjects.driver.image"));
        this.radius = Float.parseFloat(props.getProperty("gameObjects.driver.radius"));
    }

    public boolean isMovingY() {
        return isMovingY;
    }

    public boolean isMovingX() {
        return isMovingX;
    }


    /**
     * Update the GameObject object's movement states based on the input.
     * Render the game object into the screen.
     * @param input The current mouse/keyboard input.
     */
    public void update(Input input) {
        if(input != null) {
            adjustToInputMovement(input);
        }

        if (isOutside) {
            draw();
        }
    }

    /**
     * Draw the GameObject object into the screen.
     */
    public void draw() {
        image.draw(x, y);
    }

    /**
     * Adjust the movement of the taxi based on the keyboard input.
     * If the taxi has a driver, and taxi has health>0 the taxi can only move left and right (fixed in y direction).
     * If the taxi does not have a driver, the taxi can move in all directions.
     * @param input The current mouse/keyboard input.
     */
    public void adjustToInputMovement(Input input) {
        if (input.wasPressed(Keys.UP)) {
            isMovingY = true;
        }  else if(input.wasReleased(Keys.UP)) {
            isMovingY = false;
        } else if(input.isDown(Keys.LEFT)) {
            x -= SPEED_X;
            isMovingX = true;
        }  else if(input.isDown(Keys.RIGHT)) {
            x += SPEED_X;
            isMovingX =  true;
        } else if(input.wasReleased(Keys.LEFT) || input.wasReleased(Keys.RIGHT)) {
            isMovingX = false;
        }
    }

    public void setOutside() {
        isOutside = true;
    }
}
