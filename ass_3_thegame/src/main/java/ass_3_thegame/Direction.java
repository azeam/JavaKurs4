package ass_3_thegame;

public enum Direction {
            N (0, -1),
        NW (-1, -1), NE (+1, -1),
    W (-1, 0),          E (+1, 0),
        SW (-1, +1), SE (+1, +1),
            S (0, +1);

    private final int x, y;

    private Direction(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    public int getX() {
        return this.x;
    }
    
    public int getY() {
        return this.y;
    }

    public static Direction getRandom() {
        return values()[(int) (Math.random() * values().length)];
    }
}
