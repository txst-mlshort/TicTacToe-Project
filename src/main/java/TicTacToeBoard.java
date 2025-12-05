package src.main.java;

/**
 *  @file       TicTacToeBoard.java
 *  @brief      TicTacToeBoard class implementation
 *  @author     Mark L. Short
 *  @date       Dec 4, 2025
 *
 *  <dl>
 *       <dt>Course</dt><dd>CS 3354 Object Oriented Design and Programming</dd><BR>
 *       <dt>Instructor</dt><dd>Dr. Alberto Castro-Hernandez</dd>
 *  </dl>
 *
 *  <b>Assignment:</b>
 *  ==================
 *  @see https://canvas.txstate.edu/courses/2495146/assignments/39476428
 *  @see https://canvas.txstate.edu/courses/2495146/files/375703617?wrap=1
 *
 */

/**
 * This is used primarily to denote the player that owns a particular game square.
 */
enum TttPlayerOwner
{
    NOONE, //< unowned game square
    X,     //< game square owned by X player
    O      //< game square owned by Y player
}

public class TicTacToeBoard
{
    public static final int NO_WINNER = 0;
    public static final int X_WINNER = 1;
    public static final int O_WINNER = 2;
    public static final int DRAW = 3;

    /**
     * Defines the game board dimension
     */
    public static final int BOARD_SIZE = 3;

    private TttPlayerOwner[][] tttBoard = new TttPlayerOwner[BOARD_SIZE][BOARD_SIZE];

    /**
     * Will be one of NO_WINNER, X_WINNER, O_WINNER or DRAW
     */
    private int winner;
    /**
     * Will be one of 0 - NA, 1 - Horizontal, 2 - Vertical, 3 - Diagonal, 4 - Anti Diagonal
     */
    private int winType;
    /**
     * Contains the relevant Row or Col value or -1
     */
    private int winIndex;

    public TicTacToeBoard()
    {
        clear();
    }

    public int getWinner()
    {
        return winner;
    }

    public int getWinType()
    {
        return winType;
    }

    public int getWinIndex()
    {
        return winIndex;
    }

    /**
     * Clears the game board
     */
    public void clear()
    {
        winner = NO_WINNER;
        winType = 0;
        winIndex = -1;
        for (int row = 0; row < tttBoard.length; row++)
        {
            for (int col = 0; col < tttBoard[row].length; col++)
            {
                tttBoard[row][col] = TttPlayerOwner.NOONE;
            }
        }
    }

    /**
     *
     * @param row
     * @param col
     * @param playerOwner
     * @throws TicTacToeException
     */
    public void update(int row, int col, TttPlayerOwner playerOwner) throws TicTacToeException
    {
        if (tttBoard[row][col] == TttPlayerOwner.NOONE)
        {
            tttBoard[row][col] = playerOwner;
        }
        else
        {
            String message = "Invalid updateBoard for row: %d, col: %d, player: %s. "
                    + "Square already occupied by player: %s";
            message = String.format(message, row, col, playerOwner, tttBoard[row][col]);
            throw new TicTacToeException(message);
        }
    }

    /**
     *
     * @param row
     * @param col
     * @return
     */
    TttPlayerOwner getPlayerOwner(int row, int col)
    {
        return tttBoard[row][col];
    }

    /**
     *
     * @return boolean indicating if game board is full
     */
    public boolean isFull()
    {
        boolean isBoardFull = true;
        for (int i = 0; i < BOARD_SIZE; i++)
        {
            for (int j = 0; j < BOARD_SIZE; j++)
            {
                if (tttBoard[i][j] == TttPlayerOwner.NOONE)
                {
                    isBoardFull = false;
                    break;
                }
            }
            if (!isBoardFull)
                break;
        }
        return isBoardFull;
    }

    /**
     *
     * @retval 0 - NO_WINNER
     * @retval 1 - X_WINNER
     * @retval 2 - O_WINNER
     * @retval 3 - DRAW
     */
    public int checkForWinner()
    {
        // Check rows
        for (int i = 0; i < BOARD_SIZE; i++)
        {
            if (tttBoard[i][0] != TttPlayerOwner.NOONE &&
                tttBoard[i][0] == tttBoard[i][1] &&
                tttBoard[i][1] == tttBoard[i][2])
            {
                winType = 1; // horizontal win
                winIndex = i;
                winner = tttBoard[i][0].ordinal();
                return winner;
            }
        }

        // Check columns
        for (int j = 0; j < BOARD_SIZE; j++)
        {
            if (tttBoard[0][j] != TttPlayerOwner.NOONE &&
                tttBoard[0][j] == tttBoard[1][j] &&
                tttBoard[1][j] == tttBoard[2][j])
            {
                winType = 2; // vertical win
                winIndex = j;
                winner = tttBoard[0][j].ordinal();
                return winner;
            }
        }

        // Check main diagonal (top-left to bottom-right)
        if (tttBoard[0][0] != TttPlayerOwner.NOONE &&
            tttBoard[0][0] == tttBoard[1][1] &&
            tttBoard[1][1] == tttBoard[2][2])
        {
            winType = 3; // diagonal win
            winIndex = -1; // not used
            winner = tttBoard[0][0].ordinal();
            return winner;
        }

        // Check anti-diagonal (top-right to bottom-left)
        if (tttBoard[0][2] != TttPlayerOwner.NOONE &&
            tttBoard[0][2] == tttBoard[1][1] &&
            tttBoard[1][1] == tttBoard[2][0])
        {
            winType = 4; // anti-diagonal win
            winIndex = -1; // not used
            winner = tttBoard[0][2].ordinal();
            return winner;
        }

        // No winner yet, check for draw
        if (isFull())
        {
            winner = DRAW;
        }
        else
        {
            winner = NO_WINNER;
        }

        return winner;
    }
}