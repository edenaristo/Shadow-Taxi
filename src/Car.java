import bagel.Font;
import bagel.Image;
import bagel.Input;
import bagel.Keys;

import java.util.Properties;

public class  Car extends GameObject{
    protected int taxiSpeed;
    protected float health;
    protected float damage;
    protected int speedY;

    public Car(Properties props) {

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
        taxiSpeed = Integer.parseInt(props.getProperty("gameObjects.taxi.speedY"));

        // assign the image randomly between 2 images
        image = new Image((String.format(props.getProperty("gameObjects.otherCar.image"), MiscUtils.getRandomInt(0, Integer.parseInt(props.getProperty("gameObjects.otherCar.types"))) + 1)));

        // randomly assign the speed of the car
        speedY = MiscUtils.getRandomInt(Integer.parseInt(props.getProperty("gameObjects.otherCar.minSpeedY")), Integer.parseInt(props.getProperty("gameObjects.otherCar.maxSpeedY")));

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
        this.y += (taxiSpeed * moveY) - speedY;
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

    /**
     * Check if it is valid to spawn car using a randomized number
     */
    public static boolean canSpawnCar() {
        return MiscUtils.canSpawn(200);
    }


}
