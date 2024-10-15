import bagel.Image;

import java.util.Properties;

/**
 * The Fire class represents a fire particle effect in the game.
 * It extends the Particle class and uses properties to define its image and duration.
 */
public class Fire extends Particle{

    public Fire(int x, int y, Properties props) {
        super(x, y, props);
        image = new Image(props.getProperty("gameObjects.fire.image"));
        duration = Integer.parseInt(props.getProperty("gameObjects.fire.ttl"));
    }
}
