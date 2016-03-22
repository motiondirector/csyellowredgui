
public class GameBoard {
	
	protected int[][] gameGrid;
	protected int connection;
	
	public GameBoard(int rows, int columns, int reqcon){
		gameGrid = new int[rows][columns];
		connection = reqcon;
	}
	
	public boolean insertPiece(int player, int column)
	{
		for (int i = gameGrid.length -1; i >= 0; i--)
		{
			if (canPlace(i, column))
			{
				gameGrid [i][column] = player;
				break;
			}
		}
		return checkWin();
	}
	
	private boolean checkWin()
	{
		
		if (checkHorizontal()) {return true;}
		else if (checkVertical()) {return true;}
		else if (checkUpperPositiveDiaganols()) {return true;}
		else if (checkLowerPositiveDiaganols()) {return true;}
		else if (checkUpperNegativeDiaganols()) {return true;}
		else if (checkLowerNegativeDiaganols()) {return true;}
		return false;
	}
	
	private boolean checkHorizontal()
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
					if (count == connection)
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
	
	private boolean checkVertical()
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
					if (count == connection)
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
	
	private boolean checkUpperPositiveDiaganols()
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
					if (count == connection)
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
	
	private boolean checkLowerPositiveDiaganols()
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
					if (count == connection)
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
	
	private boolean checkUpperNegativeDiaganols()
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
					if (count == connection)
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
	private boolean checkLowerNegativeDiaganols()
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
					if (count == connection)
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
	
	private boolean canPlace(int i, int j)
	{
		boolean validArrayIndex = i >= 0 && i < gameGrid.length && j>=0 && j < gameGrid[0].length;
		if (!validArrayIndex) {return false;}
		else {return gameGrid[i][j] == 0;}
	}
	
	public void clear()
	{
		for (int i = 0; i < gameGrid.length; i++)
		{
			for (int j = 0; j < gameGrid[0].length; j++)
			{	
				gameGrid[i][j] = 0; 
			}
			System.out.println();
		}
	}
	
}
