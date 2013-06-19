package breadboard;

import breadboard.face.Renderer;
import org.newdawn.slick.Color;

/**
 *
 * @author Daniel
 */
public class Wire extends Block
{
    boolean powered = false;

    @Override
    public void render(Renderer g, int x, int y)
    {
        Color colour = powered ? Color.red : Color.black;
        
        int n = 0;
        for (int i = 0; i < 4; i++)
        {
            if (parent.get(row,col,i) != null)
            {
                g.drawImage(Breadboard.wire[i], x, y, colour);
                n++;
            }
        }
        if (n == 0)
            g.drawImage(Breadboard.dot, x, y);
    }

    @Override
    public void update()
    {
        powered = false;
        
        for (int i = 0; i < 4; i++)
        {
            powerTo[i] = true;
        }
        for (int i = 0; i < 4; i++)
        {
            Block b = parent.get(row,col,i);
            if (b != null && b.powerTo[(i+2)%4])
            {
                powered = true;
                powerTo[i] = false;
            }
        }
        if (!powered)
        {
            for (int i = 0; i < 4; i++)
            {
                powerTo[i] = false;
            }
        }
    }

    @Override
    public void activate()
    {
    }
    
    @Override
    public int getType()
    {
        return WIRE;
    }
    
    
}
