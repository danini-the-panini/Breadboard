/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package breadboard.face;

/**
 *
 * @author Daniel
 */
public class FaceException extends Exception
{

    /**
     * Creates a new instance of
     * <code>FaceException</code> without detail message.
     */
    public FaceException()
    {
    }

    /**
     * Constructs an instance of
     * <code>FaceException</code> with the specified detail message.
     *
     * @param msg the detail message.
     */
    public FaceException(String msg)
    {
        super(msg);
    }

    public FaceException(Throwable cause)
    {
        super(cause);
    }
    
}
