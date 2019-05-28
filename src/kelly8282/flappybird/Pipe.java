package kelly8282.flappybird;

import java.awt.*;

public class Pipe extends Block
{

    public Pipe() 
    {
        super(50, 80);
        setColor(Color.GREEN);
    }

    public Pipe(int xPos, int yPos) 
    {
        super(50, 80, xPos, yPos);
        setColor(Color.GREEN);
    }

    public Pipe(int width, int height, int xPos, int yPos) 
    {
        super(width, height, xPos, yPos);
        setColor(Color.GREEN);
    }

    public Pipe(int width, int height, int xPos, int yPos, Color color) 
    {
        super(width, height, xPos, yPos, color);
    }

}