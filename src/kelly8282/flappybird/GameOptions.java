package kelly8282.flappybird;

public class GameOptions {

    private int pipeSize; // 0 = Small, 1 = Medium, 2 = Large
    private int speed; // Game speed, 0 = Normal, 1 = Medium, 2 = Fast & defaulted to 1
    private int pipeSpace; // Space between bottom and top pipe, 0 = 140px, 1 = 155px, 2 = 170px, defaulted to 2
    private int pipeMiddleSpace; // Space between 2 pipes, 0 = 260px, 1 = 280px, 2 = 300px, defaulted to 2

    private boolean devMode; // Used for testing

    public GameOptions() 
    {
        setPipeSize(0);
        setSpeed(1);
        setPipeSpace(2);
        setPipeMiddleSpace(2);
        setDevMode(false);
    }

    public GameOptions(int speed) 
    {
        setPipeSize(0);
        setSpeed(speed);
        setPipeSpace(2);
        setPipeMiddleSpace(2);
        setDevMode(false);
    }

    public GameOptions(int pipeSize, int speed) 
    {
        setPipeSize(pipeSize);
        setSpeed(speed);
        setPipeSpace(2);
        setPipeMiddleSpace(2);
        setDevMode(false);
    }

    public GameOptions(int pipeSize, boolean devMode) 
    {
        setPipeSize(pipeSize);
        setSpeed(1);
        setPipeSpace(2);
        setPipeMiddleSpace(2);
        setDevMode(devMode);
    }

    public GameOptions(boolean devMode, int speed) 
    {
        setPipeSize(0);
        setSpeed(speed);
        setPipeSpace(2);
        setPipeMiddleSpace(2);
        setDevMode(devMode);
    }

    public GameOptions(int pipeSize, int speed, boolean devMode) 
    {
        setPipeSize(pipeSize);
        setSpeed(speed);
        setPipeSpace(2);
        setPipeMiddleSpace(2);
        setDevMode(devMode);
    }

    public GameOptions(int pipeSize, int speed, int pipeSpace, int pipeMiddleSpace, boolean devMode) 
    {
        setPipeSize(pipeSize);
        setSpeed(speed);
        setPipeSpace(pipeSpace);
        setPipeMiddleSpace(pipeMiddleSpace);
        setDevMode(devMode);
    }

    public int getPipeSize() 
    { 
        return pipeSize; 
    }

    public void setPipeSize(int pipeSize) 
    { 
        this.pipeSize = pipeSize; 
    }

    public boolean isDevMode() 
    { 
        return devMode; 
    }

    public void setDevMode(boolean devMode) 
    { 
        this.devMode = devMode; 
    }

    public int getSpeed() 
    { 
        return speed; 
    }

    public void setSpeed(int speed) 
    { 
        this.speed = speed; 
    }

    public int getPipeSpace() 
    { 
        return pipeSpace; 
    }

    public void setPipeSpace(int pipeSpace) 
    {
        this.pipeSpace = pipeSpace; 
    }

    public int getPipeMiddleSpace() 
    { 
        return pipeMiddleSpace; 
    }

    public void setPipeMiddleSpace(int pipeMiddleSpace) 
    {
        this.pipeMiddleSpace = pipeMiddleSpace; 
    }
}
