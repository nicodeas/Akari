/**
 * AkariViewer represents an interface for a player of Akari.
 *
 * @author Lyndon While
 * @version 2021
 */
import java.awt.*;
import java.awt.event.*; 

public class AkariViewer implements MouseListener
{    
    private Akari puzzle;    // the internal representation of the puzzle
    private SimpleCanvas sc; // the display window

    private int putCentre;
    private final int tilesize = 30; //tile size of 30 to allow for puzzles up to size 25
    private final int largestBoardSize = 750;

    private final Color light_up =     new Color(255,255,143);
    private final Color clash =        new Color(220, 20, 60);
    private final Color background =   new Color(65,65,65);
    private final Color backFrame =    new Color(30,30,30);
    private final Color unlit =        new Color(80,80,80);
    private final Color button_color = new Color(139,0,0);

    /**
     * Constructor for objects of class AkariViewer.
     * Sets all fields and displays the initial puzzle.
     */
    public AkariViewer(Akari puzzle)
    {
        this.puzzle = puzzle;
        sc = new SimpleCanvas("Akari viewer",750,900,background);
        sc.addMouseListener(this);
        sc.setFont(new Font("Times", Font.PLAIN,20));
        putCentre = (largestBoardSize-(puzzle.getSize()*tilesize))/2;
        displayPuzzle();
        // TODO 10
    }

    public void drawFrame()
    {

        sc.drawRectangle(0 , 0,largestBoardSize,largestBoardSize, backFrame);

    }

    public void drawTile(int r, int c, String s)
    {
        sc.drawRectangle(c*tilesize+putCentre,r*tilesize+putCentre, (c+1)*tilesize+putCentre,(r+1)*tilesize+putCentre, Color.black);
        sc.drawString(s,(int) (tilesize * (c + 0.35))+putCentre, (int) (tilesize * (r + 0.75))+putCentre,Color.white);
    }

    public void drawBulb(int r, int c, Color col)
    {
        sc.drawRectangle(c*tilesize+putCentre,r*tilesize+putCentre, (c+1)*tilesize+putCentre,(r+1)*tilesize+putCentre, light_up);
        sc.drawString("\uD83D\uDCA1",(int) (tilesize * (c + 0.20))+putCentre, (int) (tilesize * (r + 0.75))+putCentre, col);
    }

    public void drawSpace(int r, int c, Color col)
    {
        sc.drawRectangle(c*tilesize+putCentre,r*tilesize+putCentre, (c+1)*tilesize+putCentre,(r+1)*tilesize+putCentre, col);
    }

    public void drawClearButton()
    {
        sc.drawRectangle(0,750,150,900,button_color);
        sc.setFont(new Font("Comic Sans MS", Font.PLAIN,40));
        sc.drawString("CLEAR",10,845, Color.white);
        sc.setFont(new Font("Times", Font.PLAIN,20));
    }

    public void drawCheckButton()
    {
        sc.drawRectangle(600,750,750,900,button_color);
        sc.setFont(new Font("Comic Sans MS", Font.PLAIN,40));
        sc.drawString("CHECK",615,845, Color.white);
        sc.setFont(new Font("Times", Font.PLAIN,20));
    }

    public void drawGrid()
    {
        for(int i = 0;  i<=puzzle.getSize();i++)
        {
            sc.drawLine(0+putCentre,i*tilesize+putCentre,puzzle.getSize()*tilesize+putCentre,i*tilesize+putCentre,Color.black);
            sc.drawLine(i*tilesize+putCentre,0+putCentre,i*tilesize+putCentre,puzzle.getSize()*tilesize+putCentre,Color.black);
        }
    }

    /**
     * Selects from among the provided files in folder Puzzles. 
     * The number xyz selects pxy-ez.txt. 
     */
    public AkariViewer(int n)
    {
        this(new Akari("Puzzles/p" + n / 10 + "-e" + n % 10 + ".txt"));
    }

    /**
     * Uses the provided example file on the LMS page.
     */
    public AkariViewer()
    {
        this(77);
    }

    /**
     * Returns the internal state of the puzzle.
     */
    public Akari getPuzzle()
    {
        // TODO 9a
        return puzzle;
    }

    /**
     * Returns the canvas.
     */
    public SimpleCanvas getCanvas()
    {
        // TODO 9b
        return sc;
    }

    /**
     * Displays the initial puzzle; see the LMS page for a suggested layout. 
     */
    private void displayPuzzle()
    {
        drawFrame();
        for(int i = 0 ;i<puzzle.getSize(); i++)
        {
            for(int j = 0; j<puzzle.getSize();j++)
            {
                if(puzzle.getBoard(i,j)==Space.BLACK) drawTile(i,j,"");
                if(puzzle.getBoard(i,j)==Space.ZERO)  drawTile(i,j,"0");              
                if(puzzle.getBoard(i,j)==Space.ONE)   drawTile(i,j,"1");
                if(puzzle.getBoard(i,j)==Space.TWO)   drawTile(i,j,"2");
                if(puzzle.getBoard(i,j)==Space.THREE) drawTile(i,j,"3");
                if(puzzle.getBoard(i,j)==Space.FOUR)  drawTile(i,j,"4");

                if(puzzle.getBoard(i,j)==Space.EMPTY&& puzzle.canSeeBulb(i,j)){
                    drawSpace(i,j,light_up);
                }
                if(puzzle.getBoard(i,j)==Space.EMPTY && !puzzle.canSeeBulb(i,j)){
                    drawSpace(i,j,unlit);
                }

                if(puzzle.getBoard(i,j)==Space.BULB){
                    drawBulb(i,j,Color.black);
                }
                if(puzzle.getBoard(i,j)==Space.BULB && puzzle.canSeeBulb(i,j)){
                    drawBulb(i,j,clash);
                }
            }
        }
        drawCheckButton();
        drawClearButton();
        drawGrid();
        // TODO 11
    }

    /**
     * Performs a left click on the square at r,c if the indices are legal, o/w does nothing. 
     * Updates both puzzle and the display. 
     */
    public void leftClick(int r, int c)
    {
        if(puzzle.isLegal(r,c)){
            puzzle.leftClick(r,c);   
        }
        // TODO 12
    }

    // TODO 13
    public void mousePressed (MouseEvent e) {}

    public void mouseClicked (MouseEvent e) {}

    public void mouseReleased(MouseEvent e) 
    {
        int x = e.getX();
        int y = e.getY();
        int size = puzzle.getSize();
        if(x>=putCentre && x<=size*tilesize+putCentre&&y>=putCentre&&y<=size*tilesize+putCentre){
            leftClick((int)((e.getY()- putCentre)/tilesize), (int)((e.getX()- putCentre)/tilesize));
        }

        if(x>0&&x<150&&y>750&&y<900) {
            puzzle.clear();
            sc.drawRectangle(0,0,750,1000,background);
        }

        if(x>600&&x<750&&y>750&&y<900) {
            sc.drawRectangle(0,0,750,1000,background);

            if(!puzzle.isSolution().equals("\u2713\u2713\u2713")) sc.setFont(new Font("Comic Sans MS", Font.PLAIN,40));
            else sc.setFont(new Font("Times", Font.PLAIN,40));
            sc.drawString(puzzle.isSolution(),185,845,light_up);
            sc.setFont(new Font("Times", Font.PLAIN,20));
        }        
        displayPuzzle();
    }

    public void mouseEntered (MouseEvent e) {}

    public void mouseExited  (MouseEvent e) {}
}
