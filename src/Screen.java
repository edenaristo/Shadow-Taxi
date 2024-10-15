import bagel.*;

/**
 * The Screen class is an abstract representation of a game screen.
 * It defines the structure for updating the screen's state based on user input.
 */
public abstract class Screen {
    public abstract boolean update(Input input);
}
