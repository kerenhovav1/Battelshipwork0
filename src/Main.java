import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

public class Main {
    public static Scanner scanner;
    public static Random rnd;


    /** function that Gets the board size as an STR input, split the string to a
     * 2 dimensional array using "X"
     * @return an int array that represents the num of rows and cols
     */
    public static int[] getBoardSizes() {
        System.out.println("Enter the board size");
        scanner = new Scanner(System.in);  //Delete before submitting
        String board_sizes = scanner.nextLine();
        String[] dimensions = board_sizes.split("X");
        int rows = Integer.parseInt(dimensions[0]);
        int columns = Integer.parseInt(dimensions[1]);
        return new int[] {rows, columns};
    }

    /** The function gets the number and the sizes of the ships as an STR
     * and split it once to a 1 big array using " " and then the one big array
     * it separates it to 2 arrays using "X" for representation of amount and size.
     * @return 2 dimensional array, the first will be the amount of ships and the second
     * will be the sizes of the ships
     */
    public static int[][] getBattleshipSizes() {
        System.out.println("Enter the battleship sizes");
        String ships_info = scanner.nextLine();
        String[] ships_num_size = ships_info.split(" ");
        int len = ships_num_size.length;
        int[] sizes = new int[len];
        int[] num = new int[len];
        for (int i = 0; i < len; ++i) {
            String[] ship_parts = ships_num_size[i].split("X");
            sizes[i] = Integer.parseInt(ship_parts[1]);
            num[i] = Integer.parseInt(ship_parts[0]);
        }
        return new int[][] {num, sizes};
    }


    /**
     * function that calculates the number of spaces needed
     * @param spaces and int var - will be the largest number of rows.
     * we will take this number and convert to a str and then will check the len of the str
     * @return number of how much spaces we need
     */
    public static int getSpaces(int spaces) {
        String numSpacesStr = Integer.toString(spaces);
        return numSpacesStr.length();
    }


    /** function that only creates the initial game board
     * we split the function into 2 : the first row is only the number of cols. in the place [0][0]
     * it will print the amount of spaces as needed with the help of get spaces function.
     * the second part is all the other rows. in the place [i][0] will be printed the according
     * spaces using a loop that checks the len of the number (converting to str) and print
     * accordingly the num of spaces and the num of rows using counter.In all the other places
     * will be printed the sign "-".
     * @param boardSizes an int array that tells the functions how much rows and cols will be
     * @return the updated game board as an 2 dim array
     */
    public static String[][] createGameBoard(int[] boardSizes) {
        int numRows = boardSizes[0], numCols = boardSizes[1];
        int countCols = 0, countRows = 0;
        int numSpaceForTable = getSpaces(numRows) + 1;
        String space = " ";
        String resultSpace = "";
        String[][] gameBoard = new String[numRows+1][numCols+1];
        int countToRemove = 1;
        if (numSpaceForTable - 1 != getSpaces(numRows - 1))
            countToRemove = 2;

        // fill the first row with spaces and numbers
        for (int index_row = 0; index_row < numRows+1; ++index_row) {
            if (index_row==0) {
                //loop that`s creating the spaces in the first row
                for (int i=0; i < numSpaceForTable - countToRemove;++i){
                    resultSpace += space;
                }
                gameBoard[0][0] = resultSpace;
                resultSpace = "";

                //printing the cols number
                for (int index_cols = 1; index_cols < numCols+1; ++index_cols) {
                    String CountColsSTR = Integer.toString(countCols);
                    gameBoard[0][index_cols] = (CountColsSTR);
                    ++countCols;
                }
            }
            // filling the rest of the board
            else if (index_row < numRows+1) {
                String CountRowsSTR = Integer.toString(countRows);
                String spaceForRowSTR = Integer.toString(countRows);
                int numSpaceForRow = numSpaceForTable - spaceForRowSTR.length();
                for (int i = 0; i < numSpaceForRow - countToRemove;++i)
                    resultSpace += space;
                gameBoard[index_row][0] = resultSpace + CountRowsSTR;
                resultSpace = "";
                for (int index_col = 1;index_col < numCols+1; index_col++)
                    gameBoard[index_row][index_col] = "–";
                countRows++;
            }
        }
        return gameBoard;
    }


    /** function that prints the current board game of the player
     * the function gets the updated board and prints it on the scree.
     * @param boardGame a STR 2 - dim array that gets the updated board
     */
    public static void printBoardGame(String[][] boardGame) {
        for (int i = 0; i < boardGame.length; i++) {
            for (int j = 0; j < boardGame[i].length; j++) {
                if (j == (boardGame[i].length - 1))
                    System.out.println(boardGame[i][j]);
                else
                    System.out.print(boardGame[i][j] + " ");
            }
        }
        System.out.println(); // space row after every boardGame
    }

