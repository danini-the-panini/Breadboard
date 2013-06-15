package breadboard;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

/**
 *
 * @author Daniel
 */
public class Diode extends Block
{
    int dir = UP;
    boolean powered = false;

    public Diode(Board parent, int row, int col)
    {
        super(parent, row, col);
    }
    

    @Override
    public void render(Graphics g, int x, int y)
    {
        parent.diode.setRotation(90*dir);
        g.drawImage(parent.diode, x, y, powered ? Color.red : Color.black);
    }

    @Override
    public void update()
    {
        powered = false;
        Block b = parent.get(row, col, (dir+2)%4);
        powered = (b != null && b.powerTo[dir]);
        
        powerTo[dir] = powered;
    }

    @Override
    public void activate()
    {
        powered = false;
        powerTo[dir] = false;
        dir = (dir+1)%4;
    }

    @Override
    public int getType()
    {
        return DIODE;
    }
    
}
