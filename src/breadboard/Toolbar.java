package breadboard;

import java.util.ArrayList;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

/**
 *
 * @author Daniel
 */
public class Toolbar
{
    int x, y, width, height, iconSize;
    int rows, cols;
    Image background, selected;
    int s = 0;
    
    ArrayList<Image> icons = new ArrayList<>();

    public Toolbar(int x, int y, int rows, int cols, int iconSize, Image background, Image selected)
    {
        this.x = x;
        this.y = y;
        this.rows = rows;
        this.cols = cols;
        this.background = background;
        this.selected = selected;
        this.iconSize = iconSize;
    }
    
    public void render(Graphics g)
    {
        g.fillRect(x, y, cols*iconSize, rows*iconSize, background, 0, 0);
        
        for (int i = 0; i < rows; i++)
        {
            for (int j = 0; j < cols; j++)
            {
                int index = i*cols+j;
                
                if (index == s)
                    drawImage(g, selected, i, j);
                
                Image icon = icons.get(index);
                drawImage(g, icon, i, j);
            }
        }
    }
    
    public void drawImage(Graphics g, Image icon, int i, int j)
    {
        g.drawImage(icon, j*iconSize, i*iconSize, j*iconSize+iconSize,
                i*iconSize+iconSize, 0, 0, icon.getWidth(), icon.getHeight());
    }
    
    public boolean clicked(int mx, int my)
    {
        if (mx < x || my < y || mx > cols*iconSize + x || my > rows*iconSize + y)
            return false;
        
        int row = (my-y)/iconSize;
        int col = (mx-x)/iconSize;
        
        int i = row*cols+col;
        
        if (i < 0 || i >= icons.size()) return false;
        
        s = i;
        
        return true;
    }
    
    public boolean keyPressed(char c)
    {
        if (c >= '1' && c <= '6')
        {
            s = c-'1';
            return true;
        }
        else return false;
    }
    
    public int getSelected()
    {
        return s;
    }
    
}
