package kelly8282.flappybird;

import java.awt.*;

public class Bird extends Block
{

    private String name;

    public Bird(String name) 
    {
        setName(name);
        setColor(Color.YELLOW);
    }

    public Bird(String name, Color color) 
    {
        setName(name);
        setColor(color);
    }

    public Bird(String name, int width, int height, int xPos, int yPos) 
    {
        super(width, height, xPos, yPos);
        setName(name);
        setColor(Color.YELLOW);
    }

    public Bird(String name, int width, int height, int xPos, int yPos, Color color) 
    {
        super(width, height, xPos, yPos, color);
        setName(name);
    }

    //Override for equals(Object object) method in block
    public boolean equals(Object object) 
    {
        if (object instanceof Bird)
        {
            Bird b = (Bird)object;
            if ( b.getxPos() == getxPos() && b.getyPos() == getyPos()
                    && b.getHeight() == getHeight() && b.getWidth() == getWidth()
                    && b.getColor() == getColor() && b.getName() == getName() )
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

    public String getName() 
    { 
        return name; 
    }

    public void setName(String name) 
    { 
        this.name = name; 
    }
}