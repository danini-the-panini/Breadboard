package breadboard;

import breadboard.face.FaceException;
import breadboard.face.Game;
import breadboard.face.Renderer;
import breadboard.face.Sprite;
import breadboard.face.Utils;
import breadboard.ui.Toolbar;
import breadboard.ui.ButtonBar;
import breadboard.ui.ComponentItem;
import breadboard.ui.Item;
import breadboard.ui.ButtonBarListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import org.newdawn.slick.Input;

/**
 *
 * @author Daniel
 */
public class Breadboard extends Game implements ButtonBarListener, Serializable
{
    static Sprite block, selected, dot;
    static Sprite[] wire;
    static Sprite source, transistor, diode, lamp;
    static Sprite[] cross;
    
    File saveLocation = null;
    boolean consistent = true;
    
    int srow, scol;
    int ox, oy;
    
    Board board;
    
    ButtonBar<Item> buttonbar;
    Toolbar<Item> commonToolbar;
    Toolbar<ComponentItem> componentToolbar;
    ComponentItem[] componentTools;
    Class[] components = {
        Wire.class,
        Source.class,
        Lamp.class,
        Cross.class,
        Transistor.class,
        Diode.class
    };
    
    ArrayList<ButtonBar> ui = new ArrayList<>();
    
    Item cursorItem, panItem, editItem;
    
    Item newItem, openItem, saveItem;
    
    public Breadboard(int rows, int cols)
    {
        //super("Breadboard");
        
        board = new Board(rows,cols);
    }

    @Override
    public void init(Utils utils) throws FaceException
    {
        //container.setShowFPS(false);
        
        block = utils.loadImage("res/block.png");
        selected = utils.loadImage("res/selected.png");
        dot = utils.loadImage("res/dot.png");
        wire = new Sprite[4];
        for (int i = 0; i < wire.length; i++)
        {
            wire[i] = utils.loadImage("res/wire_"+i+".png");
        }
        source = utils.loadImage("res/source.png");
        transistor = utils.loadImage("res/transistor.png");
        diode = utils.loadImage("res/diode.png");
        lamp = utils.loadImage("res/lamp.png");
        cross = new Sprite[2];
        for (int i = 0; i < cross.length; i++)
        {
            cross[i] = utils.loadImage("res/cross_"+i+".png");
        }
        
        Sprite tbBackground = utils.loadImage("res/toolbar.png");
        Sprite tbSelected = utils.loadImage("res/toolbar_selected.png");
        
        buttonbar = new ButtonBar<>(0, 0, false, 32, tbBackground);
        
        newItem = new Item(utils.loadImage("res/new.png"));
        openItem = new Item(utils.loadImage("res/open.png"));
        saveItem = new Item(utils.loadImage("res/save.png"));
        
        buttonbar.add(newItem);
        buttonbar.add(openItem);
        buttonbar.add(saveItem);
        
        commonToolbar = new Toolbar<>(0, buttonbar.getHeight()+8, true, 32, tbBackground, tbSelected);
        
        cursorItem = new Item(utils.loadImage("res/cursor.png"), '.');
        panItem = new Item(utils.loadImage("res/pan.png"), ' ');
        editItem = new Item(utils.loadImage("res/edit.png"), 'e');
        
        commonToolbar.add(cursorItem);
        commonToolbar.add(panItem);
        commonToolbar.add(editItem);
        
        componentToolbar = new Toolbar<>(0, buttonbar.getHeight()
                +commonToolbar.getHeight()+16, true, 32,
                tbBackground, tbSelected);
        
        componentTools = new ComponentItem[components.length];
        for (int i = 0; i < components.length; i++)
        {
            String compname = components[i].getSimpleName().toLowerCase();
            componentTools[i] = new ComponentItem(utils.loadImage("res/"+ compname + "_tb.png"), compname.charAt(0),
                    components[i]);
            componentToolbar.add(componentTools[i]);
        }
        
        componentToolbar.setVisible(false);
        
        ui.add(buttonbar);
        ui.add(commonToolbar);
        ui.add(componentToolbar);
        
        for (ButtonBar bar :ui)
        {
            bar.addListener(this);
        }
    }

    @Override
    public void update(int delta) throws FaceException
    {
        board.update(delta);
    }
    
