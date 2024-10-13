import bagel.Font;
import bagel.Image;
import bagel.Input;
import bagel.Keys;

import java.util.Properties;

public abstract class Car extends GameObject{
    protected float health;
    protected float damage;
    protected int speedY;

    // random number generator to check if it is valid to spawn a car
    public static boolean canSpawnCar() {
        return MiscUtils.canSpawn(200);
    }

    public abstract void update(Input input);
}
