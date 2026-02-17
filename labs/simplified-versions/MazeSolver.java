/**
 * File: MazeSolver.java
 * Description: Lab Week 2: Maze Solver  Recursive solution to solving a 2d maze
 * This is the simplified version of my project for single file submission & clarity.
 *
 * @author Ronin Richman
 *
 * Full Project Source: https://github.com/ropyro/CIST4B1_AdvJava/tree/main/labs/lab-week-2-maze-solver
 */
public class MazeSolver {
    public static void main(String[] args) {
        int[][] maze =
                        {{0, 0, 1, 0, 0, 0, 1, 0, 0, 0},
                        {1, 0, 1, 0, 1, 0, 1, 0, 1, 0},
                        {1, 0, 0, 0, 1, 0, 0, 0, 1, 0},
                        {1, 1, 1, 0, 1, 1, 1, 0, 1, 0},
                        {0, 0, 0, 0, 0, 0, 1, 0, 0, 0},
                        {0, 1, 1, 1, 1, 0, 1, 1, 1, 0},
                        {0, 1, 0, 0, 0, 0, 0, 0, 1, 0},
                        {0, 1, 0, 1, 1, 1, 1, 0, 1, 0},
                        {0, 0, 0, 1, 0, 0, 0, 0, 0, 0},
                        {1, 1, 0, 1, 0, 1, 1, 1, 1, 0}};


        //Starts the solving recursion & stores verdict
        boolean hasSolution = solve(maze);

        //Final verdict output
        if (hasSolution) {
            System.out.println("Solved maze:");
            printMaze(maze);
        } else {
            System.out.println("Maze is impossible...");
        }
    }

    /**
     * Recursive function to solve the provided maze starting at (0,0)
     * ending at (n,n). Updates internal flags after completed
     *
     * @return true if a valid path exists, false if one does not
     */
    public static boolean solve(int[][] maze, int x, int y) {
        //edge case of origin or current position being impossible (0,0) is a 1
        if (maze[x][y] == 1) return false;
        //edge case (n,n) is a 1
        if (maze[maze.length - 1][maze[0].length - 1] == 1) return false;

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

        //Fails checks reset current position & fail (dead end) base case
        maze[x][y] = 0;
        return false;
    }

    /**
     * Prints the provided "maze" to the console
     * where 0 is an open space, 1 is a wall, 2 is the solved path
     * @param maze the 2d array to print as a maze
     */
    public static void printMaze(int[][] maze) {
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
}