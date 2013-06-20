/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package breadboard.java2d;

import breadboard.face.FaceException;
import breadboard.face.Sprite;
import breadboard.face.Utils;

/**
 *
 * @author Daniel
 */
public class Java2DUtils implements Utils
{

    @Override
    public Sprite loadImage(String ref) throws FaceException
    {
        return new Java2DImage(ref);
    }
    
}
