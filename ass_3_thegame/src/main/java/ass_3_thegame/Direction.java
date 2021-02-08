package ass_3_thegame;

import java.util.concurrent.ThreadLocalRandom;

public enum Direction {
    N(0, -1), 
    NW(-1, -1), 
    NE(+1, -1), 
    W(-1, 0), 
    E(+1, 0), 
    SW(-1, +1), 
    SE(+1, +1), 
    S(0, +1);

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

    public static Direction getOpposite(Direction curDir) {
        Direction newDir = switch (curDir) {
            case N -> values()[getRandomOf(5, 6, 7)];
            case NW -> values()[getRandomOf(7, 6, 4)];
            case NE -> values()[getRandomOf(5, 3, 7)];
            case W -> values()[getRandomOf(2, 4, 6)];
            case E -> values()[getRandomOf(1, 3, 5)];
            case SW -> values()[getRandomOf(0, 2, 4)];
            case SE -> values()[getRandomOf(0, 2, 3)];
            default -> values()[getRandomOf(0, 1, 2)];
        };
        return newDir;
    }

    private static int getRandomOf(int first, int second, int third) {
        int i;
        do {
            i = ThreadLocalRandom.current().nextInt(0, 7 + 1);
        } while (i != first && i != second && i != third);  
        return i;
    }
}
