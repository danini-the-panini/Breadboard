/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package breadboard;

import static breadboard.Block.DOWN;
import static breadboard.Block.LEFT;
import static breadboard.Block.RIGHT;
import static breadboard.Block.UP;
import java.io.Serializable;

/**
 *
 * @author Daniel
 */
public class Board implements Serializable
{
    public Block[][] data;
    
    public int rows, cols;

    public Board(Block[][] data)
    {
        this.data = data;
        
        rows = data.length;
        cols = data[0].length;
    }

    public Board(int rows, int cols)
    {
        this.rows = rows;
        this.cols = cols;
        
        data = new Block[rows][cols];
        for (Block[] b :data)
        {
            for (int i = 0; i < b.length; i++)
                b[i] = null;
        }
    }
    
    public Block get(int row, int col)
    {
        if (row < 0 || col < 0 || row >= data.length || col >= data[0].length)
            return null;
        
        return data[row][col];
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
    
    public void update(int delta)
    {
        
        for (int i = 0; i < data.length; i++)
        {
            for (int j = 0; j < data[i].length; j++)
            {
                if (data[i][j] != null)
                {
                    data[i][j].update();
                }
            }
        }
    }
    
}
