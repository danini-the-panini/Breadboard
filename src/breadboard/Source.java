package breadboard;

import breadboard.face.Renderer;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

/**
 *
 * @author Daniel
 */
public class Source extends Block
{
    boolean on = true;

    @Override
    public void render(Renderer g, int x, int y)
    {
        g.drawImage(Breadboard.source, x, y, on ? Color.red : Color.black);
    }

    @Override
    public void update()
    {
        for (int i = 0; i < 4; i++)
        {
            powerTo[i] = on;
        }
    }
    

    @Override
    public void activate()
    {
        on = !on;
    }

    @Override
    public int getType()
    {
        return SOURCE;
    }
    
}
