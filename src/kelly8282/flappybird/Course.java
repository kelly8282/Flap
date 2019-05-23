package kelly8282.flappybird;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Random;

public class Course extends Canvas implements KeyListener, MouseListener, Runnable {

    private BufferedImage bg;
    private Bird bird;
    private ArrayList<Pipe> pipes;
    private Random rand;                                                                          
    private JPanel jpanel;
    private Graphics graphics;
    private Graphics2D renderer;
    private GameOptions gameOptions;
    private Block startButton;
    private Block leaderboardsButton;
    private Block infoButton;
    private Block settingButton;
    private Block backButton;
    private Block plusPipeSizeButton;
    private Block minusPipeSizeButton;
    private Block plusSpeedButton;
    private Block minusSpeedButton;
    private Block plusSpaceButton;
    private Block minusSpaceButton;
    private Block plusMidButton;
    private Block minusMidButton;
    private boolean playing = false;
    private boolean gameover = false; 
    private boolean started = false;
    private int ticks;
    private int yMotion;
    private int highscore = 0;
    private int score;
    private int gui = 1; //1 = Main menu, 2 = Info, 3 = Leaderboards, 4 = Settings
    private ArrayList<String> leaderboardNames;
    private ArrayList<Integer> leaderboardScores;


    public Course(GameOptions gameOptions) 
    {
        try 
        {
            bird = new Bird("Bird", 20, 20, getWidth()/3, getHeight()/2 - 10);
            pipes = new ArrayList<Pipe>();
            this.gameOptions = gameOptions;

            leaderboardNames =  new ArrayList<String>();
            leaderboardScores = new ArrayList<Integer>();

            //Test
            leaderboardNames.add("Kelly");
            leaderboardScores.add(5);
            leaderboardNames.add("Harrybo");
            leaderboardScores.add(4);
            leaderboardNames.add("Dave");
            leaderboardScores.add(4);
            leaderboardNames.add("Elysia");
            leaderboardScores.add(3);
            leaderboardNames.add("Michael");
            leaderboardScores.add(1);

            bg = ImageIO.read(Course.class.getResource("/assets/bg-blur.png"));

            rand = new Random();
            jpanel = new JPanel();

            //Add listeners
            jpanel.addMouseListener(this);
            jpanel.addKeyListener(this);
            addMouseListener(this);
            addKeyListener(this);
            setBackground(Color.WHITE);
            jpanel.add(this);

            addPipes(true);
            addPipes(true);
            addPipes(true);
            addPipes(false);

            new Thread(this).start();

        } 
        catch (Exception ex) 
        {
            ex.printStackTrace();
        }
    }

    public void addPipes(boolean first) 
    {

        int space = 150;
        int middleSpace = 280;
        int pipeWidth = 50;

        if (gameOptions.getPipeSpace() == 0) 
        {
            space = 140;
        }
        else if (gameOptions.getPipeSpace() == 1) 
        {
            space = 155;
        }
        else if (gameOptions.getPipeSpace() == 2) 
        {
            space = 170;
        }

        if (gameOptions.getPipeMiddleSpace() == 0) 
        {
            middleSpace = 260;
        }
        else if (gameOptions.getPipeMiddleSpace() == 1) 
        {
            middleSpace = 280;
        }
        else if (gameOptions.getPipeMiddleSpace() == 2) 
        {
            middleSpace = 300;
        }

        if (gameOptions.getPipeSize() == 0) 
        {
            pipeWidth = 50;
        }
        else if (gameOptions.getPipeSize() == 1) 
        {
            pipeWidth = 65;
        }
        else if (gameOptions.getPipeSize() == 2) 
        {
            pipeWidth = 80;
        }


        int pipeHeight = 50 + rand.nextInt(100);

        //Give player a boost by having pipes slightly offset on start
        if (first == true)
        {
            pipes.add(new Pipe(pipeWidth, pipeHeight, getWidth() + pipeWidth + pipes.size() * 300, getHeight() - pipeHeight));
            pipes.add(new Pipe( pipeWidth, getHeight() - pipeHeight - space, getWidth() + pipeHeight + (pipes.size() - 1) * 300, 0));
        }
        //Normal pipe generation
        else
        {
            pipes.add(new Pipe( pipeWidth, pipeHeight, pipes.get(pipes.size() - 1).getxPos() + middleSpace, getHeight() - pipeHeight));
            pipes.add(new Pipe(pipeWidth, getHeight() - pipeHeight - space, pipes.get(pipes.size() - 1).getxPos(), 0));
        }
    }

