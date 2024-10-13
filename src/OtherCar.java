import bagel.Font;
import bagel.Image;
import bagel.Input;
import bagel.Keys;

import java.util.Properties;

public class OtherCar extends Car{
    public OtherCar(Properties props) {

        // randomly assign road lane
        switch (MiscUtils.getRandomInt(0,3)) {
            case 0:
                x = 360;
                break;
            case 1:
                x = 480;
                break;
            case 2:
                x = 620;
                break;
        }

        // randomly assign y position
        this.y = MiscUtils.selectAValue(0, Integer.parseInt(props.getProperty("window.height")));

        this.moveY = 0;

        health = Float.parseFloat(props.getProperty("gameObjects.otherCar.health"));
        damage = Float.parseFloat(props.getProperty("gameObjects.otherCar.damage"));
        radius = Float.parseFloat(props.getProperty("gameObjects.otherCar.radius"));
        // assign the image randomly between 2 images
        image = new Image((String.format(props.getProperty("gameObjects.otherCar.image"), MiscUtils.getRandomInt(0, Integer.parseInt(props.getProperty("gameObjects.otherCar.types"))) + 1)));

        // randomly assign the speed of the car
        speedY = Integer.parseInt(props.getProperty("gameObjects.taxi.speedY"));

    }

    /**
     * Draw the GameObject object into the screen.
     */
    public void draw() {
        image.draw(x, y);
    }

    /**
     * Move the GameObject object in the y-direction based on the speedY attribute.
     */
    public void move() {
        this.y += speedY * moveY;
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
     * Update the GameObject object's movement states based on the input.
     * Render the game object into the screen.
     * @param input The current mouse/keyboard input.
     */
    public void update(Input input) {
        if(input != null) {
            adjustToInputMovement(input);
        }
        move();
        draw();
    }



}
