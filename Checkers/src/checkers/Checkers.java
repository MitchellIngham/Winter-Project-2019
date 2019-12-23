package checkers;
import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Point;

/**
 *
 * @author Mitchell Ingham
 * 
 * This is a program that lets you play against a checkers AI, made by me
 * 
 */
public class Checkers {

    public static void main(String[] args) {
        
        int[][] data = initdata();  //initializes all data into a two dimensional integer array
        
        JButton[][] buttons = initgui(data);  //initializes the gui
                
        printdata(data);
        
    }
    
    
    
    //updates the button icons
    public static void updategame(int[][] data, JButton[][] buttons) {
        
        //first get all the icons
        Icon white = new ImageIcon("Images/White.png");
        Icon black = new ImageIcon("Images/Black.png");
        Icon green = new ImageIcon("Images/Green.png");
        Icon whitepiece = new ImageIcon("Images/Whitepiece.png");
        Icon blackpiece = new ImageIcon("Images/Blackpiece.png");
        Icon whiteking = new ImageIcon("Images/Whiteking.png");
        Icon blackking = new ImageIcon("Images/Blackking.png");
        
        //next loop through all the buttons
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                
                //next compare the button location with the database and change the icon accordingly
                if (data[x][y] == 0) {
                    buttons[x][y].setIcon(white);
                }
                else if (data[x][y] == 1) {
                    buttons[x][y].setIcon(black);
                }
                else if (data[x][y] == 2) {
                    buttons[x][y].setIcon(whitepiece);
                }
                else if (data[x][y] == 3) {
                    buttons[x][y].setIcon(blackpiece);
                }
                else if (data[x][y] == 4) {
                    buttons[x][y].setIcon(whiteking);
                }
                else if (data[x][y] == 5) {
                    buttons[x][y].setIcon(blackking);
                }
                else {
                    buttons[x][y].setIcon(green);
                }
                
            }
        }

    }
    
    
    
    //initializes the gui for the game
    public static JButton[][] initgui(int[][] data) {
        
        //this makes an anonymous action event for whenever a JButton is pressed, so that code runs when you press a button
        ActionListener listener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() instanceof JButton) {
                    Point p = ((JButton) e.getSource()).getLocation();  //get the button location
                    
                    //convert into array location so that we can compare with the data array
                    int x = p.x / 64;
                    int y = p.y / 64;
                    
                    System.out.println("(" + x + ", " + y + ")");  //print for now
                }
            }
        };
        
        
        
        //first make the frame to put the buttons on
        JFrame frame = new JFrame("Checkers");
        JPanel panel = new JPanel();
        panel.setLayout(null);
        
        //then change the properties of the frame so that it is visible and correctly sized
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //then make the array of buttons
        JButton[][] buttons = new JButton[8][8];

        //loop through every button
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                
                buttons[y][x] = new JButton();  //create the button

                panel.add(buttons[y][x]);  //add the button
                
                buttons[y][x].setBounds(y * 64, x * 64, 64, 64);  //set the location and size
                
                buttons[y][x].addActionListener(listener);  //adds the listener at the start of the function so that buttons do stuff
            }
        }
       
        //add the panel and resize the frame
        frame.add(panel);
        frame.setSize(525, 548);
        frame.setLocationRelativeTo(null);
        
        //update the game before finished
        updategame(data, buttons);
        
        return buttons;
    }



    //initializes all data for runtime
    public static int[][] initdata() {
        
        /* Database data key (all pieces are on black squares
        * 0 = white square
        * 1 = black square
        * 2 = white piece
        * 3 = black piece
        * 4 = white king
        * 5 = black king
        * 6 = green square
        * 4-6 won't be used for a while
        */
        
        int[][] data = new int[8][8];  //makes an empty array
        
        for (int x = 0; x < 8; x++){ //loops through every row   
            for (int y = 0; y < 8; y++){  //loops through every column
                
                if ((x + y) % 2 == 0) {  //This finds all the white squares of a checkerboard, which is when the x + y coordinates are even.
                    
                    data[x][y] = 0;  //set to white square
                    
                }
                else if (y <= 2) {  //This finds all the starting white pieces, which fill in all black squares in the top 3 rows
                    
                    data[x][y] = 2;
                    
                }
                else if (y <= 4) {  //This finds all the empty black squares in the middle, which cover two rows
                    
                    data[x][y] = 1;
                    
                }
                else {  //At this point all the rest of the squares are black pieces
                    
                    data[x][y] = 3;
                    
                }
            }   
        }
        
        return data;
    }
    
    
    
    //prints the database to the console in a readable way
    public static void printdata(int[][] data) {
        
        for (int y = 0; y < 8; y++){
            System.out.print(data[0][y]);
            System.out.print(data[1][y]);
            System.out.print(data[2][y]);
            System.out.print(data[3][y]);
            System.out.print(data[4][y]);
            System.out.print(data[5][y]);
            System.out.print(data[6][y]);
            System.out.println(data[7][y]);
        }
        
    }
}
