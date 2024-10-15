import bagel.Image;

import java.util.Properties;


/**
 * The Blood class represents a particle effect in the game that simulates blood.
 * It extends the Particle class and uses properties to initialize its image and duration.
 */
public class Blood extends Particle{

    public Blood(int x, int y, Properties props) {
        super(x, y, props);
        image = new Image(props.getProperty("gameObjects.blood.image"));
        duration = Integer.parseInt(props.getProperty("gameObjects.blood.ttl"));
    }
}
