package src.main.java;
import java.io.IOException;
import javax.swing.*;

/**
 *  @file       TicTacToeMain.java
 *  @brief      Main source file for the "Project" assignment
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

// https://stackoverflow.com/questions/23097418/hello-i-am-creating-a-tictactoe-game-for-myself-to-understand-java-better


public class TicTacToeMain
{
    private static void createAndShowGui()
    {
        TicTacToeModel model = new TicTacToeModel();
        TicTacToeView view = null;
        try
        {
            view = new TicTacToeView();
        }
        catch (IOException e)
        {
            e.printStackTrace();
            System.exit(-1);
        }

        TicTacToeController controller = new TicTacToeController(model, view);

        JFrame frame = new JFrame("Tic Tac Toe");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.getContentPane().add(view.getMainPanel());
        frame.pack();
        frame.setLocationByPlatform(true);
        frame.setVisible(true);
    }

    public static void main(String[] args)
    {
        SwingUtilities.invokeLater(new Runnable()
        {
            public void run()
            {
                createAndShowGui();
            }
        });
    }
}