    //Update the window to keep it from flashing like crazy
    public void update(Graphics window)
    {
        paint(window);
    }

    //Renders all menus/screens
    public void paint(Graphics window) 
    {
        try 
        {
            renderer = (Graphics2D) window;

            //Use blurred background when in a menu
            bg = ImageIO.read(Course.class.getResource("/assets/bg-blur.png"));
            if (playing == true) 
            {
                bg = ImageIO.read(Course.class.getResource("/assets/bg.png"));
            }

            graphics = bg.createGraphics();

            // Handle running the game
            if (playing == true && gameover == false) 
            {
                int speed = 10;

                if (gameOptions.getSpeed() == 0)
                {
                    speed = 10;
                }
                else if (gameOptions.getSpeed() == 1) 
                {
                    speed = 12;
                }
                else if (gameOptions.getSpeed() == 2) 
                {
                    speed = 15;
                }

                ticks++;

                //Move pipes
                for (int i = 0; i < pipes.size(); i++)
                {
                    Pipe pipe = pipes.get(i);

                    pipe.xPos -= speed;

                    pipe.draw(graphics, Color.GREEN);
                }

                //Pull object down with yMotion
                if (ticks % 2 == 0 && yMotion < 15) 
                {

                    yMotion += 2;
                }

                //Remove pipes outside of the screen
                for (int i = 0; i < pipes.size(); i++)
                {
                    Pipe pipe = pipes.get(i);

                    if (pipe.getxPos() + pipe.getWidth() < 0)
                    {
                        pipes.remove(pipe);

                        if (pipe.getyPos() == 0)
                        {
                            addPipes(false);
                        }
                    }
                }

                //Add gravity to the bird
                bird.yPos += yMotion;
                bird.xPos = getWidth()/4;

                BufferedImage birdy = ImageIO.read(Course.class.getResource("/assets/bird.png"));
                bird.draw(graphics, birdy, this);


                for( int i = 0; i < pipes.size(); i++)
                {
                    Pipe pipe = pipes.get(i);
                    if (pipe.getyPos() == 0 && bird.getxPos() + bird.getWidth() / 2 > pipe.getxPos() + pipe.getWidth() / 2 - 10 && bird.getxPos() + bird.getWidth() / 2 < pipe.getxPos() + pipe.getWidth() / 2 + 10)
                    {
                        score++;
                    }

                    //Check if bird collides with pipe
                    if (pipe.isIntersecting(bird))
                    {
                        gameover = true;

                        if (bird.getxPos() <= pipe.getxPos())
                        {
                            bird.xPos = pipe.getxPos() - bird.getWidth();

                        }
                        else
                        {
                            if (pipe.getyPos() != 0)
                            {
                                bird.yPos = pipe.getyPos() - bird.getHeight();
                            }
                            else if (bird.getyPos() < pipe.getHeight())
                            {
                                bird.yPos = pipe.getHeight();
                            }
                        }
                    }
                }

                graphics.setFont(new Font("Arial", Font.BOLD, 18));
                graphics.setColor(Color.WHITE);
                graphics.drawString("Score: " + score, 10, 18);

                //Check if bird hits a vertical boundary
                if (bird.getyPos() > getHeight() || bird.getyPos() < 1) 
                {
                    gameover = true;
                }

            }

            //Reset bird location
            if (playing && gameover) 
            {

                if (score > highscore) 
                {
                    highscore = score;
                }

                bird.yPos = getHeight()/2;
                bird.xPos = getWidth()/4;

                BufferedImage birdy = ImageIO.read(Course.class.getResource("/assets/bird.png"));
                bird.draw(graphics, birdy, this);

//                graphics.setFont(new Font("Arial", Font.BOLD, 50));
//                graphics.setColor(Color.WHITE);
//
//                graphics.drawString("Score: " + score, getWidth()/3, getHeight()/2 - 15);
            }

            //Draw Main Menu
            else if (gui == 1) 
            {
                BufferedImage logo = ImageIO.read(Course.class.getResource("/assets/logo.png"));
                BufferedImage start = ImageIO.read(Course.class.getResource("/assets/start.png"));
                BufferedImage info = ImageIO.read(Course.class.getResource("/assets/info.png"));
                BufferedImage settings = ImageIO.read(Course.class.getResource("/assets/settings.png"));
                BufferedImage leaderboards = ImageIO.read(Course.class.getResource("/assets/leaderboards.png"));

                graphics.drawImage(logo, getWidth() / 2 - logo.getWidth() / 2, getHeight() / 5, this);

                startButton = new Block(start.getWidth(), start.getHeight(), getWidth() / 2 - start.getWidth() / 2, getHeight() / 2);
                leaderboardsButton = new Block(leaderboards.getWidth(), leaderboards.getHeight(), getWidth() / 4 - leaderboards.getWidth() / 2, getHeight() / 2);
                infoButton = new Block(info.getWidth(), info.getHeight(), getWidth() / 4 * 3 - info.getWidth() / 2, getHeight() / 2);
                settingButton = new Block(settings.getWidth(), settings.getHeight(), getWidth() - settings.getWidth(), getHeight() - settings.getHeight());

                startButton.draw(graphics, start, this);
                leaderboardsButton.draw(graphics, leaderboards, this);
                infoButton.draw(graphics, info, this);
                settingButton.draw(graphics, settings, this);
            }

            //Draw Info Menu
            else if (gui == 2) 
            {
                BufferedImage logo = ImageIO.read(Course.class.getResource("/assets/logo.png"));
                graphics.drawImage(logo, getWidth() / 2 - logo.getWidth() / 2, getHeight() / 5, this);

                graphics.drawString("How To Play? Press SPACE to jump and avoid the pipes. Press escape to go back to main menu.", getWidth()/2 - 180, getHeight()/2 - 20);

                graphics.drawString("Version: " + FlappyBird.getVersion(), getWidth()/2 - 50, getHeight()/2 - 2);
                graphics.drawString("Author: " + FlappyBird.getAuthor(), getWidth()/2 - 50, getHeight()/2 + 18);

                BufferedImage back = ImageIO.read(Course.class.getResource("/assets/back.png"));
                backButton = new Block(back.getWidth(), back.getHeight(), getWidth()/2 - back.getWidth()/2, getHeight()/4*3 - back.getHeight()/2);
                backButton.draw(graphics, back, this);

            }

            //Draw Leaderboards
            else if (gui == 3) 
            {

                BufferedImage logo = ImageIO.read(Course.class.getResource("/assets/leaderboards-logo.png"));
                graphics.drawImage(logo, getWidth() / 2 - logo.getWidth() / 2, getHeight() / 5, this);

                graphics.drawString("Latest High Score", getWidth()/2 - 40, getHeight()/3);

                graphics.drawString(String.valueOf(highscore), getWidth()/2, getHeight()/3 + 20);

//                for (int i = 0; i < leaderboardNames.size(); i++) {
//
//                    String name = leaderboardNames.get(i);
//                    int score = leaderboardScores.get(i);
//
//                    graphics.drawString(name + " - " + score, getWidth()/2 - 40, getHeight()/3 + 20 + i*20);
//                }

                BufferedImage back = ImageIO.read(Course.class.getResource("/assets/back.png"));
                backButton = new Block(back.getWidth(), back.getHeight(), getWidth()/2 - back.getWidth()/2, getHeight()/4*3 - back.getHeight()/2);
                backButton.draw(graphics, back, this);

            }

            //Draw Settings Menu
            else if (gui == 4) 
            {

                BufferedImage plus = ImageIO.read(Course.class.getResource("/assets/plus.png"));
                BufferedImage minus = ImageIO.read(Course.class.getResource("/assets/minus.png"));

                graphics.setFont(new Font("Arial", Font.PLAIN, 12));

                graphics.drawString("Choose game speed:", getWidth()/2 - 54, getHeight()/5 - 20);
                graphics.drawString(String.valueOf(gameOptions.getSpeed()), getWidth()/2, getHeight()/5 + 30 - 20);

                plusSpeedButton = new Block(plus.getWidth(), plus.getHeight(), getWidth()/2 - plus.getWidth()/2 + 35, getHeight()/5 - plus.getHeight()/2 + 30 - 20);
                plusSpeedButton.draw(graphics, plus, this);

                minusSpeedButton = new Block(minus.getWidth(), minus.getHeight(), getWidth()/2 - minus.getWidth()/2 - 30, getHeight()/5 - minus.getHeight()/2 + 30 - 20);
                minusSpeedButton.draw(graphics, minus, this);

                graphics.drawString("Choose pipe size:", getWidth()/2 - 44, getHeight()/5 + 60);
                graphics.drawString(String.valueOf(gameOptions.getPipeSize()), getWidth()/2, getHeight()/5 + 30 + 60);

                plusPipeSizeButton = new Block(plus.getWidth(), plus.getHeight(), getWidth()/2 - plus.getWidth()/2 + 35, getHeight()/5 - plus.getHeight()/2 + 30 + 60);
                plusPipeSizeButton.draw(graphics, plus, this);

                minusPipeSizeButton = new Block(minus.getWidth(), minus.getHeight(), getWidth()/2 - minus.getWidth()/2 - 30, getHeight()/5 - minus.getHeight()/2 + 30 + 60);
                minusPipeSizeButton.draw(graphics, minus, this);

                graphics.drawString("Choose pipe space:", getWidth()/2 - 54, getHeight()/5 + 140);
                graphics.drawString(String.valueOf(gameOptions.getPipeSpace()), getWidth()/2, getHeight()/5 + 30 + 140);

                plusSpaceButton = new Block(plus.getWidth(), plus.getHeight(), getWidth()/2 - plus.getWidth()/2 + 35, getHeight()/5 - plus.getHeight()/2 + 30 + 140);
                plusSpaceButton.draw(graphics, plus, this);

                minusSpaceButton = new Block(minus.getWidth(), minus.getHeight(), getWidth()/2 - minus.getWidth()/2 - 30, getHeight()/5 - minus.getHeight()/2 + 30 + 140);
                minusSpaceButton.draw(graphics, minus, this);

                graphics.drawString("Choose pipe mid space:", getWidth()/2 - 64, getHeight()/5 + 200);
                graphics.drawString(String.valueOf(gameOptions.getPipeMiddleSpace()), getWidth()/2, getHeight()/5 + 30 + 200);

                plusMidButton = new Block(plus.getWidth(), plus.getHeight(), getWidth()/2 - plus.getWidth()/2 + 35, getHeight()/5 - plus.getHeight()/2 + 30 + 200);
                plusMidButton.draw(graphics, plus, this);

                minusMidButton = new Block(minus.getWidth(), minus.getHeight(), getWidth()/2 - minus.getWidth()/2 - 30, getHeight()/5 - minus.getHeight()/2 + 30 + 200);
                minusMidButton.draw(graphics, minus, this);

                BufferedImage back = ImageIO.read(Course.class.getResource("/assets/back.png"));
                backButton = new Block(back.getWidth(), back.getHeight(), getWidth()/2 - back.getWidth()/2, getHeight()/5*4 - back.getHeight()/2);
                backButton.draw(graphics, back, this);
            }

            //Render drawn graphics
            renderer.drawImage(bg, null, 0, 0);
        }
        catch (Exception e) 
        {
            e.printStackTrace();
        }
    }

