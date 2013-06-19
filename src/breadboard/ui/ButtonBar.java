/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package breadboard.ui;

import breadboard.face.Renderer;
import breadboard.face.Sprite;
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
    boolean vertical;
    Sprite background;
    
    boolean visible = true;
    
    ArrayList<T> items = new ArrayList<>();
    ArrayList<ButtonBarListener> listeners = new ArrayList<>();

    public ButtonBar(int x, int y, boolean vertical, int iconSize, Sprite background)
    {
        this.x = x;
        this.y = y;
        this.vertical = vertical;
        this.background = background;
        this.iconSize = iconSize;
    }
    
    public void add(T item)
    {
        items.add(item);
    }
    
    public void addListener(ButtonBarListener listener)
    {
        listeners.add(listener);
    }
    
    public void render(Renderer g)
    {
        if (!visible) return;
        
        int len = items.size();
        
        g.fillRect(x, y, (vertical ? 1 : len) * iconSize,
                (vertical ? len : 1) * iconSize, background, 0, 0);
        
        for (int i = 0; i < len; i++)
        {
            renderItem(g, i);
        }
    }
    
    public void renderItem(Renderer g, int i)
    {
        Sprite icon = items.get(i).icon;
        drawImage(g, icon, i);
    }
    
    public void drawImage(Renderer g, Sprite icon, int index)
    {
        int i = vertical ? index : 0;
        int j = vertical ? 0 : index;
        
        g.drawImage(icon, x+j*iconSize, y+i*iconSize, x+j*iconSize+iconSize,
                y+i*iconSize+iconSize, 0, 0, icon.getWidth(), icon.getHeight());
    }
    
    public boolean clicked(int mx, int my)
    {
        if (!visible) return false;
        
        int len = items.size();
        
        if (mx < x || my < y ||
                mx > (vertical ? 1 : len)*iconSize + x ||
                my > (vertical ? len : 1)*iconSize + y)
            return false;
        
        int row = (my-y)/iconSize;
        int col = (mx-x)/iconSize;
        
        int i = vertical ? row : col;
        
        //if (i < 0 || i >= items.size()) return false;
        
        click(i);
        return true;
    }
    
    public boolean keyPressed(char c)
    {
        if (!visible) return false;
        
        for (int i = 0; i < items.size(); i++)
        {
            if (c == items.get(i).mnemonic && items.get(i).mnemonic != '\0')
            {
                click(i);
                return true;
            }
        }
        return false;
    }
    
    public void click(int i)
    {
        for (ButtonBarListener listener :listeners)
        {
            listener.buttonBarEvent(this, items.get(i));
        }
    }
    
    public int getWidth()
    {
        if (!visible) return 0; // TODO: should I?
        
        if (vertical) return iconSize;
        return iconSize*items.size();
    }
    
    public int getHeight()
    {
        if (!visible) return 0; // TODO: should I?
        
        if (vertical) return iconSize*items.size();
        return iconSize;
    }

    public void setVisible(boolean visible)
    {
        this.visible = visible;
    }

    public boolean isVisible()
    {
        return visible;
    }
}
