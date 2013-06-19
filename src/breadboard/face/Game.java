/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package breadboard.face;

/**
 *
 * @author Daniel
 */
public abstract class Game
{
    protected Utils utils;
    
    public abstract void init(Utils utils) throws FaceException;
    
    public abstract void update(int delta) throws FaceException;
    
    public abstract void render(int width, int height, Renderer g)
            throws FaceException;
    
    public void mouseMoved(int oldx, int oldy, int newx, int newy)
    { }
    
    public void mousePressed(int button, int x, int y)
    { }
    
    public void mouseReleased(int button, int x, int y)
    { }
    
    public void mouseClicked(int button, int x, int y)
    { }
    
    public void mouseDragged(int oldx, int oldy, int newx, int newy)
    { }
    
    public void keyPressed(int key, char c)
    { }
    
    public void keyReleased(int key, char c)
    { }
    
}
