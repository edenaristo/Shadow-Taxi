public class Weather {
    private final boolean IS_RAINY;
    private final int START_FRAME;
    private final int END_FRAME;

    public Weather(boolean isRainy, int startFrame, int endFrame) {
        this.IS_RAINY = isRainy;
        this.END_FRAME = endFrame;
        this.START_FRAME = startFrame;
    }

    public boolean isIS_RAINY() {
        return IS_RAINY;
    }

    public int getSTART_FRAME() {
        return START_FRAME;
    }

    public int getEND_FRAME() {
        return END_FRAME;
    }
}
