/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package breadboard.slick;

import breadboard.face.FaceException;
import breadboard.face.Sprite;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/**
 *
 * @author Daniel
 */
public class SlickImage implements Sprite
{
    private Image image;

    public SlickImage(String ref)
            throws FaceException
    {
        try
        {
            image = new Image(ref);
        } catch (SlickException ex)
        {
            throw new FaceException(ex);
        }
    }

    @Override
    public int getHeight()
    {
        return image.getHeight();
    }

    @Override
    public int getWidth()
    {
        return image.getWidth();
    }

    public Image getImage()
    {
        return image;
    }

    @Override
    public void setRotation(float angle)
    {
        image.setRotation(angle);
    }
    
}
