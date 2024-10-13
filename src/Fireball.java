import bagel.Font;
import bagel.Image;
import bagel.Input;
import bagel.Keys;

import java.util.Properties;

public class Fireball extends GameObject{
    private int taxiSpeed;
    private float damage;
    private int speedY;
    private boolean hasCollided;

    public Fireball(int x, int y, Properties props) {
        this.x = x;
        this.y = y;
        image = new Image(props.getProperty("gameObjects.fireball.image"));
        radius = Float.parseFloat(props.getProperty("gameObjects.fireball.radius"));
        damage = Float.parseFloat(props.getProperty("gameObjects.fireball.damage"));
        speedY = Integer.parseInt(props.getProperty("gameObjects.fireball.shootSpeedY"));
        taxiSpeed = Integer.parseInt(props.getProperty("gameObjects.taxi.speedY"));
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
        this.y +=  - speedY + (taxiSpeed * moveY);
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
     * Update the GameObject object's movement states based on the input.
     * Render the game object into the screen.
     * @param input The current mouse/keyboard input.
     */
    public void update(Input input) {
        // check if out of bounds
        if (x < 0) {
            hasCollided = true;
        }
        adjustToInputMovement(input);
        move();

        if (!hasCollided) {
            draw();
        }
    }

    /*
     * check for collision with taxi
     */
    public void collide(Taxi taxi) {
        taxi.hit(damage);
    }

    /*
     * check for collision with driver
     */
    public void collide(Driver driver) {
        driver.hit(damage);
    }

    /*
     * check for collision with enemyCar
     */
    public void collide(EnemyCar enemyCar) {
        enemyCar.hit(damage);
    }

    /*
     * check for collision with car
     */
    public void collide(Car car) {

    }

    /*
     * check for collision with passenger
     */
    public void collide(Passenger passenger) {

    }
}
