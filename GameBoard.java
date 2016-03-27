import java.awt.Color;
import java.awt.Graphics2D;

/**
 * GameBoard containing the locations of the current pieces played in the game
 */
public class GameBoard
{
	private Player[][] gameGrid;
	private int connection;
	private GridReader winChecker;

	/**
	 * Constructs a game board with the given number of rows, columns, and
	 * required connections
	 * 
	 * @param rows the rows of the game board
	 * @param columns the columns of the game board
	 * @param reqcon the required number of connections of the game board
	 */
	public GameBoard(int rows, int columns, int reqcon)
	{
		gameGrid = new Player[rows][columns];
		connection = reqcon;
		winChecker = new LinearGridReader();
	}

	/**
	 * Attempts to insert a piece into the specified column
	 * @param player the piece to be inserted
	 * @param column the column to insert the piece into
	 * @return true if insertion was successful, false otherwise
	 */
	public boolean insertPiece(Player player, int column)
	{
		for (int i = gameGrid.length - 1; i >= 0; i--)
		{
			if (canPlace(i, column))
			{
				gameGrid[i][column] = player;
				return true;
			}
		}
		return false;
	}

	/**
	 * Checks if the winning condition was fulfilled
	 * @return true if a user has won, false otherwise
	 */
	public boolean checkWin()
	{
		return winChecker.checkWin(gameGrid, connection);
	}

	/**
	 * Private helper method to check if a piece can be placed in the specified
	 * position
	 * 
	 * @param row the row that the piece is going to be placed into
	 * @param column the column that the piece is going to be placed into
	 * @return true if the piece can be placed, false otherwise
	 */
	private boolean canPlace(int row, int column)
	{
		boolean validArrayIndex = row >= 0 && row < gameGrid.length && column >= 0 && column < gameGrid[0].length;
		if (!validArrayIndex)
		{
			return false;
		}
		return gameGrid[row][column] == null;
	}

	/**
	 * Clears the entire game grid
	 */
	public void clear()
	{
		for (int i = 0; i < gameGrid.length; i++)
		{
			for (int j = 0; j < gameGrid[0].length; j++)
			{
				gameGrid[i][j] = null;
			}
		}
	}

	/**
	 * Sets the winning condition to the given grid reader
	 * 
	 * @param gr the grid reader that determines the new winning condition
	 */
	public void setWinMethod(GridReader gr)
	{
		winChecker = gr;
	}

	/**
	 * Draws the game grid
	 * 
	 * @param g2d
	 * @param x the side offset
	 * @param y the top offset
	 * @param circleDiameter the diameter of the circles
	 */
	public void draw(Graphics2D g2d, int x, int y, int circleDiameter)
	{
		int gridRows = gameGrid.length;
		int gridCols = gameGrid[0].length;
		int circlePadding = 5;
		int initial_x = x;

		for (int i = 0; i < gridRows; i++)
		{
			for (int g = 0; g < gridCols; g++)
			{
				if (gameGrid[i][g] == null) // position is open for play
				{
					g2d.setColor(Color.CYAN);
					g2d.fillOval(x, y, circleDiameter, circleDiameter);
				}
				else if (gameGrid[i][g] == Player.YELLOW) // player one has played this position
				{ 
					g2d.setColor(Color.YELLOW);
					g2d.fillOval(x, y, circleDiameter, circleDiameter);
				}
				else// player Two has played this position
				{ 
					g2d.setColor(Color.RED);
					g2d.fillOval(x, y, circleDiameter, circleDiameter);
				}
				x = x + circleDiameter + circlePadding;
			}
			x = initial_x;
			y = y + circleDiameter + circlePadding;
		}
	}

	/**
	 * Gets the current required number of connections to win
	 * @return the number of connections
	 */
	public int getConnection()
	{
		return connection;
	}

	/**
	 * Gets the number of rows in the game grid
	 * @return the number of rows
	 */
	public int getRows()
	{
		return gameGrid.length;
	}

	/**
	 * Gets the number of columns in the game grid
	 * @return the number of columns
	 */
	public int getColumns()
	{
		return gameGrid[0].length;
	}
}
