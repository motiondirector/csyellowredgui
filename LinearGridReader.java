
/**
 * Linear grid reader checks for horizontal, vertical, or diagonal winning conditions
 */
public class LinearGridReader implements GridReader {
	
    /**
     * {@inheritDoc}
     */
    @Override
	public boolean checkWin(Player[][] gameGrid, int numOfPieces)
	{
		
		if (checkHorizontal(gameGrid,numOfPieces)) {return true;}
		else if (checkVertical(gameGrid,numOfPieces)) {return true;}
		else if (checkUpperPositiveDiaganols(gameGrid,numOfPieces)) {return true;}
		else if (checkLowerPositiveDiaganols(gameGrid,numOfPieces)) {return true;}
		else if (checkUpperNegativeDiaganols(gameGrid,numOfPieces)) {return true;}
		else if (checkLowerNegativeDiaganols(gameGrid,numOfPieces)) {return true;}
		return false;
	}
	
    /**
     * Private helper method to check for a horizontal win
     * @param gameGrid the game grid to check
     * @param numOfPieces the number of pieces that need to be found in a row to win
     * @return true if winning condition was fulfilled
     */
	private boolean checkHorizontal(Player[][] gameGrid, int numOfPieces)
	{
		for (int i = gameGrid.length -1 ; i >=0; i--)
		{
			Player p = gameGrid[i][0];
			int count = 1; 
			for (int j = 1; j < gameGrid[0].length; j++)
			{
				if (p == gameGrid[i][j] && p != null)
				{
					count++;
					if (count == numOfPieces)
					{
						return true;
					}
				}
				else
				{
					p = gameGrid[i][j];
					count = 1;
				}
			}
		}
		return false;
	}
	

	    /**
	     * Private helper method to check for a vertical win
	     * @param gameGrid the game grid to check
	     * @param numOfPieces the number of pieces that need to be found in a row to win
	     * @return true if winning condition was fulfilled
	     */
	private boolean checkVertical(Player[][] gameGrid, int numOfPieces)
	{
		for (int j = 0 ; j < gameGrid[0].length; j++)
		{
		        Player p = gameGrid[gameGrid.length-1][j];
			int count = 1;
			for (int i = gameGrid.length -2; i >= 0; i--)
			{
				if (p == gameGrid[i][j] && p != null)
				{
					count++;
					if (count == numOfPieces)
					{
						return true;
					}
				}
				else
				{
					p = gameGrid[i][j];
					count = 1;
				}
			}
		}
		return false;
	}

	    /**
	     * Private helper method to check for a upper position diagonal win
	     * @param gameGrid the game grid to check
	     * @param numOfPieces the number of pieces that need to be found in a row to win
	     * @return true if winning condition was fulfilled
	     */
	private boolean checkUpperPositiveDiaganols(Player[][] gameGrid, int numOfPieces)
	{
		for (int j = 1; j < gameGrid[0].length; j++)
		{
			Player p = gameGrid[0][j];
			int count = 1; 
			for (int i = 1, k = j-1; k >= 0 && i < gameGrid.length; i++, k--)
			{
				if (gameGrid[i][k] == p && p != null)
				{
					count++;
					if (count == numOfPieces)
					{
						return true;
					}
				}
				else
				{
					p = gameGrid[i][k];
					count = 1; 
				}
			}
		}
		return false;
	}

	    /**
	     * Private helper method to check for a lower positive diagonal win
	     * @param gameGrid the game grid to check
	     * @param numOfPieces the number of pieces that need to be found in a row to win
	     * @return true if winning condition was fulfilled
	     */
	private boolean checkLowerPositiveDiaganols(Player[][] gameGrid, int numOfPieces)
	{
		int lastColumn = gameGrid[0].length -1; 
		int lastRow = gameGrid.length -1;
		
		for (int i = 1; i <= lastRow -1; i++)
		{
			Player p = gameGrid[i][lastColumn];
			int count = 1; 
			for (int j = lastColumn - 1, k = i+1;  k <= lastRow && j >= 0; k++, j--)
			{
				if (gameGrid[k][j] == p && p != null)
				{
					count++;
					if (count == numOfPieces)
					{
						return true;
					}
				}
				else
				{
					p = gameGrid[k][j];
					count = 1; 
				}
			}
		}
		return false;
	}

	    /**
	     * Private helper method to check for a upper negative diagonal win
	     * @param gameGrid the game grid to check
	     * @param numOfPieces the number of pieces that need to be found in a row to win
	     * @return true if winning condition was fulfilled
	     */
	private boolean checkUpperNegativeDiaganols(Player[][] gameGrid, int numOfPieces)
	{
		int lastColumn = gameGrid[0].length -1; 
		
		for (int j = lastColumn -1; j >= 0; j--)
		{
			Player p = gameGrid[0][j];
			int count = 1; 
			for (int i = 1, k = j+1; k <= lastColumn && i < gameGrid.length; i++, k++)
			{
				if (gameGrid[i][k] == p && p != null)
				{
					count++;
					if (count == numOfPieces)
					{
						return true;
					}
				}
				else
				{
					p = gameGrid[i][k];
					count = 1; 
				}
			}
		}
		return false;
	}

	    /**
	     * Private helper method to check for a lower negative diagonal win
	     * @param gameGrid the game grid to check
	     * @param numOfPieces the number of pieces that need to be found in a row to win
	     * @return true if winning condition was fulfilled
	     */
	private boolean checkLowerNegativeDiaganols(Player[][] gameGrid, int numOfPieces)
	{
		int lastColumn = gameGrid[0].length -1; 
		int lastRow = gameGrid.length -1;
		
		for (int i = 1; i <= lastRow -1; i++)
		{
			Player p = gameGrid[i][0];
			int count = 1; 
			for (int j = 1, k = i+1; k <= lastRow && j <= lastColumn; j++, k++)
			{
				if (gameGrid[k][j] == p && p != null)
				{
					count++;
					if (count == numOfPieces)
					{
						return true;
					}
				}
				else
				{
					p = gameGrid[k][j];
					count = 1; 
				}
			}
		}
		return false;
	}
}
