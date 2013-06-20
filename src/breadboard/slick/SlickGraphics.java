/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package breadboard.slick;

import breadboard.face.Renderer;
import breadboard.face.Sprite;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

/**
 *
 * @author Daniel
 */
public class SlickGraphics implements Renderer<SlickImage>
{
    private Graphics g;

    public SlickGraphics(Graphics g)
    {
        this.g = g;
    }

    public void setGraphics(Graphics g)
    {
        this.g = g;
    }

    @Override
    public void drawImage(SlickImage image, int x, int y)
    {
        g.drawImage(image.getImage(), x, y);
    }

    @Override
    public void drawImage(SlickImage image, int x, int y, int color)
    {
        g.drawImage(image.getImage(), x, y, new Color(color));
    }

    @Override
    public void drawImage(SlickImage image, int dx1, int dy1, int dx2, int dy2, int sx1, int sy1, int sx2, int sy2)
    {
        g.drawImage(image.getImage(), dx1, dy1, dx2, dy2, sx1, sy1, sx2, sy2);
    }

    @Override
    public void fillRect(int x, int y, int width, int height)
    {
        g.fillRect(x, y, width, height);
    }

    @Override
    public void fillRect(int x, int y, int width, int height, SlickImage pattern, int ox, int oy)
    {
        g.fillRect(x, y, width, height, pattern.getImage(), ox, oy);
    }
    
}
