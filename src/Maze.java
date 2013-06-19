import java.io.*;
import java.util.*;
import java.awt.*;
import java.lang.Math;
public class Maze
{
    
    public static int ROWS =10;
    public static int COLUMNS =10;
    public static Point start = new Point(1,1);
    public static Point end = new Point(4,7);
    int[][] board;
    boolean wall[][];
    ArrayList<Point> pathArray = new ArrayList<Point>(); 
    
    public Maze(String fileName)
    {
        board = new int[ROWS][COLUMNS];
        wall =new boolean[ROWS][COLUMNS];
        String s;
        
        try
        {
            Scanner m = new Scanner(new FileReader(fileName));
            for(int x=0; x<ROWS; x++)
            {
                s=m.nextLine();
                for(int y=0; y<COLUMNS; y++)
                {
                   board[x][y] = (int)(s.charAt(y))%2;
                   System.out.print(board[x][y]);
                   if(board[x][x]==1)
                       wall[x][y]=true;
                   else if(board[x][y]==0)
                        wall[x][y]=false;
                }
                System.out.println();
            }
                       
                   
            
            
            
        }
        catch (FileNotFoundException e) 
        {
            System.out.println("Error: Cannot open file for reading:");
        } 
        catch (NoSuchElementException e) 
        {
            System.out.println("Error: EOF encountered, file may be corrupt");
        } 
        catch (IOException e) 
        {
            System.out.println("Error: Cannot read from file");
        }
        
    }
    public int[][] getOnesZeros(){return board;}
    public boolean[][] getWalls(){return wall;}
    public int getValue(int row, int column){return board[row][column];}
    public boolean getBool(int row, int column){return wall[row][column];}
    public void setValue(int x, int y, int val){board[x][y]=val;}

    private boolean valid (Point p)
    {
        int row = (int)p.getX();
        int column = (int)p.getY();
        boolean result = false;
        // check if cell is in the bounds of the matrix
        if ((row >= 0) && (row < board.length) && (column >= 0) && (column < board[row].length))
        {
            // check if cell is not blocked and not previously tried
            if (board[row][column] == 0)
                result = true;
        }
           return result;
    }
    public boolean pathExists(Point start, Point end)
    {
        Point right = new Point((int)end.getX()+1, (int)end.getY());
        Point left = new Point((int)end.getX()-1, (int)end.getY());
        Point up = new Point((int)end.getX(), (int)end.getY()+1);
        Point down = new Point((int)end.getX(), (int)end.getY()-1);
        boolean done = false;
        Point temp;
        
        
        if (valid (end))
        {
            board[(int)end.getX()][(int)end.getY()] = 3; // this cell has been tried
            if ((end.getX() == start.getX()) && (end.getY() == start.getY()))
                done = true; // the maze is solved
            else
            {
                done = pathExists(start, down); // down
                if (!done)
                done = pathExists(start, right); // right
                if (!done)
                done = pathExists(start, up); // up
                if (!done)
                done = pathExists(start, left); // left
            }
            if(done)
            {
                temp = new Point((int)end.getX(),(int)end.getY());
                pathArray.add(temp);
            }
            
        }
        return done; 
    }
    
    public ArrayList pathFrom(Point start, Point end)
    {
        Point right = new Point((int)end.getX()+1, (int)end.getY());
        Point left = new Point((int)end.getX()-1, (int)end.getY());
        Point up = new Point((int)end.getX(), (int)end.getY()+1);
        Point down = new Point((int)end.getX(), (int)end.getY()-1);
        
        boolean done = false;
        int counter=0;
        
        if (valid (end))
        {
            board[(int)end.getX()][(int)end.getY()] = 3; // this cell has been tried
            if ((end.getX() == start.getX()) && (end.getY() == start.getY()))
                done = true; // the maze is solved
            else
            {
                
                done = pathExists(start, down); // down
                if (!done)
                done = pathExists(start, right); // right
                if (!done)
                done = pathExists(start, up); // up
                if (!done)
                done = pathExists(start, left); // left
            }
                if(done)
                {   
                    
                    board[(int)end.getX()][(int)end.getY()] = 7;
 
                }
            
            
        }
        return pathArray;
        
    }
    public void printPath()
    {
        for(int x=pathArray.size(); x>0; x--)
            System.out.println(pathArray.get(x-1));
    }
    public String toString ()
    {
        String result = "\n";
        for (int row=0; row < board.length; row++)
        {
            for (int column=0; column < board[row].length; column++)
                if(board[row][column]==1)
                {
                    result += "1" +"";
                }
                else if(board[row][column]==3)
                {
                    result += "3" +"";
                }
                else
                    result += "7" +"";
            result += "\n";
        }
        return result;
    }


}
