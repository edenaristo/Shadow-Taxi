import bagel.Font;
import bagel.Input;
import java.util.ArrayList;

import java.util.*;

/**
 * Represents the gameplay screen in the game.
 */
public class GamePlayScreen extends Screen{
    private final Properties GAME_PROPS;
    private final Properties MSG_PROPS;

    // keep track of earning and coin timeout
    private float totalEarnings;
    private float coinFramesActive;

    private int currFrame = 0;
    private int[] rainIntervals;

    // game objects
    private Taxi taxi;
    private Driver driver;
    private Passenger[] passengers;
    private Coin[] coins;
    private InvinciblePower[] invinciblePowers;
    private ArrayList<Car> cars;
    private ArrayList<EnemyCar> enemyCars;
    private ArrayList<Fireball> fireballs;
    private ArrayList<Particle> particles;
    private Background background1;
    private Background background2;

    private final float TARGET;
    private final int MAX_FRAMES;

    // vars for save score into the file
    private final String PLAYER_NAME;
    private boolean savedData;

    // display text vars
    private final Font INFO_FONT;
    private final int EARNINGS_Y;
    private final int EARNINGS_X;
    private final int COIN_X;
    private final int COIN_Y;
    private final int TARGET_X;
    private final int TARGET_Y;
    private final int MAX_FRAMES_X;
    private final int MAX_FRAMES_Y;

    private final int TRIP_INFO_X;
    private final int TRIP_INFO_Y;
    private final int TRIP_INFO_OFFSET_1;
    private final int TRIP_INFO_OFFSET_2;
    private final int TRIP_INFO_OFFSET_3;

    public GamePlayScreen(Properties gameProps, Properties msgProps, String playerName) {
        this.GAME_PROPS = gameProps;
        this.MSG_PROPS = msgProps;

        // read game objects from file and weather file and populate the game objects and weather conditions
        ArrayList<String[]> objectLines = IOUtils.readCommaSeperatedFile(gameProps.getProperty("gamePlay.objectsFile"));
        ArrayList<String[]> weatherLines = IOUtils.readCommaSeperatedFile(gameProps.getProperty("gamePlay.weatherFile"));
        populateGameObjects(objectLines);
        populateBackground(weatherLines);
        cars = new ArrayList<>();
        enemyCars = new ArrayList<>();
        fireballs = new ArrayList<>();
        particles = new ArrayList<>();

        this.TARGET = Float.parseFloat(gameProps.getProperty("gamePlay.target"));
        this.MAX_FRAMES = Integer.parseInt(gameProps.getProperty("gamePlay.maxFrames"));

        // display text vars
        INFO_FONT = new Font(gameProps.getProperty("font"), Integer.parseInt(
                gameProps.getProperty("gameplay.info.fontSize")));
        EARNINGS_Y = Integer.parseInt(gameProps.getProperty("gameplay.earnings.y"));
        EARNINGS_X = Integer.parseInt(gameProps.getProperty("gameplay.earnings.x"));
        COIN_X = Integer.parseInt(gameProps.getProperty("gameplay.coin.x"));
        COIN_Y = Integer.parseInt(gameProps.getProperty("gameplay.coin.y"));
        TARGET_X = Integer.parseInt(gameProps.getProperty("gameplay.target.x"));
        TARGET_Y = Integer.parseInt(gameProps.getProperty("gameplay.target.y"));
        MAX_FRAMES_X = Integer.parseInt(gameProps.getProperty("gameplay.maxFrames.x"));
        MAX_FRAMES_Y = Integer.parseInt(gameProps.getProperty("gameplay.maxFrames.y"));

        // current trip info vars
        TRIP_INFO_X = Integer.parseInt(gameProps.getProperty("gameplay.tripInfo.x"));
        TRIP_INFO_Y = Integer.parseInt(gameProps.getProperty("gameplay.tripInfo.y"));
        TRIP_INFO_OFFSET_1 = 30;
        TRIP_INFO_OFFSET_2 = 60;
        TRIP_INFO_OFFSET_3 = 90;

        this.PLAYER_NAME = playerName;
    }

