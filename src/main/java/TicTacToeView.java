package src.main.java;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.EnumMap;
import java.util.Map;
import java.net.*;
import javax.imageio.ImageIO;
import javax.swing.*;

/**
 *  @file       TicTacToeView.java
 *  @brief      TicTacToeView class implementation
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
 * Displays the data from the Model to the user and sends user inputs to the
 * Controller. It is passive and does not directly interact with the Model.
 * Instead, it receives data from the Model and sends user inputs to the
 * Controller for processing.
 */
public class TicTacToeView
{
    private static final String PATH_TO_IMG = "https://i.sstatic.net/mwfLB.png";
    private static final int LINE_WIDTH = 5;

    private JPanel mainPanel = new JPanel();
    private JPanel gameBoardPanel = new GameBoardPanel();
    private JLabel statusLabel = new JLabel();
    private Map<TttPlayerOwner, Icon> iconMap = new EnumMap<>(TttPlayerOwner.class);
    private JLabel[][] boardLabels = new JLabel[TicTacToeModel.BOARD_SIZE][TicTacToeModel.BOARD_SIZE];

    private TicTacToeController controller;

    private int winner;
    private int winType;
    private int winIndex;

    /**
     * default constructor
     * @throws IOException
     */
    public TicTacToeView() throws IOException
    {
        winner = TicTacToeBoard.NO_WINNER;
        winType = 0;
        winIndex = -1;
        initIconMap(PATH_TO_IMG);
        initComponents();
    }

    /**
     *
     * @param strPathToImg
     * @throws IOException
     */
    private void initIconMap(String strPathToImg) throws IOException
    {
       // BufferedImage img = ImageIO.read(getClass().getResourceAsStream(IMAGE));
        URL imgUrl = null;
        try
        {
            imgUrl = new URI(strPathToImg).toURL();
        }
        catch (URISyntaxException e)
        {
            System.err.println("Invalid URI: " + e.getMessage());
        }

        if (imgUrl != null)
        {
            // note - the following can throw an IOException
            BufferedImage img = ImageIO.read(imgUrl);
            Icon[] imgIcons = splitImg(img);
            iconMap.put(TttPlayerOwner.X, imgIcons[0]);
            iconMap.put(TttPlayerOwner.O, imgIcons[1]);
            iconMap.put(TttPlayerOwner.NOONE, createEmptyIcon(imgIcons[0]));
        }
    }

