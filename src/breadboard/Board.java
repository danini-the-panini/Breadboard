package breadboard;

import breadboard.ui.Toolbar;
import static breadboard.Block.DOWN;
import static breadboard.Block.LEFT;
import static breadboard.Block.RIGHT;
import static breadboard.Block.UP;
import breadboard.ui.ButtonBar;
import breadboard.ui.ComponentItem;
import breadboard.ui.Item;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

/**
 *
 * @author Daniel
 */
public class Board extends BasicGame
{
    Image block, selected, dot;
    Image[] wire;
    Image source, transistor, diode, lamp;
    Image[] cross;
    
    int srow, scol;
    int ox, oy;
    
    Block[][] board;
    
    ButtonBar<Item> buttonbar;
    Toolbar<ComponentItem> componentToolbar, commonToolbar;
    ComponentItem[] componentTools;
    Class[] components = {
        Wire.class,
        Source.class,
        Lamp.class,
        Cross.class,
        Transistor.class,
        Diode.class
    };
    
    public Board(int rows, int cols)
    {
        super("Breadboard");
        
        board = new Block[rows][cols];
        
        for (int i = 0; i < rows; i++)
            for (int j = 0; j < cols; j++)
            {
                board[i][j] = null;
            }
    }
    
    public Block get(int row, int col)
    {
        if (row < 0 || col < 0 || row >= board.length || col >= board[0].length)
            return null;
        
        return board[row][col];
    }
    
    public Block get(int row, int col, int dir)
    {
        switch (dir)
        {
            case UP:
                return get(row-1,col);
            case RIGHT:
                return get(row,col+1);
            case DOWN:
                return get(row+1, col);
            case LEFT:
                return get(row,col-1);
        }
        return null;
    }

    @Override
    public void init(GameContainer container) throws SlickException
    {
        container.setShowFPS(false);
        
        block = new Image("res/block.png");
        selected = new Image("res/selected.png");
        dot = new Image("res/dot.png");
        wire = new Image[4];
        for (int i = 0; i < wire.length; i++)
        {
            wire[i] = new Image("res/wire_"+i+".png");
        }
        source = new Image("res/source.png");
        transistor = new Image("res/transistor.png");
        diode = new Image("res/diode.png");
        lamp = new Image("res/lamp.png");
        cross = new Image[2];
        for (int i = 0; i < cross.length; i++)
        {
            cross[i] = new Image("res/cross_"+i+".png");
        }
        componentToolbar = new Toolbar(0, 32, 6, 1, 32, new Image("res/toolbar.png"),
                new Image("res/toolbar_selected.png"));
        
        componentTools = new ComponentItem[components.length];
        for (int i = 0; i < components.length; i++)
        {
            String compname = components[i].getSimpleName().toLowerCase();
            componentTools[i] = new ComponentItem(new Image("res/"+ compname + "_tb.png"), compname.charAt(0),
                    components[i]);
            componentToolbar.add(componentTools[i]);
        }
    }

    @Override
    public void update(GameContainer container, int delta) throws SlickException
    {
        for (int i = 0; i < board.length; i++)
        {
            for (int j = 0; j < board[i].length; j++)
            {
                if (board[i][j] != null)
                {
                    board[i][j].update();
                }
            }
        }
    }
    
    @Override
    public void render(GameContainer container, Graphics g) throws SlickException
    {
        g.fillRect(0, 0, (ox+container.getWidth()) > board[0].length*Block.BLOCK_SIZE ? (-ox+board[0].length*Block.BLOCK_SIZE) : container.getWidth(),
                (oy+container.getHeight()) > board.length*Block.BLOCK_SIZE ? (-oy+board.length*Block.BLOCK_SIZE) : container.getHeight(),
                block, ox < 0 ? ox : ox%Block.BLOCK_SIZE+Block.BLOCK_SIZE,
                oy < 0 ? oy : oy%Block.BLOCK_SIZE+Block.BLOCK_SIZE);
        
        int x, y;
        for (int i = 0; i < board.length; i++)
        {
            for (int j = 0; j < board[i].length; j++)
            {
                x = j*Block.BLOCK_SIZE-ox;
                y = i*Block.BLOCK_SIZE-oy;
                
                if (i == srow && j == scol)
                    g.drawImage(selected, x, y);
                
                if (board[i][j] != null)
                {
                    board[i][j].render(g, x, y);
                }
            }
        }
        
        componentToolbar.render(g);
    }
    
    boolean shift = false;
    
    boolean[] buttons = new boolean[32];

    @Override
    public void mouseMoved(int oldx, int oldy, int newx, int newy)
    {
        srow = (newy+oy)/Block.BLOCK_SIZE;
        scol = (newx+ox)/Block.BLOCK_SIZE;
    }

    @Override
    public void mousePressed(int button, int x, int y)
    {
        buttons[button] = true;
        if (!shift)
        {
            mouseClicked(button, x, y);
        }
    }

    @Override
    public void mouseReleased(int button, int x, int y)
    {
        buttons[button] = false;
    }
    
    public void mouseClicked(int button, int x, int y)
    {
        int row = (y+oy)/Block.BLOCK_SIZE;
        int col = (x+ox)/Block.BLOCK_SIZE;
        
        if (button == 0)
        {
            if (!componentToolbar.clicked(x, y))
            {
                Class type = componentToolbar.getSelected().getComponent();
                
                if (board[row][col] != null && board[row][col].getClass() == type)
                {
                    board[row][col].activate();
                }
                else
                {
                    try
                    {
                        Block b = (Block)(type.newInstance());
                        b.init(this, row, col);
                        board[row][col] = b;
                    }
                    catch (InstantiationException | IllegalAccessException e)
                    {
                        e.printStackTrace(System.err);
                    }
                }
            }
        }
        else if (button == 1 && board[row][col] != null)
        {
            Block b = board[row][col];
            board[row][col] = null;
        }
    }

    @Override
    public void mouseDragged(int oldx, int oldy, int newx, int newy)
    {
        if (shift || buttons[2])
        {
            ox += oldx-newx;
            oy += oldy-newy;
        }
        else
        {
            int nrow = (newy+oy)/Block.BLOCK_SIZE;
            int ncol = (newx+ox)/Block.BLOCK_SIZE;
            
            if (nrow != srow || ncol != scol)
            {
                srow = nrow;
                scol = ncol;
                mouseClicked(buttons[0] ? 0 : (buttons[1] ? 1 : -1) , newx, newy);
            }
        }
    }

    @Override
    public void keyPressed(int key, char c)
    {
        componentToolbar.keyPressed(c);
        
        if (key == Input.KEY_LSHIFT)
            shift = true;
    }

    @Override
    public void keyReleased(int key, char c)
    {
        
        if (key == Input.KEY_LSHIFT)
            shift = false;
    }
}
