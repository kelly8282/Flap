package kelly8282.flappybird;

import java.awt.*;
import java.awt.image.ImageObserver;

public class Block implements Intersectable {

    public int width;
    public int height;
    public int xPos;
    public int yPos;

    private Color color; //Can be null if using image

    public Block() 
    {
        setWidth(30);
        setHeight(30);
        setPos(100, 100);
        setColor(Color.WHITE);
    }

    public Block(int width, int height) 
    {
        setWidth(width);
        setHeight(height);
        setPos(100, 100);
        setColor(Color.WHITE);
    }

    public Block(int width, int height, int xPos, int yPos) 
    {
        setWidth(width);
        setHeight(height);
        setPos(xPos, yPos);
        setColor(Color.WHITE);
    }

    public Block(int width, int height, int xPos, int yPos, Color color) 
    {
        setWidth(width);
        setHeight(height);
        setPos(xPos, yPos);
        setColor(color);
    }

    public void draw(Graphics block) 
    {
        block.setColor(getColor());
        block.fillRect(getxPos(), getyPos(), getWidth(), getHeight());
    }

    public void draw(Graphics block, Color color) 
    {
        block.setColor(color);
        block.fillRect(getxPos(), getyPos(), getWidth(), getHeight());
    }

    //Used to draw images on blocks
    public void draw(Graphics block, Image image, ImageObserver observer) 
    {
        block.drawImage(image, getxPos(), getyPos(), getWidth(), getHeight(), observer);
    }

    public int getWidth() 
    {
        return width;
    }

    public void setWidth(int width) 
    {
        this.width = width;
    }

    public int getHeight() 
    {
        return height;
    }

    public void setHeight(int height) 
    {
        this.height = height;
    }

    public int getxPos()
    {
        return xPos;
    }

    public void setxPos(int xPos) 
    { 
        this.xPos = xPos; 
    }

    public int getyPos() 
    {
        return yPos;
    }

    public void setyPos(int yPos)
    {
        this.yPos = yPos;
    }

    public void setPos (int xPos, int yPos) 
    { 
        setxPos(xPos); 
        setyPos(yPos);
    }

    public Color getColor() 
    { 
        return color; 
    }

    public void setColor(Color color) 
    { 
        this.color = color; 
    }

    //Check if the object is the current block
    public boolean equals(Object object) 
    {
        if (object instanceof Block)
        {
            Block b = (Block)object;
            if ( b.getxPos() == getxPos() && b.getyPos() == getyPos()
                    && b.getHeight() == getHeight() && b.getWidth() == getWidth()
                    && b.getColor() == getColor() )
            {
                return true;
            }
            else
            {
                return false;
            }
        }
        return false;
    }

    public boolean isInside(Point p) 
    { 
        return isInside(p.x, p.y); 
    }

    //Check if point is inside the block
    //Useful for simulating buttons and checking mouseclick events
    public boolean isInside(int xPos, int yPos) 
    {
        int w = this.getWidth();
        int h = this.getHeight();

        // check if the values are negative
        if ((w | h) < 0) 
        { 
            return false; 
        }


        int x = this.getxPos();
        int y = this.getyPos();

        //make sure that the point's coords are not less than the block's start pos
        if (xPos < x || yPos < y) 
        { 
            return false; 
        }

        w += x;
        h += y;
        if ( ((w < x || w > xPos) && (h < y || h > yPos))  == true)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    //Used to check if 2 blocks intersect, useful for checking block collision
    public boolean isIntersecting(Block block) 
    {
        int tw = getWidth();
        int th = getHeight();
        int blockWidth = block.getWidth();
        int blockHeight = block.getHeight();

        //Make sure all values to compare are positive
        if (blockWidth <= 0 || blockHeight <= 0 || tw <= 0 || th <= 0) 
        {
            return false;
        }

        int tx = getxPos();
        int ty = getyPos();
        int blockX = block.getxPos();
        int blockY = block.getyPos();
        blockWidth += blockX;
        blockHeight += blockY;
        tw += tx;
        th += ty;
        //Check if they intersect
        if ( ((blockWidth < blockX || blockWidth > tx) &&
                (blockHeight < blockY || blockHeight > ty) &&
                (tw < tx || tw > blockX) &&
                (th < ty || th > blockY)) )
        {
            return true;
        }
        else
        {
            return false;
        }
    }
}