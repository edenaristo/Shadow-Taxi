import bagel.Image;
import bagel.Input;
import bagel.Keys;

import java.util.Properties;

/**
 * A class representing the background of the game play.
 */
public class Background extends GameObject{

    private final int WINDOW_HEIGHT;
    private boolean isRainy;
    private Image imageRainy;
    private Image imageSunny;

    public Background(int x, int y, boolean initialRain, Properties props) {
        this.x = x;
        this.y = y;
        this.moveY = 0;

        this.speedY = Integer.parseInt(props.getProperty("gameObjects.taxi.speedY"));
        this.imageSunny = new Image(props.getProperty("backgroundImage.sunny"));
        this.imageRainy = new Image(props.getProperty("backgroundImage.raining"));
        isRainy = initialRain;

        this.WINDOW_HEIGHT = Integer.parseInt(props.getProperty("window.height"));
    }

    /**
     * Move the background in y direction according to the keyboard input. And render the background image.
     * @param input The current mouse/keyboard input.
     */
    public void update(Input input, Background background) {
        if(input != null) {
            adjustToInputMovement(input);
        }

        if (isRainy) {
            image = imageRainy;
        } else {
            image = imageSunny;
        }

        move();
        draw();

        if (y >= WINDOW_HEIGHT * 1.5) {
            y = background.getY() - WINDOW_HEIGHT;
        }
    }

    /**
     * Move the GameObject object in the y-direction based on the speedY attribute.
     */
    public void move() {
        this.y += speedY * moveY;
    }

    /**
     * Switch the background between rainy and sunny
     */
    public void switchWeather() {
        isRainy = !isRainy;
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
