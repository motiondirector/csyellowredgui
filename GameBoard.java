import java.awt.Color;
import java.awt.Graphics2D;

public class GameBoard {
	
	private int[][] gameGrid;
	private int connection;
	private GridReader winChecker;
	
	public GameBoard(int rows, int columns, int reqcon){
		gameGrid = new int[rows][columns];
		connection = reqcon;
		winChecker = new LinearGridReader();
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
		
		return winChecker.checkWin(gameGrid, connection);
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
		}
	}
	
	public void setWinMethod(GridReader gr)
	{
		winChecker = gr;
	}
	
	public void draw(Graphics2D g2d, int w, int h) {
        
        int gridCols = gameGrid[0].length;
        int gridRows = gameGrid.length; 
        int topOffset = 40; 
        int sideOffset = 20;
        int circlePadding = 5;
        int gridWidth = w - sideOffset * 2;
        int gridHeight = h - topOffset;
        
        if (gridWidth < gridHeight)
        {
        	gridWidth = gridHeight;
        }
        
        int circleDiameter = gridWidth / gridCols - circlePadding;    
        int cY = topOffset; 
        int cX = sideOffset;
        int[][] thisGame = gameGrid; 
        
        
        
        for (int i = 0; i < gridRows; i++ ) {
            
            for (int g = 0; g < gridCols; g++ ) {
                if (thisGame[i][g] == 0 ) { // position is open for play
                    g2d.setColor(Color.CYAN);
                    g2d.fillOval(cX, cY,circleDiameter, circleDiameter);
                }
                else if (thisGame[i][g] == 1 ) { //player one has play this position
                    g2d.setColor(Color.YELLOW);
                    g2d.fillOval(cX, cY,circleDiameter, circleDiameter);
                }
                else { //player Two has play this position
                    g2d.setColor(Color.RED);
                    g2d.fillOval(cX, cY,circleDiameter, circleDiameter);
                }
                
                cX = cX + circleDiameter + circlePadding;
            }
            cX = sideOffset;
            cY = cY + circleDiameter + circlePadding;
        }
    }
	
	public int getConnection() {
		return connection;
	}
	
	public int getRows()
	{
		return gameGrid.length;
	}
	
	public int getColumns()
	{
		return gameGrid[0].length;
	}
}
