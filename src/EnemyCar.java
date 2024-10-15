import bagel.Font;
import bagel.Image;
import bagel.Input;
import bagel.Keys;

import java.util.Properties;

public class EnemyCar extends Car {

    public EnemyCar(Properties props) {
        super(props);
        health = Float.parseFloat(props.getProperty("gameObjects.enemyCar.health"));
        damage = Float.parseFloat(props.getProperty("gameObjects.enemyCar.damage"));
        radius = Float.parseFloat(props.getProperty("gameObjects.enemyCar.radius"));

        taxiSpeed = Integer.parseInt(props.getProperty("gameObjects.taxi.speedY"));
        image = new Image(props.getProperty("gameObjects.enemyCar.image"));
    }

    /**
     * Check if it is valid to spawn car using a randomized number
     */
    public static boolean canSpawnCar() {
        return MiscUtils.canSpawn(400);
    }

    /**
     * Check if the enemy car can shoot fireball
     */
    public boolean canShootFireball() {
        return MiscUtils.canSpawn(300);
    }

    /**
     * Shoot fireball by spawning one
     */
    public Fireball shootFireball(Properties props) {
        return new Fireball(x, y, this, props);
    }
}