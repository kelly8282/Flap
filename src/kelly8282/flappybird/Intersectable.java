package kelly8282.flappybird;

import java.awt.*;

public interface Intersectable 
{
    public abstract boolean isIntersecting(Block block);
    public abstract boolean isInside(Point p);
    public abstract boolean isInside(int x, int y);
}
