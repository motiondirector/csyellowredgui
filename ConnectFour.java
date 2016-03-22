import javax.swing.*;

import com.sun.org.apache.regexp.internal.recompile;

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
    private JPanel uiPanel;
    private JFrame settingsWindow;
    
    //These values should be changed later. 3 and 12 were just random numbers I used for testing purposes.
    private int ROW_MIN = 3;
    private int ROW_MAX = 12;
    private int COLUMN_MIN = 3;
    private int COLUMN_MAX = 12;
    private int CONNECTIONS_MIN = 3;
    private int CONNECTIONS_MAX = 12;
    
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
        uiPanel.repaint();
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
                		// Would be cool to outline connection elements
                	    	JOptionPane.showMessageDialog(frame, plyrClr + "player wins", "Game finished", JOptionPane.PLAIN_MESSAGE);
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
                		// Would be cool to outline connection elements
            			JOptionPane.showMessageDialog(frame, plyrClr + "player wins", "Game finished", JOptionPane.PLAIN_MESSAGE);
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
        
        //check upper positive diagonals
        int lastColumn = gameGrid[0].length -1; 
		int lastRow = gameGrid.length -1;
        for (int j = 1; j <= lastColumn; j++)
		{
			double p = gameGrid[0][j];
			int count = 1; 
			for (int i = 1, k = j-1; k >= 0 && i <= lastRow; i++, k--)
			{
				if (gameGrid[i][k] == p && p != 0)
				{
					count++;
					if (count == requiredConections)
					{
            			JOptionPane.showMessageDialog(frame, plyrClr + "player wins", "Game finished", JOptionPane.PLAIN_MESSAGE);
                		win = true;
                        break;
					}
				}
				else
				{
					p = gameGrid[i][k];
					count = 1; 
				}
			}
		}
        
		//check lower positive diagonals
		for (int i = 1; i <= lastRow -1; i++)
		{
			double p = gameGrid[i][lastColumn];
			int count = 1; 
			for (int j = lastColumn - 1, k = i+1;  k <= lastRow && j >= 0; k++, j--)
			{
				if (gameGrid[k][j] == p && p != 0)
				{
					count++;
					if (count == requiredConections)
					{
						JOptionPane.showMessageDialog(frame, plyrClr + "player wins", "Game finished", JOptionPane.PLAIN_MESSAGE);
                		win = true;
                        break;
					}
				}
				else
				{
					p = gameGrid[k][j];
					count = 1; 
				}
			}
		}
        
		//check upper negative diagonals
		for (int j = lastColumn -1; j >= 0; j--)
		{
			double p = gameGrid[0][j];
			int count = 1; 
			for (int i = 1, k = j+1; k <= lastColumn && i < gameGrid.length; i++, k++)
			{
				if (gameGrid[i][k] == p && p != 0)
				{
					count++;
					if (count == requiredConections)
					{
						JOptionPane.showMessageDialog(frame, plyrClr + "player wins", "Game finished", JOptionPane.PLAIN_MESSAGE);
                		win = true;
                        break;
					}
				}
				else
				{
					p = gameGrid[i][k];
					count = 1; 
				}
			}
		}
		
		
		//check lower negative diagonals
		for (int i = 1; i <= lastRow -1; i++)
		{
			double p = gameGrid[i][0];
			int count = 1; 
			for (int j = 1, k = i+1; k <= lastRow && j <= lastColumn; j++, k++)
			{
				if (gameGrid[k][j] == p && p != 0)
				{
					count++;
					if (count == requiredConections)
					{
						JOptionPane.showMessageDialog(frame, plyrClr + "player wins", "Game finished", JOptionPane.PLAIN_MESSAGE);
                		win = true;
                        break;
					}
				}
				else
				{
					p = gameGrid[k][j];
					count = 1; 
				}
			}
		}
        return win;
        
    }

    private void initComponents() {

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        final DrawGrid dg = new DrawGrid();

        uiPanel = new JPanel() {
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
        
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("Game");
        menuBar.add(fileMenu);

        JMenuItem newGame = new JMenuItem("New Game");
        newGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
        	startGame();
            }
        });
        fileMenu.add(newGame);
        
        //Settings pop-up menu containing text fields, so the user can enter the new connection requirements or grid size
        JMenuItem settings = new JMenuItem("Settings");
        settings.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
        	settingsWindow.setVisible(true);
            }
        });
        fileMenu.add(settings);


        settingsWindow = new JFrame();
        settingsWindow.setLayout(new GridLayout(10, 10)); //Fixed size?
        JTextField reqConnections = new JTextField("Required Connections: ");
        reqConnections.setEditable(false);
        settingsWindow.add(reqConnections);
        JTextField connectionsInput = new JTextField("" + requiredConections);
        settingsWindow.add(connectionsInput);
        JTextField numRows = new JTextField("Current # of Rows: ");
        numRows.setEditable(false);
        settingsWindow.add(numRows);
        JTextField rowsInput = new JTextField("" + gameRows);
        settingsWindow.add(rowsInput);
        JTextField numCol = new JTextField("Current # of Columns: ");
        numCol.setEditable(false);
        settingsWindow.add(numCol);
        JTextField columnInput = new JTextField("" + gameColumns);
        settingsWindow.add(columnInput);
        
        JButton saveBtn = new JButton("Save");
        saveBtn.addActionListener(new ActionListener () { 
            @Override 
            public void actionPerformed(ActionEvent event) {
        	try {
            	int newRows = Integer.parseInt(rowsInput.getText());
            	int newColumns = Integer.parseInt(columnInput.getText());
            	int newConnections = Integer.parseInt(connectionsInput.getText()); 

            	//TODO: Need to include code to validate that numConnections is not bigger than the number of rows or columns
            	
        	if (newRows > ROW_MAX || newRows < ROW_MIN)
        	{
        	    JOptionPane.showMessageDialog(settingsWindow, "Rows must be between " + ROW_MIN + " and " + ROW_MAX);
        	}
        	else if (newColumns > COLUMN_MAX || newColumns < COLUMN_MIN)
        	{
        	    JOptionPane.showMessageDialog(settingsWindow, "Columns must be between " + COLUMN_MIN + " and " + COLUMN_MAX);
        	}
        	else if (newConnections > CONNECTIONS_MAX || newConnections < CONNECTIONS_MIN)
        	{
        	    JOptionPane.showMessageDialog(settingsWindow, "Connections must be between " + CONNECTIONS_MIN + " and " + CONNECTIONS_MAX);
        	}
        	else
        	{

            	settingsWindow.setVisible(false);
            	gameRows = newRows;
            	gameColumns = newColumns;
            	requiredConections = newConnections;
            	
            	gameGrid = new double[gameRows][gameColumns];
            	uiPanel.repaint();   
        	}
        	}
        	catch (NumberFormatException e){
        	    JOptionPane.showMessageDialog(settingsWindow, "Please enter only integers");
        	}
        	
        	}
        });
        
        //Exits the window and resets the text fields to previous values if user hits cancel
        //TODO: If user exits the window by hitting "X" or pressing alt+f4(Windows) or command-w(Mac), then the text fields do not get reset. Need to fix this somehow.
        JButton cancelBtn = new JButton("Cancel");
        cancelBtn.addActionListener(new ActionListener () { 
            @Override 
            public void actionPerformed(ActionEvent event) { 
        	settingsWindow.setVisible(false);
        	rowsInput.setText(String.valueOf(gameRows));
        	columnInput.setText(String.valueOf(gameColumns));
        	connectionsInput.setText(String.valueOf(requiredConections));
            }});
        
        settingsWindow.add(saveBtn);
        settingsWindow.add(cancelBtn);
        
        settingsWindow.pack();
        
        frame.setJMenuBar(menuBar);
        frame.add(uiPanel);
        frame.pack();
        frame.setVisible(true);
    }
    
    public void startGame() {
    	win = false;
    	
    	for (int row = 0; row < gameRows; row++)
    	{
    	    for (int col = 0; col < gameColumns; col++)
    	    {
    		gameGrid[row][col] = 0.0;
    	    }
    	}
    	uiPanel.repaint();
    }
}
