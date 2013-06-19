package breadboard.ui;

import breadboard.face.Renderer;
import breadboard.face.Sprite;

/**
 *
 * @author Daniel
 */
public class Toolbar<T extends Item> extends ButtonBar<T>
{
    Sprite selected;
    int s = 0;

    public Toolbar(int x, int y, boolean vertical, int iconSize,
            Sprite background, Sprite selected)
    {
        super(x, y, vertical, iconSize, background);
        this.selected = selected;
    }

    @Override
    public void renderItem(Renderer g, int i)
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
