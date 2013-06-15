package breadboard;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

/**
 *
 * @author Daniel
 */
public class Cross extends Block
{
    boolean[] powered;

    public Cross(Board parent, int row, int col)
    {
        super(parent, row, col);
        powered = new boolean[2];
        powered[0] = powered[1] = false;
    }

    @Override
    public void render(Graphics g, int x, int y)
    {
        for (int i = 0; i < powered.length; i++)
        {
            g.drawImage(parent.cross[i], x, y, powered[i] ? Color.red : Color.black);
        }
    }

    @Override
    public void update()
    {
        powered[0] = powered[1] = false;
        
        for (int i = 0; i < 4; i++)
        {
            powerTo[i] = true;
        }
        for (int i = 0; i < 4; i++)
        {
            Block b = parent.get(row, col, i);
            if (b != null && b.powerTo[(i+2)%4])
            {
                powered[i%2] = true;
                powerTo[i] = false;
            }
        }
        for (int i = 0; i < 2; i++)
        if (!powered[i])
        {
            powerTo[i] = powerTo[i+2] = false;
        }
    }

    @Override
    public void activate()
    {
    }

    @Override
    public int getType()
    {
        return CROSS;
    }
    
}
