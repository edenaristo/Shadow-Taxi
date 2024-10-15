import bagel.Image;
import bagel.Input;
import bagel.Keys;

import java.util.Properties;

/**
 * The Driver class represents a character in the game who can move both inside and outside the taxi.
 * It handles movement, collision detection, and the life cycle of the driver, including death animations and invincibility.
 */
public class Driver extends GameObject{

    private final int SPEED_X;
    private boolean isMovingY;
    private boolean isMovingX;
    private boolean isOutside;

    final Properties GAME_PROPS;

    private Blood blood;

    private boolean finishedDeath;


    public Driver(int x, int y, Properties props) {
        this.x = x;
        this.y = y;
        this.moveY = 0;
        isOutside = true;
        finishedDeath = false;
        invincibilityFrames = 0;

        this.SPEED_X = Integer.parseInt(props.getProperty("gameObjects.driver.walkSpeedX"));
        this.speedY = Integer.parseInt(props.getProperty("gameObjects.driver.walkSpeedY"));
        this.image = new Image(props.getProperty("gameObjects.driver.image"));
        this.radius = Float.parseFloat(props.getProperty("gameObjects.driver.radius"));
        this.health = Float.parseFloat(props.getProperty("gameObjects.driver.health"));
        GAME_PROPS = props;
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
            adjustToInputMovementOutside(input);
        }

        if (isAlive) {
            checkLife();
        }

        if (blood != null) {
            blood.update(input);
            if (!blood.isAlive()) {
                blood = null;
                finishedDeath = true;
            }
        }

        invincibilityFrames--;
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

    /**
     * Adjust the position based on the input movement when the driver is outside the vehicle.
     */
    public void adjustToInputMovementOutside(Input input) {
        if (input.isDown(Keys.UP)) {
            y -= speedY;
        } else if(input.isDown(Keys.DOWN)) {
            y += speedY;
        }
    }

    /**
     * Set the driver as outside the taxi
     */
    public void setOutside() {
        isOutside = true;
    }


    /**
     * Check for collision and do the logic for collision
     */
    @Override
    public void collide(GameObject object) {
        if (isAlive && object.isAlive() && this.hasCollidedWith(object) && object instanceof Taxi && isOutside){
            isOutside = false;
            this.x = object.x;
            this.y = object.y;
            ((Taxi) object).driverGoesIn();
        }
    }

    /**
     * Do the death animation which is spilling blood
     */
    public void deathAnimation() {
        blood = new Blood(this.x, this.y, GAME_PROPS);
    }

    /**
     * check whether the driver is still alive and update isalive
     */
    @Override
    public void checkLife() {
        if (health <= 0) {
            isAlive = false;
            deathAnimation();
        }
    }

    /**
     * check whether the death animation is finished
     */
    public boolean finishedDeath() {
        return finishedDeath;
    }

    /**
     * collect invincibility power and use it
     */
    public void collectInvincible(InvinciblePower star) {
        invincibilityFrames = star.getMAX_FRAMES();
    }

    /**
     * damage the driver when get hit
     */
    @Override
    public void hit(float damage) {
        if (isOutside) {
            this.health -= damage;
        }
    }
}