    /**
     *
     */
    private void initComponents()
    {
        gameBoardPanel.setLayout(new GridLayout(boardLabels.length, boardLabels[0].length, LINE_WIDTH, LINE_WIDTH));
        gameBoardPanel.setBackground(Color.black);

        for (int row = 0; row < boardLabels.length; row++)
        {
            for (int col = 0; col < boardLabels[row].length; col++)
            {
                boardLabels[row][col] = new JLabel(iconMap.get(TttPlayerOwner.NOONE));
                boardLabels[row][col].setOpaque(true);
                boardLabels[row][col].setBackground(Color.LIGHT_GRAY);
                gameBoardPanel.add(boardLabels[row][col]);
            }
        }

        JPanel btnPanel = new JPanel(new GridLayout(1, 0, LINE_WIDTH, 0));
        btnPanel.add(new JButton(new ClearAction("Clear", KeyEvent.VK_C)));
        btnPanel.add(new JButton(new ExitAction("Exit", KeyEvent.VK_X)));

        statusLabel.setText("Welcome to Tic Tac Toe!");
        int borderWidth = 2;
        mainPanel.setLayout(new BorderLayout(borderWidth, borderWidth));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(borderWidth, borderWidth, borderWidth, borderWidth));
        mainPanel.add(gameBoardPanel, BorderLayout.NORTH);
        mainPanel.add(btnPanel, BorderLayout.CENTER);
        mainPanel.add(statusLabel, BorderLayout.SOUTH);
    }

    /**
     *
     */
    public void resetBoardLabels()
    {
        for (int row = 0; row < boardLabels.length; row++)
        {
            for (int col = 0; col < boardLabels[row].length; col++)
            {
                boardLabels[row][col].setIcon(iconMap.get(TttPlayerOwner.NOONE));
                boardLabels[row][col].setOpaque(true);
                boardLabels[row][col].setBackground(Color.LIGHT_GRAY);
            }
        }
    }

    /**
     *
     * @param strText
     */
    public void setStatusText(String strText)
    {
        statusLabel.setText(strText);
    }

    /**
     * Adds the specified mouseAdapter to receive mouse events from
     * this component.
     * @param mouseAdapter
     */
    public void addMouseListener(MouseAdapter mouseAdapter)
    {
        for (int row = 0; row < boardLabels.length; row++)
        {
            for (int col = 0; col < boardLabels[row].length; col++)
            {
                boardLabels[row][col].addMouseListener(mouseAdapter);
            }
        }
    }

    public void setController(TicTacToeController controller)
    {
        this.controller = controller;
    }

    public JComponent getMainPanel()
    {
        return mainPanel;
    }

    public JLabel[][] getBoardLabels()
    {
        return boardLabels;
    }

    /**
     * Sets the game square with the icon associated with the owning player
     * @param row location of game square
     * @param col location of game square
     * @param playerOwner owner of the game square
     */
    public void setBoardIcon(int row, int col, TttPlayerOwner playerOwner)
    {
        boardLabels[row][col].setIcon(iconMap.get(playerOwner));
    }

    public void setWinner(int aWinner, int type, int index)
    {
        winner = aWinner;
        winType = type;
        winIndex = index;

        switch (winner)
        {
            case TicTacToeBoard.NO_WINNER:
                setStatusText("Welcome to Tic Tac Toe!");
                break;
            case TicTacToeBoard.X_WINNER:
                setStatusText("X Player Won !!!");
                break;
            case TicTacToeBoard.O_WINNER:
                setStatusText("O Player Won !!!");
                break;
            case TicTacToeBoard.DRAW:
                setStatusText("Game is a Draw !!!  Press the Clear Btn to reset.");
                break;
        }

        gameBoardPanel.repaint(); // Request a repaint
    }

    /**
     * Creates an empty ImageIcon using the size of a given icon object.
     * @param icon
     * @return Icon object containing empty buffered image.
     */
    private static Icon createEmptyIcon(Icon icon)
    {
        int width  = icon.getIconWidth();
        int height = icon.getIconHeight();
        BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        return new ImageIcon(img);
    }

    /**
    * Splits a BufferedImage object in half and returns two icons.
    * @param img
    * @return Icon[] containing separated images
    */
    private static Icon[] splitImg(BufferedImage img)
    {
        int width  = img.getWidth();
        int height = img.getHeight();
        int subImageWidth  = width / 2 - LINE_WIDTH;
        int subImageHeight = height / 2 - LINE_WIDTH;
        int subImageOffset = 2 * LINE_WIDTH;
        Icon[] rgIcons = new ImageIcon[2];
        rgIcons[0] = new ImageIcon(img.getSubimage(0, 0, subImageWidth, subImageHeight));
        rgIcons[1] = new ImageIcon(img.getSubimage(subImageWidth + subImageOffset, 0, subImageWidth, subImageHeight));
        return rgIcons;
    }

    private class ClearAction extends AbstractAction
    {
        public ClearAction(String name, int mnemonic)
        {
            super(name);
            putValue(MNEMONIC_KEY, mnemonic);
        }

        @Override
        public void actionPerformed(ActionEvent evt)
        {
            if (controller != null)
            {
                controller.clear();
            }
            resetBoardLabels();
        }
    }

    private class ExitAction extends AbstractAction
    {
        public ExitAction(String name, int mnemonic)
        {
            super(name);
            putValue(MNEMONIC_KEY, mnemonic);
        }

        @Override
        public void actionPerformed(ActionEvent evt)
        {
            if (controller != null)
            {
                controller.exit(evt);
            }
        }
    }

    private class GameBoardPanel extends JPanel
    {
        @Override
        protected void paintComponent(Graphics g)
        {
            super.paintComponent(g);

            if (winType != 0)
                {
                g.setColor(Color.RED); // Or another desired color
                Graphics2D g2d = (Graphics2D) g;
                g2d.setStroke(new BasicStroke(8)); // Make the line thicker

                int cellSize = getWidth() / TicTacToeBoard.BOARD_SIZE; //  3;

                if (winType == 1)
                { // Horizontal win
                    g2d.drawLine(0, winIndex * cellSize + cellSize / 2, getWidth(), winIndex * cellSize + cellSize / 2);
                    boardLabels[winIndex][0].setBackground(Color.GREEN);
                    boardLabels[winIndex][1].setBackground(Color.GREEN);
                    boardLabels[winIndex][2].setBackground(Color.GREEN);
                }
                else if (winType == 2)
                { // Vertical win
                    g2d.drawLine(winIndex * cellSize + cellSize / 2, 0, winIndex * cellSize + cellSize / 2, getHeight());
                    boardLabels[0][winIndex].setBackground(Color.GREEN);
                    boardLabels[1][winIndex].setBackground(Color.GREEN);
                    boardLabels[2][winIndex].setBackground(Color.GREEN);
                }
                else if (winType == 3)
                { // Main diagonal win (top-left to bottom-right)
                    g2d.drawLine(0, 0, getWidth(), getHeight());
                    boardLabels[0][0].setBackground(Color.GREEN);
                    boardLabels[1][1].setBackground(Color.GREEN);
                    boardLabels[2][2].setBackground(Color.GREEN);
                }
                else if (winType == 4)
                { // Anti-diagonal win (bottom-left to top-right)
                    g2d.drawLine(0, getHeight(), getWidth(), 0);
                    boardLabels[2][0].setBackground(Color.GREEN);
                    boardLabels[1][1].setBackground(Color.GREEN);
                    boardLabels[0][2].setBackground(Color.GREEN);
                }
            }
        }
    }

}
