/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package breadboard.java2d;

import breadboard.face.Renderer;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.TexturePaint;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.awt.image.WritableRaster;

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
    public void drawImage(Java2DImage image, int x, int y, int color)
    {
        Color c = new Color(color);
        
        BufferedImage img = image.getImage();
        
        WritableRaster raster = img.copyData(null);
        
        for (int xx = 0; xx < raster.getWidth(); xx++)
            for (int yy = 0; yy < raster.getHeight(); yy++)
            {
                float[] pixel = raster.getPixel(xx, yy, new float[3]);
                
                pixel[0] *= (float)(c.getRed())/256.0f;
                pixel[1] *= (float)(c.getGreen())/256.0f;
                pixel[2] *= (float)(c.getBlue())/256.0f;
                
                pixel[0] = 1.0f;
                pixel[1] = 0.0f;
                pixel[2] = 1.0f;
                
                raster.setPixel(xx, yy, pixel);
            }
        
        BufferedImage newImage = new BufferedImage(img.getColorModel(), raster, true, null);
        
        g.drawImage(newImage, x, y, observer);
    }

    @Override
    public void drawImage(Java2DImage image, int dx1, int dy1, int dx2, int dy2, int sx1, int sy1, int sx2, int sy2)
    {
        g.drawImage(image.getImage(), dx1, dy1, dx2, dy2, sx1, sy1, sx2, sy2, observer);
    }

    @Override
    public void fillRect(int x, int y, int width, int height)
    {
        g.fillRect(x, y, width, height);
    }

    @Override
    public void fillRect(int x, int y, int width, int height, Java2DImage pattern, int ox, int oy)
    {
        Paint oldPaint = g.getPaint();
        g.setPaint(new TexturePaint(pattern.getImage(), new Rectangle2D.Float(x, y, pattern.getWidth(), pattern.getHeight())));
        g.fillRect(x, y, width, height);
        g.setPaint(oldPaint);
    }
    
}
