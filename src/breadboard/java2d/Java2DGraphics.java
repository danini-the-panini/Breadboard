/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package breadboard.java2d;

import breadboard.face.Renderer;
import java.awt.Graphics2D;
import java.awt.image.ImageObserver;

/**
 *
 * @author Daniel
 */
public class Java2DGraphics implements Renderer<Java2DImage>
{
    private Graphics2D g;
    private ImageObserver observer;

    public Java2DGraphics(Graphics2D g)
    {
        this.g = g;
    }

    public void setGraphics(Graphics2D g)
    {
        this.g = g;
    }

    public void setObserver(ImageObserver observer)
    {
        this.observer = observer;
    }

    @Override
    public void drawImage(Java2DImage image, int x, int y)
    {
        g.drawImage(image.getImage(), x, y, observer);
    }

    @Override
    public void drawImage(Java2DImage image, int x, int y, Object color)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void drawImage(Java2DImage image, int dx1, int dy1, int dx2, int dy2, int sx1, int sy1, int sx2, int sy2)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void fillRect(int x, int y, int width, int height)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void fillRect(int x, int y, int width, int height, Java2DImage pattern, int ox, int oy)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
