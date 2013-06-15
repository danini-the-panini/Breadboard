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

    public Toolbar(int x, int y, int rows, int cols, int iconSize, Image background, Image selected)
    {
        super(x, y, rows, cols, iconSize, background);
        this.selected = selected;
    }

    @Override
    public void renderItem(Graphics g, int index, int i, int j)
    {
        if (index == s)
            drawImage(g, selected, i, j);
        
        super.renderItem(g, index, i, j);
    }
    
    @Override
    public void click(int i)
    {
        super.click(i);
        
        s = i;
    }
    
    public T getSelected()
    {
        return items.get(s);
    }
    
}
