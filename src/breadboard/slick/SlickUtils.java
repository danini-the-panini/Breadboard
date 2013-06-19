/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package breadboard.slick;

import breadboard.face.FaceException;
import breadboard.face.Sprite;
import breadboard.face.Utils;
import breadboard.slick.SlickImage;

/**
 *
 * @author Daniel
 */
public class SlickUtils implements Utils
{

    @Override
    public Sprite loadImage(String ref)
            throws FaceException
    {
        return new SlickImage(ref);
    }
    
}
