
public class GameManager {
private GameBoard gameBoard;
	
	public GameManager(int rows, int columns, int reqcon)
	{
		gameBoard = new GameBoard(rows, columns, reqcon);
	}
	
	public int getReqCon()
	{
		return gameBoard.connection;
	}
	
	public int getNumOfColumns()
	{
		return gameBoard.gameGrid[0].length;
	}
	
	public int getNumOfRows()
	{
		return gameBoard.gameGrid.length;
	}
	
	public boolean insert(int player, int column)
	{
		return gameBoard.insertPiece(player, column);
	}
	
	public void newGame()
	{
		gameBoard.clear();
	}
	
	public int[][] getGameBoard()
	{
		return gameBoard.gameGrid;
	}
	
}
