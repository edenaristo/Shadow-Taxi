import bagel.*;

import java.util.Properties;

/**
 * Skeleton Code for SWEN20003 Project 2, Semester 2, 2024
 * Please enter your name below
 * @author Eden Aristo Tingkir
 */
public class ShadowTaxi extends AbstractGame {

    private final Properties GAME_PROPS;
    private final Properties MESSAGE_PROPS;

    private HomeScreen homeScreen;
    private GamePlayScreen gamePlayScreen;
    private PlayerInfoScreen playerInfoScreen;
    private GameEndScreen gameEndScreen;

    public ShadowTaxi(Properties gameProps, Properties messageProps) {
        super(Integer.parseInt(gameProps.getProperty("window.width")),
                Integer.parseInt(gameProps.getProperty("window.height")),
                messageProps.getProperty("home.title"));

        this.GAME_PROPS = gameProps;
        this.MESSAGE_PROPS = messageProps;

        homeScreen = new HomeScreen(GAME_PROPS, MESSAGE_PROPS);
    }

    /**
     * Render the relevant screen based on the keyboard input given by the user and the status of the game play.
     * @param input The current mouse/keyboard input.
     */
    @Override
    protected void update(Input input) {
        if (input.wasPressed(Keys.ESCAPE)){
            Window.close();
        }

        // HOME SCREEN
        if (gamePlayScreen == null && playerInfoScreen == null && gameEndScreen == null) {
            // if the user click ENTER button when in the Home Screen, generate the player info screen
            if(homeScreen.update(input)) {
                playerInfoScreen = new PlayerInfoScreen(GAME_PROPS, MESSAGE_PROPS);
            }

        // PLAYER INFO SCREEN
        } else if(playerInfoScreen != null && gamePlayScreen == null && gameEndScreen == null) {
            // if the user selects to start the game, generate a new game play screen
            if(playerInfoScreen.update(input)) {
                gamePlayScreen = new GamePlayScreen(GAME_PROPS, MESSAGE_PROPS, playerInfoScreen.getPlayerName());
                playerInfoScreen = null;
            }

        // GAME PLAY SCREEN
        } else if (playerInfoScreen == null && gamePlayScreen != null && gameEndScreen == null){
            // if the game is over or the level is completed, generate new game end screen
            if(gamePlayScreen.update(input)) {
                boolean isWon = gamePlayScreen.isLevelCompleted();

                gameEndScreen = new GameEndScreen(GAME_PROPS, MESSAGE_PROPS);
                gameEndScreen.setIsWon(isWon);

                gamePlayScreen = null;
            }

        // GAME END SCREEN
        } else if(playerInfoScreen == null && gamePlayScreen == null && gameEndScreen != null) {
            if(gameEndScreen.update(input)) {
                gamePlayScreen = null;
                playerInfoScreen = null;
                gameEndScreen = null;
            }
        }
    }

    public static void main(String[] args) {
        // read the properties
        Properties game_props = IOUtils.readPropertiesFile("res/app.properties");
        Properties message_props = IOUtils.readPropertiesFile("res/message_en.properties");

        // make and run the game
        ShadowTaxi game = new ShadowTaxi(game_props, message_props);
        game.run();
    }
}
