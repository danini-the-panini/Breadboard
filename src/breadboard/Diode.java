package breadboard;

import breadboard.face.Renderer;

/**
 *
 * @author Daniel
 */
public class Diode extends Block
{
    int dir = UP;
    boolean powered = false;

    @Override
    public void render(Renderer g, int x, int y)
    {
        Breadboard.diode.setRotation(90*dir);
        g.drawImage(Breadboard.diode, x, y, powered ? 0xff0000 : 0x000000);
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
