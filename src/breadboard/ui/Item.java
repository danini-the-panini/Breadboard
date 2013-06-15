package breadboard.ui;

import org.newdawn.slick.Image;

/**
 *
 * @author Daniel
 */
public class Item
{
    Image icon;
    char mnemonic;

    public Item(Image icon)
    {
        this(icon,'\0');
    }

    public Item(Image icon, char mnemonic)
    {
        this.icon = icon;
        this.mnemonic = mnemonic;
    }
    
}
