import bagel.Image;

import java.util.Properties;

/**
 * The Smoke class represents a smoke particle effect in the game.
 * It extends the Particle class and uses properties to define its image and duration.
 */
public class Smoke extends Particle{

    public Smoke(int x, int y, Properties props) {
        super(x, y, props);
        image = new Image(props.getProperty("gameObjects.smoke.image"));
        duration = Integer.parseInt(props.getProperty("gameObjects.smoke.ttl"));
    }
}
