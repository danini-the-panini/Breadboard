/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package breadboard.java2d;

import breadboard.face.FaceException;
import breadboard.face.Game;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author Daniel
 */
public class Java2DGame extends JPanel
{
    private Java2DGraphics graphics = new Java2DGraphics(null);
    
    private Game game;
    
    private JFrame frame;
    
    private Timer timer;

    public Java2DGame(String title, Game g)
    {
        this.game = g;
        
        frame = new JFrame(title);
        
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        frame.setSize(1280, 720);
        
        timer = new Timer();
        
        MouseAdapter mouse = new MouseAdapter() {

            int x = -1, y = -1;
            
            @Override
            public void mouseDragged(MouseEvent e)
            {
                game.mouseDragged(x, y, e.getX(), e.getY());
                x = e.getX();
                y = e.getY();
            }

            @Override
            public void mouseMoved(MouseEvent e)
            {
                game.mouseMoved(x, y, e.getX(), e.getY());
                x = e.getX();
                y = e.getY();
            }

            @Override
            public void mousePressed(MouseEvent e)
            {
                game.mousePressed(e.getButton(), e.getX(), e.getY());
                x = e.getX();
                y = e.getY();
            }
            
            @Override
            public void mouseReleased(MouseEvent e)
            {
                game.mouseReleased(e.getButton(), e.getX(), e.getY());
                x = e.getX();
                y = e.getY();
            }
        };
        
        addMouseListener(mouse);
        addMouseMotionListener(mouse);
        
        KeyAdapter keyboard = new KeyAdapter() {

            @Override
            public void keyPressed(KeyEvent e)
            {
                game.keyPressed(e.getKeyCode(), e.getKeyChar());
            }

            @Override
            public void keyReleased(KeyEvent e)
            {
                game.keyReleased(e.getKeyCode(), e.getKeyChar());
            }
            
        };
        
        frame.addKeyListener(keyboard);
    }
    
    public void start() throws FaceException
    {
        game.init(new Java2DUtils());
        
        frame.add(this);
        
        frame.setVisible(true);
        
        //this.createBufferStrategy(2);
        
        lastUpdate = System.nanoTime();
        
        timer.scheduleAtFixedRate(updater, 0, 16);
    }
    
    long lastUpdate;
    
    private TimerTask updater = new TimerTask() {

        @Override
        public void run()
        {
            long thisUpdate = System.nanoTime();
            long delta = thisUpdate - lastUpdate;
            lastUpdate = thisUpdate;
            
            try
            {
                game.update((int)(delta/1000000));
                repaint();
            } catch (FaceException ex)
            {
                throw new RuntimeException(ex);
            }
            
        }
    };

    @Override
    public void paint(Graphics g)
    {
        try
        {
            BufferedImage backBuffer = new BufferedImage(getWidth(), getHeight(),
                    BufferedImage.OPAQUE);
            Graphics2D bufferGraphics = backBuffer.createGraphics();
            
            graphics.setGraphics(bufferGraphics);
            
            bufferGraphics.setBackground(Color.BLACK);
            bufferGraphics.clearRect(0, 0, getWidth(), getHeight());
            
            game.render(getWidth(), getHeight(), graphics);
            
            bufferGraphics.dispose();
            
            ((Graphics2D)g).drawImage(backBuffer, null, 0, 0);
        } catch (FaceException ex)
        {
            ex.printStackTrace(System.err);
        }
    }
}
