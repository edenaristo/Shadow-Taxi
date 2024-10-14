import bagel.Font;
import bagel.Image;
import bagel.Input;
import bagel.Keys;

import java.util.Properties;

public class Smoke extends Particle{

    public Smoke(int x, int y, Properties props) {
        super(x, y, props);
        image = new Image(props.getProperty("gameObjects.smoke.image"));
        duration = Integer.parseInt(props.getProperty("gameObjects.smoke.ttl"));
    }
}
