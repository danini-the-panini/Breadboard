/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package breadboard.face;

/**
 *
 * @author Daniel
 */
public interface Renderer<T extends Sprite>
{
    public abstract void drawImage(T image, int x, int y);
    public abstract void drawImage(T image, int x, int y, int color);
    public abstract void drawImage(T image, int dx1, int dy1, int dx2, int dy2,
            int sx1, int sy1, int sx2, int sy2);
    
    public abstract void fillRect(int x, int y, int width, int height);
    public abstract void fillRect(int x, int y, int width, int height,
            T pattern, int ox, int oy);
}
