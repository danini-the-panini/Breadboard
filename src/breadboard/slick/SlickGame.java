/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package breadboard.slick;

import breadboard.face.FaceException;
import breadboard.face.Game;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

/**
 *
 * @author Daniel
 */
public class SlickGame extends BasicGame
{
    private SlickGraphics graphics = new SlickGraphics(null);
    
    private Game game;

    public SlickGame(String title, Game game)
    {
        super(title);
        this.game = game;
    }
    
    public void start() throws SlickException
    {
        AppGameContainer container = new AppGameContainer(this, 1280, 720, false);
        container.setShowFPS(false);

        container.start();
    }

    @Override
    public void init(GameContainer container) throws SlickException
    {
        try
        {
            game.init(new SlickUtils());
        } catch (FaceException ex)
        {
            throw new SlickException(ex.getMessage(), ex);
        }
    }

    @Override
    public void update(GameContainer container, int delta) throws SlickException
    {
        try
        {
            game.update(delta);
        } catch (FaceException ex)
        {
            throw new SlickException(ex.getMessage(), ex);
        }
    }

    @Override
    public void render(GameContainer container, Graphics g) throws SlickException
    {
        graphics.setGraphics(g);
        try
        {
            game.render(container.getWidth(), container.getHeight(), graphics);
        } catch (FaceException ex)
        {
            throw new SlickException(ex.getMessage(), ex);
        }
    }

    @Override
    public void mouseDragged(int oldx, int oldy, int newx, int newy)
    {
        game.mouseDragged(oldx, oldy, newx, newy);
    }

    @Override
    public void mouseMoved(int oldx, int oldy, int newx, int newy)
    {
        game.mouseMoved(oldx, oldy, newx, newy);
    }

    @Override
    public void mousePressed(int button, int x, int y)
    {
        game.mousePressed(button, x, y);
    }

    @Override
    public void mouseReleased(int button, int x, int y)
    {
        game.mouseReleased(button, x, y);
    }

    @Override
    public void keyPressed(int key, char c)
    {
        game.keyPressed(key, c);
    }

    @Override
    public void keyReleased(int key, char c)
    {
        game.keyReleased(key, c);
    }
    
}
