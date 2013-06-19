package breadboard.ui;

import breadboard.face.Sprite;
import org.newdawn.slick.Image;

/**
 *
 * @author Daniel
 */
public class Item
{
    Sprite icon;
    char mnemonic;

    public Item(Sprite icon)
    {
        this(icon,'\0');
    }

    public Item(Sprite icon, char mnemonic)
    {
        this.icon = icon;
        this.mnemonic = mnemonic;
    }
    
}
