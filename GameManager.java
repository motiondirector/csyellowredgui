import java.awt.Graphics2D;


public class GameManager {
private GameBoard gameBoard;
private boolean isRunning;
	
	public GameManager(int rows, int columns, int reqcon)
	{
		gameBoard = new GameBoard(rows, columns, reqcon);
		isRunning = true;
	}
	
	public int getReqCon()
	{
		return gameBoard.getConnection();
	}
	
	public int getNumOfColumns()
	{
		return gameBoard.getColumns();
	}
	
	public int getNumOfRows()
	{
		return gameBoard.getRows();
	}
	
	public boolean insert(int player, int column)
	{
		return gameBoard.insertPiece(player, column);
	}
	
	public void newGame()
	{
		gameBoard.clear();
		setRunning(true);
	}
	
	public void draw(Graphics2D g2d, int w, int h)
	{
		gameBoard.draw(g2d, w, h);
	}
	
	public void setRunning(boolean running)
	{
	    isRunning = running;
	}
	
	public boolean isRunning()
	{
	    return isRunning;
	}
	
}
