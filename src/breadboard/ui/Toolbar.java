package breadboard.ui;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

/**
 *
 * @author Daniel
 */
public class Toolbar<T extends Item> extends ButtonBar<T>
{
    Image selected;
    int s = 0;

    public Toolbar(int x, int y, boolean vertical, int iconSize, Image background, Image selected)
    {
        super(x, y, vertical, iconSize, background);
        this.selected = selected;
    }

    @Override
    public void renderItem(Graphics g, int i)
    {
        if (i == s)
            drawImage(g, selected, i);
        
        super.renderItem(g, i);
    }
    
    @Override
    public void click(int i)
    {
        super.click(i);
        
        s = i;
    }
    
    public T getSelected()
    {
        try
        {
            return items.get(s);
        }
        catch (Exception e) { return null; }
    }
    
    public void deselect()
    {
        s = -1;
    }
    
}