    /** The function takes a sty array and convert it to tn int array
     * @param infoStr gets a str array that comes from the function battleshipPlace
     * @return a int array with the 3 cordinates for placement.
     */
    public static int[] stringToIntArr(String[] infoStr) {
        int[] infoInt = new int[infoStr.length];
        for (int i = 0; i < infoStr.length; ++i)
            infoInt[i] = Integer.parseInt(infoStr[i]);
        return infoInt;
    }

    /**
     * function that help the battelship location that checks Orientation
     * @param placementIntInfo gets an int array with the data of the placement
     * @return bol true or false
     */
    public static boolean checkOrientation (int []placementIntInfo ) {
        if (placementIntInfo[2] != 0 || placementIntInfo[2] != 1) {
            System.out.println("Illegal orientation, try again!");
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * function that help the battleship location function that checks if the current tile is inside the board
     * @param placementIntInfo an int array with battelship location info
     * @param gameBoard the str 2-dim game board to check if the tile is ligal.
     * @return bol - true or false
     */
    public static boolean checkTile (int []placementIntInfo, String[][] gameBoard){
        if ((placementIntInfo[0] < 0) || (placementIntInfo[0] >= gameBoard.length - 1) ||
                (placementIntInfo[1] < 0) || (placementIntInfo[1] >= gameBoard[0].length - 1)) {
            System.out.println("Illegal tile, try again!");
        }
        return true;
    }

    /**
     * function that help the battleship location function that checks if from the current tile
     * and the ship size will be exceeding from the board
     * @param gameBoard str 2-dim array of the board
     * @param placementIntInfo int 1- dim array of the placement
     * @param sizeOfShip the current size of ship we are checking if the ship will exceed
     * @return bol true or false
     */
    public static boolean checkExceed (String[][] gameBoard,int []placementIntInfo,int sizeOfShip) {
        if (placementIntInfo[2] == 0) {
            if (placementIntInfo[1] + sizeOfShip > gameBoard[0].length - 1) {
                System.out.println("Battleship exceeds the boundaries of the board, try again!");

            }
           else if (placementIntInfo[2] == 1){
               if(placementIntInfo[0] + sizeOfShip > gameBoard.length) {
                   System.out.println("Battleship exceeds the boundaries of the board, try again!");
               }
            }
        }
        return true;
    }

    /**
     * function that helps the battleship location function that checks the new placement overlaps another ship
     * the overlapping will be depended on the Orientation of the ship
     * @param placingBoard str 2 dim array that will be updated only if the placement is ok!
     * @param placementIntInfo int array with all the data location of the ship
     * @param sizeOfShip the size of the ship we currently checking
     * @return bol - true or false
     */
    public static boolean checkOverlap (String[][] placingBoard,int []placementIntInfo,int sizeOfShip ){
            if (placementIntInfo[2]==0){
            for (int index = 0; index < sizeOfShip; ++index) {
                if ((placingBoard[placementIntInfo[0] + 1][placementIntInfo[1] + 1 + index]).equals("#")) {
                    System.out.println("Battleship overlaps another Battleship, try again!");
                }
            }
        }
            else {
                for (int index = 0; index < sizeOfShip; ++index) {
                    if ((placingBoard[placementIntInfo[0] + 1 + index][placementIntInfo[1] + 1]).equals("#")) {
                        System.out.println("Battleship overlaps another Battleship, try again!");

                    }
                }
            }
        return true;
    }


    public static boolean checkAdjacent(String[][] placingBoard,String[][] gameBoard ,int []placementIntInfo,int[] boardSizes,int sizeOfShip) {
        // the case that the placement is [0][0]
        if (placementIntInfo[0] == 0 && placementIntInfo[1] == 0) {
            if (placementIntInfo[2] == 0) {
                for (int index_row = 1; index_row < 2; ++index_row) {
                    for (int index_col = 0; index_col < sizeOfShip + 1; ++index_col) {
                        if ((placingBoard[index_row][index_col + 1]).equals("#")) {
                            System.out.println("Adjacent battleship detected, try again!");
                        }
                    }
                }
            } else {
                for (int index_row = 0; index_row < sizeOfShip + 1; ++index_row) {
                    for (int index_col = 1; index_col < 2; ++index_col) {
                        if ((placingBoard[index_row + 1][index_col]).equals("#")) {
                            System.out.println("Adjacent battleship detected, try again!");
                        }
                    }
                }
            }


        }
        // the case that the placement is in row 0 only
        else if (placementIntInfo[0] == 0 && placementIntInfo[1] != 0) {
            if (placementIntInfo[2] == 0) {
                for (int index_row = 1; index_row < 2; ++index_row) {
                    for (int index_col = placementIntInfo[1] + 1; index_col <= sizeOfShip; ++index_col) {
                        if ((placingBoard[index_row][index_col + 1]).equals("#")) {
                            System.out.println("Adjacent battleship detected, try again!");
                        }
                    }
                }
            } else {
                for (int index_row = 1; index_row <= sizeOfShip; ++index_row) {
                    for (int index_col = placementIntInfo[0]; index_col < 2; ++index_col) {
                        if ((placingBoard[index_row + 1][index_col]).equals("#")) {
                            System.out.println("Adjacent battleship detected, try again!");
                        }
                    }
                }
            }
        }
        // the case that the placement is in the col 0 only
        else if (placementIntInfo[1] == 0 && placementIntInfo[0] != 0) {
            if (placementIntInfo[2]==0){
                for (int index_row =placementIntInfo[0]-1; index_row < 2; ++index_row) {
                    for (int index_col = 1; index_col <= sizeOfShip; ++index_col) {
                        if ((placingBoard[index_row][index_col + 1]).equals("#")) {
                            System.out.println("Adjacent battleship detected, try again!");
                        }
                    }
                }
            }
            else {
                for (int index_row =placementIntInfo[0]; index_row <=sizeOfShip; ++index_row) {
                    for (int index_col = 1; index_col <2; ++index_col) {
                        if ((placingBoard[index_row][index_col]).equals("#")) {
                            System.out.println("Adjacent battleship detected, try again!");
                        }
                    }
                }

            }
        }
        // thr case that placement is in the last col
        // the case that placement is in the last row
        // all the other
        return;// what to return ??

    }





    public static void battleShipsPlace(String[][] gameBoard, int[] numOfShips, int[] sizeOfShips, int[] boardSizes) {
        System.out.println("Your current game board:");
        printBoardGame(gameBoard);
        String [][] placingBoard=createGameBoard(boardSizes);
        String[] placeInfoStr = (scanner.nextLine()).split(", ");
        int[] placeInfoInt = stringToIntArr(placeInfoStr);

        for(int i = 0; i < numOfShips.length; ++i) {
            for(int j = 0; j < numOfShips[i]; ++j) {
                System.out.println("Enter location and orientation for battleship of size " + sizeOfShips[i]);

                // while something is wrong you need to get a different input from the user
                while ((checkOrientation(placeInfoInt)) || (checkTile(placeInfoInt, gameBoard)) ||
                        (checkExceed(gameBoard, placeInfoInt, sizeOfShips[i])) ||
                        (checkOverlap(placingBoard, placeInfoInt, sizeOfShips[i]))) {

                    placeInfoStr = (scanner.nextLine()).split(", ");
                    placeInfoInt = stringToIntArr(placeInfoStr);

                }

                // all the data was correct - placement of the battleship in the board
                    for (int indexForSigh = 0; indexForSigh < sizeOfShips[i]; ++indexForSigh) {
                        gameBoard[placeInfoInt[0] + 1][placeInfoInt[1] + 1 + indexForSigh] = "#";
                        System.out.println("Your current game board:");
                        printBoardGame(gameBoard);
                        System.arraycopy(gameBoard, 0, placingBoard, 0, gameBoard.length);
                        printBoardGame(placingBoard);

                    }

                }

                        for (int index = 0; index < sizeOfShips[i]; ++index) {
                            gameBoard[placeInfoInt[0] + 1 + index][placeInfoInt[1] + 1] = "#";
                        }
                        printBoardGame(gameBoard);
                    }

                }


    public static void battleshipGame() {
        // TODO: Add your code here (and add more methods).
        int[] boardSizes = getBoardSizes();
        String[][] gameBoard = createGameBoard(boardSizes);
        String [][] computerGameBoard=createGameBoard(boardSizes);
        int[][] battleShipSizes = getBattleshipSizes();
        int[] numOfShips = battleShipSizes[0];
        int[] sizeOfShips = battleShipSizes[1];
        battleShipsPlace(gameBoard, numOfShips, sizeOfShips,boardSizes);



    }


    public static void main(String[] args) throws IOException {
        String path = args[0];
        scanner = new Scanner(new File(path));
        int numberOfGames = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Total of " + numberOfGames + " games.");

        for (int i = 1; i <= numberOfGames; i++) {
            scanner.nextLine();
            int seed = scanner.nextInt();
            rnd = new Random(seed);
            scanner.nextLine();
            System.out.println("Game number " + i + " starts.");
            battleshipGame();
            System.out.println("Game number " + i + " is over.");
            System.out.println("------------------------------------------------------------");
        }
        System.out.println("All games are over.");
    }
}



