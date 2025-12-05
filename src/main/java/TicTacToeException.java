package src.main.java;

@SuppressWarnings("serial")
class TicTacToeException extends Exception
{
    public TicTacToeException()
    {
        super();
    }

    public TicTacToeException(String message)
    {
        super(message);
    }
}