/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package breadboard.ui;

import java.util.ArrayList;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

/**
 *
 * @author Daniel
 */
public class ButtonBar<T extends Item>
{
    int x, y, width, height, iconSize;
    int rows, cols;
    Image background;
    
    ArrayList<T> items = new ArrayList<>();
    ArrayList<ToolbarListener> listeners = new ArrayList<>();

    public ButtonBar(int x, int y, int rows, int cols, int iconSize, Image background)
    {
        this.x = x;
        this.y = y;
        this.rows = rows;
        this.cols = cols;
        this.background = background;
        this.iconSize = iconSize;
    }
    
    public void add(T item)
    {
        items.add(item);
    }
    
    public void addListener(ToolbarListener listener)
    {
        listeners.add(listener);
    }
    
    public void render(Graphics g)
    {
        g.fillRect(x, y, cols*iconSize, rows*iconSize, background, 0, 0);
        
        for (int i = 0; i < rows; i++)
        {
            for (int j = 0; j < cols; j++)
            {
                int index = i*cols+j;
                
                renderItem(g, index, i, j);
            }
        }
    }
    
    public void renderItem(Graphics g, int index, int i, int j)
    {
        Image icon = items.get(index).icon;
        drawImage(g, icon, i, j);
    }
    
    public void drawImage(Graphics g, Image icon, int i, int j)
    {
        g.drawImage(icon, x+j*iconSize, y+i*iconSize, x+j*iconSize+iconSize,
                y+i*iconSize+iconSize, 0, 0, icon.getWidth(), icon.getHeight());
    }
    
    public boolean clicked(int mx, int my)
    {
        if (mx < x || my < y || mx > cols*iconSize + x || my > rows*iconSize + y)
            return false;
        
        int row = (my-y)/iconSize;
        int col = (mx-x)/iconSize;
        
        int i = row*cols+col;
        
        if (i < 0 || i >= items.size()) return false;
        
        click(i);
        return true;
    }
    
    public boolean keyPressed(char c)
    {
        for (int i = 0; i < items.size(); i++)
        {
            if (c == items.get(i).mnemonic)
            {
                click(i);
                return true;
            }
        }
        return false;
    }
    
    public void click(int i)
    {
        for (ToolbarListener listener :listeners)
        {
            listener.toolbarEvent(items.get(i));
        }
    }
}
