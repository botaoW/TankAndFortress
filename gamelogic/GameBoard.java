package ca.cmpt213.as3.gamelogic;

public class GameBoard {

    // The game board is a 2D array of integers.
    // I use 0,1,2,3 to represent the different status
    // of each cell. 0 for empty, un-hit; 1 for tank, un-hit;
    // 2 for empty, hit; 3 for tank, hit.
    private int[][] board;
    // tankList stores all the tanks on the board.
    private Tank[] tankList;
    //  structure stores the fortress's left structure
    private int structure;

    public boolean initiateGameBoard(int numberOfTank) {
        //initiate the tankList
        tankList = null;
        structure = 1500;
        int boardLength = 10;
        int boardHeight = 10;
        // now set all the locations' status to 0
        for (int i = 0; i < boardHeight; i++)
            for (int j = 0; j < boardLength; j++)
                board[i][j] = 0;
        // Now we start to generate tanks.
        // only give 100 chances for the board to generate tanks
        // without overlapping.
        int trialsLeft = 50;
        int tankPicked = 0;
        while ((tankPicked < numberOfTank) && (trialsLeft > 0)) {
            trialsLeft--;
            int initialLocation;
            initialLocation = (int) (Math.random() * boardHeight * boardLength);
            // If there is an overlap, continue to the next trial.
            if (!checkInitialLocation(initialLocation,tankPicked))
                continue;
            // Check if the tank cells in the tank also initiate successfully
            tankList[tankPicked].initiateTank(initialLocation);

            // Now check if the newly created tank overlap with any other existing tanks
            boolean newTankNoOverlap = checkTankOverlap(tankList[tankPicked],tankPicked);
            if (!newTankNoOverlap) {
                tankList[tankPicked] = null;
            }
            else{
                putTankToBoard(tankList[tankPicked]);
                tankPicked++;
            }
        }

        // If successfully create all the tanks, then the game is initiated successfully.
        return tankPicked == numberOfTank;
    }

    private boolean checkInitialLocation(int initialLocation, int tankPicked) {
        // Compare the chosen initial location to all the tanks that are already
        // created, if there is an overlap, return false.
        for (int i = 0; i < tankPicked; i++) {
            for (int j = 0; j < 4 ; j++){
               if (initialLocation == tankList[i].getCellLocation()[j])
                   return false;
            }
        }
        return true;
    }

    private boolean checkTankOverlap(Tank tankPicked, int tankNumber){
        // Pick each of the four cells from the tank being checked,
        // and compare it to all the cells from the existing tanks.
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < tankNumber; j++)
                for (int k = 0; k < 4; k++)
                    if (tankPicked.getCellLocation()[i] == tankList[j].getCellLocation()[k])
                        return false;
        }
        return true;
    }

    private void putTankToBoard(Tank tankPicked){
        for (int i = 0; i < 4; i++){
            // change all the four cells on board to status 1
            int tankLocation = tankPicked.getCellLocation()[i];
            board[tankLocation / 10][tankLocation % 10] = 1;
        }
    }

    public int[][] getBoard() {
        return board;
    }

    public int getStructure(){
        return structure;
    }

    public Tank[] getTankList() {
        return tankList;
    }

    public boolean userShotProcess(int userShot){
        for (int i = 0; i < tankList.length; i++){
            for (int j = 0; j < 4; j++){
                if (userShot == tankList[i].getCellLocation()[j]){
                    board[userShot / 10][userShot % 10] = 3;
                    return true;
                }
            }
        }
        if (board[userShot / 10][userShot % 10] != 3 )
            board[userShot / 10][userShot % 10] = 2;
        return false;
    }



}
