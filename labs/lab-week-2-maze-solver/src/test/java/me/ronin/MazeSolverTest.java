package me.ronin;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MazeSolverTest {

    @Test
    void testClosedBlock(){
        int[][] maze = MazeTestCase.CLOSED_BLOCK.newMaze();
        MazeSolver solver = new MazeSolver(maze);
        assertFalse(solver.solve());
    }

    @Test
    void testOpenBlock(){
        int[][] maze = MazeTestCase.OPEN_BLOCK.newMaze();
        MazeSolver solver = new MazeSolver(maze);
        assertTrue(solver.solve());
    }

    @Test
    void testSimpleImpossibleMaze(){
        int[][] maze = MazeTestCase.SMALL_IMPOSSIBLE.newMaze();
        MazeSolver solver = new MazeSolver(maze);
        assertFalse(solver.solve());
    }

    @Test
    void testMediumImpossibleMaze(){
        int[][] maze = MazeTestCase.MEDIUM_IMPOSSIBLE.newMaze();
        MazeSolver solver = new MazeSolver(maze);
        assertFalse(solver.solve());
    }

    @Test
    void testLargeImpossibleMaze(){
        int[][] maze = MazeTestCase.LARGE_IMPOSSIBLE.newMaze();
        MazeSolver solver = new MazeSolver(maze);
        assertFalse(solver.solve());
    }

    @Test
    void testXLImpossibleMaze(){
        int[][] maze = MazeTestCase.XL_IMPOSSIBLE.newMaze();
        MazeSolver solver = new MazeSolver(maze);
        assertFalse(solver.solve());
    }

    @Test
    void testSmallMaze(){
        int[][] maze = MazeTestCase.SMALL.newMaze();
        MazeSolver solver = new MazeSolver(maze);
        assertTrue(solver.solve());
    }

    @Test
    void testMediumMaze(){
        int[][] maze = MazeTestCase.MEDIUM.newMaze();
        MazeSolver solver = new MazeSolver(maze);
        assertTrue(solver.solve());
    }

    @Test
    void testLargeMaze(){
        int[][] maze = MazeTestCase.LARGE.newMaze();
        MazeSolver solver = new MazeSolver(maze);
        assertTrue(solver.solve());
    }

    @Test
    void testXLMaze(){
        int[][] maze = MazeTestCase.XL.newMaze();
        MazeSolver solver = new MazeSolver(maze);
        assertTrue(solver.solve());
    }
}
