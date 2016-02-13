package hr.fer.zemris.optjava.anttrail.chart;

public enum Tile {

    FOOD, EMPTY;

    public static Tile fromString(String string) {
        switch (string) {
        case "0":
        case ".":
            return EMPTY;
        default:
            return FOOD;
        }
    }

}
