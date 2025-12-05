package src.main.java;
import java.awt.Component;
import java.awt.Window;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.*;

/**
 *  @file       TicTacToeController.java
 *  @brief      TicTacToeController class implementation
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
 * Controller acts as an intermediary between the Model and the View. It handles
 * user input and updates the Model accordingly and updates the View to reflect
 * changes in the Model. It contains application logic, such as input validation
 * and data transformation.
 */
public class TicTacToeController extends MouseAdapter implements PropertyChangeListener
{
    private TicTacToeModel model;
    private TicTacToeView  view;

    /**
     *
     * @param model
     * @param view
     */
    public TicTacToeController(TicTacToeModel model, TicTacToeView view)
    {
        this.model = model;
        this.view = view;
        if (view != null)
        {
            view.setController(this);
            view.addMouseListener(this);
        }
        model.addPropertyChangeListener(this);
    }

    /**
     *
     * @param evt
     */
    public void exit(ActionEvent evt)
    {
        Window win = SwingUtilities.getWindowAncestor((Component) evt.getSource());
        win.dispose();
    }

    /**
     *
     * @param row
     * @param col
     */
    public void onBoardClick(int row, int col)
    {
        try
        {
            model.onBoardClick(row, col);
        }
        catch (TicTacToeException e)
        {  // catch and log the exception... not really an error
            System.err.println("onBoardClick Exception: " + e.getMessage());
        }
    }

    /**
     *
     */
    public void clear()
    {
        model.clearBoard();
    }

    @Override
    public void mousePressed(MouseEvent evt)
    {
        JLabel[][] board = view.getBoardLabels();

        for (int row = 0; row < board.length; row++)
        {
            for (int col = 0; col < board[row].length; col++)
            {
                if (board[row][col] == evt.getSource())
                {
                    onBoardClick(row, col);
                }
            }
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt)
    {
        if (TicTacToeModel.BOARD_UPDATE_PROP_NAME.equals(evt.getPropertyName()))
            {
                TicTacToeBoard gameBoard = model.getGameBoard();
                for (int row = 0; row < TicTacToeBoard.BOARD_SIZE; row++)
                {
                    for (int col = 0; col < TicTacToeBoard.BOARD_SIZE; col++)
                    {
                        view.setBoardIcon(row, col, gameBoard.getPlayerOwner(row, col));
                    }
                }

                view.setWinner(gameBoard.getWinner(), gameBoard.getWinType(), gameBoard.getWinIndex());
                System.out.println("Set Winner winner: " + gameBoard.getWinner() + " winType: " + gameBoard.getWinType() + " winIndex: " + gameBoard.getWinIndex());
            }
    }

}

