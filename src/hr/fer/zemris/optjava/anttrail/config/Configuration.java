package hr.fer.zemris.optjava.anttrail.config;

public class Configuration {

    public static int     MAXIMUM_GENERATIONS        = 100;
    public static int     TOURNAMENT_SELECTION_SIZE  = 7;
    public static int     MAXIMUM_TREE_DEPTH         = 10;
    public static int     MAXIMUM_INITIAL_TREE_DEPTH = 6;
    public static int     MAXIMUM_NODE_COUNT         = 200;
    public static int     POPULATION_SIZE            = 500;
    public static int     MAXIMUM_ACTIONS            = 600;

    public static double  REPRODUCTION_RATE          = 0.01;
    public static double  MUTATION_RATE              = 0.14;
    public static double  CROSSING_RATE              = 0.85;
    public static double  PLAGIARISM_PENALTY         = 0.9;

    public static boolean ELITIST_MODE             = true;

}
