package breadboard;

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
public class Breadboard extends BasicGame implements ButtonBarListener, Serializable
{
    static Image block, selected, dot;
    static Image[] wire;
    static Image source, transistor, diode, lamp;
    static Image[] cross;
    
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
        super("Breadboard");
        
        board = new Board(rows,cols);
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
        
        Image tbBackground = new Image("res/toolbar.png");
        Image tbSelected = new Image("res/toolbar_selected.png");
        
        buttonbar = new ButtonBar<>(0, 0, false, 32, tbBackground);
        
        newItem = new Item(new Image("res/new.png"));
        openItem = new Item(new Image("res/open.png"));
        saveItem = new Item(new Image("res/save.png"));
        
        buttonbar.add(newItem);
        buttonbar.add(openItem);
        buttonbar.add(saveItem);
        
        commonToolbar = new Toolbar<>(0, buttonbar.getHeight()+8, true, 32, tbBackground, tbSelected);
        
        cursorItem = new Item(new Image("res/cursor.png"), '.');
        panItem = new Item(new Image("res/pan.png"), ' ');
        editItem = new Item(new Image("res/edit.png"), 'e');
        
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
            componentTools[i] = new ComponentItem(new Image("res/"+ compname + "_tb.png"), compname.charAt(0),
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
    public void update(GameContainer container, int delta) throws SlickException
    {
        board.update(delta);
    }
    
    @Override
    public void render(GameContainer container, Graphics g) throws SlickException
    {
        g.fillRect(0, 0, (ox+container.getWidth()) > board.cols*Block.BLOCK_SIZE ? (-ox+board.cols*Block.BLOCK_SIZE) : container.getWidth(),
                (oy+container.getHeight()) > board.data.length*Block.BLOCK_SIZE ? (-oy+board.data.length*Block.BLOCK_SIZE) : container.getHeight(),
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
