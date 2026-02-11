package me.ronin;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        //Start up header
        System.out.print(
                "\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n" +
                " ▗▄▖     ▗▖  ▗▖ ▗▄▖ ▗▄▄▄▄▖▗▄▄▄▖    ▗▄▄▖  ▗▄▖ ▗▖   ▗▖    ▗▄▄▖\n" +
                "▐▌ ▐▌    ▐▛▚▞▜▌▐▌ ▐▌   ▗▞▘▐▌       ▐▌ ▐▌▐▌ ▐▌▐▌   ▐▌   ▐▌   \n" +
                "▐▛▀▜▌    ▐▌  ▐▌▐▛▀▜▌ ▗▞▘  ▐▛▀▀▘    ▐▛▀▚▖▐▛▀▜▌▐▌   ▐▌    ▝▀▚▖\n" +
                "▐▌ ▐▌    ▐▌  ▐▌▐▌ ▐▌▐▙▄▄▄▖▐▙▄▄▖    ▐▙▄▞▘▐▌ ▐▌▐▙▄▄▖▐▙▄▄▖▗▄▄▞▘\n" +
                "                                                            \n");
        System.out.println("Welcome to the Maze solver! \n");

        //Scanner input loop
        Scanner scanner = new Scanner(System.in);
        while(true){
            //Prints Available mazes from enum declaration
            System.out.println("Available Mazes:");
            for(MazeTestCase mazeDeclaration : MazeTestCase.values()){
                System.out.print(mazeDeclaration.toString() + " ");
            }

            //Select a maze
            MazeTestCase selectedMaze = requestInputMaze(scanner);
            if(selectedMaze == null) return;

            //Handles boolean input for enabling/disabling step visualizing
            System.out.println("\nWould you like to see each step? (yes/no)");
            String eachStepInput = scanner.next();
            boolean eachStep = false;
            if(eachStepInput.equalsIgnoreCase("yes")
                    || eachStepInput.equalsIgnoreCase("true")) eachStep = true;

            //Solving begins
            System.out.println("\nNow solving maze: " + selectedMaze);

            //Initializes a MazeSolver object with the desired maze & settings
            MazeSolver mazeSolver = new MazeSolver(selectedMaze.newMaze(), eachStep);

            //Prints unsolved maze only if steps not being visualized
            if(!eachStep){
                mazeSolver.printUnsolvedMaze();
            }

            //Starts the solving recursion & stores verdict
            boolean hasSolution = mazeSolver.solve();

            //Final verdict output
            if(hasSolution){
                System.out.println("Solved maze:");
                mazeSolver.printCurrentMaze();
            }else{
                System.out.println("Maze is impossible...");
                mazeSolver.printUnsolvedMaze();
            }
        }
    }

    /**
     * Prompts user to enter a test maze name
     * @param scanner the current scanner object to listen to the cli inputs
     * @return Returns the desired maze or null to exit the program
     */
    private static MazeTestCase requestInputMaze(Scanner scanner){
        System.out.println("\n\nInput the name of a maze from above to begin (or type exit to leave)");
        String mazeSelection;
        MazeTestCase selectedMaze = null;
        while(selectedMaze == null){
            mazeSelection = scanner.next();
            if(mazeSelection.equalsIgnoreCase("exit")){
                System.out.println("Exiting...");
                scanner.close();
                return null;
            }
            try{
                selectedMaze = MazeTestCase.valueOf(mazeSelection.toUpperCase());
            }catch (IllegalArgumentException e){
                System.out.println("Unknown maze try again...");
            }
        }
        return selectedMaze;
    }
}