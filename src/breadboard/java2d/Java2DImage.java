/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package breadboard.java2d;

import breadboard.face.FaceException;
import breadboard.face.Sprite;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author Daniel
 */
public class Java2DImage implements Sprite
{
    private BufferedImage image;

    public Java2DImage(String ref)
            throws FaceException
    {
        try
        {
            image = ImageIO.read(new File(ref));
        }
        catch (IOException ex)
        {
            throw new FaceException(ex);
        }
    }

    public BufferedImage getImage()
    {
        return image;
    }

    @Override
    public int getWidth()
    {
        return image.getWidth();
    }

    @Override
    public int getHeight()
    {
        return image.getHeight();
    }

    @Override
    public void setRotation(float angle)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
