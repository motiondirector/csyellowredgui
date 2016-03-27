/**
 * Interface that reads the grid, checking for a winner based on the specified winning condition
 */
public interface GridReader {
	
    	/**
    	 * Checks the given game board for a winning condition
    	 * @param gameBoard the given game board
    	 * @param numOfPieces the number of pieces needed to fulfill the winning condition
    	 * @return true if the game is won, false otherwise
    	 */
	boolean checkWin(Player[][] gameBoard, int numOfPieces);

}
