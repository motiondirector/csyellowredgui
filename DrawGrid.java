import java.awt.Color;
import java.awt.Graphics2D;

class DrawGrid {
    
    /**
     * Draws the grid circles and assigns color depending on array element value
     */
    public void draw(Graphics2D g2d, int w, int h, double[][] gameGrid) {
            
        int gridCols = gameGrid[0].length;
        int gridRows = gameGrid.length; 
        int topOffset = 40; 
        int sideOffset = 20;
        int circlePadding = 5;
        int gridWidth = w - sideOffset * 2;
        int circleDiameter = gridWidth / gridCols - circlePadding;    
        int cY = topOffset; 
        int cX = sideOffset;
        double[][] thisGame = gameGrid;   
        
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
}


