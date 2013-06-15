package breadboard;

import org.newdawn.slick.Graphics;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Daniel
 */
public abstract class Block
{
    public static final int BLOCK_SIZE = 16;
    
    public static final int EMPTY = -1, WIRE = 0, SOURCE = 1, TRANSISTOR = 2,
            LAMP = 3, CROSS = 4, DIODE = 5;
    
    public static final int UP = 0, RIGHT = 1, DOWN = 2, LEFT = 3;
    
    Board parent;
    int row, col;
    boolean[] powerTo;

    public Block(Board parent, int row, int col)
    {
        this.parent = parent;
        this.row = row;
        this.col = col;
        powerTo = new boolean[4];
        for (int i = 0; i < 4; i++)
            powerTo[i] = false;
    }
    
    public abstract void render(Graphics g, int x, int y);
    
    public abstract void update();
    
    public abstract void activate();
    
    public abstract int getType();
}
