package checkers;

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
        
        printdata(data);
        
    }
    
    
    
    //initializes the gui for the game
    public static void initgui() {
        
        
        
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
