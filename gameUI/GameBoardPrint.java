package ca.cmpt213.as3.gameUI;

import ca.cmpt213.as3.gamelogic.GameBoard;
import ca.cmpt213.as3.gamelogic.Tank;

public class GameBoardPrint {

    private GameBoard gameBoard;

    public void loadBoard(GameBoard gameBoard){
        this.gameBoard = gameBoard;
    }

    public void inGamePrint(){

    }

    public void endGamePrint(){
        System.out.println("\nGame Board: ");
        System.out.println("       1  2  3  4  5  6  7  8  9 10");

        Tank[] currentTanks = gameBoard.getTankList();
        int[][] currentBoard = gameBoard.getBoard();
        for (int i = 0; i < 10; i++){
            // First print the row number
            System.out.print("    " + convertToChar(i));
            for (int j = 0; j < 10 ;j++){
                if (currentBoard[i][j] == 0)
                    System.out.print("  .");
                else if (currentBoard[i][j] == 1){
                    tankUnhitPrint(i,j,currentTanks);
                }
                else if (currentBoard[i][j] == 2){
                    System.out.print("   ");
                }
                else{
                    tankHitPrint(i,j,currentTanks);
                }
            }
        }
    }

    private String convertToChar(int i) {
        return i >= 0 && i < 10 ? String.valueOf((char)(i + 65)) : null;
    }

    private void tankUnhitPrint(int row, int column,Tank[] currentTanks) {
        for (int i = 0; i < currentTanks.length; i++) {
            for (int j = 0; j < 4; j++) {
                int cellLocation = currentTanks[i].getCellLocation()[j];
                if (cellLocation == (row * 10 + column))
                    System.out.print("  " + convertToChar(i));
            }
        }
    }

    private void tankHitPrint(int row, int column,Tank[] currentTanks) {
        for (int i = 0; i < currentTanks.length; i++) {
            for (int j = 0; j < 4; j++) {
                int cellLocation = currentTanks[i].getCellLocation()[j];
                if (cellLocation == (row * 10 + column))
                    System.out.print("  " + convertToChar(i + 32));
            }
        }
    }

}