    /**
     * Populate the weathers from the lines read from the weather file.
     * @param lines list of lines read from the game objects file. lines are processed into String arrays using comma as
     *             delimiter.
     */
    private void populateBackground(ArrayList<String[]> lines) {

        int numberOfIntervals = lines.size();
        rainIntervals = new int[numberOfIntervals];

        boolean initialRain;
        if (lines.get(0)[0].equals(WeatherType.RAINING.name())){
            initialRain = true;
        } else if (lines.get(0)[0].equals(WeatherType.SUNNY.name())){
            initialRain = false;
        } else {
            initialRain = true;
        }

        int i = 0;
        for(String[] lineElement: lines) {
            rainIntervals[i] = Integer.parseInt(lineElement[2]);
            i++;
        }

        // two background images stacked in y-axis are used to create a scrolling effect
        background1 = new Background(
                Integer.parseInt(GAME_PROPS.getProperty("window.width")) / 2,
                Integer.parseInt(GAME_PROPS.getProperty("window.height")) / 2, initialRain,
                GAME_PROPS);
        background2 = new Background(
                Integer.parseInt(GAME_PROPS.getProperty("window.width")) / 2,
                -1 * Integer.parseInt(GAME_PROPS.getProperty("window.height")) / 2, initialRain,
                GAME_PROPS);
    }

    /**
     * Populate the game objects from the lines read from the game objects file.
     * @param lines list of lines read from the game objects file. lines are processed into String arrays using comma as
     *             delimiter.
     */
    private void populateGameObjects(ArrayList<String[]> lines) {

        // Since you haven't learned Lists in Java, we have to use two for loops to iterate over the lines.
        int passengerCount = 0;
        int coinCount = 0;
        int invinciblePowerCount = 0;
        for(String[] lineElement: lines) {
            if(lineElement[0].equals(GameObjectType.PASSENGER.name())) {
                passengerCount++;
            } else if(lineElement[0].equals(GameObjectType.COIN.name())) {
                coinCount++;
            } else if(lineElement[0].equals(GameObjectType.INVINCIBLE_POWER.name())) {
                invinciblePowerCount++;
            }
        }
        passengers = new Passenger[passengerCount];
        coins = new Coin[coinCount];
        invinciblePowers = new InvinciblePower[invinciblePowerCount];

        // process each line in the file
        int passenger_idx = 0;
        int coin_idx = 0;
        int invinciblePower_idx = 0;
        for(String[] lineElement: lines) {
            int x = Integer.parseInt(lineElement[1]);
            int y = Integer.parseInt(lineElement[2]);

            if(lineElement[0].equals(GameObjectType.TAXI.name())) {
                taxi = new Taxi(x, y, passengerCount, this.GAME_PROPS);
            } else if(lineElement[0].equals(GameObjectType.DRIVER.name())) {
                driver = new Driver(x, y, this.GAME_PROPS);
            } else if(lineElement[0].equals(GameObjectType.PASSENGER.name())) {
                int priority = Integer.parseInt(lineElement[3]);
                int travelEndX = Integer.parseInt(lineElement[4]);
                int travelEndY = Integer.parseInt(lineElement[5]);
                boolean hasUmbrella = Integer.parseInt(lineElement[6]) == 1;

                Passenger passenger = new Passenger(x, y, priority, travelEndX, travelEndY, hasUmbrella, GAME_PROPS);
                passengers[passenger_idx] = passenger;
                passenger_idx++;

            } else if(lineElement[0].equals(GameObjectType.COIN.name())) {
                Coin coinPower = new Coin(x, y, this.GAME_PROPS);
                coins[coin_idx] = coinPower;
                coin_idx++;
            } else if(lineElement[0].equals(GameObjectType.INVINCIBLE_POWER.name())) {
                InvinciblePower invinciblePower = new InvinciblePower(x, y, this.GAME_PROPS);
                invinciblePowers[invinciblePower_idx] = invinciblePower;
                invinciblePower_idx++;
            }
        }
    }

