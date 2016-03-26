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
	
	public boolean hasWin()
	{
	    return gameBoard.checkWin();
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
	
	public int getCircleDiameter(int w, int h)
	{
		int gridRows = this.getNumOfRows();
   	    int gridCols = this.getNumOfColumns();
   	    int topOffset = 40; 
        int sideOffset = 20;
        int circlePadding = 5;
        int gridWidth = w - sideOffset * 2;
        int gridHeight = h - topOffset * 2;
        int circleDiameter = gridWidth / gridCols - circlePadding;  ;
        if (h < gridRows * circleDiameter + (circlePadding * (gridCols -1)) )
        {
        	circleDiameter = gridHeight / gridRows - circlePadding;  
        }
        return circleDiameter;
	}
	
	public int getSideOffset(int w, int h)
	{
		int circlePadding = 5;
		return (w - ((this.getCircleDiameter(w, h) * this.getNumOfColumns()) + (circlePadding * (this.getNumOfColumns() -1))))/2;
	}
}
