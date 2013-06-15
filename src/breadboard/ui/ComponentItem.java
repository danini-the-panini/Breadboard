/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package breadboard.ui;

import org.newdawn.slick.Image;

/**
 *
 * @author Daniel
 */
public class ComponentItem extends Item
{
    Class component;

    public ComponentItem(Image icon, Class component)
    {
        super(icon);
        this.component = component;
    }

    public ComponentItem(Image icon, char mnemonic, Class component)
    {
        super(icon, mnemonic);
        this.component = component;
    }

    public Class getComponent()
    {
        return component;
    }
    
}
