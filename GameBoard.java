import java.awt.Color;
import java.awt.Graphics2D;

public class GameBoard {
	
	private Player[][] gameGrid;
	private int connection;
	private GridReader winChecker;
	
	public GameBoard(int rows, int columns, int reqcon){
		gameGrid = new Player[rows][columns];
		connection = reqcon;
		winChecker = new LinearGridReader();
	}
	
	public boolean insertPiece(Player player, int column)
	{
		for (int i = gameGrid.length -1; i >= 0; i--)
		{
			if (canPlace(i, column))
			{
				gameGrid [i][column] = player;
				return true;
			}
		}
		return false;
	}
	
	public boolean checkWin()
	{
		return winChecker.checkWin(gameGrid, connection);
	}
	
	
	private boolean canPlace(int i, int j)
	{
		boolean validArrayIndex = i >= 0 && i < gameGrid.length && j>=0 && j < gameGrid[0].length;
		if (!validArrayIndex) {return false;}
		else {return gameGrid[i][j] == null;}
	}
	
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
	
	public void setWinMethod(GridReader gr)
	{
		winChecker = gr;
	}
	
	public void draw(Graphics2D g2d, int x, int y, int circleDiameter) {
        
		int gridRows = gameGrid.length;
        int gridCols = gameGrid[0].length;
        int circlePadding = 5;
        int initial_x = x; 
        
        
        
        for (int i = 0; i < gridRows; i++ ) {
            
            for (int g = 0; g < gridCols; g++ ) {
                if (gameGrid[i][g] == null ) { // position is open for play
                    g2d.setColor(Color.CYAN);
                    g2d.fillOval(x, y,circleDiameter, circleDiameter);
                }
                else if (gameGrid[i][g] == Player.YELLOW ) { //player one has play this position
                    g2d.setColor(Color.YELLOW);
                    g2d.fillOval(x, y,circleDiameter, circleDiameter);
                }
                else { //player Two has play this position
                    g2d.setColor(Color.RED);
                    g2d.fillOval(x, y,circleDiameter, circleDiameter);
                }
                
                x = x + circleDiameter + circlePadding;
            }
            x = initial_x;
            y = y + circleDiameter + circlePadding;
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
