/*
	 * Name: Sean Ruda
	 * Date: 3/7/22
	 * Description: Simulates the game of Minesweeper
*/

import java.util.Scanner;

class Main {
  public static void main(String[] args) 
  {
    int length = 3;	// You can make this any size. . . I will change to rectangle to test
    int width = 5;	// You can make this any size. . . I recommend 4x4 early for testing

    //inital boards and total mines
    String[][] key = new String[length][width];
    String[][] gameBoard = new String[length][width];
    int totalMines = (int)((length * width) * 0.15625);

    Scanner input = new Scanner(System.in);
    
    System.out.println("Welcome to Minesweeper!");

    //resets both boards, places mines, fills the key board, and prints the game board
    resetBoard(gameBoard);
    resetBoard(key);
    placeMines(key, totalMines);
    fillBoard(key);
    printBoard(gameBoard);

    //prompts the user to pick a row
    System.out.println("Pick a cell");
    System.out.print("Row: ");
    
    int row = input.nextInt();
    //makes sure the row is valid
    while(row > gameBoard.length - 1 || row < 0)
    {
      System.out.println("Pick a cell inbounds");
      System.out.print("Row: ");
      row = input.nextInt();
    }

    //prompts the user to pick a column
    System.out.print("Col: ");
    int col = input.nextInt();

    //makes sure the column is valid
    while(col > gameBoard[0].length - 1 || col < 0)
    {
      System.out.println("Pick a cell inbounds");
      System.out.print("Col: ");
      col = input.nextInt();
    }

    //makes sure the 1st pick isn't a mine
    boolean check = true;
    if(!(makeMove(gameBoard, key, row, col)))
    {
      check = false;
    }

    //prints the game board
    System.out.println();
    printBoard(gameBoard);

    //makes sure game board isn't full and user didn't pick a mine on first pick
    while(gameBoardFull(gameBoard) != true && check)
      {
        System.out.println("Would you like to place a mine? 1 for yes");
        int minePlace = input.nextInt();
        //if the user wants to place a mine
        if(minePlace == 1)
        {
          System.out.println("Pick a cell");
        
          System.out.print("Row: ");
          row = input.nextInt();
          
          while(row > gameBoard.length - 1 || row < 0)
          {
            System.out.println("Pick a cell inbounds");
            System.out.print("Row: ");
            row = input.nextInt();
          }
          
          System.out.print("Col: ");
          col = input.nextInt();

          while(col > gameBoard[0].length - 1 || col < 0)
          {
            System.out.println("Pick a cell inbounds");
            System.out.print("Col: ");
            col = input.nextInt();
          }  

          //sets the gameboard to a mine and makes the move at that point
          gameBoard[row][col] = "M";
          makeMove(gameBoard, key, row, col);
        }
        
        //the user doesn't want to pick a mine, just reveal the cell
        else
        {
          System.out.println("Pick a cell");
        
          System.out.print("Row: ");
          row = input.nextInt();

          while(row > gameBoard.length - 1 || row < 0)
          {
            System.out.println("Pick a cell inbounds");
            System.out.print("Row: ");
            row = input.nextInt();
          }
        
          System.out.print("Col: ");
          col = input.nextInt();

          while(col > gameBoard[0].length - 1 || col < 0)
          {
            System.out.println("Pick a cell inbounds");
            System.out.print("Col: ");
            col = input.nextInt();
          }  

          //if the cell revealed is an M break out of the for loop
          if(!(makeMove(gameBoard, key, row, col)))
          {
            break;
          }
        }

        //prints the board at the end
        System.out.println();
        printBoard(gameBoard);
      }

    //after the loop this cchecks if the boards match
    if(checkValidMines(key, gameBoard) && gameBoardFull(gameBoard) == true)
    {
      System.out.println("\nCongratulations!  Winner Winner!");
    }

    //if the mines are not in the right place
    else if(!(checkValidMines(key, gameBoard)) && gameBoardFull(gameBoard) == true)
    {
      System.out.println("\nYou lose!  Your mines were not in the correct place.");
      System.out.println("Here's the key:");
      printBoard(key);
    }
    //else the user hit a mine and lost 
    else
    {
      System.out.println("\nYOU LOSE!");
      printBoard(key);
    }
    
    
  }

