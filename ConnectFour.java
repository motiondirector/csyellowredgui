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
        
        int thisX = theX;
        int rows = gameGrid.length;
        double colW = 560 / gameColumns;
        int clickedCol = 0;
        int p = 0;
        int gridOffset = 20;
        
        if (currentPlayer == 1){
            p = 1;
        }
        else if (currentPlayer == 2) {
            p = 2;
        }
        
        //To see what column was clicked and we need to update on the grid
        if (thisX < colW + gridOffset) {
            clickedCol = 0;
        }
        else if (thisX > colW + gridOffset && thisX < colW * 2 + gridOffset ) {
            clickedCol = 1;
        }
        else if (thisX > colW * 2 + gridOffset && thisX < colW * 3 + gridOffset ) {
            clickedCol = 2;
        }
        else if (thisX > colW * 3 + gridOffset && thisX < colW * 4 + gridOffset ) {
            clickedCol = 3;
        }
        else if (thisX > colW * 4 + gridOffset && thisX < colW * 5 + gridOffset ) {
            clickedCol = 4;
        }
        else if (thisX > colW * 5 + gridOffset && thisX < colW * 6 + gridOffset ) {
            clickedCol = 5;
        }
        else if (thisX > colW * 6 + gridOffset) {
            clickedCol = 6;
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
        hasWin();
    }
    
    private void hasWin() { // This is incomplete
        
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
            for(int j = 0; j < gameGrid[0].length - rq; j++) {
                if (gameGrid[i][j] != 0 
                    && gameGrid[i][j] == gameGrid[i][j + 1] 
                    && gameGrid[i][j] == gameGrid[i][j + 2] 
                    && gameGrid[i][j] == gameGrid[i][j + 3]) {
                    
                    // Will need to output winner on GUI. Would be cool to outline connection elements
                    System.out.println(plyrClr + "player wins");
                    break;
                }
            }
        }
        //Code to check vertical and diagonal connections pending
        
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