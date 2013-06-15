/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package breadboard;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

/**
 *
 * @author Daniel
 */
public class Transistor extends Block
{
    boolean flip = false;
    boolean on = false;

    @Override
    public void render(Graphics g, int x, int y)
    {
        for (int i = 0; i < 4; i++)
        {
            Block b = parent.get(row,col,i);
            if (b != null)
            {
                g.drawImage(parent.wire[i], x, y,
                        (b.powerTo[(i+2)%4] || powerTo[i]) ? Color.red : Color.black);
            }
        }
        
        g.drawImage(parent.transistor, x, y, flip ? Color.red : Color.black);
        
    }

    @Override
    public void update()
    {
        Block x = null, y = null, t = null;
        int xi = 0, yi = 0, ti = 0;
        for (int i = 0; i < 4; i++)
        {
            Block b = parent.get(row,col,i);
            if (b == null)
            {
                ti = (i+2)%4;
                xi = (i+1)%4;
                yi = (i+3)%4;
                t = parent.get(row,col,ti);
                x = parent.get(row,col,xi);
                y = parent.get(row,col,yi);
                break;
            }
        }
        
        for (int i = 0; i < 4; i++)
        {
            powerTo[i] = false;
        }
        if (t == null || x == null || y == null) // incorrect number of wires
        {
            return;
        }
        
        on = t.powerTo[(ti+2)%4] != flip;
        
        if (on)
        {
            if (x.powerTo[(xi+2)%4])
            {
                powerTo[yi] = true;
                powerTo[xi] = false;
            }
            else if (y.powerTo[(yi+2)%4])
            {
                powerTo[xi] = true;
                powerTo[yi] = false;
            }
        }
    }

    @Override
    public void activate()
    {
        flip = !flip;
    }

    @Override
    public int getType()
    {
        return TRANSISTOR;
    }
    
}
