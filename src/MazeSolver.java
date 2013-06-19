import java.util.*;
import java.awt.*; 
import java.awt.event.*; 
import java.io.File;
import javax.swing.*; 
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

public class MazeSolver extends JFrame implements ActionListener
{
    public static int ROWS = 10;
    public static int COLUMNS = 10;
    int mouseCounter=0;
    Maze m;
    JButton[][] buttons = new JButton[ROWS][COLUMNS];
    JButton start, findPath, pathExists;
    JButton begin, end;
    int i,j;
    int a,b;
    Point startloc;
    Point endloc;
    ArrayList<Point> path = new ArrayList<Point>();
    boolean locationsSelected =false;
    
    JFileChooser chooser;
    Container c;
    public MazeSolver(String title)
    {
        super(title);
        setSize(800,700);
        setResizable(false);
        setLayout(null);
        
        c = getContentPane();
        JPanel p = new JPanel();
        p.setLayout(new GridLayout(ROWS, COLUMNS));
        p.setSize(600,600);
        p.setLocation(0,0);
        
        
        
        
        start = new JButton("Start");
        start.setSize(200,230);
        start.setOpaque(true);
        start.addActionListener(this);
        start.setLocation(600,0);
        c.add(start);
        
        findPath = new JButton("Find Path");
        findPath.setSize(200,230);
        findPath.setOpaque(true);
        
        findPath.setLocation(600,231);
        c.add(findPath);
        
        pathExists = new JButton("Path Exists");
        pathExists.setSize(200,230);
        pathExists.setOpaque(true);
        
        pathExists.setLocation(600,462);
        c.add(pathExists);
        
        begin = new JButton();
        begin.setSize(50,50);
        begin.setOpaque(true);
        begin.addActionListener(this);
        begin.setBorder(new LineBorder(Color.BLACK, 1));
        
        end = new JButton();
        end.setSize(50,50);
        end.setOpaque(true);
        end.addActionListener(this);
        end.setBorder(new LineBorder(Color.BLACK, 1));
        
        pathExists.addActionListener(this);
        findPath.addActionListener(this);
        
         String workingdir = System.getProperty("user.dir");
         chooser = new JFileChooser(new File(workingdir));

         for(int x=0; x<ROWS; x++){
            for(int y=0; y<COLUMNS; y++)
            {
                    buttons[x][y] = new JButton();
                    buttons[x][y].setSize(50,50);
                    buttons[x][y].setOpaque(true);
                    buttons[x][y].setBackground(Color.GREEN);
                    buttons[x][y].setBorder(new LineBorder(Color.BLACK, 1));
                    p.add(buttons[x][y]);
            }

        }

        c.add(p);


    }
    public void reset()
    {
        locationsSelected = false;
        mouseCounter=0;
        updateButtons();
        pathExists.addActionListener(this);
        
       // i=0;j=0;a=0;b=0;
    }
    public void updateButtons()
    {
        for(int x=0; x<ROWS; x++)
            for(int y=0; y<COLUMNS; y++)
            {
                updateButton(x,y);
                

            }
            
    }
    public void updateButton(int x, int y)
    {
    	  buttons[x][y].removeActionListener(this);
        if(m.getValue(x,y)==1){
                    buttons[x][y].setBackground(Color.GRAY);
        						
        }
        else if(m.getValue(x,y)==0){
                    buttons[x][y].setBackground(Color.GREEN);
                    buttons[x][y].addActionListener(this);
        }
         
    }
    public void actionPerformed(ActionEvent e)
    {
        
        if(e.getSource()==start)
        {
            
            int returnVal = chooser.showOpenDialog(this); 
            if (returnVal == JFileChooser.APPROVE_OPTION) { 
            System.out.println("You chose to open this file: " + 
                       chooser.getSelectedFile().getName()); 
            }
            m=new Maze(chooser.getSelectedFile().getName());
         
         
            reset();
         
        }
        else if(e.getSource()==findPath)
        {
            if(locationsSelected){
                Point p1 = new Point(i,j);
                Point p2 = new Point(a,b);
                path = m.pathFrom(p1,p2);
                for(int x=0; x<path.size(); x++)
                {
                 buttons[(int)path.get(x).getX()][(int)path.get(x).getY()].setBackground(Color.YELLOW);
                }
                
                
                pathExists.removeActionListener(this);
            }
            else
                {
                    JOptionPane.showMessageDialog(null, "Please select starting and ending locations", "Find Path",JOptionPane.PLAIN_MESSAGE);

                }
           
        }
        else if(e.getSource()==pathExists)
        {
        		if(locationsSelected){
            Point p1 = new Point(i,j);
            Point p2 = new Point(a,b);
            boolean ans = m.pathExists(p1,p2);
            if (ans)
            	JOptionPane.showMessageDialog(null, "A Path Exist", "Path Exists",JOptionPane.PLAIN_MESSAGE);
            else
            	JOptionPane.showMessageDialog(null, "A Path Does Not Exist", "Path Does Not Exist",JOptionPane.PLAIN_MESSAGE);
        		}
        		else{
        			JOptionPane.showMessageDialog(null, "Please select starting and ending locations", "Find Path",JOptionPane.PLAIN_MESSAGE);
        		}
            
        }
        else
        {   
            for(int x=0; x<ROWS; x++){
                for(int y=0; y<COLUMNS; y++)
                {
                    if(e.getSource()==buttons[x][y] && m.getValue(x,y)==0)
                    {   
                    		System.out.println("**********");
                        if(mouseCounter%2==0)
                        {
                            
                            buttons[x][y].setBackground(Color.BLUE);
                            updateButton(i,j);
                            i=x;
                            j=y;
                            
                           
                        }
                        else if(mouseCounter%2==1)
                        {
                            buttons[x][y].setBackground(Color.RED);
                            updateButton(a,b);
                            locationsSelected=true;
                            a=x;
                            b=y;
                            
                        }
                        mouseCounter++;
                    }
                }
            }
        }
            
        
    }
    
     public static void main(String args[]){
        JFrame frame = new MazeSolver("MAZE");
        frame.setVisible(true);
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);


    }

  
}
