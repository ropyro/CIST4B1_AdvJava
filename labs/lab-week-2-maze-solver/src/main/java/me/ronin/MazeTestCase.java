package me.ronin;

/**
 * Declaration of some test mazes used for testing
 */
public enum MazeTestCase {

    TEACHER(new int[][]{
            {0, 0, 1, 0, 0, 0, 1, 0, 0, 0},
            {1, 0, 1, 0, 1, 0, 1, 0, 1, 0},
            {1, 0, 0, 0, 1, 0, 0, 0, 1, 0},
            {1, 1, 1, 0, 1, 1, 1, 0, 1, 0},
            {0, 0, 0, 0, 0, 0, 1, 0, 0, 0},
            {0, 1, 1, 1, 1, 0, 1, 1, 1, 0},
            {0, 1, 0, 0, 0, 0, 0, 0, 1, 0},
            {0, 1, 0, 1, 1, 1, 1, 0, 1, 0},
            {0, 0, 0, 1, 0, 0, 0, 0, 0, 0},
            {1, 1, 0, 1, 0, 1, 1, 1, 1, 0}}),
    SMALL(new int[][]{
            {0, 1, 0},
            {0, 0, 0},
            {1, 1, 0}}
    ),
    MEDIUM(new int[][]{
            {0, 1, 0, 1},
            {0, 0, 0, 0},
            {1, 0, 1, 1},
            {0, 0, 0, 0}}
    ),
    LARGE(new int[][]{
            {0, 0, 1, 1, 1},
            {1, 0, 1, 0, 1},
            {0, 0, 0, 0, 0},
            {0, 1, 1, 0, 1},
            {0, 0, 1, 0, 0}}
    ),
    XL(new int[][]{
            {0, 1, 0, 0, 0, 1, 0, 0, 1, 0},
            {0, 1, 0, 1, 0, 1, 0, 1, 1, 0},
            {0, 0, 0, 1, 0, 0, 0, 0, 0, 0},
            {1, 1, 0, 1, 1, 0, 1, 1, 1, 1},
            {0, 0, 0, 0, 0, 0, 0, 1, 0, 0},
            {0, 1, 1, 1, 1, 1, 0, 1, 0, 1},
            {0, 0, 0, 0, 0, 1, 0, 0, 0, 0},
            {1, 1, 0, 1, 1, 1, 1, 1, 1, 0},
            {0, 0, 0, 0, 0, 0, 0, 1, 0, 0},
            {0, 1, 1, 1, 1, 1, 0, 1, 1, 0}}
    ),
    WIDE(new int[][]{
            {0, 1, 0, 0, 0, 1, 0, 0, 1, 0},
            {0, 1, 0, 1, 0, 1, 0, 1, 1, 0},
            {0, 0, 0, 1, 0, 0, 0, 0, 0, 0},
            {1, 1, 0, 1, 1, 0, 1, 1, 1, 0},
            {0, 1, 1, 1, 1, 1, 0, 1, 1, 0}}
    ),
    TALL(new int[][]{
            {0, 1, 0, 0, 0, 1},
            {0, 1, 0, 1, 0, 1},
            {0, 0, 0, 1, 0, 0},
            {1, 1, 0, 1, 1, 0},
            {0, 0, 0, 0, 0, 0},
            {0, 1, 1, 1, 1, 1},
            {0, 0, 0, 0, 0, 1},
            {1, 1, 0, 1, 1, 1},
            {0, 0, 0, 0, 0, 0},
            {0, 1, 1, 1, 1, 0}}
    ),
    SMALL_IMPOSSIBLE(new int[][]{
            {0, 1, 0},
            {0, 0, 0},
            {1, 1, 1}}
    ),
    MEDIUM_IMPOSSIBLE(new int[][]{
            {0, 1, 0, 1},
            {0, 0, 0, 1},
            {1, 0, 1, 1},
            {0, 0, 1, 1}}
    ),
    LARGE_IMPOSSIBLE(new int[][]{
            {0, 0, 1, 1, 1},
            {1, 0, 1, 0, 1},
            {0, 0, 0, 0, 0},
            {0, 1, 1, 1, 1},
            {0, 0, 1, 0, 0}}
    ),
    XL_IMPOSSIBLE(new int[][]{
            {0, 1, 0, 0, 0, 1, 0, 0, 1, 0},
            {0, 1, 0, 1, 0, 1, 0, 1, 1, 0},
            {0, 0, 0, 1, 0, 0, 0, 0, 0, 0},
            {1, 1, 0, 1, 1, 0, 1, 1, 1, 1},
            {0, 0, 0, 0, 0, 0, 0, 1, 0, 0},
            {0, 1, 1, 1, 1, 1, 1, 1, 0, 1},
            {0, 0, 0, 0, 0, 1, 0, 0, 0, 0},
            {1, 1, 0, 1, 1, 1, 1, 1, 1, 0},
            {0, 0, 0, 0, 0, 0, 0, 1, 0, 0},
            {0, 1, 1, 1, 1, 1, 0, 1, 1, 0}}
    ),
    CLOSED_BLOCK(new int[][]{
            {1, 1, 1},
            {1, 1, 1},
            {1, 1, 1}}
    ),
    OPEN_BLOCK(new int[][]{
            {0, 0, 0},
            {0, 0, 0},
            {0, 0, 0}}
    );


    /**
     * 2D array that represents a maze
     * 0 = open space
     * 1 = wall
     */
    private final int[][] maze;

    /**
     * Constructor for maze declarations
     *
     * @param maze 2D array that represents a maze
     */
    MazeTestCase(int[][] maze) {
        this.maze = maze;
    }

    /**
     * Returns a copy of the maze defined for the enum value
     *
     * @return returns 2D array that represents a maze
     */
    public int[][] newMaze() {
        return getMazeCopy();
    }

    private int[][] getMazeCopy() {
        int[][] copy = new int[maze.length][];
        for (int i = 0; i < maze.length; i++) {
            copy[i] = maze[i].clone();
        }
        return copy;
    }
}
