package breadboard;

import breadboard.slick.SlickGame;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;

/**
 *
 * @author Daniel
 */
public class Main
{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        
        try
        {
            Breadboard board = new Breadboard(256, 256);
            
            SlickGame game = new SlickGame("Breadboard", board);
            
            AppGameContainer container = new AppGameContainer(game, 1280, 720, false);
            container.setShowFPS(false);
            
            container.start();
        } catch (SlickException ex)
        {
            ex.printStackTrace(System.err);
        }
    }
}
