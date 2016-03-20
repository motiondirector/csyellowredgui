import javax.swing.*;

import java.awt.*;
import java.awt.event.*;

public class ConnectFour {
    
    int gameColumns = 7;
    int gameRows = 6;
    int requiredConections = 4;
    double[][] gameGrid = new double[gameRows][gameColumns]; 
    int currentPlayer = 1;
  
    JFrame frame = new JFrame();
    private JButton topButton;
    private boolean win;
    
    /**
     * Constructor.
     * 
     * @param number of Columns 
     * @param number of Rows
     * @param number of Required Connections
     */
    public ConnectFour(int newCols, int newRows, int newRequiredConections ) {
        requiredConections = newRequiredConections;
        gameColumns = newCols;
        gameRows = newRows;
        gameGrid = new double[gameColumns][gameRows];
        win = false;
    }


    public ConnectFour() {
        initComponents();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ConnectFour();
            }
        });
    }
    

    public void redraw(int theX) {
        if (win)
        	return;
        
        int thisX = theX;
        int rows = gameGrid.length;
        int gridWidth = 560;    //dimention fixed for now
        double colW = gridWidth / gameColumns;
        int clickedCol = 0;
        int p = 0;
        int gridOffset = 20;
        
        double colsSum = colW;
        
        if (currentPlayer == 1){
            p = 1;
        }
        else if (currentPlayer == 2) {
            p = 2;
        }
        
       //To see what column was clicked regardless of number of columns. 
        while (thisX > colsSum + gridOffset) {
            colsSum = colsSum + colW;
            clickedCol++;
            if ( clickedCol > gameColumns - 1) { //Prevents Out of Bounds
                clickedCol = gameColumns - 1;
            }
        }
        
        //Checks for empty spaces (value = 0) starting from bottom of grid column
        for (int i = rows - 1; i >= 0; i--) {
            if (gameGrid[i][clickedCol] == 0) { //slot is empty
                
                gameGrid[i][clickedCol] = p; //assign value 1 or 2 depending on who played
                
                //done redrawing so toggle player
                if (currentPlayer == 1){
                    currentPlayer = 2;
                }
                else if (currentPlayer == 2) {
                    currentPlayer = 1;
                }
                break;
            }
        }
        initComponents();
        win = hasWin();
    }
    
    private boolean hasWin() { // This is incomplete
        
        int rq = requiredConections;
        String plyrClr = null;
        if ( currentPlayer == 1) {
            plyrClr = "Red ";
        }
        else {
            plyrClr = "Yellow ";
        }
        
        //Check horizontal connections (Not working well. Got to make scalable to allow more or less connections)
        for(int i = 0; i < gameGrid.length; i++) {
        	double piece = gameGrid[i][0]; // looks at the first piece of every row
        	int count = 1; 
            for(int j = 1; j < gameGrid[0].length; j++) {
            	double current = gameGrid[i][j];
                if (current != 0 && current == piece) {
                	count++;
                	if (count == requiredConections)
                	{
                		// Will need to output winner on GUI. Would be cool to outline connection elements
                		System.out.println(plyrClr + "player wins");
                		win = true;
                        break;
                	} 
                }
                else {
                	count = 1;
                	piece = gameGrid[i][j];
                }     
            }
            if (win) //Breaks out of outer loop
            	break;
        }
        
        //Check vertical connections
        for(int col = 0; col < gameColumns; col++) {
        	//Scan every column looking for required connections, starting from the bottom-most row (gameRows - 1)
        	double piece = gameGrid[gameRows - 1][col];
        	int count = 1; 
            for(int row = gameRows - 2; row >= 0; row--) {
            	double current = gameGrid[row][col];
                if (current != 0 && current == piece) {
                	count++;
                	if (count == requiredConections)
                	{
                		// Will need to output winner on GUI. Would be cool to outline connection elements
                		System.out.println(plyrClr + "player wins");
                		win = true;
                        break;
                	} 
                }
                else {
                	count = 1;
                	piece = gameGrid[row][col];
                }     
            }
            if (win) //Break out of outer for loop
            	break;
        }
        //TODO: Code to check diagonal connections pending
        return win;
        
    }

    private void initComponents() {

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      
        final DrawGrid dg = new DrawGrid();

        JPanel uiPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics grphcs) {
                super.paintComponent(grphcs);
                Graphics2D g2d = (Graphics2D) grphcs;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                dg.draw(g2d, getWidth(), getHeight(), gameGrid);
            }

            @Override
            public Dimension getPreferredSize() {
                return new Dimension(600, 600);
            }
        };

        uiPanel.setLayout(null);
        uiPanel.setBackground(Color.BLUE);

        topButton = new JButton("");
        topButton.setBackground(Color.YELLOW);
        topButton.setOpaque(true);
        topButton.setBorderPainted(false);

        uiPanel.add(topButton);
        
        uiPanel.addMouseMotionListener(new MouseMotionListener() {
            public void mouseDragged(MouseEvent e) {// I guess we need this for now
                
            }
            /**
             * updates top "button" x position to match mouse movement whiting the panel
             */
            public void mouseMoved(MouseEvent e) {
                
                //This needs some love to work on window resize. Ok for now...
                
                int mouseX = e.getX() - 30;

                if (mouseX < 30 ) {
                    topButton.setBounds(30, 12, 60, 20);
                }
                else if (mouseX > 510) {
                    topButton.setBounds(510, 12, 60, 20);
                }
                else {
                    topButton.setBounds(mouseX, 12, 60, 20);
                }  
            }        
        });
        
        /**
         * toggles top "button" color and 
         * calls redraw method with click x position
         */
        uiPanel.addMouseListener(new MouseAdapter() {
            // provides empty implementation of all
            // MouseListener`s methods, allowing us to
            // override only needed ones
            @Override //Now just overriding single method
            public void mousePressed(MouseEvent e) {

                redraw(e.getX()); 
                
                if (currentPlayer == 1){
                    topButton.setBackground(Color.YELLOW); 
                }
                else if (currentPlayer == 2) {
                    topButton.setBackground(Color.RED); 
                }
            }
        });

        frame.add(uiPanel);
        frame.pack();
        frame.setVisible(true);

    }
}