  /*  Description: Places Mines
  *   Parameters: String[][] theKey, int theTotalMines - needs the total mines and a board to fill
  *   Return type: void - nothing is returned the key is filled
  */
  public static void placeMines(String[][] theKey, int theTotalMines)
  {
    //creates a variable to keep track of the mines that have been placed
    int theMines = 0;

    //while loop that randomly places the mines until limit is reached
    while(theMines < theTotalMines)
    {
      int randRow = (int)(Math.random() * theKey.length);
      int randCol = (int)(Math.random() * theKey[0].length);

      if(theKey[randRow][randCol].equals("_"))
      {
        theKey[randRow][randCol] = "M";
        theMines++;
      }
    }
  }

  /*  Description: fills key board with space values
  *   Parameters: String[][] theKey - needs the key board to fill with numbers
  *   Return type: void - nothing is returned the key is filled
  */
  public static void fillBoard(String[][] theKey)
  {
    for(int row = 0; row < theKey.length; row++)
    {    
      for(int col = 0; col < theKey[row].length; col++)
      {
            int mineCounter = 0;
            if(theKey[row][col] != "M")
            {  
              //first row
              if(row == 0)
              {
                //for first cell in first row
                if(col == 0)
                {
                  if(theKey[0][1].equals("M"))
                  {
                  mineCounter++;
                  }
                  if(theKey[1][1].equals("M"))
                  {
                  mineCounter++;
                  }
                  if(theKey[1][0].equals("M"))
                  {
                    mineCounter++; 
                  }
                }
                //for last space in first row
                else if(col == theKey[row].length - 1)
                {
                  if(theKey[0][theKey[row].length - 2].equals("M"))
                  {
                    mineCounter++;
                  }
                  if(theKey[1][theKey[row].length - 2].equals("M"))
                  {
                    mineCounter++;
                  }
                  if(theKey[1][theKey[row].length - 1].equals("M"))
                  {
                    mineCounter++; 
                  }
                }
                //for the rest of the cells in first row
                else
                {
                  if(theKey[row][col - 1].equals("M"))
                  {
                    mineCounter++;
                  }
                  if(theKey[row][col + 1].equals("M"))
                  {
                    mineCounter++;
                  }
                  if(theKey[row + 1][col - 1].equals("M"))
                  {
                    mineCounter++; 
                  }
                  if(theKey[row + 1][col].equals("M"))
                  {
                    mineCounter++; 
                  }
                  if(theKey[row + 1][col + 1].equals("M"))
                  {
                    mineCounter++; 
                  }
                }
              }
              //for the last row
              else if(row == theKey.length - 1)
              {
                //for first space in last row
                if(col == 0)
                {
                  if(theKey[theKey.length - 1][1].equals("M"))
                  {
                    mineCounter++;
                  }
                  if(theKey[theKey.length - 2][1].equals("M"))
                  {
                    mineCounter++;
                  }
                  if(theKey[theKey.length - 2][0].equals("M"))
                  {
                    mineCounter++; 
                  }
                }
                //for the last space in last row
                else if(col == theKey[row].length - 1)
                {
                  if(theKey[theKey.length - 1][theKey[row].length - 2].equals("M"))
                  {
                    mineCounter++;
                  }
                  if(theKey[theKey.length - 2][theKey[row].length - 1].equals("M"))
                  {
                    mineCounter++;
                  }
                  if(theKey[theKey.length - 2][theKey[row].length - 2].equals("M"))
                  {
                    mineCounter++; 
                  }
                }
                //for the rest of the cells in last row
                else
                {
                  if(theKey[row][col - 1].equals("M"))
                  {
                    mineCounter++;
                  }
                  if(theKey[row][col + 1].equals("M"))
                  {
                    mineCounter++;
                  }
                  if(theKey[row - 1][col - 1].equals("M"))
                  {
                    mineCounter++; 
                  }
                  if(theKey[row - 1][col].equals("M"))
                  {
                    mineCounter++; 
                  }
                  if(theKey[row - 1][col + 1].equals("M"))
                  {
                    mineCounter++; 
                  }
                }
              }
              //for the first column
              else if(col == 0)
              {
                if(theKey[row - 1][col].equals("M"))
                {
                  mineCounter++; 
                }
                if(theKey[row + 1][col].equals("M"))
                {
                  mineCounter++; 
                }
                if(theKey[row - 1][col + 1].equals("M"))
                {
                  mineCounter++; 
                }
                if(theKey[row][col + 1].equals("M"))
                {
                  mineCounter++; 
                }
                if(theKey[row + 1][col + 1].equals("M"))
                {
                  mineCounter++; 
                }
              }
              //for the last column
              else if(col == theKey[row].length - 1)
              {
                if(theKey[row - 1][col].equals("M"))
                {
                  mineCounter++; 
                }
                if(theKey[row + 1][col].equals("M"))
                {
                  mineCounter++; 
                }
                if(theKey[row - 1][col - 1].equals("M"))
                {
                  mineCounter++; 
                }
                if(theKey[row][col - 1].equals("M"))
                {
                  mineCounter++; 
                }
                if(theKey[row + 1][col - 1].equals("M"))
                {
                  mineCounter++; 
                }
              }

              //for the rest of the cells
              else
              {
                if(theKey[row - 1][col - 1].equals("M"))
                {
                  mineCounter++; 
                }
                if(theKey[row - 1][col].equals("M"))
                {
                  mineCounter++; 
                }
                if(theKey[row - 1][col + 1].equals("M"))
                {
                  mineCounter++; 
                }
                if(theKey[row][col - 1].equals("M"))
                {
                  mineCounter++; 
                }
                if(theKey[row][col + 1].equals("M"))
                {
                  mineCounter++; 
                }
                if(theKey[row + 1][col - 1].equals("M"))
                {
                  mineCounter++; 
                }
                if(theKey[row + 1][col].equals("M"))
                {
                  mineCounter++; 
                }
                if(theKey[row + 1][col + 1].equals("M"))
                {
                  mineCounter++; 
                }
              }
              //sets value in the key equal to how many mines are around the value
              theKey[row][col] = "" + mineCounter;
            }
      }    
    }
  }
  
