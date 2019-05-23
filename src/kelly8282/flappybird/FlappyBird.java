package kelly8282.flappybird;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class FlappyBird extends JFrame 
{

    private static final String name = "Flappy Bird";
    private static final String version = "1.1";
    private static final String author = "Kelly Liu";

    //Set window dimensions
    public int width = 750, height = 500;

    public FlappyBird() 
    {
        try 
        {
            Course course = new Course(new GameOptions(0, 1, 1, 2, true));

            setTitle(FlappyBird.getGameName() + " -Kelly Liu- " + FlappyBird.getVersion());
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setSize(width, height);
            
            BufferedImage icon = ImageIO.read(FlappyBird.class.getResource("/assets/icon.png"));
            setIconImage(icon);
            setResizable(false);
            getContentPane().add(course);
            setVisible(true);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public static void main(String args[]) 
    {
        System.out.println("Starting " + name + " - " + version + " by " + author);
        FlappyBird flappyBird = new FlappyBird();
    }


    public static String getGameName() 
    {
        return name;
    }

    public static String getVersion() 
    {
        return version;
    }

    public static String getAuthor() 
    {
        return author;
    }
}
