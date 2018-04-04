package ca.cmpt213.as3.gamelogic;

import ca.cmpt213.as3.gameUI.GameBoardPrint;

public class MainGame {

    // private int structure;
    // private GameBoard gameBoard;

    public static void main(String[] args){



        if (userInputHandle(args) == -1){
                System.exit(1);
        }

        GameBoard gameBoard = new GameBoard();
        int numberOfTank;
        numberOfTank = Integer.parseInt(args[0]);
        if (!gameBoard.initiateGameBoard(numberOfTank)){
            System.out.println("Error: Unable to place " + numberOfTank + " on the board.");
            System.out.println("Try running game again with fewer tanks.");
            System.exit(1);
        }

        if (userInputHandle(args) == 1){
            GameBoardPrint currentBoard = new GameBoardPrint();
            currentBoard.loadBoard(gameBoard);
            currentBoard.endGamePrint();
            
        }

        boolean gameEnd = false;
        while (!gameEnd){

            userShotHandle()
        }

    }

    private static int userInputHandle(String[] args){
        // This function handles the initial input of tank number
        // and cheat of the user.
        // Return -1  t indicate fail, 0 for normal, 1 for cheat.
        if (args.length == 2){
            if ((!args[1].equals("--cheat")) || (Integer.parseInt(args[0]) <= 0)){
                System.out.println("Error: Invalid input, please restart the game.");
                return -1;
            }
            else
                return 1;
        }
        else if (args.length == 1)
            return 0;
        // If there are more than 2 arguments, then it's a fail.
        System.out.println("Error: Invalid input, please restart the game.");
        return -1;
    }


}