  /*  Description: Prints the board
   *   Parameters: String[][] arr, the key or the game board needs to be printed
   *   Return type: void - nothing is returned the boards are filled
  */
  public static void printBoard(String[][] arr)
  {
    //enhanced for loop to print
    for(String[] row: arr)
      {
        for(String space: row)
          {
            System.out.print(space + " ");
          }
        System.out.println();
      }
  }

  /*  Description: resets the board
   *   Parameters: String[][] arr, the key or the game board needs to be reset
   *   Return type: void - nothing is returned the boards are reset
  */
  public static void resetBoard(String[][] arr)
  {
    //sets every cell to an underscore
    for(int row = 0; row < arr.length; row++)
      {
        for(int col = 0; col < arr[row].length; col++)
          {
            arr[row][col] = "_";
          }
      }
  }

  /*  Description: checks if the mines in each board match
   *   Parameters: String[][] theKey, String[][] theGameBoard
   *   Return type: boolean - returns true if there are 
  */
  public static boolean checkValidMines(String[][] theKey, String[][] theGameBoard)
  {
    //checks every cell for if the mines match
    for(int row = 0; row < theGameBoard.length; row++)
    {
      for(int col = 0; col < theGameBoard[row].length; col++)
      {
        if(theGameBoard[row][col].equals("M"))
        {
          if(!(theKey[row][col].equals("M")))
          {
            return false;
          }
        }
      }
    }
    return true;
  }

  /*  Description: checks if the mines in each board match
   *   Parameters: String[][] theGameBoard - needs to check the game board
   *   Return type: boolean - returns true if the mines are in the correct cells
  */
  public static boolean gameBoardFull(String[][] theGameBoard)
  {
    for(int row = 0; row < theGameBoard.length; row++)
    {
      for(int col = 0; col < theGameBoard[row].length; col++)
      {
        if(theGameBoard[row][col].equals("_"))
        {
          return false;
        }
      }
    }
    return true;
  }

  /*  Description: makes the move and changes the board
   *   Parameters: String[][] theGameBoard, String[][] theKey, int row, int col - uncovers the 
   *   Return type: boolean - returns true if the move is successful return false if not.
  */
  public static boolean makeMove(String[][] theGameBoard, String[][] theKey, int row, int col)
  {
    if(theGameBoard[row][col].equals("M"))
    {
      
    }
    else
    {
      theGameBoard[row][col] = theKey[row][col];
      if(theKey[row][col].equals("M"))
      {
        return false;
      }
    }
    return true;
  }
  
}