    @Override
    public void render(int width, int height, Renderer g) throws FaceException
    {
        g.fillRect(0, 0, (ox+width) > board.cols*Block.BLOCK_SIZE ? (-ox+board.cols*Block.BLOCK_SIZE) : width,
                (oy+height) > board.data.length*Block.BLOCK_SIZE ? (-oy+board.data.length*Block.BLOCK_SIZE) : height,
                block, ox < 0 ? ox : ox%Block.BLOCK_SIZE+Block.BLOCK_SIZE,
                oy < 0 ? oy : oy%Block.BLOCK_SIZE+Block.BLOCK_SIZE);
        
        int x, y;
        for (int i = 0; i < board.rows; i++)
        {
            for (int j = 0; j < board.cols; j++)
            {
                x = j*Block.BLOCK_SIZE-ox;
                y = i*Block.BLOCK_SIZE-oy;
                
                if (i == srow && j == scol)
                    g.drawImage(selected, x, y);
                
                if (board.data[i][j] != null)
                {
                    board.data[i][j].render(g, x, y);
                }
            }
        }
        
        
        for (int i = ui.size()-1; i >= 0; i--)
        {
            ui.get(i).render(g);
        }
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
        
        mouseClicked(button, x, y);
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
            boolean clicked = false;
            for (ButtonBar bar :ui)
            {
                if (bar.clicked(x, y))
                {
                    clicked = true;
                    break;
                }
            }
            
            if (!clicked)
            {
                Item currentTool = commonToolbar.getSelected();
                
                if (currentTool == cursorItem && board.data[row][col] != null)
                {
                    board.data[row][col].activate();
                    
                    consistent = false;
                }
                else if (currentTool == editItem)
                {
                    Class type = componentToolbar.getSelected().getComponent();
                    
                    try
                    {
                        Block b = (Block)(type.newInstance());
                        b.init(board, row, col);
                        board.data[row][col] = b;
                        
                        consistent = false;
                    }
                    catch (InstantiationException | IllegalAccessException e)
                    {
                        e.printStackTrace(System.err);
                    }
                }
            }
        }
        else if (button == 1 && board.data[row][col] != null)
        {
            Block b = board.data[row][col];
            board.data[row][col] = null;
        }
    }

    @Override
    public void mouseDragged(int oldx, int oldy, int newx, int newy)
    {
        if (commonToolbar.getSelected() == panItem)
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
        boolean clicked = false;
        for (ButtonBar bar :ui)
        {
            clicked = bar.keyPressed(c) || clicked;
        }
        
        if (key == Input.KEY_LSHIFT)
            shift = true;
    }

    @Override
    public void keyReleased(int key, char c)
    {
        
        if (key == Input.KEY_LSHIFT)
            shift = false;
    }

    @Override
    public void buttonBarEvent(ButtonBar bar, Item source)
    {
        if (bar == commonToolbar)
            componentToolbar.setVisible(source == editItem);
        
        if (source == newItem)
        {
            if (confirm())
            {
                newBoard();
            }
        }
        else if (source == openItem)
        {
            if (confirm())
            {
                load();
            }
        }
        else if (source == saveItem)
        {
            save();
        }
    }
    
    public boolean confirm()
    {
        if (consistent) return true;
        
        int confirm = JOptionPane.showConfirmDialog(null, "Save current work?", "Confirm?",
                JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
        
        switch (confirm)
        {
            case JOptionPane.YES_OPTION:
                return save();
            case JOptionPane.NO_OPTION:
                break;
            default:
                return false;
        }
        return true;
    }
    
    public void newBoard()
    {
        board = new Board(board.rows, board.cols); // TODO: ask user for size?
        
        saveLocation = null;
        consistent = true;
    }
    
    public boolean save()
    {
        if (saveLocation == null)
        {
            JFileChooser chooser = new JFileChooser(".");
            chooser.showSaveDialog(null);
            saveLocation = chooser.getSelectedFile();
        }
        
        if (saveLocation == null) return false;
        
        try (ObjectOutputStream out
                = new ObjectOutputStream(new FileOutputStream(saveLocation)))
        {
            out.writeObject(board);
            consistent = true;
        }
        catch (IOException ioe)
        {
            JOptionPane.showMessageDialog(null, ioe.toString(), "I/O Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        return true;
    }
    
    public void load()
    {
        JFileChooser chooser = new JFileChooser(".");
        chooser.showOpenDialog(null);
        File loadLocation = chooser.getSelectedFile();
        
        if (loadLocation == null) return;
        
        try (ObjectInputStream in
                = new ObjectInputStream(new FileInputStream(loadLocation)))
        {
            Board loadedBoard = (Board)(in.readObject());
            if (loadedBoard != null)
            {
                board = loadedBoard;
                saveLocation = loadLocation;
                consistent = true;
            }
        }
        catch (ClassNotFoundException | IOException ioe)
        {
            JOptionPane.showMessageDialog(null, "Error reading file.", "I/O Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
