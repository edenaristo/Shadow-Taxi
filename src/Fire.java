import bagel.Font;
import bagel.Image;
import bagel.Input;
import bagel.Keys;

import java.util.Properties;

public class Fire extends Particle{

    public Fire(int x, int y, Properties props) {
        super(x, y, props);
        image = new Image(props.getProperty("gameObjects.fire.image"));
        duration = Integer.parseInt(props.getProperty("gameObjects.fire.ttl"));
    }
}
