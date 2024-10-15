import bagel.Image;
import bagel.Input;
import bagel.Keys;

import java.util.Properties;

/**
 * The class representing the taxis in the game play
 */
public class Taxi extends GameObject{

    private static Trip[] TRIPS;
    private static int tripCount;
    private static Trip trip;

    private final int SPEED_X;
    private boolean isMovingY;
    private boolean isMovingX;

    private Coin coinPower;

    private Smoke smoke;
    private Fire fire;
    private final Properties GAME_PROPS;

    private int timeoutTimer;
    private final int TIMEOUT_FRAME = 200;
    private final int TIMEOUT_ANIMATION_FRAME = 10;
    private boolean timeoutPositionOnTop;

    private int driverSpeed;

    private Image damagedImage;
    private boolean isDriverless;

    public Taxi(int x, int y, int maxTripCount, Properties props) {
        this.x = x;
        this.y = y;
        if (TRIPS == null) {
            TRIPS = new Trip[maxTripCount];
        }

        this.SPEED_X = Integer.parseInt(props.getProperty("gameObjects.taxi.speedX"));
        this.image = new Image(props.getProperty("gameObjects.taxi.image"));
        this.radius = Float.parseFloat(props.getProperty("gameObjects.taxi.radius"));
        this.health = Float.parseFloat(props.getProperty("gameObjects.taxi.health"));
        this.damage = Float.parseFloat(props.getProperty("gameObjects.taxi.damage"));

        GAME_PROPS = props;
        smoke = null;
        fire = null;
        timeoutTimer = 0;

        damagedImage = new Image(props.getProperty("gameObjects.taxi.damagedImage"));
        driverSpeed = Integer.parseInt(props.getProperty("gameObjects.taxi.speedY"));

        isDriverless = true;
    }

    public boolean isMovingY() {
        return isMovingY;
    }

    public boolean isMovingX() {
        return isMovingX;
    }

    /**
     * If it's a new trip, it will added to the list of trips.
     * @param trip trip object
     */
    public void setTrip(Trip trip) {
        this.trip = trip;
        if(trip != null) {
            this.TRIPS[tripCount] = trip;
            tripCount++;
        }
    }

    public Trip getTrip() {
        return this.trip;
    }

    /**
     * Get the last trip from the list of trips.
     * @return Trip object
     */
    public Trip getLastTrip() {
        if(tripCount == 0) {
            return null;
        }
        return TRIPS[tripCount - 1];
    }

    /**
     * Update the GameObject object's movement states based on the input.
     * Render the game object into the screen.
     * @param input The current mouse/keyboard input.
     */
    public void update(Input input) {
        if (timeoutTimer >= TIMEOUT_FRAME - TIMEOUT_ANIMATION_FRAME) {
            // timeout animation
            if (timeoutPositionOnTop) {
                y--;
            } else {
                y++;
            }
        } else if (isAlive && !isDriverless){
            // general movement
            if(input != null) {
                adjustToInputMovement(input);
            }
        } else {
            adjustToInputMovementDead(input);
        }
        // if the taxi has coin power, apply the effect of the coin on the priority of the passenger
        // (See the logic in TravelPlan class)
        if (trip != null && coinPower != null) {
            TravelPlan tp = trip.getPassenger().getTravelPlan();
            int newPriority = tp.getPriority();
            if(!tp.getCoinPowerApplied()) {
                newPriority = coinPower.applyEffect(tp.getPriority());
            }
            if(newPriority < tp.getPriority()) {
                tp.setCoinPowerApplied();
            }
            tp.setPriority(newPriority);
        }

        if(trip != null && trip.hasReachedEnd()) {
            getTrip().end();
        }

        draw();
        if (isAlive) {
            checkLife();
            // the flag of the current trip renders to the screen
            if(tripCount > 0) {
                Trip lastTrip = TRIPS[tripCount - 1];
                if (lastTrip != null) {
                    if(!lastTrip.getPassenger().hasReachedFlag()) {
                        lastTrip.getTripEndFlag().update(input);
                    }
                }
            }
        }

        if (smoke != null) {
            smoke.update(input);
            if (!smoke.isAlive()) {
                smoke = null;
            }
        }

        if (fire != null) {
            fire.update(input);
                if (!fire.isAlive()){
                    fire = null;
                }
        }

        timeoutTimer--;
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

    public void adjustToInputMovementDead(Input input) {
        if (input.isDown(Keys.UP)) {
            y += driverSpeed;
        }
    }

    public void collectPower(Coin coin) {
        coinPower = coin;
    }

    /**
     * Calculate total earnings. (See how fee is calculated for each trip in Trip class.)
     * @return int, total earnings
     */
    public float calculateTotalEarnings() {
        float totalEarnings = 0;
        for(Trip trip : TRIPS) {
            if (trip != null) {
                totalEarnings += trip.getFee();
            }
        }
        return totalEarnings;
    }

    @Override
    public void hit(float damage) {
        super.hit(damage);
        smoke = new Smoke(this.x, this.y, GAME_PROPS);
    }

    @Override
    public void collide(GameObject object) {
        if (isAlive && object.isAlive() && this.hasCollidedWith(object) && timeoutTimer <= 0) {
            object.hit(damage);
            timeoutTimer = TIMEOUT_FRAME;
            timeoutPositionOnTop = (this.y < object.y);
        }
    }

    private void deathAnimation() {
        image = damagedImage;
        fire = new Fire(this.x, this.y, GAME_PROPS);
    }

    @Override
    public void checkLife() {
        if (health <= 0) {
            isAlive = false;
            deathAnimation();
        }
    }

    public void driverGoesIn() {
        isDriverless = false;
    }
}
