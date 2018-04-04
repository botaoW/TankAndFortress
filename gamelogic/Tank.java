package ca.cmpt213.as3.gamelogic;

public class Tank {
    private int[] cellLocation;
    private boolean[] cellHit;

    public void initiateTank(int initialLocation) {
        // use -1 to indicate fail of initiating
        int fail = -1;
        cellLocation[0] = initialLocation;
        cellHit[0] = false;
        // now we "recursively" (not quite) create the tank
        // randomly pick an existing cell, and then randomly
        // select a direction. Then check if that direction
        // is out of the board. Then check if that direction
        // overlap with other existing cell.
        for (int cellIndex = 1; cellIndex < 4; cellIndex++) {
            // randomly pick an existing cell,
            boolean newCellSuccess = false;
            while (!newCellSuccess) {
                int cellPicked = (int) (Math.random() * cellIndex);
                // Now get rid of the "out of board" directions
                int directionAvailable = checkDirectionAvailable(cellPicked);
                // Now try to select blocks and not overlapping others
                while (directionAvailable != 0) {
                    int nextDirection;
                    nextDirection = tryNextDirection(directionAvailable, cellPicked, cellIndex);
                    if (nextDirection == 0)
                        break;
                    else
                        directionAvailable -= nextDirection;
                }
                if (directionAvailable != 0)
                    newCellSuccess = true;
            }
        }
    }


    private int randomlyPickDirection(int directionAvailable){
        switch (directionAvailable){
            // Randomly pick directions from the available choices left
            case 1:
                return 1;
            case 2:
                return 2;
            case 3:
                return (int)Math.pow(2, randomTwo());
            case 4:
                return 4;
            case 5:
                return (int)Math.pow(2, 2 * randomTwo());
            case 6:
                return  (int)Math.pow(2, randomTwo() + 1);
            case 7:
                return (int)Math.pow(2, randomThree());
            case 8:
                return 8;
            case 9:
                return (int)Math.pow(2, 3 * randomTwo());
            case 10:
                return (int)Math.pow(2, 2 * randomTwo() + 1);
            case 11:
                return (int)Math.pow(2, (int)Math.pow(2, randomThree()) - 1);
            case 12:
                return (int)Math.pow(2, randomTwo() + 2);
            case 13:
                return (int)Math.pow(2, 3 - ((int)Math.pow(2, randomThree()) - 1));
            case 14:
                return (int)Math.pow(2, randomThree() + 1);
            case 15:
                return (int)Math.pow(2, randomFour());
        }
        return 0;
    }

    private int randomTwo(){
        return  (int) (Math.random() * 2);
    }

    private  int randomThree(){
        return  (int) (Math.random() * 3);
    }

    private int randomFour(){
        return  (int) (Math.random() * 4);
    }

    private int checkDirectionAvailable(int cellPicked){
        // check if the selected direction will get out of the game board.
        int directAvailable = 0;
        // The combination of 2s power is very useful here.
        // Different sum stands for different availabilities.
        if (cellLocation[cellPicked] > 9)
            directAvailable += 1;
        if ((cellLocation[cellPicked] % 10) < 9)
            directAvailable += 2;
        if ((cellLocation[cellPicked] / 10) < 9)
            directAvailable += 4;
        if ((cellLocation[cellPicked] % 10) > 0)
            directAvailable += 8;
        return directAvailable;
    }


    private int tryNextDirection(int directionAvailable, int cellPicked, int cellIndex){
        int direction = randomlyPickDirection(directionAvailable);
        // If there's no overlap, we successfully add a block.
        // and return 0 to represent success.
        int success = 0;
        int newCell = checkOverlap(direction,cellPicked,cellIndex);
        if (newCell != -1){
            cellLocation[cellIndex] = newCell;
            return success;
        }
        // Otherwise, return the direction to tell the caller that
        // this direction failed.
        else
            return direction;
    }





    private int checkOverlap(int direction, int cellPicked, int cellIndex){
        // use number -1 to indicate the failure of generating.
        int fail = -1;
        for (int i = 0; i < cellIndex; i++){
            switch(direction) {
                case 1:
                    if  ((cellLocation[cellPicked] - 10) != cellLocation[i])
                        return (cellLocation[cellPicked] - 10);
                case 2:
                    if ((cellLocation[cellPicked] + 1) != cellLocation[i])
                        return (cellLocation[cellPicked] + 1);
                case 4:
                    if ((cellLocation[cellPicked] + 10) != cellLocation[i])
                        return (cellLocation[cellPicked] + 10);
                case 8:
                    if ((cellLocation[cellPicked] - 1) != cellLocation[i])
                        return (cellLocation[cellPicked] - 1);
            }
        }
        return fail;
    }

    public int[] getCellLocation() {
        return cellLocation;
    }

    public boolean[] getCellHit() {
        return cellHit;
    }

    public int getNumberOfAliveCell(){
        int NumberOfAliveCell = 0;
        for (int i = 0; i < 4; i++){
            // if the cell location is its minus number (or 100 for (0,0)).
            // then it means the cell has been hit and dead.
            if (cellLocation[i] < 0 || cellLocation[i] == 100)
                NumberOfAliveCell++;
        }
        return NumberOfAliveCell;
    }

    public void tankHitHandle(int userShot){
        for (int i = 0; i < 4; i++){
            if (cellLocation[i] == userShot){
                // if there is a new hit, change the location number of that cell to its opposite (minus)
                // if the hit cell is on location (0,0), then use 100 to represent hit.
                if (cellLocation[i] > 0)
                    cellLocation[i] = 0 - cellLocation[i];
                else if (cellLocation[i] == 0)
                    cellLocation[i] = 100;
            }
        }
    }

}