    /**
     * Update the states of the game objects based on the keyboard input.
     * Handle the spawning of other cars in random intervals
     * Change the background image and change priorities based on the weather condition
     * Handle collision between game objects
     * Spawn new taxi if the active taxi is destroyed
     * @param input
     * @return true if the game is finished, false otherwise
     */
    @Override
    public boolean update(Input input) {
        currFrame++;

        for (int interval : rainIntervals) {
            if (currFrame == interval) {
                background1.switchWeather();
                background2.switchWeather();
            }
        }

        background1.update(input, background2);
        background2.update(input, background1);

        // spawning process for car
        if (Car.canSpawnCar()) {
            Car car = new Car(GAME_PROPS);
            cars.add(car);
        }

        //spawning process for enemy car
        if (EnemyCar.canSpawnCar()) {
            EnemyCar enemyCar = new EnemyCar(GAME_PROPS);
            enemyCars.add(enemyCar);
        }

        for(Passenger passenger: passengers) {
            passenger.updateWithTaxi(input, taxi);
        }

        taxi.update(input);
        driver.update(input);
        totalEarnings = taxi.calculateTotalEarnings();

        // update cars
        for (Car car : cars) {
            car.update(input);
        }

        // update enemy cars
        for (EnemyCar enemyCar : enemyCars) {
            enemyCar.update(input);
            if (enemyCar.canShootFireball()){
                Fireball fireball = enemyCar.shootFireball(GAME_PROPS);
                fireballs.add(fireball);
            }
        }

        // update fireballs
        for (Fireball fireball : fireballs) {
            fireball.update(input);
        }

        // update coins
        if(coins.length > 0) {
            int minFramesActive = coins[0].getMaxFrames();
            for(Coin coinPower: coins) {
                coinPower.update(input);
                coinPower.collide(taxi);

                // check if there's active coin and finding the coin with maximum ttl
                int framesActive = coinPower.getFramesActive();
                if(coinPower.getIsActive() && minFramesActive > framesActive) {
                    minFramesActive = framesActive;
                }
            }
            coinFramesActive = minFramesActive;
        }

        // update invincibility
        if(invinciblePowers.length > 0) {
            for (InvinciblePower invinciblePower : invinciblePowers) {
                invinciblePower.update(input);
                invinciblePower.collide(taxi);
                /// invinciblePower.collide(driver);
            }
        }

        // update effects
        for (Particle particle : particles) {
            particle.update(input);
        }

        // COLLISION LOGIC: make a double loop for each of the gameobjects

        // Driver
//        driver.collide(taxi);
//
//        // Taxi
//        for (EnemyCar enemyCar : enemyCars) {
//            taxi.collide(enemyCar);
//        }
//        for (Car car : cars) {
//            taxi.collide(car);
//        }

        // Fireball
        for (Fireball fireball : fireballs) {
            if (!fireball.hasCollided()) {
                fireball.collide(taxi);
                fireball.collide(driver);
                for (EnemyCar enemyCar : enemyCars) {
                    fireball.collide(enemyCar);
                }
                for (Car car : cars) {
                    fireball.collide(car);
                }
                for (Passenger passenger : passengers) {
                    fireball.collide(passenger);
                }
            }
        }

        // Car
//        for (Car car : cars) {
//            car.collide(taxi);
//            car.collide(driver);
//            for (EnemyCar enemyCar : enemyCars) {
//                car.collide(enemyCar);
//            }
//            for (Car car2 : cars) {
//                car.collide(car2);
//            }
//            for (Passenger passenger : passengers) {
//                car.collide(passenger);
//            }
//        }
//
//        // EnemyCar
//        for (EnemyCar enemyCar : enemyCars) {
//            enemyCar.collide(taxi);
//            enemyCar.collide(driver);
//            for (EnemyCar enemyCar2 : enemyCars) {
//                enemyCar.collide(enemyCar2);
//            }
//            for (Car car : cars) {
//                enemyCar.collide(car);
//            }
//            for (Passenger passenger : passengers) {
//                enemyCar.collide(passenger);
//            }
//        }

        displayInfo();

        return isGameOver() || isLevelCompleted();

    }

