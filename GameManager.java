import java.awt.Graphics2D;

/**
 * A manager for the overall game
 */
public class GameManager {
private GameBoard gameBoard;
private boolean isRunning;
	
	/**
	 * Creates a GameManager containing a game board with the specified number of rows, columns, and connections
	 * @param rows the number of rows for the game board
	 * @param columns the number of columns for the game board
	 * @param reqcon the number of required connections to win the game
	 */
	public GameManager(int rows, int columns, int reqcon)
	{
		gameBoard = new GameBoard(rows, columns, reqcon);
		isRunning = true;
	}
	
	/**
	 * Gets the number of required connections of the game board
	 * @return the number of required connections
	 */
	public int getReqCon()
	{
		return gameBoard.getConnection();
	}
	
	/**
	 * Gets the number of columns of the game board
	 * @return the number of columns
	 */
	public int getNumOfColumns()
	{
		return gameBoard.getColumns();
	}
	
	/**
	 * Gets the number of rows of the game board
	 * @return the number of rows
	 */
	public int getNumOfRows()
	{
		return gameBoard.getRows();
	}
	
	/**
	 * Checks if the game currently has a winner
	 * @return true if there is a winner, false otherwise
	 */
	public boolean hasWin()
	{
	    return gameBoard.checkWin();
	}
	
	/**
	 * Attempts to insert a piece into the specified column
	 * @param player the piece to be inserted
	 * @param column the column to be inserted into
	 * @return true if insertion was successful, false otherwise
	 */
	public boolean insert(Player player, int column)
	{
		return gameBoard.insertPiece(player, column);
	}
	
	/**
	 * Clears the baord and starts a new game
	 */
	public void newGame()
	{
		gameBoard.clear();
		setRunning(true);
	}
	
	/**
	 * Draws the game board
	 * @param g2d
	 * @param w the width of the container containing the game board
	 * @param h the height of the container containing the game board
	 */
	public void draw(Graphics2D g2d, int w, int h)
	{
		int topOffset = 40;
		gameBoard.draw(g2d, this.getSideOffset(w, h), topOffset, this.getCircleDiameter(w, h));
	}
	
	/**
	 * Sets whether the game is running or finished
	 * @param running the boolean to set
	 */
	public void setRunning(boolean running)
	{
	    isRunning = running;
	}
	
	/**
	 * Checks if the game is running or finished
	 * @return true if the game is running, false if it is finished
	 */
	public boolean isRunning()
	{
	    return isRunning;
	}
	
	/**
	 * Calculates the circle diameter needed based on the given width and height
	 * @param w the width of the container
	 * @param h the height of the container
	 * @return the expected circle diameter
	 */
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
        if (h < gridRows * circleDiameter + (circlePadding * (gridCols -1)) + topOffset )
        {
        	circleDiameter = gridHeight / gridRows - circlePadding;  
        }
        return circleDiameter;
	}
	
	/**
	 * Calculates the side offset needed based on the given width and height
	 * @param w the width of the container
	 * @param h the height of the container
	 * @return the calculated side offset
	 */
	public int getSideOffset(int w, int h)
	{
		int circlePadding = 5;
		return (w - ((this.getCircleDiameter(w, h) * this.getNumOfColumns()) + (circlePadding * (this.getNumOfColumns() -1))))/2;
	}
}