    //Handle jumping
    public void jump()
    {

        if (gameover == false)
        {
        if (yMotion > 0)
        {
            yMotion = 0;
        }

        yMotion -= 8;
        }

        //Check if game is over and reset values
        if (gameover == true)
        {
            pipes.clear();
            yMotion = 0;
            score = 0;

            addPipes(true);
            addPipes(true);
            addPipes(true);
            addPipes(true);

            gameover = false;
        }


    }

    //Check for key presses and handle them
    public void keyReleased(KeyEvent e) 
    {
        if (e.getKeyCode() == KeyEvent.VK_SPACE)
        {
            if (playing == true) 
            {
                jump();
            }
        }
        //Exit to main menu
        else if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
        {
            gui = 1;
            if (gameover == true) 
            {
                gameover = false;
            }
            playing = false;
        }
    }

    //Check for button clicks and handle them
    public void mouseClicked(MouseEvent e) 
    {
        Point p = e.getPoint();
        if (gui == 2 || gui == 3 || gui == 4) 
        {
            if (backButton.isInside(p))
            {
                gui = 1;
            }
        }
        if (gui == 1) 
        {
            if (startButton.isInside(p))
            {
                gui = 0;
                playing = true;
            }
            else if (leaderboardsButton.isInside(p))
            {
                gui = 3;
            }
            else if (infoButton.isInside(p))
            {
                gui = 2;
            }
            else if (settingButton.isInside(p)) 
            {
                gui = 4;
            }
        }
        else if (gui == 4) 
        {
            if (plusPipeSizeButton.isInside(p))
            {
                if (gameOptions.getPipeSize() < 2)
                {
                gameOptions.setPipeSize(gameOptions.getPipeSize() + 1);
                }
            }
            else if (minusPipeSizeButton.isInside(p)) 
            {
                if (gameOptions.getPipeSize() > 0)
                {
                    gameOptions.setPipeSize(gameOptions.getPipeSize() - 1);
                }
            }
            else if (plusSpeedButton.isInside(p))
            {
                if (gameOptions.getSpeed() < 2)
                {
                    gameOptions.setSpeed(gameOptions.getSpeed() + 1);
                }
            }
            else if (minusSpeedButton.isInside(p)) 
            {
                if (gameOptions.getSpeed() > 0)
                {
                    gameOptions.setSpeed(gameOptions.getSpeed() - 1);
                }
            }
            else if (plusSpaceButton.isInside(p))
            {
                if (gameOptions.getPipeSpace() < 2)
                {
                    gameOptions.setPipeSpace(gameOptions.getPipeSpace() + 1);
                }
            }
            else if (minusSpaceButton.isInside(p)) 
            {
                if (gameOptions.getPipeSpace() > 0)
                {
                    gameOptions.setPipeSpace(gameOptions.getPipeSpace() - 1);
                }
            }
            else if (plusMidButton.isInside(p))
            {
                if (gameOptions.getPipeMiddleSpace() < 2)
                {
                    gameOptions.setPipeMiddleSpace(gameOptions.getPipeMiddleSpace() + 1);
                }
            }
            else if (minusMidButton.isInside(p)) 
            {
                if (gameOptions.getPipeMiddleSpace() > 0)
                {
                    gameOptions.setPipeMiddleSpace(gameOptions.getPipeMiddleSpace() - 1);
                }
            }
        }
    }

    //Unused events required for listeners
    public void mousePressed(MouseEvent e) 
    {
        
    }
    public void mouseReleased(MouseEvent e) 
    {
    
    }
    public void mouseEntered(MouseEvent e) 
    {
    
    }
    public void mouseExited(MouseEvent e) 
    {
    
    }
    public void keyTyped(KeyEvent e) 
    {
    
    }
    public void keyPressed(KeyEvent e) 
    {
    
    }

    //Runnable
    public void run() 
    {
        try 
        {
            while(true) 
            {
                Thread.currentThread().sleep(8);
                repaint();
            }
        }
        catch(Exception e) 
        {
            e.printStackTrace();
        }
    }
}