
public class LinearGridReader implements GridReader {
	
	public boolean checkWin(int[][] gameGrid, int numOfPieces)
	{
		
		if (checkHorizontal(gameGrid,numOfPieces)) {return true;}
		else if (checkVertical(gameGrid,numOfPieces)) {return true;}
		else if (checkUpperPositiveDiaganols(gameGrid,numOfPieces)) {return true;}
		else if (checkLowerPositiveDiaganols(gameGrid,numOfPieces)) {return true;}
		else if (checkUpperNegativeDiaganols(gameGrid,numOfPieces)) {return true;}
		else if (checkLowerNegativeDiaganols(gameGrid,numOfPieces)) {return true;}
		return false;
	}
	
	private boolean checkHorizontal(int[][] gameGrid, int numOfPieces)
	{
		for (int i = gameGrid.length -1 ; i >=0; i--)
		{
			int p = gameGrid[i][0];
			int count = 1; 
			for (int j = 1; j < gameGrid[0].length; j++)
			{
				if (p == gameGrid[i][j] && p != 0)
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
	
	private boolean checkVertical(int[][] gameGrid, int numOfPieces)
	{
		for (int j = 0 ; j < gameGrid[0].length; j++)
		{
			int p = gameGrid[gameGrid.length-1][j];
			int count = 1;
			for (int i = gameGrid.length -2; i >= 0; i--)
			{
				if (p == gameGrid[i][j] && p != 0)
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
	
	private boolean checkUpperPositiveDiaganols(int[][] gameGrid, int numOfPieces)
	{
		for (int j = 1; j < gameGrid[0].length; j++)
		{
			int p = gameGrid[0][j];
			int count = 1; 
			for (int i = 1, k = j-1; k >= 0 && i < gameGrid.length; i++, k--)
			{
				if (gameGrid[i][k] == p && p != 0)
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
	
	private boolean checkLowerPositiveDiaganols(int[][] gameGrid, int numOfPieces)
	{
		int lastColumn = gameGrid[0].length -1; 
		int lastRow = gameGrid.length -1;
		
		for (int i = 1; i <= lastRow -1; i++)
		{
			int p = gameGrid[i][lastColumn];
			int count = 1; 
			for (int j = lastColumn - 1, k = i+1;  k <= lastRow && j >= 0; k++, j--)
			{
				if (gameGrid[k][j] == p && p != 0)
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
	
	private boolean checkUpperNegativeDiaganols(int[][] gameGrid, int numOfPieces)
	{
		int lastColumn = gameGrid[0].length -1; 
		
		for (int j = lastColumn -1; j >= 0; j--)
		{
			int p = gameGrid[0][j];
			int count = 1; 
			for (int i = 1, k = j+1; k <= lastColumn && i < gameGrid.length; i++, k++)
			{
				if (gameGrid[i][k] == p && p != 0)
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
	private boolean checkLowerNegativeDiaganols(int[][] gameGrid, int numOfPieces)
	{
		int lastColumn = gameGrid[0].length -1; 
		int lastRow = gameGrid.length -1;
		
		for (int i = 1; i <= lastRow -1; i++)
		{
			int p = gameGrid[i][0];
			int count = 1; 
			for (int j = 1, k = i+1; k <= lastRow && j <= lastColumn; j++, k++)
			{
				if (gameGrid[k][j] == p && p != 0)
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
