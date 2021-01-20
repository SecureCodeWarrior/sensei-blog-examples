package immutability.immutable;

public final class Coordinates {

    private final int x;
    private final int y;

    private Coordinates(final int x, final int y) {
        this.x = x;
        this.y = y;
    }

    public static Coordinates create(final int x, final int y) {
        return new Coordinates(x, y);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Coordinates transformPositionBy(final int xadjust, final int yadjust) {
        return new Coordinates(this.x + xadjust, this.y + yadjust);
    }
}
