package checkers;
import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Point;
import java.util.Vector;



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
        
        makelisteners(data, buttons);  //makes an event listener for the buttons and adds them

    }
    
    
    
    //makes an action listener for every button
    public static void makelisteners(int[][] data, JButton[][] buttons) {
        
        //this makes an anonymous action event for whenever a JButton is pressed, so that code runs when you press a button
        ActionListener listener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() instanceof JButton) {
                    Point p = ((JButton) e.getSource()).getLocation();  //get the button location
                    
                    //convert into array location so that we can compare with the data array
                    int x = p.x / 64;
                    int y = p.y / 64;
                    
                    //process button press
                    buttonpress(data, buttons, x, y);
                }
            }
        };
        
        //add the listener to all the buttons
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                
                buttons[y][x].addActionListener(listener);  //adds the listener at the start of the function so that buttons do stuff
                
            }
        }
    }
    
    
    
    //returns the board to a deselected state
    public static int[][] deselectpieces(int[][] data) {
        
        //loop through all squares
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                
                //if a square is green, change it back to black
                if (data[y][x] == 6) {
                    data[y][x] = 1;
                }
                else if (data[y][x] == 7) {  //if a square is a selected black piece, change it back to a normal black piece
                    data[y][x] = 3;
                }
                else if (data[y][x] == 8) {  //same for selected black kings
                    data[y][x] = 5;
                }
                
            }
        }
        
        return data;
        
    }
    
    
    
    //returns the location of a selected piece as well as the type of piece
    public static int[] selectedpiece(int[][] data) {
        
        /*this stores the data to be returned
        * 0 - xpos
        * 1 - ypos
        * 2 - piece type (0 = normal black, 1 = black king)
        */
        int[] returndata = new int[3];
        
        //loop through all squares
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                
                //if the square is a selected piece
                if (data[y][x] == 7 || data[y][x] == 8) {
                    
                    //return coordinates
                    returndata[0] = y;
                    returndata[1] = x;
                    
                    //then return the type
                    if (data[y][x] == 7) {
                        returndata[2] = 0;
                    }
                    else {
                        returndata[2] = 1;
                    }
                    
                }
                
            }
        }
        
        return returndata;
        
    }
    
    
    
    //shows multijump moves
    public static int[][] showmultimoves(int[][] data, int x, int y, int isking) {
        
        //this recursive function finds any additional jumps on a move
        //first check top left
        if (x > 1 && y > 1) {  //check bounds
            
            if ((data[x - 1][y - 1] == 2 || data[x - 1][y - 1] == 4) && data[x - 2][y - 2] == 1) {  //if there is a piece to be taken and the destination is a black square
            
                data[x - 2][y - 2] = 6;  //make it green
                
                data = showmultimoves(data, x - 2, y - 2, isking);  //check for multimoves there too
            
            }
            
        }
        
        //check top right
        if (x < 6 && y > 1) {
        
            if ((data[x + 1][y - 1] == 2 || data[x + 1][y - 1] == 4) && data[x + 2][y - 2] == 1) {  //if there is a piece to be taken and the destination is a black square
            
                data[x + 2][y - 2] = 6;  //make it green
            
                data = showmultimoves(data, x + 2, y - 2, isking);  //check for multimoves there too
                
            }
            
        }
        
        //if king
        if (isking == 1) {
            
            //check bottom left
            if (x > 1 && y < 6) {
                
                if ((data[x - 1][y + 1] == 2 || data[x - 1][y + 1] == 4) && data[x - 2][y + 2] == 1) {  //if there is a piece to be taken and the destination is a black square
            
                    data[x - 2][y + 2] = 6;  //make it green
            
                    data = showmultimoves(data, x - 2, y + 2, isking);  //check for multimoves there too
                    
                }
                
            }
            
            //check bottom right
            if (x < 6 && y < 6) {
                
                if ((data[x + 1][y + 1] == 2 || data[x + 1][y + 1] == 4) && data[x + 2][y + 2] == 1) {  //if there is a piece to be taken and the destination is a black square
            
                    data[x + 2][y + 2] = 6;  //make it green
            
                    data = showmultimoves(data, x + 2, y + 2, isking);  //check for multimoves there too
                    
                }
                
            }
            
        }
        
        return data;
        
    }
    
    
    
    //finds legal all legal moves for black pieces
    public static int[][] showblackmoves(int[][] data) {
        
        //first find the location of the selected piece
        int[] selected = selectedpiece(data);
        
        //now that we have the information of the selected piece, we can check legal moves
        //first we'll check moves for normal pieces
        //first check upper left moves
        if (selected[0] > 0 && selected[1] > 0) {  //if not on the left edge or top edge, check upper left move
            
            if (data[selected[0] - 1][selected[1] - 1] == 1) {  //if diagonally up left one is a black square
                
                data[selected[0] - 1][selected[1] - 1] = 6;  //make it a green square
                
            }
            else if (data[selected[0] - 1][selected[1] - 1] == 2 || data[selected[0] - 1][selected[1] - 1] == 4) {  //if it is an enemy piece, check if able to take it
                
                if (selected[0] > 1 && selected[1] > 1) {  //make sure it is far enough away from the top and left edges to jump over that piece
                    
                    if (data[selected[0] - 2][selected[1] - 2] == 1) {  //if diagonally up left two is a black square
                        
                        data[selected[0] - 2][selected[1] - 2] = 6;  //make it a green square
                        
                        data = showmultimoves(data, selected[0] - 2, selected[1] - 2, selected[2]);  //check for multi moves
                        
                    }
                    
                }
                
            }
        
        }
        
        //next check upper right moves
        if (selected[0] < 7 && selected[1] > 0) {  //if not on the right edge or top edge, check upper right move
            
            if (data[selected[0] + 1][selected[1] - 1] == 1) {  //if diagonally up right one is a black square
                
                data[selected[0] + 1][selected[1] - 1] = 6;  //make it a green square
                
            }
            else if (data[selected[0] + 1][selected[1] - 1] == 2 || data[selected[0] + 1][selected[1] - 1] == 4) {  //if that square is an enemy, check if you can take it
                
                if (selected[0] < 6 && selected[1] > 1) {  //if far enough away from the top and right edges to take the piece
                    
                    if (data[selected[0] + 2][selected[1] - 2] == 1) {  //if diagonally up right two is a black square
                        
                        data[selected[0] + 2][selected[1] - 2] = 6;  //make it a green square
                        
                        data = showmultimoves(data, selected[0] + 2, selected[1] - 2, selected[2]);  //check for multi moves
                        
                    }
                    
                }
                
            }
            
        }
        
        //if a king, check lower left and right moves
        if (selected[2] == 1) {
            
            //check lower left moves
            if (selected[0] > 0 && selected[1] < 7) {  //if far enough away from the lower and left edges
                
                if (data[selected[0] - 1][selected[1] + 1] == 1) {  //if diagonally down left is a black square
                    
                    data[selected[0] - 1][selected[1] + 1] = 6;  //make it a green square
                    
                }
                else if (data[selected[0] - 1][selected[1] + 1] == 2 || data[selected[0] - 1][selected[1] + 1] == 4) {  //if it is an enemy piece, check if able to take it
                    
                    if (selected[0] > 1 && selected[1] < 6) {  //if far enough away from the lower and left edges
                        
                        if (data[selected[0] - 2][selected[1] + 2] == 1) {  //if diagonally down left two is a black square
                            
                            data[selected[0] - 2][selected[1] + 2] = 6;  //make it green
                            
                            data = showmultimoves(data, selected[0] - 2, selected[1] + 2, 1);  //check for multi moves
                            
                        }
                        
                    }
                    
                }
                
            }
            
            //check lower right moves
            if (selected[0] < 7 && selected[1] < 7) {  //if far enough away from the lower and right edges
                
                if (data[selected[0] + 1][selected[1] + 1] == 1) {  //if diagonally down right one is a black square
                    
                    data[selected[0] + 1][selected[1] + 1] = 6;  //make it green
                    
                }
                else if (data[selected[0] + 1][selected[1] + 1] == 2 || data[selected[0] + 1][selected[1] + 1] == 4) {  //if it is an enemy piece, check if able to take it
                    
                    if (selected[0] < 6 && selected[1] < 6) {  //if far enough away from the lower and right edges
                        
                        if (data[selected[0] + 2][selected[1] + 2] == 1) {  //if diagonally down right two is a black square
                            
                            data[selected[0] + 2][selected[1] + 2] = 6;  //make it green
                            
                            data = showmultimoves(data, selected[0] + 2, selected[1] + 2, 1);  //check for multi moves
                            
                        }
                        
                    }
                    
                }
                
            }
            
        }
        
        return data;
        
    }
    
    
    
    //decides what happens when a button is pressed
    public static void buttonpress(int[][] data, JButton[][] buttons, int xpos, int ypos) {
        
        //if the button pressed is a black piece or a black king
        if (data[xpos][ypos] == 3 || data[xpos][ypos] == 5) {
            
            //first deselect any piece
            data = deselectpieces(data);
            
            //change that piece to be selected
            if (data[xpos][ypos] == 3) {
                data[xpos][ypos] = 7;  //for normal piece
            }
            else {
                data[xpos][ypos] = 8;  //for king
            }
            
            //find all legal moves for this piece
            data = showblackmoves(data);
            
            //after all that, update the gui
            updategame(data, buttons);
        }
        else if (data[xpos][ypos] == 6) {  //if the button pressed is a green square
            
            //we have to move the selected piece to the green square, first we find the coordinates of the selected piece
            int[] selected = selectedpiece(data);
            
            //then we check how far away the piece is
            //we know that it is a passive move when both x's and y's subtracted are both -1 or 1
            if ((xpos - selected[0] == -1 || xpos - selected[0] == 1) && (ypos - selected[1] == -1 || ypos - selected[1] == 1)) {  //this is a passive move
                
                //we know that it is a passive move, so we change the selected piece to a black square, and the pressed button to a black piece or king
                data[selected[0]][selected[1]] = 1;
                if (selected[2] == 0) {
                    data[xpos][ypos] = 3;
                }
                else {
                    data[xpos][ypos] = 5;
                }
                
                //then return the board to normal
                data = deselectpieces(data);
                
            }  //we know that it takes one piece when both x's and y's subtracted are both -2 or 2 and the one in between(which we can find by taking the average of both x's and y's) is an enemy piece
            else if ((xpos - selected[0] == -2 || xpos - selected[0] == 2) && (ypos - selected[1] == -2 || ypos - selected[1] == 2) && (data[(xpos + selected[0]) / 2][(ypos + selected[1]) / 2] == 2 || data[(xpos + selected[0]) / 2][(ypos + selected[1]) / 2] == 4)) {
                
                //first we do the same thing as passive moves
                data[selected[0]][selected[1]] = 1;
                if (selected[2] == 0) {
                    data[xpos][ypos] = 3;
                }
                else {
                    data[xpos][ypos] = 5;
                }
                
                //then we find the piece that was jumped over by taking the average of both x's and y's, and replace it with a black square
                data[(xpos + selected[0]) / 2][(ypos + selected[1]) / 2] = 1;
                
                //then return the board to normal
                data = deselectpieces(data);
                
            }
            else {  //if not a passive or single capture move, it is a multi move
                
                //first change the pressed square to 9 so the algorithm knows it is the destination
                data[xpos][ypos] = 9;
                
                //then call the algorithm to move the piece
                data = domultimove(data, selected[0], selected[1]);
                
                //then return the board to normal
                data = deselectpieces(data);
                
            }
            
            //check if the black player has made a king by moving to the end of the board
            data = checkblackking(data);
            
            //then the white player moves
            data = dowhitemoves(data);
            
            //after all that, update the game
            updategame(data, buttons);
            
        }
        
    }
    
    
    
    //dictates where the white player will move
    public static int[][] dowhitemoves(int[][] data) {

        //first loop through the board and find the number of pieces
        int numpieces = 0;
        int[][] locations = new int[12][2];
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                
                //then if it's a white piece
                if (data[y][x] == 2 || data[y][x] == 4) {
                    
                    //count all the pieces and store their locations
                    locations[numpieces][0] = y;  //x and y are backwards in this for loop
                    locations[numpieces][1] = x;  //and I'm too lazy to change it
                    numpieces++;
                    
                }
                
            }
        }
        
        //then thing is to initialize the vector to store all the moves in
        Vector<String>[] whitemoves = new Vector[numpieces];
        
        //then loop through all the pieces
        for (int x = 0; x < numpieces; x++) {
            
            //get the moves for every piece
            whitemoves[x] = getwhitemove(data, locations[x][0], locations[x][1]);
            
        }
        
        //Then count how many moves there are
        int movenum = 0;  //in index format
        for (int x = 0; x < numpieces; x++) {  //loop through every player
            
            for (int y = 0; y < whitemoves[x].size(); y++) {  //loop through every move
                
                movenum++;  //count it
                
            }
            
        }
        
        //if there are no moves, the black player wins
        if (movenum == 0) {
            
            //display win
            JOptionPane.showMessageDialog(null, "You won! Congratulations, also the game is going to close now. ", "Congrats bro", JOptionPane.INFORMATION_MESSAGE);
            
            System.exit(0);
            
        }
        
        //Then make a vector to store ratings
        Vector<Integer>[] ratings = new Vector[numpieces];
        
        //initialize each vector
        for (int x = 0; x < numpieces; x++) {
            
            ratings[x] = new Vector();
            
        }
        
        //Then loop through every move and rate it
        for (int x = 0; x < numpieces; x++) {
            
            for (int y = 0;  y < whitemoves[x].size(); y++) {
                
                //first get the starting x and y
                int startingx = locations[x][0], startingy = locations[x][1];
                
                //then get the move
                String move = whitemoves[x].get(y);
                
                //then get the rating
                ratings[x].add(ratemove(data, startingx, startingy, move));
                
            }
        }
        
        //Then pick the move with the biggest rating
        int maxrating = -9999999, chosenpiece = 0, chosenmove = 0;
        for (int x = 0; x < numpieces; x++) {
            for (int y = 0; y < whitemoves[x].size(); y++) {
                
                //a move is better than the best found one
                if (ratings[x].get(y) > maxrating) {
                    
                    //set the chosen move to this new best move
                    chosenpiece = x;
                    chosenmove = y;
                    
                    //set the new maximum
                    maxrating = ratings[x].get(y);
                    
                }
                
            }
        }
        
        //Finally, do the move
        int startingx = locations[chosenpiece][0], startingy = locations[chosenpiece][1];
        String move = whitemoves[chosenpiece].get(chosenmove);
        
        data = dowhitemove(data, startingx, startingy, move);
        
        return data;
        
    }
    
    
    
    //does a white move
    public static int[][] dowhitemove(int[][] data, int startingx, int startingy, String move) {
        
        //if not a multimove (multimoves have more than two points because there are multiple jumps)
        if (move.length() == 2) {
            
            //get the destination
            int endingx = Character.getNumericValue(move.charAt(0)), endingy = Character.getNumericValue(move.charAt(1));
            
            //if it is a passive move
            if ((startingx - endingx == -1 || startingx - endingx == 1) && (startingy - endingy == -1 || startingy - endingy == 1)) {
                
                //do the passive move
                data[endingx][endingy] = data[startingx][startingy];  //move the piece
                data[startingx][startingy] = 1;  //set the starting square back to a black square
                
            }
            else {  //if takes one piece
                
                //do the move
                data[endingx][endingy] = data[startingx][startingy];  //move the piece
                data[startingx][startingy] = 1;  //set the starting square back to a black square
                data[(startingx + endingx) / 2][(startingy + endingy) / 2] = 1;  //set the captured piece to a black square
                
            }
            
        }
        else {  //if it is a multimove
            
            //we know it can't be a passive move to be a multimove
            //so all we need to do is follow the steps and take pieces along the way
            
            //first thing is to get the number of points
            int jumpnum = move.length() / 2;  //each jump has an x and a y, so taking the number of coordinates and dividing by two gives us the number of jumps
            
            //do the jumps one by one
            for (int x = 0; x < jumpnum; x++) {
                
                //get the destination point in readable terms
                int endingx = Character.getNumericValue(move.charAt(x * 2)), endingy = Character.getNumericValue(move.charAt((x * 2) + 1));
                
                //if on the first jump, start from startingx and startingy
                if (x == 0) {
                    
                    //do the jump
                    data[endingx][endingy] = data[startingx][startingy];  //move the piece
                    data[startingx][startingy] = 1;  //make the left square black
                    data[(startingx + endingx) / 2][(startingy + endingy) / 2] = 1;  //delete the captured piece
                    
                }
                else {  //if not on the first jump
                    
                    //then we have to get the last point jumped to (in other words, where the piece is right now)
                    int lastx = Character.getNumericValue(move.charAt((x - 1) * 2)), lasty = Character.getNumericValue(move.charAt(((x - 1) * 2) + 1));
                    
                    //do the jump
                    data[endingx][endingy] = data[lastx][lasty];  //move the piece
                    data[lastx][lasty] = 1;  //make the left square black
                    data[(lastx + endingx) / 2][(lasty + endingy) / 2] = 1;  //delete the captured piece
                    
                }
                
            }
            
        }
        
        //after the move, check if the white player made a king
        for (int x = 0; x < 8; x++) {
            
            if (data[x][7] == 2) {  //if it's a white piece
                
                data[x][7] = 4;  //it's a king now whoooo
                
            }
            
        }
        
        return data;
        
    }
    
    
    
    //gets every move for a specific white piece
    public static Vector getwhitemove(int[][] data, int xpos, int ypos) {
        
        //make a vector to store all the moves
        //each move is a string with coordinates, so a move from 1,0 to 2,1 would be 21
        Vector<String> moves = new Vector();
        int isking = 0;
        if (data[xpos][ypos] == 4) {
            isking = 1;
        }
        
        //first check lower left moves
        if (xpos > 0 && ypos < 7) {
            
            if (data[xpos - 1][ypos + 1] == 1) {  //if it's a black square
                
                moves.add((xpos - 1) + "" + (ypos + 1));  //add the move
                
            }
            else if (data[xpos - 1][ypos + 1] == 3 || data[xpos - 1][ypos + 1] == 5) {  //if it's an enemy
                
                if (xpos > 1 && ypos < 6) {  //check bounds again
                    
                    if (data[xpos - 2][ypos + 2] == 1) {  //if it's a black square
                        
                        String move = (xpos - 2) + "" + (ypos + 2);
                        
                        moves.add(move);  //add the move
                        
                        //check for any multimoves
                        moves = whitemultimoves(data, moves, xpos - 2, ypos + 2, move, 0, isking);
                        
                    }
                    
                }
                
            }
            
        }
        
        //then check lower right moves
        if (xpos < 7 && ypos < 7) {
            
            if (data[xpos + 1][ypos + 1] == 1) {  //if it's a black square
                
                moves.add((xpos + 1) + "" + (ypos + 1));  //add the move
                
            }
            else if (data[xpos + 1][ypos + 1] == 3 || data[xpos + 1][ypos + 1] == 5) {  //if it's an enemy
                
                if (xpos < 6 && ypos < 6) {  //check bounds again
                    
                    if (data[xpos + 2][ypos + 2] == 1) {  //if it's a black square
                        
                        String move = (xpos + 2) + "" + (ypos + 2) ;
                        
                        moves.add(move);  //add the move
                        
                        //check for any multimoves
                        moves = whitemultimoves(data, moves, xpos + 2, ypos + 2, move, 1, isking);
                        
                    }
                    
                }
                
            }
            
        }
        
        //then if it's a king
        if (data[xpos][ypos] == 4) {
            
            //check upper left
            if (xpos > 0 && xpos > 0) {
                
                if (data[xpos - 1][ypos - 1] == 1) {  //if it's a black square
                    
                    moves.add((xpos - 1) + "" + (ypos - 1));  //add the move
                    
                }
                else if (data[xpos - 1][ypos - 1] == 3 || data[xpos - 1][ypos - 1] == 5) {  //if it's an enemy
                
                    if (xpos > 1 && ypos > 1) {  //check bounds again
                    
                        if (data[xpos - 2][ypos - 2] == 1) {  //if it's a black square
                        
                            String move = (xpos - 2) + "" + (ypos - 2) ;
                            
                            moves.add(move);  //add the move
                            
                            //check for any multimoves
                            moves = whitemultimoves(data, moves, xpos - 2, ypos - 2, move, 2, 1);
                        
                        }
                    
                    }
                
                }
                
            }
            
            //then check upper right
            if (xpos < 7 && ypos > 0) {
                
                if (data[xpos + 1][ypos - 1] == 1) {  //if it's a black square
                    
                    moves.add((xpos + 1) + "" + (ypos - 1));  //add the move
                    
                }
                else if (data[xpos + 1][ypos - 1] == 3 || data[xpos + 1][ypos - 1] == 5) {  //if it's an enemy
                
                    if (xpos < 6 && ypos > 1) {  //check bounds again
                    
                        if (data[xpos + 2][ypos - 2] == 1) {  //if it's a black square
                        
                            String move = (xpos + 2) + "" + (ypos - 2) ;
                            
                            moves.add(move);  //add the move
                            
                            //check for any multimoves
                            moves = whitemultimoves(data, moves, xpos + 2, ypos - 2, move, 3, 1);
                        
                        }
                    
                    }
                
                }
                
            }
            
        }
        
        return moves;
        
    }
    
    
    
    //rates a white move
    public static int ratemove(int[][] data, int startingx, int startingy, String move) {
        
        //make a rating starting with the departure, as it doesn't change at all with different moves
        int rating = ratedeparture(data, startingx, startingy);
        
        //first get what kind of move it is
        if (move.length() == 2) {  //if it's not a multimove
            
            //get the ending point
            int endingx = Character.getNumericValue(move.charAt(0)), endingy = Character.getNumericValue(move.charAt(1));
            
            //first do the destination rating
            int isking = 0;
            if (data[startingx][startingy] == 4) {  //if it's a king
                isking = 1;
            }
            rating += ratedestination(data, endingx, endingy, isking);
            
            //if it's a passive move
            if ((startingx - endingx == -1 || startingx - endingx == 1) && (startingy - endingy == -1 || startingy - endingy == 1)) {
                
                //do the rating
                rating += ratepassivemove(data, startingx, startingy, endingx, endingy);
                
            }
            else {  //if it takes one piece
                
                //do the rating
                rating += ratesinglemove(data, startingx, startingy, endingx, endingy);
                
            }
            
        }
        else {  //if it's a multimove
            
            //first get the number of jumps
            int jumpnum = move.length() / 2;
            
            //then make an array of the jumps
            int[][] jumps = new int[jumpnum][2];
            
            //fill the array
            for (int x = 0; x < jumpnum; x++) {
                
                //enter the jumps into the array
                jumps[x][0] = Character.getNumericValue(move.charAt(x * 2));
                jumps[x][1] = Character.getNumericValue(move.charAt((x * 2) + 1));
                
            }
            
            //then do the destination rating
            int isking = 0;
            if (data[startingx][startingy] == 4) {
                isking = 1;
            }
            rating += ratedestination(data, jumps[jumpnum - 1][0], jumps[jumpnum - 1][1], isking);
            
            //do the rating
            rating += jumpnum + ratemultimove(data, startingx, startingy, jumps);
            
        }
        
        return rating;
        
    }
    
    
    
    //rates multi moves
    public static int ratemultimove(int[][] data, int startx, int starty, int[][] jumps) {
        
        int rating = 0;
        
        //first temporarily do the jump
        int[][] tempdata = copyarray(data);
        
        //do the first jump manually
        tempdata[jumps[0][0]][jumps[0][1]] = tempdata[startx][starty];
        tempdata[(jumps[0][0] + startx) / 2][(jumps[0][1] + starty) / 2] = 1;
        tempdata[startx][starty] = 1;
        
        //loop through each next jump
        for (int x = 1; x < jumps.length; x++) {
            
            //move the jumping piece
            tempdata[jumps[x][0]][jumps[x][1]] = tempdata[jumps[x - 1][0]][jumps[x - 1][1]];
            
            //destroy the jumped piece
            tempdata[(jumps[x][0] + jumps[x - 1][0]) / 2][(jumps[x][1] + jumps[x - 1][1]) / 2] = 1;
            
            //destroy the moved piece
            tempdata[jumps[x - 1][0]][jumps[x - 1][1]] = 1;
            
        }
        
        //then check for any black moves that can take this piece
        //we can do this by sending the move to black tradeoff, and it will tell if it is a good move
        rating += blacktradeoff(tempdata, startx, starty, jumps[jumps.length - 1][0], jumps[jumps.length - 1][1]);
        
        return rating;
        
    }
    
    
    
    //rates moves where the white player takes a single piece
    public static int ratesinglemove(int[][] data, int startx, int starty, int endx, int endy) {
        
        int rating = 10;  //start at 10 because it's already taking a black piece, which is good
        
        //first temporarily make the white move
        int[][] tempdata = copyarray(data);
        tempdata[endx][endy] = tempdata[startx][starty];
        tempdata[(startx + endx) / 2][(starty + endy) / 2] = 1;
        tempdata[startx][starty] = 1;
        
        //then check for any black moves that can take this piece
        //we can do this by sending the move to black tradeoff, and it will tell if it is a good move
        rating += blacktradeoff(tempdata, startx, starty, endx, endy);
        
        return rating;
        
    }
    
    
    
    //rates white passive moves
    public static int ratepassivemove(int[][] data, int startx, int starty, int endx, int endy) {
        
        int rating = 0;
        
        //first temporarily make the white move
        int[][] tempdata = copyarray(data);
        tempdata[endx][endy] = tempdata[startx][starty];
        tempdata[startx][starty] = 1;
        
        //then check for any black moves that can take this piece
        //we can do this by sending the move to black tradeoff, and it will tell if it is a good move
        rating += blacktradeoff(tempdata, startx, starty, endx, endy);
        
        return rating;
        
    }
    
    
    
    //copies the data array
    public static int[][] copyarray(int[][] data) {
        
        int[][] newdata = new int[8][8];
        
        for (int x = 0; x < 8; x++) {
            
            newdata[x] = data[x].clone();
            
        }
        
        return newdata;
        
    }
    
    
    
    //rates the advantage of leaving a square, if any
    public static int ratedeparture(int[][] data, int xpos, int ypos) {
        
        //make a rating
        int rating = 0;
        
        //first half, if moving lets black take a piece
        //check bottom left
        if (xpos > 1 && ypos < 6) {  //if there's an enemy that can jump to this point
            if ((data[xpos - 2][ypos + 2] == 3 || data[xpos - 2][ypos + 2] == 5) && (data[xpos - 1][ypos + 1] == 2 || data[xpos - 1][ypos + 1] == 4)) {
                
                //that's bad
                rating = -10;
                
            }
        }
        
        //then check bottom right
        if (xpos < 6 && ypos < 6) {  //if there's an enemy that can jump to this point
            if ((data[xpos + 2][ypos + 2] == 3 || data[xpos + 2][ypos + 2] == 5) && (data[xpos + 1][ypos + 1] == 2 || data[xpos + 1][ypos + 1] == 4)) {
                
                //that's bad
                rating = -10;
                
            }
        }
        
        //then check top left
        if (xpos > 1 && ypos > 1) {  //if there's an enemy king that can jump to this point
            if (data[xpos - 2][ypos - 2] == 5 && (data[xpos - 1][ypos - 1] == 2 || data[xpos - 1][ypos - 1] == 4)) {
                
                //that's bad
                rating = -10;
                
            }
        }
        
        //then check top right
        if (xpos < 6 && ypos > 1) {  //if there's an enemy king that can jump to this point
            if (data[xpos + 2][ypos - 2] == 5 && (data[xpos + 1][ypos - 1] == 2 || data[xpos + 1][ypos - 1] == 4)) {
                
                //that's bad
                rating = -10;
                
            }
        }
        
        //second half, if moving saves the piece
        //if not in a corner
        if ((xpos > 0 && ypos < 7) && (xpos < 7 && ypos > 0)) {
            
            //make a temporary rating that stores the best rating when it comes to the secondhalf
            int secondrating = 0;
            
            //check bottom left
            if ((data[xpos - 1][ypos + 1] == 3 || data[xpos - 1][ypos + 1] == 5) && data[xpos + 1][ypos - 1] == 1) {
                
                //temporarily do the black move
                int[][] tempdata = copyarray(data);
                tempdata[xpos + 1][ypos - 1] = tempdata[xpos - 1][ypos + 1];
                tempdata[xpos][ypos] = 1;
                tempdata[xpos - 1][ypos + 1] = 1;
                
                //get the rating
                int temprating = 10;  //starts at 10 because black can take the piece
                //minus because if the tradeoff is good enough, it might be better to not move
                //so 10 for moving, minus how much the tradeoff is worth
                temprating -= whitetradeoff(tempdata, xpos - 1, ypos + 1, xpos + 1, ypos - 1);
                
                if (temprating > secondrating) {  //if it's better set it to the better one
                    secondrating = temprating;
                }
                
            }
            
            //check bottom right
            if ((data[xpos + 1][ypos + 1] == 3 || data[xpos + 1][ypos + 1] == 5) && data[xpos - 1][ypos - 1] == 1) {
                
                //temporarily do the black move
                int[][] tempdata = copyarray(data);
                tempdata[xpos - 1][ypos - 1] = tempdata[xpos + 1][ypos + 1];
                tempdata[xpos][ypos] = 1;
                tempdata[xpos + 1][ypos + 1] = 1;
                
                //get the rating
                int temprating = 10;  //starts at 10 because black can take the piece
                temprating -= whitetradeoff(tempdata, xpos + 1, ypos + 1, xpos - 1, ypos - 1);
                
                if (temprating > secondrating) {  //if it's better set it to the better one
                    secondrating = temprating;
                }
                
            }
            
            //check top left
            if (data[xpos - 1][ypos - 1] == 5 && data[xpos + 1][ypos + 1] == 1) {
                
                //temporarily do the black move
                int[][] tempdata = copyarray(data);
                tempdata[xpos + 1][ypos + 1] = tempdata[xpos - 1][ypos - 1];
                tempdata[xpos][ypos] = 1;
                tempdata[xpos - 1][ypos - 1] = 1;
                
                //get the rating
                int temprating = 10;  //starts at 10 because black can take the piece
                temprating -= whitetradeoff(tempdata, xpos - 1, ypos - 1, xpos + 1, ypos + 1);
                
                if (temprating > secondrating) {  //if it's better set it to the better one
                    secondrating = temprating;
                }
                
            }
            
            //check top right
            if (data[xpos + 1][ypos - 1] == 5 && data[xpos - 1][ypos + 1] == 1) {
                
                //temporarily do the black move
                int[][] tempdata = copyarray(data);
                tempdata[xpos - 1][ypos + 1] = tempdata[xpos + 1][ypos - 1];
                tempdata[xpos][ypos] = 1;
                tempdata[xpos + 1][ypos - 1] = 1;
                
                //get the rating
                int temprating = 10;  //starts at 10 because black can take the piece
                temprating -= whitetradeoff(tempdata, xpos + 1, ypos - 1, xpos - 1, ypos + 1);
                
                if (temprating > secondrating) {  //if it's better set it to the better one
                    secondrating = temprating;
                }
                
            }
            
            //then add the temporary rating to the total
            rating += secondrating;
            
        }
        
        return rating;
        
    }
    
    
    
    /* half of the tradeoff algorithm
    ** It is given the coordinates of a black move
    ** It checks if a white piece can take a black piece as a result of this move
    ** If there are no moves, it returns 0
    ** If there are moves, it picks the best one and returns it
    ** for every move, if it is possible, it checks the other half of the algorithm,
    ** which is the same but for black instead of white
    ** It also won't check for more than two jump moves, which might change later
    */
    public static int whitetradeoff(int[][] data, int startx, int starty, int endx, int endy) {
        
        int rating = 0;
        
        //first check the departed square
        //check top left
        if (startx > 1 && starty > 1) {
            //if a white piece can jump here
            if ((data[startx - 2][starty - 2] == 2 || data[startx - 2][starty - 2] == 4) && (data[startx - 1][starty - 1] == 3 || data[startx - 1][starty - 1] == 5)) {
                
                //check the other three directions to see if it can double jump
                //check bottom left
                if (starty < 6) {
                    //if there's a spot to jump to and a piece to jump over
                    if ((data[startx - 2][starty + 2] == 1) && (data[startx - 1][starty + 1] == 3 || data[startx - 1][starty + 1] == 5)) {
                        
                        //temporarily do the move
                        int[][] tempdata = copyarray(data);
                        tempdata[startx - 2][starty + 2] = tempdata[startx - 2][starty - 2];
                        tempdata[startx - 1][starty + 1] = 1;
                        tempdata[startx - 1][starty - 1] = 1;
                        tempdata[startx - 2][starty - 2] = 1;
                        
                        //then see if black can take any pieces because of this
                        int newrating = 20 + blacktradeoff(tempdata, startx - 2, starty - 2, startx - 2, starty + 2);
                        
                        //if this is a better move
                        if (newrating > rating) {
                            rating = newrating;  //set it
                        }
                        
                    }
                }
                
                //check top right
                if (startx < 6) {
                    //if there's a spot to jump to, a piece to jump over, and it's using a white king
                    if (data[startx + 2][starty - 2] == 1 && data[startx - 2][starty - 2] == 4 && (data[startx + 1][starty - 1] == 3 || data[startx + 1][starty - 1] == 5)) {
                        
                        //temporarily do the move
                        int[][] tempdata = copyarray(data);
                        tempdata[startx + 2][starty - 2] = tempdata[startx - 2][starty - 2];
                        tempdata[startx + 1][starty - 1] = 1;
                        tempdata[startx - 1][starty - 1] = 1;
                        tempdata[startx - 2][starty - 2] = 1;
                        
                        //then see if black can take any pieces because of this
                        int newrating = 20 + blacktradeoff(tempdata, startx - 2, starty - 2, startx + 2, starty - 2);
                        
                        //if this is a better move
                        if (newrating > rating) {
                            rating = newrating;  //set it
                        }
                        
                    }
                }
                
                //check bottom right
                if (startx < 6 && starty < 6) {
                    //if there's a spot to jump to and a piece to take
                    if (data[startx + 2][starty + 2] == 1 && (data[startx + 1][starty + 1] == 3 || data[startx + 1][starty + 1] == 5)) {
                        
                        //temporarily do the move
                        int[][] tempdata = copyarray(data);
                        tempdata[startx + 2][starty + 2] = tempdata[startx - 2][starty - 2];
                        tempdata[startx + 1][starty + 1] = 1;
                        tempdata[startx - 1][starty - 1] = 1;
                        tempdata[startx - 2][starty - 2] = 1;
                        
                        //then see if black can take any pieces because of this
                        int newrating = 20 + blacktradeoff(tempdata, startx - 2, starty - 2, startx + 2, starty + 2);
                        
                        //if this is a better move
                        if (newrating > rating) {
                            rating = newrating;  //set it
                        }
                        
                    }
                }
                
                //and also check the regular jump
                //temporarily make the move
                int[][] tempdata = copyarray(data);
                tempdata[startx][starty] = tempdata[startx - 2][starty - 2];
                tempdata[startx - 1][starty - 1] = 1;
                tempdata[startx - 2][starty - 2] = 1;
                
                //then see if black can take any pieces because of this
                int newrating = 10 + blacktradeoff(tempdata, startx - 2, starty - 2, startx, starty);
                        
                //if this is a better move
                if (newrating > rating) {
                    rating = newrating;  //set it
                }
                
            }
        }
        
        //check top right
        if (startx < 6 && starty > 1) {
            //if a white piece can jump here
            if ((data[startx + 2][starty - 2] == 2 || data[startx + 2][starty - 2] == 4) && (data[startx + 1][starty - 1] == 3 || data[startx + 1][starty - 1] == 5)) {
                
                //check the other three directions to see if it can double jump
                //check top left
                if (startx > 1) {
                    //if there's a spot to jump to, a piece to jump over, and it's a white king
                    if (data[startx - 2][starty - 2] == 1 && data[startx + 2][starty - 2] == 4 && (data[startx - 1][starty - 1] == 3 || data[startx - 1][starty - 1] == 5)) {
                        
                        //temporarily do the move
                        int[][] tempdata = copyarray(data);
                        tempdata[startx - 2][starty - 2] = tempdata[startx + 2][starty - 2];
                        tempdata[startx - 1][starty - 1] = 1;
                        tempdata[startx + 1][starty - 1] = 1;
                        tempdata[startx + 2][starty - 2] = 1;
                        
                        //then see if black can take any pieces because of this
                        int newrating = 20 + blacktradeoff(tempdata, startx + 2, starty - 2, startx - 2, starty - 2);
                        
                        //if this is a better move
                        if (newrating > rating) {
                            rating = newrating;  //set it
                        }
                        
                    }
                }
                
                //check bottom left
                if (startx > 1 && starty < 6) {
                    //if there's a spot to jump to and a piece to jump over
                    if ((data[startx - 2][starty + 2] == 1) && (data[startx - 1][starty + 1] == 3 || data[startx - 1][starty + 1] == 5)) {
                        
                        //temporarily do the move
                        int[][] tempdata = copyarray(data);
                        tempdata[startx - 2][starty + 2] = tempdata[startx + 2][starty - 2];
                        tempdata[startx - 1][starty + 1] = 1;
                        tempdata[startx + 1][starty - 1] = 1;
                        tempdata[startx + 2][starty - 2] = 1;
                        
                        //then see if black can take any pieces because of this
                        int newrating = 20 + blacktradeoff(tempdata, startx + 2, starty - 2, startx - 2, starty + 2);
                        
                        //if this is a better move
                        if (newrating > rating) {
                            rating = newrating;  //set it
                        }
                        
                    }
                }
                
                //check bottom right
                if (starty < 6) {
                    //if there's a spot to jump to and a piece to jump over
                    if (data[startx + 2][starty + 2] == 1 && (data[startx + 1][starty + 1] == 3 || data[startx + 1][starty + 1] == 5)) {
                        
                        //temporarily do the move
                        int[][] tempdata = copyarray(data);
                        tempdata[startx + 2][starty + 2] = tempdata[startx + 2][starty - 2];
                        tempdata[startx + 1][starty + 1] = 1;
                        tempdata[startx + 1][starty - 1] = 1;
                        tempdata[startx + 2][starty - 2] = 1;
                        
                        //then see if black can take any pieces because of this
                        int newrating = 20 + blacktradeoff(tempdata, startx + 2, starty - 2, startx + 2, starty + 2);
                        
                        //if this is a better move
                        if (newrating > rating) {
                            rating = newrating;  //set it
                        }
                        
                    }
                }
                
                //and also check the regular jump
                //temporarily make the move
                int[][] tempdata = copyarray(data);
                tempdata[startx][starty] = tempdata[startx + 2][starty - 2];
                tempdata[startx + 1][starty - 1] = 1;
                tempdata[startx + 2][starty - 2] = 1;
                
                //then see if black can take any pieces because of this
                int newrating = 10 + blacktradeoff(tempdata, startx + 2, starty - 2, startx, starty);
                        
                //if this is a better move
                if (newrating > rating) {
                    rating = newrating;  //set it
                }
                
            }
        }
        
        //check bottom left
        if (startx > 1 && starty < 6) {
            //if a white king can jump here
            if ((data[startx - 2][starty + 2] == 4) && (data[startx - 1][starty + 1] == 3 || data[startx - 1][starty + 1] == 5)) {
                
                //check the other three directions to see if it can double jump
                //check top left
                if (starty > 1) {
                    //if there's a spot to jump to and a piece to jump over
                    if (data[startx - 2][starty - 2] == 1 && (data[startx - 1][starty - 1] == 3 || data[startx - 1][starty - 1] == 5)) {
                        
                        //temporarily do the move
                        int[][] tempdata = copyarray(data);
                        tempdata[startx - 2][starty - 2] = tempdata[startx - 2][starty + 2];
                        tempdata[startx - 1][starty - 1] = 1;
                        tempdata[startx - 1][starty + 1] = 1;
                        tempdata[startx - 2][starty + 2] = 1;
                        
                        //then see if black can take any pieces because of this
                        int newrating = 20 + blacktradeoff(tempdata, startx - 2, starty + 2, startx - 2, starty - 2);
                        
                        //if this is a better move
                        if (newrating > rating) {
                            rating = newrating;  //set it
                        }
                        
                    }
                }
                
                //check top right
                if (startx < 6 && starty > 1) {
                    //if there's a spot to jump to and a piece to jump over
                    if (data[startx + 2][starty - 2] == 1 && (data[startx + 1][starty - 1] == 3 || data[startx + 1][starty - 1] == 5)) {
                        
                        //temporarily do the move
                        int[][] tempdata = copyarray(data);
                        tempdata[startx + 2][starty - 2] = tempdata[startx - 2][starty + 2];
                        tempdata[startx + 1][starty - 1] = 1;
                        tempdata[startx - 1][starty + 1] = 1;
                        tempdata[startx - 2][starty + 2] = 1;
                        
                        //then see if black can take any pieces because of this
                        int newrating = 20 + blacktradeoff(tempdata, startx - 2, starty + 2, startx + 2, starty - 2);
                        
                        //if this is a better move
                        if (newrating > rating) {
                            rating = newrating;  //set it
                        }
                        
                    }
                }
                
                //check bottom right
                if (startx < 6) {
                    //if there's a spot to jump to and a piece to jump over
                    if (data[startx + 2][starty + 2] == 1 && (data[startx + 1][starty + 1] == 3 || data[startx + 1][starty + 1] == 5)) {
                        
                        //temporarily do the move
                        int[][] tempdata = copyarray(data);
                        tempdata[startx + 2][starty + 2] = tempdata[startx - 2][starty + 2];
                        tempdata[startx + 1][starty + 1] = 1;
                        tempdata[startx - 1][starty + 1] = 1;
                        tempdata[startx - 2][starty + 2] = 1;
                        
                        //then see if black can take any pieces because of this
                        int newrating = 20 + blacktradeoff(tempdata, startx - 2, starty + 2, startx + 2, starty + 2);
                        
                        //if this is a better move
                        if (newrating > rating) {
                            rating = newrating;  //set it
                        }
                        
                    }
                }
                
                //and also check the regular jump
                //temporarily make the move
                int[][] tempdata = copyarray(data);
                tempdata[startx][starty] = tempdata[startx - 2][starty + 2];
                tempdata[startx - 1][starty + 1] = 1;
                tempdata[startx - 2][starty + 2] = 1;
                
                //then see if black can take any pieces because of this
                int newrating = 10 + blacktradeoff(tempdata, startx - 2, starty + 2, startx, starty);
                        
                //if this is a better move
                if (newrating > rating) {
                    rating = newrating;  //set it
                }
                
            }
        }
        
        //check bottom right
        if (startx < 6 && starty < 6) {
            //if a white king can jump here
            if ((data[startx + 2][starty + 2] == 4) && (data[startx + 1][starty + 1] == 3 || data[startx + 1][starty + 1] == 5)) {
                
                //check the other three directions to see if it can double jump
                //check top left
                if (startx > 1 && starty > 1) {
                    //if there's a spot to jump to and a piece to jump over
                    if (data[startx - 2][starty - 2] == 1 && (data[startx - 1][starty - 1] == 3 || data[startx - 1][starty - 1] == 5)) {
                        
                        //temporarily do the move
                        int[][] tempdata = copyarray(data);
                        tempdata[startx - 2][starty - 2] = tempdata[startx + 2][starty + 2];
                        tempdata[startx - 1][starty - 1] = 1;
                        tempdata[startx + 1][starty + 1] = 1;
                        tempdata[startx + 2][starty + 2] = 1;
                        
                        //then see if black can take any pieces because of this
                        int newrating = 20 + blacktradeoff(tempdata, startx + 2, starty + 2, startx - 2, starty - 2);
                        
                        //if this is a better move
                        if (newrating > rating) {
                            rating = newrating;  //set it
                        }
                        
                    }
                }
                
                //check top right
                if (starty > 1) {
                    //if there's a spot to jump to and a piece to jump over
                    if (data[startx + 2][starty - 2] == 1 && (data[startx + 1][starty - 1] == 3 || data[startx + 1][starty - 1] == 5)) {
                        
                        //temporarily do the move
                        int[][] tempdata = copyarray(data);
                        tempdata[startx + 2][starty - 2] = tempdata[startx + 2][starty + 2];
                        tempdata[startx + 1][starty - 1] = 1;
                        tempdata[startx + 1][starty + 1] = 1;
                        tempdata[startx + 2][starty + 2] = 1;
                        
                        //then see if black can take any pieces because of this
                        int newrating = 20 + blacktradeoff(tempdata, startx + 2, starty + 2, startx + 2, starty - 2);
                        
                        //if this is a better move
                        if (newrating > rating) {
                            rating = newrating;  //set it
                        }
                        
                    }
                }
                
                //check bottom left
                if (startx > 1) {
                    //if there's a spot to jump to and a piece to jump over
                    if ((data[startx - 2][starty + 2] == 1) && (data[startx - 1][starty + 1] == 3 || data[startx - 1][starty + 1] == 5)) {
                        
                        //temporarily do the move
                        int[][] tempdata = copyarray(data);
                        tempdata[startx - 2][starty + 2] = tempdata[startx + 2][starty + 2];
                        tempdata[startx - 1][starty + 1] = 1;
                        tempdata[startx + 1][starty + 1] = 1;
                        tempdata[startx + 2][starty + 2] = 1;
                        
                        //then see if black can take any pieces because of this
                        int newrating = 20 + blacktradeoff(tempdata, startx + 2, starty + 2, startx - 2, starty + 2);
                        
                        //if this is a better move
                        if (newrating > rating) {
                            rating = newrating;  //set it
                        }
                        
                    }
                }
                
                //and also check the regular jump
                //temporarily make the move
                int[][] tempdata = copyarray(data);
                tempdata[startx][starty] = tempdata[startx + 2][starty + 2];
                tempdata[startx + 1][starty + 1] = 1;
                tempdata[startx + 2][starty + 2] = 1;
                
                //then see if black can take any pieces because of this
                int newrating = 10 + blacktradeoff(tempdata, startx + 2, starty + 2, startx, starty);
                        
                //if this is a better move
                if (newrating > rating) {
                    rating = newrating;  //set it
                }
                
            }
        }
        
        //and then check the arrived square
        //make sure it's not in the corner
        if ((endx > 0 && endx < 7) && (endy > 0 && endy < 7)) {
            
            //check top left
            //if a white piece here can jump
            if ((data[endx - 1][endy - 1] == 2 || data[endx - 1][endy - 1] == 4) && data[endx + 1][endy + 1] == 1) {
                
                //check the other three directions to see if it can double jump
                //check top right if it's a king
                if (data[endx - 1][endy - 1] == 4 && endx + 1 < 6 && endy + 1 > 1) {
                    //if there's a spot to jump and an enemy to take
                    if (data[endx + 3][endy - 1] == 1 && (data[endx + 2][endy] == 3 || data[endx + 2][endy] == 5)) {
                        
                        //temporarily make the move
                        int[][] tempdata = copyarray(data);
                        tempdata[endx + 3][endy - 1] = tempdata[endx - 1][endy - 1];
                        tempdata[endx + 2][endy] = 1;
                        tempdata[endx][endy] = 1;
                        tempdata[endx - 1][endy - 1] = 1;
                
                        //then see if black can take any pieces because of this
                        int newrating = 20 + blacktradeoff(tempdata, endx - 1, endy - 1, endx + 3, endy - 1);
                        
                        //if this is a better move
                        if (newrating > rating) {
                            rating = newrating;  //set it
                        }
                        
                    }
                }
                
                //check bottom left
                if (endx + 1 > 1 && endy + 1 < 6) {
                    //if there's a spot to jump and an enemy to take
                    if (data[endx - 1][endy + 3] == 1 && (data[endx][endy + 2] == 3 || data[endx][endy + 2] == 5)) {
                        
                        //temporarily make the move
                        int[][] tempdata = copyarray(data);
                        tempdata[endx - 1][endy + 3] = tempdata[endx - 1][endy - 1];
                        tempdata[endx][endy + 2] = 1;
                        tempdata[endx][endy] = 1;
                        tempdata[endx - 1][endy - 1] = 1;
                
                        //then see if black can take any pieces because of this
                        int newrating = 20 + blacktradeoff(tempdata, endx - 1, endy - 1, endx - 1, endy + 3);
                        
                        //if this is a better move
                        if (newrating > rating) {
                            rating = newrating;  //set it
                        }
                        
                    }
                }
                
                //check bottom right
                if (endx + 1 < 6 && endy + 1 < 6) {
                    //if there's a spot to jump and an enemy to take
                    if (data[endx + 3][endy + 3] == 1 && (data[endx + 2][endy + 2] == 3 || data[endx + 2][endy + 2] == 5)) {
                        
                        //temporarily make the move
                        int[][] tempdata = copyarray(data);
                        tempdata[endx + 3][endy + 3] = tempdata[endx - 1][endy - 1];
                        tempdata[endx + 2][endy + 2] = 1;
                        tempdata[endx][endy] = 1;
                        tempdata[endx - 1][endy - 1] = 1;
                
                        //then see if black can take any pieces because of this
                        int newrating = 20 + blacktradeoff(tempdata, endx - 1, endy - 1, endx + 3, endy + 3);
                        
                        //if this is a better move
                        if (newrating > rating) {
                            rating = newrating;  //set it
                        }
                        
                    }
                }
                
                //check normal
                //temporarily make the move
                int[][] tempdata = copyarray(data);
                tempdata[endx + 1][endy + 1] = tempdata[endx - 1][endy - 1];
                tempdata[endx][endy] = 1;
                tempdata[endx - 1][endy - 1] = 1;
                
                //then see if black can take any pieces because of this
                int newrating = 10 + blacktradeoff(tempdata, endx - 1, endy - 1, endx + 1, endy + 1);
                        
                //if this is a better move
                if (newrating > rating) {
                    rating = newrating;  //set it
                }
                
            }
            
            //check top right
            //if a white piece here can jump
            if ((data[endx + 1][endy - 1] == 2 || data[endx + 1][endy - 1] == 4) && data[endx - 1][endy + 1] == 1) {
                
                //check the other three directions to see if it can double jump
                //check top left if it's a king
                if (data[endx + 1][endy - 1] == 4 && endx - 1 > 1 && endy + 1 > 1) {
                    //if there's a spot to jump and an enemy to take
                    if (data[endx - 3][endy - 1] == 1 && (data[endx - 2][endy] == 3 || data[endx - 2][endy] == 5)) {
                        
                        //temporarily make the move
                        int[][] tempdata = copyarray(data);
                        tempdata[endx - 3][endy - 1] = tempdata[endx + 1][endy - 1];
                        tempdata[endx - 2][endy] = 1;
                        tempdata[endx][endy] = 1;
                        tempdata[endx + 1][endy - 1] = 1;
                
                        //then see if black can take any pieces because of this
                        int newrating = 20 + blacktradeoff(tempdata, endx + 1, endy - 1, endx - 3, endy - 1);
                        
                        //if this is a better move
                        if (newrating > rating) {
                            rating = newrating;  //set it
                        }
                        
                    }
                }
                
                //check bottom left
                if (endx - 1 > 1 && endy + 1 < 6) {
                    //if there's a spot to jump and an enemy to take
                    if (data[endx - 3][endy + 3] == 1 && (data[endx - 2][endy + 2] == 3 || data[endx - 2][endy + 2] == 5)) {
                        
                        //temporarily make the move
                        int[][] tempdata = copyarray(data);
                        tempdata[endx - 3][endy + 3] = tempdata[endx + 1][endy - 1];
                        tempdata[endx - 2][endy + 2] = 1;
                        tempdata[endx][endy] = 1;
                        tempdata[endx + 1][endy - 1] = 1;
                
                        //then see if black can take any pieces because of this
                        int newrating = 20 + blacktradeoff(tempdata, endx + 1, endy - 1, endx - 3, endy + 3);
                        
                        //if this is a better move
                        if (newrating > rating) {
                            rating = newrating;  //set it
                        }
                        
                    }
                }
                
                //check bottom right
                if (endx - 1 < 6 && endy + 1 < 6) {
                    //if there's a spot to jump and an enemy to take
                    if (data[endx + 1][endy + 3] == 1 && (data[endx][endy + 2] == 3 || data[endx][endy + 2] == 5)) {
                        
                        //temporarily make the move
                        int[][] tempdata = copyarray(data);
                        tempdata[endx + 1][endy + 3] = tempdata[endx + 1][endy - 1];
                        tempdata[endx][endy + 2] = 1;
                        tempdata[endx][endy] = 1;
                        tempdata[endx + 1][endy - 1] = 1;
                
                        //then see if black can take any pieces because of this
                        int newrating = 20 + blacktradeoff(tempdata, endx + 1, endy - 1, endx + 1, endy + 3);
                        
                        //if this is a better move
                        if (newrating > rating) {
                            rating = newrating;  //set it
                        }
                        
                    }
                }
                
                //check normal
                //temporarily make the move
                int[][] tempdata = copyarray(data);
                tempdata[endx - 1][endy + 1] = tempdata[endx + 1][endy - 1];
                tempdata[endx][endy] = 1;
                tempdata[endx + 1][endy - 1] = 1;
                
                //then see if black can take any pieces because of this
                int newrating = 10 + blacktradeoff(tempdata, endx + 1, endy - 1, endx - 1, endy + 1);
                        
                //if this is a better move
                if (newrating > rating) {
                    rating = newrating;  //set it
                }
                
            }
            
            //check bottom left
            //if a white king here can jump
            if (data[endx - 1][endy + 1] == 4 && data[endx + 1][endy - 1] == 1) {
                
                //check top left
                if (endx + 1 > 1 && endy - 1 > 1) {
                    //if there's a spot to jump and an enemy to take
                    if (data[endx - 1][endy - 3] == 1 && (data[endx][endy - 2] == 3 || data[endx][endy - 2] == 5)) {
                        
                        //temporarily make the move
                        int[][] tempdata = copyarray(data);
                        tempdata[endx - 1][endy - 3] = tempdata[endx - 1][endy + 1];
                        tempdata[endx][endy - 2] = 1;
                        tempdata[endx][endy] = 1;
                        tempdata[endx - 1][endy + 1] = 1;
                
                        //then see if black can take any pieces because of this
                        int newrating = 20 + blacktradeoff(tempdata, endx - 1, endy + 1, endx - 1, endy - 3);
                        
                        //if this is a better move
                        if (newrating > rating) {
                            rating = newrating;  //set it
                        }
                        
                    }
                }
                
                //check top right
                if (endx + 1 < 6 && endy - 1 > 1) {
                    //if there's a spot to jump and an enemy to take
                    if (data[endx + 3][endy - 3] == 1 && (data[endx + 2][endy - 2] == 3 || data[endx + 2][endy - 2] == 5)) {
                        
                        //temporarily make the move
                        int[][] tempdata = copyarray(data);
                        tempdata[endx + 3][endy - 3] = tempdata[endx - 1][endy + 1];
                        tempdata[endx + 2][endy - 2] = 1;
                        tempdata[endx][endy] = 1;
                        tempdata[endx - 1][endy + 1] = 1;
                
                        //then see if black can take any pieces because of this
                        int newrating = 20 + blacktradeoff(tempdata, endx - 1, endy + 1, endx + 3, endy - 3);
                        
                        //if this is a better move
                        if (newrating > rating) {
                            rating = newrating;  //set it
                        }
                        
                    }
                }
                
                //check bottom right
                if (endx + 1 < 6 && endy - 1 < 6) {
                    //if there's a spot to jump and an enemy to take
                    if (data[endx + 3][endy + 1] == 1 && (data[endx + 2][endy] == 3 || data[endx + 2][endy] == 5)) {
                        
                        //temporarily make the move
                        int[][] tempdata = copyarray(data);
                        tempdata[endx + 3][endy + 1] = tempdata[endx - 1][endy + 1];
                        tempdata[endx + 2][endy] = 1;
                        tempdata[endx][endy] = 1;
                        tempdata[endx - 1][endy + 1] = 1;
                
                        //then see if black can take any pieces because of this
                        int newrating = 20 + blacktradeoff(tempdata, endx - 1, endy + 1, endx + 3, endy + 1);
                        
                        //if this is a better move
                        if (newrating > rating) {
                            rating = newrating;  //set it
                        }
                        
                    }
                }
                
                //check normal
                //temporarily make the move
                int[][] tempdata = copyarray(data);
                tempdata[endx + 1][endy - 1] = tempdata[endx - 1][endy + 1];
                tempdata[endx][endy] = 1;
                tempdata[endx - 1][endy + 1] = 1;
                
                //then see if black can take any pieces because of this
                int newrating = 10 + blacktradeoff(tempdata, endx - 1, endy + 1, endx + 1, endy - 1);
                        
                //if this is a better move
                if (newrating > rating) {
                    rating = newrating;  //set it
                }
                
            }
            
            //check bottom right
            //if a white king here can jump
            if (data[endx + 1][endy + 1] == 4 && data[endx - 1][endy - 1] == 1) {
                
                //check top left
                if (endx - 1 > 1 && endy - 1 > 1) {
                    //if there's a spot to jump and an enemy to take
                    if (data[endx - 3][endy - 3] == 1 && (data[endx - 2][endy - 2] == 3 || data[endx - 2][endy - 2] == 5)) {
                        
                        //temporarily make the move
                        int[][] tempdata = copyarray(data);
                        tempdata[endx - 3][endy - 3] = tempdata[endx + 1][endy + 1];
                        tempdata[endx - 2][endy - 2] = 1;
                        tempdata[endx][endy] = 1;
                        tempdata[endx + 1][endy + 1] = 1;
                
                        //then see if black can take any pieces because of this
                        int newrating = 20 + blacktradeoff(tempdata, endx + 1, endy + 1, endx - 3, endy - 3);
                        
                        //if this is a better move
                        if (newrating > rating) {
                            rating = newrating;  //set it
                        }
                        
                    }
                }
                
                //check top right
                if (endx - 1 < 6 && endy - 1 > 1) {
                    //if there's a spot to jump and an enemy to take
                    if (data[endx + 1][endy - 3] == 1 && (data[endx][endy - 2] == 3 || data[endx][endy - 2] == 5)) {
                        
                        //temporarily make the move
                        int[][] tempdata = copyarray(data);
                        tempdata[endx + 1][endy - 3] = tempdata[endx + 1][endy + 1];
                        tempdata[endx][endy - 2] = 1;
                        tempdata[endx][endy] = 1;
                        tempdata[endx + 1][endy + 1] = 1;
                
                        //then see if black can take any pieces because of this
                        int newrating = 20 + blacktradeoff(tempdata, endx + 1, endy + 1, endx + 1, endy - 3);
                        
                        //if this is a better move
                        if (newrating > rating) {
                            rating = newrating;  //set it
                        }
                        
                    }
                }
                
                //check bottom left
                if (endx - 1 > 1 && endy - 1 < 6) {
                    //if there's a spot to jump and an enemy to take
                    if (data[endx - 3][endy + 1] == 1 && (data[endx - 2][endy] == 3 || data[endx - 2][endy] == 5)) {
                        
                        //temporarily make the move
                        int[][] tempdata = copyarray(data);
                        tempdata[endx - 3][endy + 1] = tempdata[endx + 1][endy + 1];
                        tempdata[endx - 2][endy] = 1;
                        tempdata[endx][endy] = 1;
                        tempdata[endx + 1][endy + 1] = 1;
                
                        //then see if black can take any pieces because of this
                        int newrating = 20 + blacktradeoff(tempdata, endx + 1, endy + 1, endx - 3, endy + 1);
                        
                        //if this is a better move
                        if (newrating > rating) {
                            rating = newrating;  //set it
                        }
                        
                    }
                }
                
                //check normal
                //temporarily make the move
                int[][] tempdata = copyarray(data);
                tempdata[endx - 1][endy - 1] = tempdata[endx + 1][endy + 1];
                tempdata[endx][endy] = 1;
                tempdata[endx + 1][endy + 1] = 1;
                
                //then see if black can take any pieces because of this
                int newrating = 10 + blacktradeoff(tempdata, endx + 1, endy + 1, endx - 1, endy - 1);
                        
                //if this is a better move
                if (newrating > rating) {
                    rating = newrating;  //set it
                }
                
            }
            
        }
        
        return rating;
        
    }
    
    
    
    //the other half of the tradeoff algorithm
    //white tradeoff is given a black move, black tradeoff is given a white move
    //this is because it white tradeoff checks for white moves, and vice versa
    public static int blacktradeoff(int[][] data, int startx, int starty, int endx, int endy) {
        
        int rating = 0;
        
        //first check the departed square
        //check top left
        if (startx > 1 && starty > 1) {
            //if a black king can jump here
            if ((data[startx - 2][starty - 2] == 5) && (data[startx - 1][starty - 1] == 2 || data[startx - 1][starty - 1] == 4)) {
                
                //check the other three directions to see if it can double jump
                //check bottom left
                if (starty < 6) {
                    //if there's a spot to jump to and a piece to jump over
                    if ((data[startx - 2][starty + 2] == 1) && (data[startx - 1][starty + 1] == 2 || data[startx - 1][starty + 1] == 4)) {
                        
                        //temporarily do the move
                        int[][] tempdata = copyarray(data);
                        tempdata[startx - 2][starty + 2] = tempdata[startx - 2][starty - 2];
                        tempdata[startx - 1][starty + 1] = 1;
                        tempdata[startx - 1][starty - 1] = 1;
                        tempdata[startx - 2][starty - 2] = 1;
                        
                        //then see if white can take any pieces because of this
                        int newrating = -20 + whitetradeoff(tempdata, startx - 2, starty - 2, startx - 2, starty + 2);
                        
                        //if this is a worse move
                        if (newrating < rating) {
                            rating = newrating;  //set it
                        }
                        
                    }
                }
                
                //check top right
                if (startx < 6) {
                    //if there's a spot to jump to and a piece to jump over
                    if (data[startx + 2][starty - 2] == 1 && (data[startx + 1][starty - 1] == 2 || data[startx + 1][starty - 1] == 4)) {
                        
                        //temporarily do the move
                        int[][] tempdata = copyarray(data);
                        tempdata[startx + 2][starty - 2] = tempdata[startx - 2][starty - 2];
                        tempdata[startx + 1][starty - 1] = 1;
                        tempdata[startx - 1][starty - 1] = 1;
                        tempdata[startx - 2][starty - 2] = 1;
                        
                        //then see if white can take any pieces because of this
                        int newrating = -20 + whitetradeoff(tempdata, startx - 2, starty - 2, startx + 2, starty - 2);
                        
                        //if this is a worse move
                        if (newrating < rating) {
                            rating = newrating;  //set it
                        }
                        
                    }
                }
                
                //check bottom right
                if (startx < 6 && starty < 6) {
                    //if there's a spot to jump to and a piece to take
                    if (data[startx + 2][starty + 2] == 1 && (data[startx + 1][starty + 1] == 2 || data[startx + 1][starty + 1] == 4)) {
                        
                        //temporarily do the move
                        int[][] tempdata = copyarray(data);
                        tempdata[startx + 2][starty + 2] = tempdata[startx - 2][starty - 2];
                        tempdata[startx + 1][starty + 1] = 1;
                        tempdata[startx - 1][starty - 1] = 1;
                        tempdata[startx - 2][starty - 2] = 1;
                        
                        //then see if white can take any pieces because of this
                        int newrating = -20 + whitetradeoff(tempdata, startx - 2, starty - 2, startx + 2, starty + 2);
                        
                        //if this is a worse move
                        if (newrating < rating) {
                            rating = newrating;  //set it
                        }
                        
                    }
                }
                
                //and also check the regular jump
                //temporarily make the move
                int[][] tempdata = copyarray(data);
                tempdata[startx][starty] = tempdata[startx - 2][starty - 2];
                tempdata[startx - 1][starty - 1] = 1;
                tempdata[startx - 2][starty - 2] = 1;
                
                //then see if white can take any pieces because of this
                int newrating = -10 + whitetradeoff(tempdata, startx - 2, starty - 2, startx, starty);
                        
                //if this is a worse move
                if (newrating < rating) {
                    rating = newrating;  //set it
                }
                
            }
        }
        
        //check top right
        if (startx < 6 && starty > 1) {
            //if a black king can jump here
            if ((data[startx + 2][starty - 2] == 5) && (data[startx + 1][starty - 1] == 2 || data[startx + 1][starty - 1] == 4)) {
                
                //check the other three directions to see if it can double jump
                //check top left
                if (startx > 1) {
                    //if there's a spot to jump to and a piece to jump over
                    if (data[startx - 2][starty - 2] == 1 && (data[startx - 1][starty - 1] == 2 || data[startx - 1][starty - 1] == 4)) {
                        
                        //temporarily do the move
                        int[][] tempdata = copyarray(data);
                        tempdata[startx - 2][starty - 2] = tempdata[startx + 2][starty - 2];
                        tempdata[startx - 1][starty - 1] = 1;
                        tempdata[startx + 1][starty - 1] = 1;
                        tempdata[startx + 2][starty - 2] = 1;
                        
                        //then see if white can take any pieces because of this
                        int newrating = -20 + whitetradeoff(tempdata, startx + 2, starty - 2, startx - 2, starty - 2);
                        
                        //if this is a worse move
                        if (newrating < rating) {
                            rating = newrating;  //set it
                        }
                        
                    }
                }
                
                //check bottom left
                if (startx > 1 && starty < 6) {
                    //if there's a spot to jump to and a piece to jump over
                    if ((data[startx - 2][starty + 2] == 1) && (data[startx - 1][starty + 1] == 2 || data[startx - 1][starty + 1] == 4)) {
                        
                        //temporarily do the move
                        int[][] tempdata = copyarray(data);
                        tempdata[startx - 2][starty + 2] = tempdata[startx + 2][starty - 2];
                        tempdata[startx - 1][starty + 1] = 1;
                        tempdata[startx + 1][starty - 1] = 1;
                        tempdata[startx + 2][starty - 2] = 1;
                        
                        //then see if white can take any pieces because of this
                        int newrating = -20 + whitetradeoff(tempdata, startx + 2, starty - 2, startx - 2, starty + 2);
                        
                        //if this is a worse move
                        if (newrating < rating) {
                            rating = newrating;  //set it
                        }
                        
                    }
                }
                
                //check bottom right
                if (starty < 6) {
                    //if there's a spot to jump to and a piece to jump over
                    if (data[startx + 2][starty + 2] == 1 && (data[startx + 1][starty + 1] == 2 || data[startx + 1][starty + 1] == 4)) {
                        
                        //temporarily do the move
                        int[][] tempdata = copyarray(data);
                        tempdata[startx + 2][starty + 2] = tempdata[startx + 2][starty - 2];
                        tempdata[startx + 1][starty + 1] = 1;
                        tempdata[startx + 1][starty - 1] = 1;
                        tempdata[startx + 2][starty - 2] = 1;
                        
                        //then see if white can take any pieces because of this
                        int newrating = -20 + whitetradeoff(tempdata, startx + 2, starty - 2, startx + 2, starty + 2);
                        
                        //if this is a worse move
                        if (newrating < rating) {
                            rating = newrating;  //set it
                        }
                        
                    }
                }
                
                //and also check the regular jump
                //temporarily make the move
                int[][] tempdata = copyarray(data);
                tempdata[startx][starty] = tempdata[startx + 2][starty - 2];
                tempdata[startx + 1][starty - 1] = 1;
                tempdata[startx + 2][starty - 2] = 1;
                
                //then see if white can take any pieces because of this
                int newrating = -10 + whitetradeoff(tempdata, startx + 2, starty - 2, startx, starty);
                        
                //if this is a worse move
                if (newrating < rating) {
                    rating = newrating;  //set it
                }
                
            }
        }
        
        //check bottom left
        if (startx > 1 && starty < 6) {
            //if a black piece can jump here
            if ((data[startx - 2][starty + 2] == 3 || data[startx - 2][starty + 2] == 5) && (data[startx - 1][starty + 1] == 2 || data[startx - 1][starty + 1] == 4)) {
                
                //check the other three directions to see if it can double jump
                //check top left
                if (starty > 1) {
                    //if there's a spot to jump to and a piece to jump over
                    if (data[startx - 2][starty - 2] == 1 && (data[startx - 1][starty - 1] == 2 || data[startx - 1][starty - 1] == 4)) {
                        
                        //temporarily do the move
                        int[][] tempdata = copyarray(data);
                        tempdata[startx - 2][starty - 2] = tempdata[startx - 2][starty + 2];
                        tempdata[startx - 1][starty - 1] = 1;
                        tempdata[startx - 1][starty + 1] = 1;
                        tempdata[startx - 2][starty + 2] = 1;
                        
                        //then see if white can take any pieces because of this
                        int newrating = -20 + whitetradeoff(tempdata, startx - 2, starty + 2, startx - 2, starty - 2);
                        
                        //if this is a worse move
                        if (newrating < rating) {
                            rating = newrating;  //set it
                        }
                        
                    }
                }
                
                //check top right
                if (startx < 6 && starty > 1) {
                    //if there's a spot to jump to and a piece to jump over
                    if (data[startx + 2][starty - 2] == 1 && (data[startx + 1][starty - 1] == 2 || data[startx + 1][starty - 1] == 4)) {
                        
                        //temporarily do the move
                        int[][] tempdata = copyarray(data);
                        tempdata[startx + 2][starty - 2] = tempdata[startx - 2][starty + 2];
                        tempdata[startx + 1][starty - 1] = 1;
                        tempdata[startx - 1][starty + 1] = 1;
                        tempdata[startx - 2][starty + 2] = 1;
                        
                        //then see if white can take any pieces because of this
                        int newrating = -20 + whitetradeoff(tempdata, startx - 2, starty + 2, startx + 2, starty - 2);
                        
                        //if this is a worse move
                        if (newrating < rating) {
                            rating = newrating;  //set it
                        }
                        
                    }
                }
                
                //check bottom right
                if (startx < 6) {
                    //if there's a spot to jump to, a piece to jump over, and it's a black king
                    if (data[startx + 2][starty + 2] == 1 && data[startx - 2][starty + 2] == 5 && (data[startx + 1][starty + 1] == 2 || data[startx + 1][starty + 1] == 4)) {
                        
                        //temporarily do the move
                        int[][] tempdata = copyarray(data);
                        tempdata[startx + 2][starty + 2] = tempdata[startx - 2][starty + 2];
                        tempdata[startx + 1][starty + 1] = 1;
                        tempdata[startx - 1][starty + 1] = 1;
                        tempdata[startx - 2][starty + 2] = 1;
                        
                        //then see if white can take any pieces because of this
                        int newrating = -20 + whitetradeoff(tempdata, startx - 2, starty + 2, startx + 2, starty + 2);
                        
                        //if this is a worse move
                        if (newrating < rating) {
                            rating = newrating;  //set it
                        }
                        
                    }
                }
                
                //and also check the regular jump
                //temporarily make the move
                int[][] tempdata = copyarray(data);
                tempdata[startx][starty] = tempdata[startx - 2][starty + 2];
                tempdata[startx - 1][starty + 1] = 1;
                tempdata[startx - 2][starty + 2] = 1;
                
                //then see if white can take any pieces because of this
                int newrating = -10 + whitetradeoff(tempdata, startx - 2, starty + 2, startx, starty);
                        
                //if this is a worse move
                if (newrating < rating) {
                    rating = newrating;  //set it
                }
                
            }
        }
        
        //check bottom right
        if (startx < 6 && starty < 6) {
            //if a black piece can jump here
            if ((data[startx + 2][starty + 2] == 3 || data[startx + 2][starty + 2] == 5) && (data[startx + 1][starty + 1] == 2 || data[startx + 1][starty + 1] == 4)) {
                
                //check the other three directions to see if it can double jump
                //check top left
                if (startx > 1 && starty > 1) {
                    //if there's a spot to jump to and a piece to jump over
                    if (data[startx - 2][starty - 2] == 1 && (data[startx - 1][starty - 1] == 2 || data[startx - 1][starty - 1] == 4)) {
                        
                        //temporarily do the move
                        int[][] tempdata = copyarray(data);
                        tempdata[startx - 2][starty - 2] = tempdata[startx + 2][starty + 2];
                        tempdata[startx - 1][starty - 1] = 1;
                        tempdata[startx + 1][starty + 1] = 1;
                        tempdata[startx + 2][starty + 2] = 1;
                        
                        //then see if white can take any pieces because of this
                        int newrating = -20 + whitetradeoff(tempdata, startx + 2, starty + 2, startx - 2, starty - 2);
                        
                        //if this is a worse move
                        if (newrating < rating) {
                            rating = newrating;  //set it
                        }
                        
                    }
                }
                
                //check top right
                if (starty > 1) {
                    //if there's a spot to jump to and a piece to jump over
                    if (data[startx + 2][starty - 2] == 1 && (data[startx + 1][starty - 1] == 2 || data[startx + 1][starty - 1] == 4)) {
                        
                        //temporarily do the move
                        int[][] tempdata = copyarray(data);
                        tempdata[startx + 2][starty - 2] = tempdata[startx + 2][starty + 2];
                        tempdata[startx + 1][starty - 1] = 1;
                        tempdata[startx + 1][starty + 1] = 1;
                        tempdata[startx + 2][starty + 2] = 1;
                        
                        //then see if white can take any pieces because of this
                        int newrating = -20 + whitetradeoff(tempdata, startx + 2, starty + 2, startx + 2, starty - 2);
                        
                        //if this is a worse move
                        if (newrating < rating) {
                            rating = newrating;  //set it
                        }
                        
                    }
                }
                
                //check bottom left
                if (startx > 1) {
                    //if there's a spot to jump to, a piece to jump over, and it's a black king
                    if ((data[startx - 2][starty + 2] == 1) && data[startx + 2][starty + 2] == 5 && (data[startx - 1][starty + 1] == 2 || data[startx - 1][starty + 1] == 4)) {
                        
                        //temporarily do the move
                        int[][] tempdata = copyarray(data);
                        tempdata[startx - 2][starty + 2] = tempdata[startx + 2][starty + 2];
                        tempdata[startx - 1][starty + 1] = 1;
                        tempdata[startx + 1][starty + 1] = 1;
                        tempdata[startx + 2][starty + 2] = 1;
                        
                        //then see if white can take any pieces because of this
                        int newrating = -20 + whitetradeoff(tempdata, startx + 2, starty + 2, startx - 2, starty + 2);
                        
                        //if this is a worse move
                        if (newrating < rating) {
                            rating = newrating;  //set it
                        }
                        
                    }
                }
                
                //and also check the regular jump
                //temporarily make the move
                int[][] tempdata = copyarray(data);
                tempdata[startx][starty] = tempdata[startx + 2][starty + 2];
                tempdata[startx + 1][starty + 1] = 1;
                tempdata[startx + 2][starty + 2] = 1;
                
                //then see if white can take any pieces because of this
                int newrating = -10 + whitetradeoff(tempdata, startx + 2, starty + 2, startx, starty);
                        
                //if this is a worse move
                if (newrating < rating) {
                    rating = newrating;  //set it
                }
                
            }
        }
        
        //and then check the arrived square
        //make sure it's not in the corner
        if ((endx > 0 && endx < 7) && (endy > 0 && endy < 7)) {
            
            //check top left
            //if a black king here can jump
            if (data[endx - 1][endy - 1] == 5 && data[endx + 1][endy + 1] == 1) {
                
                //check the other three directions to see if it can double jump
                //check top right
                if (endx + 1 < 6 && endy + 1 > 1) {
                    //if there's a spot to jump and an enemy to take
                    if (data[endx + 3][endy - 1] == 1 && (data[endx + 2][endy] == 2 || data[endx + 2][endy] == 4)) {
                        
                        //temporarily make the move
                        int[][] tempdata = copyarray(data);
                        tempdata[endx + 3][endy - 1] = tempdata[endx - 1][endy - 1];
                        tempdata[endx + 2][endy] = 1;
                        tempdata[endx][endy] = 1;
                        tempdata[endx - 1][endy - 1] = 1;
                
                        //then see if white can take any pieces because of this
                        int newrating = -20 + whitetradeoff(tempdata, endx - 1, endy - 1, endx + 3, endy - 1);
                        
                        //if this is a worse move
                        if (newrating < rating) {
                            rating = newrating;  //set it
                        }
                        
                    }
                }
                
                //check bottom left
                if (endx + 1 > 1 && endy + 1 < 6) {
                    //if there's a spot to jump and an enemy to take
                    if (data[endx - 1][endy + 3] == 1 && (data[endx][endy + 2] == 2 || data[endx][endy + 2] == 4)) {
                        
                        //temporarily make the move
                        int[][] tempdata = copyarray(data);
                        tempdata[endx - 1][endy + 3] = tempdata[endx - 1][endy - 1];
                        tempdata[endx][endy + 2] = 1;
                        tempdata[endx][endy] = 1;
                        tempdata[endx - 1][endy - 1] = 1;
                
                        //then see if white can take any pieces because of this
                        int newrating = -20 + whitetradeoff(tempdata, endx - 1, endy - 1, endx - 1, endy + 3);
                        
                        //if this is a worse move
                        if (newrating < rating) {
                            rating = newrating;  //set it
                        }
                        
                    }
                }
                
                //check bottom right
                if (endx + 1 < 6 && endy + 1 < 6) {
                    //if there's a spot to jump and an enemy to take
                    if (data[endx + 3][endy + 3] == 1 && (data[endx + 2][endy + 2] == 2 || data[endx + 2][endy + 2] == 4)) {
                        
                        //temporarily make the move
                        int[][] tempdata = copyarray(data);
                        tempdata[endx + 3][endy + 3] = tempdata[endx - 1][endy - 1];
                        tempdata[endx + 2][endy + 2] = 1;
                        tempdata[endx][endy] = 1;
                        tempdata[endx - 1][endy - 1] = 1;
                
                        //then see if white can take any pieces because of this
                        int newrating = -20 + whitetradeoff(tempdata, endx - 1, endy - 1, endx + 3, endy + 3);
                        
                        //if this is a worse move
                        if (newrating < rating) {
                            rating = newrating;  //set it
                        }
                        
                    }
                }
                
                //check normal
                //temporarily make the move
                int[][] tempdata = copyarray(data);
                tempdata[endx + 1][endy + 1] = tempdata[endx - 1][endy - 1];
                tempdata[endx][endy] = 1;
                tempdata[endx - 1][endy - 1] = 1;
                
                //then see if white can take any pieces because of this
                int newrating = -10 + whitetradeoff(tempdata, endx - 1, endy - 1, endx + 1, endy + 1);
                        
                //if this is a worse move
                if (newrating < rating) {
                    rating = newrating;  //set it
                }
                
            }
            
            //check top right
            //if a black king here can jump
            if (data[endx + 1][endy - 1] == 5 && data[endx - 1][endy + 1] == 1) {
                
                //check the other three directions to see if it can double jump
                //check top left
                if (endx - 1 > 1 && endy + 1 > 1) {
                    //if there's a spot to jump and an enemy to take
                    if (data[endx - 3][endy - 1] == 1 && (data[endx - 2][endy] == 2 || data[endx - 2][endy] == 4)) {
                        
                        //temporarily make the move
                        int[][] tempdata = copyarray(data);
                        tempdata[endx - 3][endy - 1] = tempdata[endx + 1][endy - 1];
                        tempdata[endx - 2][endy] = 1;
                        tempdata[endx][endy] = 1;
                        tempdata[endx + 1][endy - 1] = 1;
                
                        //then see if white can take any pieces because of this
                        int newrating = -20 + whitetradeoff(tempdata, endx + 1, endy - 1, endx - 3, endy - 1);
                        
                        //if this is a worse move
                        if (newrating < rating) {
                            rating = newrating;  //set it
                        }
                        
                    }
                }
                
                //check bottom left
                if (endx - 1 > 1 && endy + 1 < 6) {
                    //if there's a spot to jump and an enemy to take
                    if (data[endx - 3][endy + 3] == 1 && (data[endx - 2][endy + 2] == 2 || data[endx - 2][endy + 2] == 4)) {
                        
                        //temporarily make the move
                        int[][] tempdata = copyarray(data);
                        tempdata[endx - 3][endy + 3] = tempdata[endx + 1][endy - 1];
                        tempdata[endx - 2][endy + 2] = 1;
                        tempdata[endx][endy] = 1;
                        tempdata[endx + 1][endy - 1] = 1;
                
                        //then see if white can take any pieces because of this
                        int newrating = -20 + whitetradeoff(tempdata, endx + 1, endy - 1, endx - 3, endy + 3);
                        
                        //if this is a worse move
                        if (newrating < rating) {
                            rating = newrating;  //set it
                        }
                        
                    }
                }
                
                //check bottom right
                if (endx - 1 < 6 && endy + 1 < 6) {
                    //if there's a spot to jump and an enemy to take
                    if (data[endx + 1][endy + 3] == 1 && (data[endx][endy + 2] == 2 || data[endx][endy + 2] == 4)) {
                        
                        //temporarily make the move
                        int[][] tempdata = copyarray(data);
                        tempdata[endx + 1][endy + 3] = tempdata[endx + 1][endy - 1];
                        tempdata[endx][endy + 2] = 1;
                        tempdata[endx][endy] = 1;
                        tempdata[endx + 1][endy - 1] = 1;
                
                        //then see if white can take any pieces because of this
                        int newrating = -20 + whitetradeoff(tempdata, endx + 1, endy - 1, endx + 1, endy + 3);
                        
                        //if this is a worse move
                        if (newrating < rating) {
                            rating = newrating;  //set it
                        }
                        
                    }
                }
                
                //check normal
                //temporarily make the move
                int[][] tempdata = copyarray(data);
                tempdata[endx - 1][endy + 1] = tempdata[endx + 1][endy - 1];
                tempdata[endx][endy] = 1;
                tempdata[endx + 1][endy - 1] = 1;
                
                //then see if white can take any pieces because of this
                int newrating = -10 + whitetradeoff(tempdata, endx + 1, endy - 1, endx - 1, endy + 1);
                        
                //if this is a worse move
                if (newrating < rating) {
                    rating = newrating;  //set it
                }
                
            }
            
            //check bottom left
            //if a black piece here can jump
            if ((data[endx - 1][endy + 1] == 3 || data[endx - 1][endy + 1] == 5) && data[endx + 1][endy - 1] == 1) {
                
                //check top left
                if (endx + 1 > 1 && endy - 1 > 1) {
                    //if there's a spot to jump and an enemy to take
                    if (data[endx - 1][endy - 3] == 1 && (data[endx][endy - 2] == 2 || data[endx][endy - 2] == 4)) {
                        
                        //temporarily make the move
                        int[][] tempdata = copyarray(data);
                        tempdata[endx - 1][endy - 3] = tempdata[endx - 1][endy + 1];
                        tempdata[endx][endy - 2] = 1;
                        tempdata[endx][endy] = 1;
                        tempdata[endx - 1][endy + 1] = 1;
                
                        //then see if white can take any pieces because of this
                        int newrating = -20 + whitetradeoff(tempdata, endx - 1, endy + 1, endx - 1, endy - 3);
                        
                        //if this is a worse move
                        if (newrating < rating) {
                            rating = newrating;  //set it
                        }
                        
                    }
                }
                
                //check top right
                if (endx + 1 < 6 && endy - 1 > 1) {
                    //if there's a spot to jump and an enemy to take
                    if (data[endx + 3][endy - 3] == 1 && (data[endx + 2][endy - 2] == 2 || data[endx + 2][endy - 2] == 4)) {
                        
                        //temporarily make the move
                        int[][] tempdata = copyarray(data);
                        tempdata[endx + 3][endy - 3] = tempdata[endx - 1][endy + 1];
                        tempdata[endx + 2][endy - 2] = 1;
                        tempdata[endx][endy] = 1;
                        tempdata[endx - 1][endy + 1] = 1;
                
                        //then see if white can take any pieces because of this
                        int newrating = -20 + whitetradeoff(tempdata, endx - 1, endy + 1, endx + 3, endy - 3);
                        
                        //if this is a worse move
                        if (newrating < rating) {
                            rating = newrating;  //set it
                        }
                        
                    }
                }
                
                //check bottom right if it's a king
                if (data[endx - 1][endy + 1] == 5 && endx + 1 < 6 && endy - 1 < 6) {
                    //if there's a spot to jump and an enemy to take
                    if (data[endx + 3][endy + 1] == 1 && (data[endx + 2][endy] == 2 || data[endx + 2][endy] == 4)) {
                        
                        //temporarily make the move
                        int[][] tempdata = copyarray(data);
                        tempdata[endx + 3][endy + 1] = tempdata[endx - 1][endy + 1];
                        tempdata[endx + 2][endy] = 1;
                        tempdata[endx][endy] = 1;
                        tempdata[endx - 1][endy + 1] = 1;
                
                        //then see if white can take any pieces because of this
                        int newrating = -20 + whitetradeoff(tempdata, endx - 1, endy + 1, endx + 3, endy + 1);
                        
                        //if this is a worse move
                        if (newrating < rating) {
                            rating = newrating;  //set it
                        }
                        
                    }
                }
                
                //check normal
                //temporarily make the move
                int[][] tempdata = copyarray(data);
                tempdata[endx + 1][endy - 1] = tempdata[endx - 1][endy + 1];
                tempdata[endx][endy] = 1;
                tempdata[endx - 1][endy + 1] = 1;
                
                //then see if white can take any pieces because of this
                int newrating = -10 + whitetradeoff(tempdata, endx - 1, endy + 1, endx + 1, endy - 1);
                        
                //if this is a worse move
                if (newrating < rating) {
                    rating = newrating;  //set it
                }
                
            }
            
            //check bottom right
            //if a black piece here can jump
            if ((data[endx + 1][endy + 1] == 3 || data[endx + 1][endy + 1] == 5) && data[endx - 1][endy - 1] == 1) {
                
                //check top left
                if (endx - 1 > 1 && endy - 1 > 1) {
                    //if there's a spot to jump and an enemy to take
                    if (data[endx - 3][endy - 3] == 1 && (data[endx - 2][endy - 2] == 2 || data[endx - 2][endy - 2] == 4)) {
                        
                        //temporarily make the move
                        int[][] tempdata = copyarray(data);
                        tempdata[endx - 3][endy - 3] = tempdata[endx + 1][endy + 1];
                        tempdata[endx - 2][endy - 2] = 1;
                        tempdata[endx][endy] = 1;
                        tempdata[endx + 1][endy + 1] = 1;
                
                        //then see if white can take any pieces because of this
                        int newrating = -20 + whitetradeoff(tempdata, endx + 1, endy + 1, endx - 3, endy - 3);
                        
                        //if this is a worse move
                        if (newrating < rating) {
                            rating = newrating;  //set it
                        }
                        
                    }
                }
                
                //check top right
                if (endx - 1 < 6 && endy - 1 > 1) {
                    //if there's a spot to jump and an enemy to take
                    if (data[endx + 1][endy - 3] == 1 && (data[endx][endy - 2] == 2 || data[endx][endy - 2] == 4)) {
                        
                        //temporarily make the move
                        int[][] tempdata = copyarray(data);
                        tempdata[endx + 1][endy - 3] = tempdata[endx + 1][endy + 1];
                        tempdata[endx][endy - 2] = 1;
                        tempdata[endx][endy] = 1;
                        tempdata[endx + 1][endy + 1] = 1;
                
                        //then see if white can take any pieces because of this
                        int newrating = -20 + whitetradeoff(tempdata, endx + 1, endy + 1, endx + 1, endy - 3);
                        
                        //if this is a worse move
                        if (newrating < rating) {
                            rating = newrating;  //set it
                        }
                        
                    }
                }
                
                //check bottom left if it's a king
                if (data[endx - 1][endy + 1] == 5 && endx - 1 > 1 && endy - 1 < 6) {
                    //if there's a spot to jump and an enemy to take
                    if (data[endx - 3][endy + 1] == 1 && (data[endx - 2][endy] == 2 || data[endx - 2][endy] == 4)) {
                        
                        //temporarily make the move
                        int[][] tempdata = copyarray(data);
                        tempdata[endx - 3][endy + 1] = tempdata[endx + 1][endy + 1];
                        tempdata[endx - 2][endy] = 1;
                        tempdata[endx][endy] = 1;
                        tempdata[endx + 1][endy + 1] = 1;
                
                        //then see if white can take any pieces because of this
                        int newrating = -20 + whitetradeoff(tempdata, endx + 1, endy + 1, endx - 3, endy + 1);
                        
                        //if this is a worse move
                        if (newrating < rating) {
                            rating = newrating;  //set it
                        }
                        
                    }
                }
                
                //check normal
                //temporarily make the move
                int[][] tempdata = copyarray(data);
                tempdata[endx - 1][endy - 1] = tempdata[endx + 1][endy + 1];
                tempdata[endx][endy] = 1;
                tempdata[endx + 1][endy + 1] = 1;
                
                //then see if white can take any pieces because of this
                int newrating = -10 + whitetradeoff(tempdata, endx + 1, endy + 1, endx - 1, endy - 1);
                        
                //if this is a worse move
                if (newrating < rating) {
                    rating = newrating;  //set it
                }
                
            }
            
        }
        
        return rating;
        
    }
    
    
    
    //rates the destination of a piece
    public static int ratedestination(int[][] data, int destx, int desty, int isking) {
        
        int rating = 0;
        
        //if it's not a king, it's very simple
        if (isking == 0) {
            
            //the rating is equal to how far down the piece is going
            rating = desty;
            
        }
        else {  //if it is a king
            
            //if the destination is next to a black piece, give it a rating of 5
            //first check top left
            if (destx > 0 && desty > 0) {
                if (data[destx - 1][desty - 1] == 3 || data[destx - 1][desty - 1] == 5) {
                    
                    rating = 5;
                    
                }
                else if (rating != 5) {  //if it's not next to an enemy, look farther
                    if (destx > 1 && desty > 1) {
                        if (data[destx - 2][desty - 2] == 3 || data[destx - 2][desty - 2] == 5) {
                            
                            rating = 4;
                            
                        }
                        else if (rating < 4) {  //if it's not next to an enemy, look farther
                            if (destx > 2 && desty > 2) {
                                if (data[destx - 3][desty - 3] == 3 || data[destx - 3][desty - 3] == 5) {
                            
                                    rating = 3;
                            
                                }
                            }
                        }
                    }
                }
            }
            
            //then check top right
            if (destx < 7 && desty > 0) {
                if (data[destx + 1][desty - 1] == 3 || data[destx + 1][desty - 1] == 5) {
                    
                    rating = 5;
                    
                }
                else if (rating != 5) {  //if it's not next to an enemy, look farther
                    if (destx < 6 && desty > 1) {
                        if (data[destx + 2][desty - 2] == 3 || data[destx + 2][desty - 2] == 5) {
                            
                            rating = 4;
                            
                        }
                        else if (rating < 4) {  //if it's not next to an enemy, look farther
                            if (destx < 5 && desty > 2) {
                                if (data[destx + 3][desty - 3] == 3 || data[destx + 3][desty - 3] == 5) {
                            
                                    rating = 3;
                            
                                }
                            }
                        }
                    }
                }
            }
            
            //then check bottom left
            if (destx > 0 && desty < 7) {
                if (data[destx - 1][desty + 1] == 3 || data[destx - 1][desty + 1] == 5) {
                    
                    rating = 5;
                    
                }
                else if (rating != 5) {  //if it's not next to an enemy, look farther
                    if (destx > 1 && desty < 6) {
                        if (data[destx - 2][desty + 2] == 3 || data[destx - 2][desty + 2] == 5) {
                            
                            rating = 4;
                            
                        }
                        else if (rating < 4) {  //if it's not next to an enemy, look farther
                            if (destx > 2 && desty < 5) {
                                if (data[destx - 3][desty + 3] == 3 || data[destx - 3][desty + 3] == 5) {
                            
                                    rating = 3;
                            
                                }
                            }
                        }
                    }
                }
            }
            
            //the check bottom right
            if (destx < 7 && desty < 7) {
                if (data[destx + 1][desty + 1] == 3 || data[destx + 1][desty + 1] == 5) {
                    
                    rating = 5;
                    
                }
                else if (rating != 5) {  //if it's not next to an enemy, look farther
                    if (destx < 6 && desty < 6) {
                        if (data[destx + 2][desty + 2] == 3 || data[destx + 2][desty + 2] == 5) {
                            
                            rating = 4;
                            
                        }
                        else if (rating < 4) {  //if it's not next to an enemy, look farther
                            if (destx < 5 && desty < 5) {
                                if (data[destx + 3][desty + 3] == 3 || data[destx + 3][desty + 3] == 5) {
                            
                                    rating = 3;
                            
                                }
                            }
                        }
                    }
                }
            }
            
        }
        
        return rating;
        
    }
    
    
    
    /* calculates multimoves for the white player
    ** There are a lot of parameters for this recursive function, so I'll explain them here
    ** data is needed because it needs to check if a move is legal
    ** moves is the vector of moves for one piece, and it gets filled with moves as it finds them, then returns it
    ** x and y are the point from which it checks for more moves, which changes every time the function calls itself
    ** move is the current list of coordinates in a multi move, this grows by two digits every jump, and gets added to moves when a finished move is found
    ** direction is needed so that the function knows where it came from, and doesn't jump backwards on itself
    ** isking is needed to tell if a piece can jump backwards or not
    */
    public static Vector whitemultimoves(int[][] data, Vector moves, int x, int y, String move, int direction, int isking) {
        
        //check lower left
        if (direction != 3 && x > 1 && y < 6) {  //check bounds
            
            if ((data[x - 1][y + 1] == 3 || data[x - 1][y + 1] == 5) && (data[x - 2][y + 2] == 1)) {  //if there is an enemy to be taken and the destination is a black square
                
                //this is a legal jump, so add it to moves
                move += (x - 2) + "" + (y + 2);  //add the coordinates to the move
                
                moves.add(move);  //add the move
                
                //Then check for more moves from this point
                //but first we make this square temporarily a different color so it doesn't get caught in a loop
                data[x][y] = 0;
                
                //check for more multi moves
                moves = whitemultimoves(data, moves, x - 2, y + 2, move, 0, isking);
                
                //change the square back to black
                data[x][y] = 1;
                
            }
            
        }
        
        //check lower right
        if (direction != 2 && x < 6 && y < 6) {  //check bounds
            
            if ((data[x + 1][y + 1] == 3 || data[x + 1][y + 1] == 5) && (data[x + 2][y + 2] == 1)) {  //if there is an enemy to be taken and the destination is a black square
                
                //this is a legal jump, so add it to moves
                move += (x + 2) + "" + (y + 2);  //add the coordinates to the move
                
                moves.add(move);  //add the move
                
                //Then check for more moves from this point
                //but first we make this square temporarily a different color so it doesn't get caught in a loop
                data[x][y] = 0;
                
                //check for more multi moves
                moves = whitemultimoves(data, moves, x + 2, y + 2, move, 1, isking);
                
                //change the square back to black
                data[x][y] = 1;
                
            }
            
        }
        
        //if it's a king
        if (isking == 1) {
            
            //check upper left
            if (direction != 1 && x > 1 && y > 1) {  //check bounds
                
                if ((data[x - 1][y - 1] == 3 || data[x - 1][y - 1] == 5) && (data[x - 2][y - 2] == 1)) {  //if there is an enemy to be taken and the destination is a black square
                
                    //this is a legal jump, so add it to moves
                    move += (x - 2) + "" + (y - 2);  //add the coordinates to the move

                    moves.add(move);  //add the move

                    //Then check for more moves from this point
                    //but first we make this square temporarily a different color so it doesn't get caught in a loop
                    data[x][y] = 0;

                    //check for more multi moves
                    moves = whitemultimoves(data, moves, x - 2, y - 2, move, 2, isking);

                    //change the square back to black
                    data[x][y] = 1;
                
                }
                
            }
            
            //check upper right
            if (direction != 0 && x < 6 && y > 1) {  //check bounds
                
                if ((data[x + 1][y - 1] == 3 || data[x + 1][y - 1] == 5) && (data[x + 2][y - 2] == 1)) {  //if there is an enemy to be taken and the destination is a black square
                
                    //this is a legal jump, so add it to moves
                    move += (x + 2) + "" + (y - 2);  //add the coordinates to the move

                    moves.add(move);  //add the move

                    //Then check for more moves from this point
                    //but first we make this square temporarily a different color so it doesn't get caught in a loop
                    data[x][y] = 0;

                    //check for more multi moves
                    moves = whitemultimoves(data, moves, x + 2, y - 2, move, 3, isking);

                    //change the square back to black
                    data[x][y] = 1;
                
                }
                
            }
            
        }
        
        return moves;
        
    }
    
    
    
    //does multi moves
    public static int[][] domultimove(int[][] data, int x, int y) {
        
        //this will loop as long as there are moves to be done
        boolean finished = false;
        while (finished == false) {
            
            //first set up a boolean so that only one move is made each loop
            boolean movemade = false;
            
            //next make the exit condition, which is when the final move is made
            //there is only one destination, so we don't have to check movemade yet
            //first check top left
            if (x > 1 && y > 1) {  //if in bounds to take a piece
                
                if ((data[x - 1][y - 1] == 2 || data[x - 1][y - 1] == 4) && data[x - 2][y - 2] == 9) {  //if there is a piece to be taken and it ends in the destination
                    
                    //do the move
                    data[x - 1][y - 1] = 1;  //capture the piece
                    if (data[x][y] == 7) {  //move the piece
                        data[x - 2][y - 2] = 3;
                    }
                    else {
                        data[x - 2][y - 2] = 5;
                    }
                    data[x][y] = 1;  //get rid of moved piece
                    
                    //set to exit loop and stop moves
                    movemade = true;
                    finished = true;
                    
                }
                
            }
            
            //check top right
            if (x < 6 && y > 1) {
                
                if ((data[x + 1][y - 1] == 2 || data[x + 1][y - 1] == 4) && data[x + 2][y - 2] == 9) {  //if there is a piece to be taken and it ends in the destination
                    
                    //do the move
                    data[x + 1][y - 1] = 1;  //capture the piece
                    if (data[x][y] == 7) {  //move the piece
                        data[x + 2][y - 2] = 3;
                    }
                    else {
                        data[x + 2][y - 2] = 5;
                    }
                    data[x][y] = 1;  //get rid of moved piece
                    
                    //set to exit loop and stop moves
                    movemade = true;
                    finished = true;
                    
                }
                
            }
            
            //check bottom left
            if (x > 1 && y < 6) {
                
                if ((data[x - 1][y + 1] == 2 || data[x - 1][y + 1] == 4) && data[x - 2][y + 2] == 9) {  //if there is a piece to be taken and it ends in the destination
                    
                    //do the move
                    data[x - 1][y + 1] = 1;  //capture the piece
                    if (data[x][y] == 7) {  //move the piece
                        data[x - 2][y + 2] = 3;
                    }
                    else {
                        data[x - 2][y + 2] = 5;
                    }
                    data[x][y] = 1;  //get rid of moved piece
                    
                    //set to exit loop and stop moves
                    movemade = true;
                    finished = true;
                    
                }
                
            }
            
            //check bottom right
            if (x < 6 && y < 6) {
                
                if ((data[x + 1][y + 1] == 2 || data[x + 1][y + 1] == 4) && data[x + 2][y + 2] == 9) {  //if there is a piece to be taken and it ends in the destination
                    
                    //do the move
                    data[x + 1][y + 1] = 1;  //capture the piece
                    if (data[x][y] == 7) {  //move the piece
                        data[x + 2][y + 2] = 3;
                    }
                    else {
                        data[x + 2][y + 2] = 5;
                    }
                    data[x][y] = 1;  //get rid of moved piece
                    
                    //set to exit loop and stop moves
                    movemade = true;
                    finished = true;
                    
                }
                
            }
            
            //next is to make moves in steps
            //it checks every direction, and if can result in the destination, it makes the move
            //it keeps doing these moves until the destination is one move away, and it triggers the exit condition
            //we have to check movemade as well as bounds for these ones
            //first check top left
            if (x > 1 && y > 1 && movemade == false) {
                
                //check if there is a piece to be taken and the destination is green
                if ((data[x - 1][y - 1] == 2 || data[x - 1][y - 1] == 4) && data[x - 2][y - 2] == 6) {
                    
                    //check if it can result in getting to the destination
                    if (checknextjump(data, x - 2, y - 2) == true) {
                        
                        //do jump
                        data[x - 1][y - 1] = 1;  //capture piece
                        data[x - 2][y - 2] = data[x][y];  //move the piece, don't need to change it from selected because more moves need to be made
                        data[x][y] = 1;  //get rid of moved piece
                        
                        //change the x and y for other moves
                        x -= 2;
                        y -= 2;
                        
                        //don't do any more moves
                        movemade = true;
                        
                    }
                    
                }
                
            }
            
            //check top right
            if (x < 6 && y > 1 && movemade == false) {
                
                //check if there is a piece to be taken and the destination is green
                if ((data[x + 1][y - 1] == 2 || data[x + 1][y - 1] == 4) && data[x + 2][y - 2] == 6) {
                    
                    //check if it can result in getting to the destination
                    if (checknextjump(data, x + 2, y - 2) == true) {
                        
                        //do jump
                        data[x + 1][y - 1] = 1;  //capture piece
                        data[x + 2][y - 2] = data[x][y];  //move the piece, don't need to change it from selected because more moves need to be made
                        data[x][y] = 1;  //get rid of moved piece
                        
                        //change the x and y for other moves
                        x += 2;
                        y -= 2;
                        
                        //don't do any more moves
                        movemade = true;
                        
                    }
                    
                }
                
            }
            
            //check bottom left
            if (x > 1 && y < 6 && movemade == false) {
                
                //check if there is a piece to be taken and the destination is green
                if ((data[x - 1][y + 1] == 2 || data[x - 1][y + 1] == 4) && data[x - 2][y + 2] == 6) {
                    
                    //check if it can result in getting to the destination
                    if (checknextjump(data, x - 2, y + 2) == true) {
                        
                        //do jump
                        data[x - 1][y + 1] = 1;  //capture piece
                        data[x - 2][y + 2] = data[x][y];  //move the piece, don't need to change it from selected because more moves need to be made
                        data[x][y] = 1;  //get rid of moved piece
                        
                        //change the x and y for other moves
                        x -= 2;
                        y += 2;
                        
                        //don't do any more moves
                        movemade = true;
                        
                    }
                    
                }
                
            }
            
            //check bottom right
            if (x < 6 && y < 6 && movemade == false) {
                
                //check if there is a piece to be taken and the destination is green
                if ((data[x + 1][y + 1] == 2 || data[x + 1][y + 1] == 4) && data[x + 2][y + 2] == 6) {
                    
                    //check if it can result in getting to the destination
                    if (checknextjump(data, x + 2, y + 2) == true) {
                        
                        //do jump
                        data[x + 1][y + 1] = 1;  //capture piece
                        data[x + 2][y + 2] = data[x][y];  //move the piece, don't need to change it from selected because more moves need to be made
                        data[x][y] = 1;  //get rid of moved piece
                        
                        //change the x and y for other moves
                        x += 2;
                        y += 2;
                        
                        //don't need to set movemade as this is the last move in the loop
                        
                    }
                    
                }
                
            }
            
        }  
        
        return data;
        
    }
    
    
    
    //a recursive function that finds if a move is a step towards the destination of a multimove
    public static boolean checknextjump(int[][] data, int x, int y) {
        
        //will return false unless if it finds the destination
        //if there are no jumps, for example on a path that leads to a dead end, it will return false by default
        boolean founddest = false;
        
        //first thing to do is check if the destination is one jump away from x, y
        //if so, return true
        //if not, check recursively
        //if no available jumps, return false
        
        //first check top left
        if (x > 1 && y > 1) {
            
            if ((data[x - 1][y - 1] == 2 || data[x - 1][y - 1] == 4) && data[x - 2][y - 2] == 9) {  //if there is a piece to be taken and it ends in the destination
                
                founddest = true;
                
            }
            
        }
        
        //check top right
        if (x < 6 && y > 1) {
            
            if ((data[x + 1][y - 1] == 2 || data[x + 1][y - 1] == 4) && data[x + 2][y - 2] == 9) {  //if there is a piece to be taken and it ends in the destination
                
                founddest = true;
                
            }
            
        }
        
        //check bottom left
        if (x > 1 && y < 6) {
            
            if ((data[x - 1][y + 1] == 2 || data[x - 1][y + 1] == 4) && data[x - 2][y + 2] == 9) {  //if there is a piece to be taken and it ends in the destination
                
                founddest = true;
                
            }
            
        }
        
        //check bottom right
        if (x < 6 && y < 6) {
            
            if ((data[x + 1][y + 1] == 2 || data[x + 1][y + 1] == 4) && data[x + 2][y + 2] == 9) {  //if there is a piece to be taken and it ends in the destination
                
                founddest = true;
                
            }
            
        }
        
        if (founddest == false) {  //if the destination is not one step from this point
            
            //find any jumps, and run this function recursively on those points
            //first check top left
            if (x > 1 && y > 1) {
                
                //check if there is a piece to be taken and the destination is green
                if ((data[x - 1][y - 1] == 2 || data[x - 1][y - 1] == 4) && data[x - 2][y - 2] == 6) {
                    
                    //set the current square to 10, so that the algorithm doesn't go in circles or fall back on itself, and only goes in direct lines
                    data[x][y] = 10;
                    
                    //then call the function recursively
                    founddest = checknextjump(data, x - 2, y - 2);
                    
                    //then return the current square to green
                    data[x][y] = 6;
                    
                }
                
            }
            
            //check top right
            if (x < 6 && y > 1) {
                
                //check if there is a piece to be taken and the destination is green
                if ((data[x + 1][y - 1] == 2 || data[x + 1][y - 1] == 4) && data[x + 2][y - 2] == 6) {
                    
                    //set the current square to 10, so that the algorithm doesn't go in circles or fall back on itself, and only goes in direct lines
                    data[x][y] = 10;
                    
                    //then call the function recursively
                    founddest = checknextjump(data, x + 2, y - 2);
                    
                    //then return the current square to green
                    data[x][y] = 6;
                    
                }
            }
            
            //check bottom left
            if (x > 1 && y < 6) {
                
                //check if there is a piece to be taken and the destination is green
                if ((data[x - 1][y + 1] == 2 || data[x - 1][y + 1] == 4) && data[x - 2][y + 2] == 6) {
                    
                    //set the current square to 10, so that the algorithm doesn't go in circles or fall back on itself, and only goes in direct lines
                    data[x][y] = 10;
                    
                    //then call the function recursively
                    founddest = checknextjump(data, x - 2, y + 2);
                    
                    //then return the current square to green
                    data[x][y] = 6;
                    
                }
                
            }
            
            //check bottom right
            if (x < 6 && y < 6) {
                
                //check if there is a piece to be taken and the destination is green
                if ((data[x + 1][y + 1] == 2 || data[x + 1][y + 1] == 4) && data[x + 2][y + 2] == 6) {
                    
                    //set the current square to 10, so that the algorithm doesn't go in circles or fall back on itself, and only goes in direct lines
                    data[x][y] = 10;
                    
                    //then call the function recursively
                    founddest = checknextjump(data, x + 2, y + 2);
                    
                    //then return the current square to green
                    data[x][y] = 6;
                    
                }
                
            }
            
        }
        
        return founddest;
        
    }
    
    
    
    //checks if the black player has made a king
    public static int[][] checkblackking(int[][] data) {
        
        //loop through every square in the top row
        for (int x = 0; x < 8; x++) {
            
            if (data[x][0] == 3) {  //if it's a black piece
                
                data[x][0] = 5;  //change it to a king
                
            }
            
        }
        
        return data;
        
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
        Icon selectedpiece = new ImageIcon("Images/Selectedpiece.png");
        Icon selectedking = new ImageIcon("Images/Selectedking.png");
        
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
                else if (data[x][y] == 6) {
                    buttons[x][y].setIcon(green);
                }
                else if (data[x][y] == 7) {
                    buttons[x][y].setIcon(selectedpiece);
                }
                else {
                    buttons[x][y].setIcon(selectedking);
                }
                
            }
        }

    }
    
    
    
    //initializes the gui for the game
    public static JButton[][] initgui(int[][] data) {
        
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
        * 7 = selected black piece
        * 8 = selected black king
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

}
