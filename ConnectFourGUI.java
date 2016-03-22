import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
public class ConnectFourGUI {
	
    JFrame frame = new JFrame();
    private JButton topButton;
    private static int currentPlayer;
    private JPanel uiPanel;
    private static GameManager c4;
    private JFrame settingsWindow;
    
    //These values should be changed later. 3 and 12 were just random numbers I used for testing purposes.
    private int ROW_MIN = 3;
    private int ROW_MAX = 12;
    private int COLUMN_MIN = 3;
    private int COLUMN_MAX = 12;
    private int CONNECTIONS_MIN = 3;
    private int CONNECTIONS_MAX = 12;

    public ConnectFourGUI() {
        initComponents();
    }

    public static void main(String[] args) {
    	c4 = new GameManager(6, 7, 4);
    	currentPlayer = 1;
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ConnectFourGUI();
            }
        });
    }
    

    public void turn(int theX) {
        
        int thisX = theX;
        int gridWidth = 560;    //dimention fixed for now
        double colW = gridWidth / c4.getNumOfColumns();
        int clickedCol = 0;
        int gridOffset = 20;
        String plyrClr;
        double colsSum = colW;
       
        
       //To see what column was clicked regardless of number of columns. 
        while (thisX > colsSum + gridOffset) {
            colsSum = colsSum + colW;
            clickedCol++;
            if ( clickedCol > c4.getNumOfColumns() - 1) { //Prevents Out of Bounds
                clickedCol = c4.getNumOfColumns() - 1;
            }
        }
        if ( currentPlayer == 1) {
            plyrClr = "yellow ";
        }
        else {
            plyrClr = "red ";
        }
        
        boolean hasWin = c4.insert(currentPlayer, clickedCol);
        
        if (currentPlayer == 1){
            currentPlayer++;
        }
        else {
        	currentPlayer--;
        }
        uiPanel.repaint();
        
        if (hasWin)
        {
        	JOptionPane.showMessageDialog(frame, plyrClr + "player wins", "Game finished", JOptionPane.PLAIN_MESSAGE);
        }
    }
    
    

    private void initComponents() {

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        final GameCreator gc = new GameCreator();
        uiPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics grphcs) {
                super.paintComponent(grphcs);
                Graphics2D g2d = (Graphics2D) grphcs;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                gc.draw(g2d, getWidth(), getHeight(), c4.getGameBoard());
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

                turn(e.getX()); 
                
                if (currentPlayer == 1){
                    topButton.setBackground(Color.YELLOW); 
                }
                else if (currentPlayer == 2) {
                    topButton.setBackground(Color.RED); 
                }
            }
        });
        
        // =======================NewCode========================================
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
        JTextField reqConnections = new JTextField("Required Connections: ");
        reqConnections.setEditable(false);
        settingsWindow.add(reqConnections);
        JTextField connectionsInput = new JTextField("" + c4.getReqCon());
        settingsWindow.add(connectionsInput);
        JTextField numRows = new JTextField("Current # of Rows: ");
        numRows.setEditable(false);
        settingsWindow.add(numRows);
        JTextField rowsInput = new JTextField("" + c4.getNumOfRows());
        settingsWindow.add(rowsInput);
        JTextField numCol = new JTextField("Current # of Columns: ");
        numCol.setEditable(false);
        settingsWindow.add(numCol);
        JTextField columnInput = new JTextField("" + c4.getNumOfColumns());
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
        //TODO: If user exits the window by hitting "X" or pressing alt+f4(Windows) or command-w(Mac), then the text fields do not get reset. Need to fix this somehow.
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