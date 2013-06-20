package breadboard;

import breadboard.face.Renderer;

/**
 *
 * @author Daniel
 */
public class Lamp extends Block
{
    public static final transient int[] LAMP_COLOURS = { 0xff0000, 0x00ff00, 0x0000ff};
    
    int color = 0;
    boolean powered = false;

    @Override
    public void render(Renderer g, int x, int y)
    {
        g.drawImage(Breadboard.lamp, x, y, powered ? LAMP_COLOURS[color] : 0x000000);
    }

    @Override
    public void update()
    {
        powered = false;
        for (int i = 0; i < 4; i++)
        {
            Block b = parent.get(row, col, i);
            if (b != null && b.powerTo[(i+2)%4])
            {
                powered = true;
                break;
            }
        }
    }

    @Override
    public void activate()
    {
        color = (color+1)%LAMP_COLOURS.length;
    }

    @Override
    public int getType()
    {
        return LAMP;
    }
    
}
