package me.ronin;

/**
 * MazeSolver object that recursively solves a 2d maze
 * and can print a representation to the console
 */
public class MazeSolver {

    /**
     * 2D array that represents a maze
     * 0 = open space
     * 1 = wall
     * 2 = visited
     */
    private int[][] maze;
    private int[][] unsolvedMaze; //backup of the original maze for printing
    private boolean printSteps; //true will print each individual check as the recursive function runs
    private int stepCount = 0; //Current count of steps taken by the recursive function

    /**
     * Constructor for MazeSolver that stores the maze
     *
     * @param maze 2D array that represents a maze
     */
    public MazeSolver(int[][] maze) {
        this(maze, false);
    }

    /**
     * Overloaded Constructor for MazeSolver that stores the maze & specifies step printing
     *
     * @param maze       2D array that represents a maze
     * @param printSteps true, each step will be printed with 1s delay
     */
    public MazeSolver(int[][] maze, boolean printSteps) {
        this.maze = maze;
        this.unsolvedMaze = maze;
        this.printSteps = printSteps;
    }

    /**
     * Recursive function to solve the provided maze starting at (0,0)
     * ending at (n,n)
     *
     * @param maze 2d array representing the maze
     * @param x    current x position
     * @param y    current y position
     * @return true = contains valid path; false = has no invalid path
     */
    private boolean solve(int[][] maze, int x, int y) {
        //edge case of origin or current position being impossible (0,0) is a 1
        if (maze[x][y] == 1) return false;
        //edge case (n,n) is a 1
        if (maze[maze.length - 1][maze[0].length - 1] == 1) return false;

        if (printSteps) printStep(maze);

        //Mark current position as visited
        maze[x][y] = 2;

        //base case (reached n,n)
        if (x == maze.length - 1 && y == maze[0].length - 1) return true;

        //move right recursive case
        if (x + 1 < maze.length && maze[x + 1][y] == 0) {
            if (solve(maze, x + 1, y)) return true;
        }
        //move left recursive case
        if (x - 1 >= 0 && maze[x - 1][y] == 0) {
            if (solve(maze, x - 1, y)) return true;
        }
        //move down recursive case
        if (y + 1 < maze[0].length && maze[x][y + 1] == 0) {
            if (solve(maze, x, y + 1)) return true;
        }
        //move up recursive case
        if (y - 1 >= 0 && maze[x][y - 1] == 0) {
            if (solve(maze, x, y - 1)) return true;
        }

        if (printSteps) printStep(maze);

        //Fails checks reset current position & fail (dead end) base case
        maze[x][y] = 0;
        return false;
    }

    /**
     * Recursive function to solve the provided maze starting at (0,0)
     * ending at (n,n). Updates internal flags after completed
     *
     * @return true if a valid path exists, false if one does not
     */
    public boolean solve() {
        return solve(this.maze, 0, 0);
    }

    /**
     * Prints the provided "maze" to the console
     * where 0 is an open space, 1 is a wall, 2 is the solved path
     */
    private void printMaze(int[][] maze) {
        for (int[] line : maze) {
            StringBuilder lineToPrint = new StringBuilder();
            for (int component : line) {
                switch (component) {
                    case 0:
                        lineToPrint.append(". ");
                        break;
                    case 1:
                        lineToPrint.append("# ");
                        break;
                    case 2:
                        lineToPrint.append("* ");
                        break;
                    default:
                        lineToPrint.append("? ");
                }
            }
            System.out.println(lineToPrint);
        }
        System.out.println();
    }

    /**
     * Prints the original unsolved maze to the console
     */
    public void printUnsolvedMaze() {
        printMaze(unsolvedMaze);
    }

    /**
     * Prints the current state of the maze to the console
     */
    public void printCurrentMaze(){
        printMaze(maze);
    }

    /**
     * Prints each individual step of the recursive function
     *
     * @param maze the current maze with path
     */
    private void printStep(int[][] maze) {
        if (stepCount != 0) System.out.println("Current Step: " + stepCount);
        stepCount++;
        printMaze(maze);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            //this code will probably never be executed but the linter & compiler require this try catch block smh
            System.err.println("Sleeping the entire thread is a hacky solution and is failing for some reason...");
        }
    }

}
