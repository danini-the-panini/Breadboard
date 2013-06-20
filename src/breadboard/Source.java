package breadboard;

import breadboard.face.Renderer;

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
        g.drawImage(Breadboard.source, x, y, on ? 0xff0000 : 0x000000);
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
