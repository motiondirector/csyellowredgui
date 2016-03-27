import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
public class ConnectFourGUI {
	
    private JFrame frame;
    private JButton topButton;
    private static Player currentPlayer;
    private JPanel uiPanel;
    private static GameManager c4;
    private JFrame settingsWindow;
    
    private static final int GRID_MIN = 2;
    private static final int GRID_MAX = 20;
    private static final int CONNECTIONS_MIN = 2;
    private static final int CONNECTIONS_MAX = 20;

    public ConnectFourGUI() {
        initComponents();
    }
    
    public static void main(String[] args) {
	
	if (args.length == 0)
	{
	    //Default
	    c4 = new GameManager(6, 7, 4);   
	}
	else if (args.length != 2)
	{
	    System.err.println("Usage: <gridsize> <number of required connections>");
	    System.exit(1);
	}
	else
	{
	    try
	    {
		    int gridSize = Integer.parseInt(args[0]);
		    int numReqConnect = Integer.parseInt(args[1]);
		    if (gridSize > GRID_MAX || gridSize < GRID_MIN)
		    {
			System.err.println("Grid size must be between " + GRID_MIN + " and " + GRID_MAX);
			System.exit(1);
		    }
		    else if (numReqConnect < CONNECTIONS_MIN || numReqConnect > CONNECTIONS_MAX || numReqConnect > gridSize)
		    {
			System.err.println("Number of required connections must be between " + CONNECTIONS_MIN + " and " + CONNECTIONS_MAX);
			System.err.println("Number of required connections must be less than or equal to the grid size");
			System.exit(1);
		    }
		    c4 = new GameManager(gridSize, gridSize, numReqConnect);
	    }
	    catch (NumberFormatException e)
	    {
		System.err.println("Please enter only integers");
		System.exit(1);
	    }
	}
    	currentPlayer = Player.YELLOW;
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ConnectFourGUI();
            }
        });
    }
    

    private void turn(int theX) {
        
        int thisX = theX;
        int gridOffset = c4.getSideOffset(uiPanel.getWidth(), uiPanel.getHeight());
        int gridWidth = uiPanel.getWidth() - (gridOffset * 2);    
        double colW = gridWidth / c4.getNumOfColumns();
        int clickedCol = 0;
        double colsSum = colW;
       
        
       //To see what column was clicked regardless of number of columns. 
        while (thisX > colsSum + gridOffset) {
            colsSum = colsSum + colW;
            clickedCol++;
            if ( clickedCol > c4.getNumOfColumns() - 1) { //Prevents Out of Bounds
                clickedCol = c4.getNumOfColumns() - 1;
            }
        }
        
        if (c4.insert(currentPlayer, clickedCol)) {
            
            uiPanel.repaint();
            if (c4.hasWin()) {
            	JOptionPane.showMessageDialog(frame, currentPlayer + " player wins", "Game finished", JOptionPane.PLAIN_MESSAGE);
            	c4.setRunning(false);
            } else {
        	switch (currentPlayer) {
                case YELLOW:
            	currentPlayer = Player.RED;
            	break;
                case RED:
                currentPlayer = Player.YELLOW;
                break;
                }
            }
        }
        
    }
    
    

    private void initComponents() {
    	frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        uiPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics grphcs) {
                super.paintComponent(grphcs);
                Graphics2D g2d = (Graphics2D) grphcs;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                c4.draw(g2d, this.getWidth(), this.getHeight());
            }

            @Override
            public Dimension getPreferredSize() {
            	
                return new Dimension(600, 600);
            }
        };
        
        uiPanel.setBackground(Color.BLUE);

        topButton = new JButton("");
        topButton.setBackground(Color.YELLOW);
        topButton.setOpaque(true);
        topButton.setBorderPainted(false);
        
        
        
        uiPanel.addMouseMotionListener(new MouseMotionListener() {
            public void mouseDragged(MouseEvent e) {// I guess we need this for now
                
            }
            /**
             * updates top "button" x position to match mouse movement whiting the panel
             */
            public void mouseMoved(MouseEvent e) {
                
                //
            	
            	int panelHeight = uiPanel.getHeight();
            	int panelWidth = uiPanel.getWidth();
            	int sideOffset = c4.getSideOffset(panelWidth, panelHeight);
            	int h = 20;
            	int w = c4.getCircleDiameter(panelWidth, panelHeight);
            	
                int mouseX = e.getX() - (w/2);

                if (mouseX < sideOffset ) {
                    topButton.setBounds(sideOffset, 12, w, h);
                }
                else if (mouseX > panelWidth - sideOffset - w) {
                    topButton.setBounds(panelWidth-sideOffset - w, 12, w, h);
                }
                else {
                    topButton.setBounds(mouseX, 12, w, h);
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
        	if (!c4.isRunning())
        	    return;
                turn(e.getX()); 
                
                switch (currentPlayer) {
                case YELLOW:
                    topButton.setBackground(Color.YELLOW);
                    break;
                case RED:
                    topButton.setBackground(Color.RED);
                    break;
                }
            }
        });
        uiPanel.add(topButton);
   
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("Game");
        menuBar.add(fileMenu);

        JMenuItem newGame = new JMenuItem("New Game");
        newGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
            	c4.newGame();
            	uiPanel.repaint();
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
        final JTextField reqConnections = new JTextField("Required Connections: ");
        reqConnections.setEditable(false);
        settingsWindow.add(reqConnections);
        final JTextField connectionsInput = new JTextField("" + c4.getReqCon());
        settingsWindow.add(connectionsInput);
        final JTextField numRows = new JTextField("Current # of Rows: ");
        numRows.setEditable(false);
        settingsWindow.add(numRows);
        final JTextField rowsInput = new JTextField("" + c4.getNumOfRows());
        settingsWindow.add(rowsInput);
        final JTextField numCol = new JTextField("Current # of Columns: ");
        numCol.setEditable(false);
        settingsWindow.add(numCol);
        final JTextField columnInput = new JTextField("" + c4.getNumOfColumns());
        settingsWindow.add(columnInput);
       
        
        JButton saveBtn = new JButton("Save");
        saveBtn.addActionListener(new ActionListener () { 
            @Override 
            public void actionPerformed(ActionEvent event) {
        	try {
            	int newRows = Integer.parseInt(rowsInput.getText());
            	int newColumns = Integer.parseInt(columnInput.getText());
            	int newConnections = Integer.parseInt(connectionsInput.getText()); 

        	if (newRows > GRID_MAX || newRows < GRID_MIN)
        	{
        	    JOptionPane.showMessageDialog(settingsWindow, "Rows must be between " + GRID_MIN + " and " + GRID_MAX);
        	}
        	else if (newColumns > GRID_MAX || newColumns < GRID_MIN)
        	{
        	    JOptionPane.showMessageDialog(settingsWindow, "Columns must be between " + GRID_MIN + " and " + GRID_MAX);
        	}
        	else if (newConnections > CONNECTIONS_MAX || newConnections < CONNECTIONS_MIN || newConnections > Math.min(newRows, newColumns))
        	{
        	    JOptionPane.showMessageDialog(settingsWindow, "Connections must be between " + CONNECTIONS_MIN + " and " + CONNECTIONS_MAX + "\n" + "Connections must be less than or equal to the number of rows and columns");
        	}
        	else
        	{

            	settingsWindow.setVisible(false);
            	c4 = new GameManager(newRows, newColumns, newConnections);
            	uiPanel.repaint();   
        	}
        	}
        	catch (NumberFormatException e){
        	    JOptionPane.showMessageDialog(settingsWindow, "Please enter only integers");
        	}
        	
        	}
        });
        
        //Exits the window and resets the text fields to previous values if user hits cancel
        JButton cancelBtn = new JButton("Cancel");
        cancelBtn.addActionListener(new ActionListener () { 
            @Override 
            public void actionPerformed(ActionEvent event) { 
        	settingsWindow.setVisible(false);
        	rowsInput.setText(String.valueOf(c4.getNumOfRows()));
        	columnInput.setText(String.valueOf(c4.getNumOfColumns()));
        	connectionsInput.setText(String.valueOf(c4.getReqCon()));
            }});
        
        settingsWindow.add(saveBtn);
        settingsWindow.add(cancelBtn);
        
        settingsWindow.pack();
        
        frame.setJMenuBar(menuBar);
     
        frame.add(uiPanel);
        frame.pack();
        frame.setVisible(true);

    }
}
