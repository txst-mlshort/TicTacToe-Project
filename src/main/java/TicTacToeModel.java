/**
 *  @file       TicTacToeModel.java
 *  @brief      TicTacToeModel class implementation
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

package src.main.java;
import java.beans.PropertyChangeListener;
import javax.swing.event.SwingPropertyChangeSupport;

/**
 * The Model component in the MVC (Model-View-Controller) design pattern
 * demonstrates the data and business logic of an application. It is
 * responsible for managing the application's data, processing business rules,
 * and responding to requests for information from other components, such as
 * the View and the Controller.
 */
public class TicTacToeModel
{
    public static final int BOARD_SIZE = TicTacToeBoard.BOARD_SIZE;
    public static final String BOARD_UPDATE_PROP_NAME = "board update";

    private final SwingPropertyChangeSupport propertyChanges = new SwingPropertyChangeSupport(this);

    private TicTacToeBoard gameBoard = new TicTacToeBoard();
    private TttPlayerOwner currentPlayer;
    private boolean bIsGameOver;

    /**
     * default constructor
     */
    public TicTacToeModel()
    {
        bIsGameOver = false;
        currentPlayer = TttPlayerOwner.X;
        gameBoard.clear();
    }

    /**
     *
     * @return
     */
    public TicTacToeBoard getGameBoard()
    {
        return gameBoard;
    }

    public boolean isGameOver()
    {
        return bIsGameOver;
    }

    /**
     *
     * @return
     */
    public int getBoardWinType()
    {
        return gameBoard.getWinType();
    }

    public int getBoardWinIndex()
    {
        return gameBoard.getWinIndex();
    }

    public void updateBoard(int row, int col, TttPlayerOwner playerOwner) throws TicTacToeException
    {
        if (bIsGameOver)
        {
            return;
        }
        // update the gameBoard model data
        gameBoard.update(row, col, playerOwner);

        int iWinner = gameBoard.checkForWinner();
        switch(iWinner)
        {
            case TicTacToeBoard.NO_WINNER:
                switchPlayer();
                break;
            case TicTacToeBoard.X_WINNER:
            case TicTacToeBoard.O_WINNER:
            case TicTacToeBoard.DRAW:
                bIsGameOver = true;
                break;
        }

        propertyChanges.firePropertyChange(BOARD_UPDATE_PROP_NAME, null, gameBoard);
    }

    /**
     *
     */
    public void clearBoard()
    {
        bIsGameOver   = false;
        currentPlayer = TttPlayerOwner.X;
        gameBoard.clear();

        propertyChanges.firePropertyChange(BOARD_UPDATE_PROP_NAME, null, gameBoard);
    }

    public void onBoardClick(int row, int col) throws TicTacToeException
    {
        updateBoard(row, col, currentPlayer);
    }

    public boolean isBoardFull()
    {
        return gameBoard.isFull();
    }

    private void switchPlayer()
    {
        currentPlayer = (currentPlayer == TttPlayerOwner.X) ? TttPlayerOwner.O : TttPlayerOwner.X;
    }

    public void addPropertyChangeListener(PropertyChangeListener listener)
    {
        propertyChanges.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener)
    {
        propertyChanges.removePropertyChangeListener(listener);
    }

}