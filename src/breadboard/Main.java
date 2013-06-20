package breadboard;

import breadboard.face.FaceException;
import breadboard.java2d.Java2DGame;

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
        Breadboard board = new Breadboard(256, 256);
        
//        try
//        {
//            SlickGame game = new SlickGame("Breadboard", board);
//            
//            game.start();
//        } catch (SlickException ex)
//        {
//            ex.printStackTrace(System.err);
//        }
        
        
        try
        {
            Java2DGame game = new Java2DGame("Breadboard", board);
            
            game.start();
        } catch (FaceException ex)
        {
            ex.printStackTrace(System.err);
        }
    }
}