    /**
     * Display the game information on the screen.
     */
    public void displayInfo() {
        INFO_FONT.drawString(MSG_PROPS.getProperty("gamePlay.earnings") + getTotalEarnings(), EARNINGS_X, EARNINGS_Y);
        INFO_FONT.drawString(MSG_PROPS.getProperty("gamePlay.target") + String.format("%.02f", TARGET), TARGET_X,
                TARGET_Y);
        INFO_FONT.drawString(MSG_PROPS.getProperty("gamePlay.remFrames") + (MAX_FRAMES - currFrame), MAX_FRAMES_X,
                MAX_FRAMES_Y);

        if(coins.length > 0 && coins[0].getMaxFrames() != coinFramesActive) {
            INFO_FONT.drawString(String.valueOf(Math.round(coinFramesActive)), COIN_X, COIN_Y);
        }

        Trip lastTrip = taxi.getLastTrip();
        if(lastTrip != null) {
            if(lastTrip.isComplete()) {
                INFO_FONT.drawString(MSG_PROPS.getProperty("gamePlay.completedTrip.title"), TRIP_INFO_X, TRIP_INFO_Y);
            } else {
                INFO_FONT.drawString(MSG_PROPS.getProperty("gamePlay.onGoingTrip.title"), TRIP_INFO_X, TRIP_INFO_Y);
            }
            INFO_FONT.drawString(MSG_PROPS.getProperty("gamePlay.trip.expectedEarning")
                    + lastTrip.getPassenger().getTravelPlan().getExpectedFee(), TRIP_INFO_X, TRIP_INFO_Y
                    + TRIP_INFO_OFFSET_1);
            INFO_FONT.drawString(MSG_PROPS.getProperty("gamePlay.trip.priority")
                    + lastTrip.getPassenger().getTravelPlan().getPriority(), TRIP_INFO_X, TRIP_INFO_Y
                    + TRIP_INFO_OFFSET_2);
            if(lastTrip.isComplete()) {
                INFO_FONT.drawString(MSG_PROPS.getProperty("gamePlay.trip.penalty") + String.format("%.02f",
                        lastTrip.getPenalty()), TRIP_INFO_X, TRIP_INFO_Y + TRIP_INFO_OFFSET_3);
            }
        }
    }

    public String getTotalEarnings() {
        return String.format("%.02f", totalEarnings);
    }

    /**
     * Check if the game is over. If the game is over and not saved the score, save the score.
     * @return true if the game is over, false otherwise.
     */
    public boolean isGameOver() {
        // Game is over if the current frame is greater than the max frames
        boolean isGameOver = currFrame >= MAX_FRAMES;

        if(currFrame >= MAX_FRAMES && !savedData) {
            savedData = true;
            IOUtils.writeLineToFile(GAME_PROPS.getProperty("gameEnd.scoresFile"), PLAYER_NAME + "," + totalEarnings);
        }
        return isGameOver;
    }

    /**
     * Check if the level is completed. If the level is completed and not saved the score, save the score.
     * @return true if the level is completed, false otherwise.
     */
    public boolean isLevelCompleted() {
        // Level is completed if the total earnings is greater than or equal to the target earnings
        boolean isLevelCompleted = totalEarnings >= TARGET;
        if(isLevelCompleted && !savedData) {
            savedData = true;
            IOUtils.writeLineToFile(GAME_PROPS.getProperty("gameEnd.scoresFile"), PLAYER_NAME + "," + totalEarnings);
        }
        return isLevelCompleted;
    }
}
