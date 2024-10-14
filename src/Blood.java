import bagel.Font;
import bagel.Image;
import bagel.Input;
import bagel.Keys;

import java.util.Properties;

public class Blood extends Particle{

    public Blood(int x, int y, Properties props) {
        super(x, y, props);
        image = new Image(props.getProperty("gameObjects.blood.image"));
        duration = Integer.parseInt(props.getProperty("gameObjects.blood.ttl"));
    }
}
