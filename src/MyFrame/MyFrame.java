package MyFrame;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import java.awt.BorderLayout;

public class MyFrame extends JFrame
{
    private JMenuBar optionsBar = new JMenuBar();

    private JMenu gameOptions = new JMenu("GameObjects");
    private JMenuItem gameOpen = new JMenuItem("Open");
    private JMenuItem gameSave = new JMenuItem("Save");
    private JMenuItem gameClear = new JMenuItem("Clear");

    private JMenu gameInsert = new JMenu("Insert");
    private JMenuItem insertPacman = new JMenuItem("PACMAN");
    private JMenuItem insertFruit = new JMenuItem("Fruit");
    private JMenuItem gameRun = new JMenuItem("Run");

    public MyFrame()
    {

        setSize(1433, 642);
        setMinimumSize(new Dimension(300, 100));

        optionsBar.add(gameOptions);

        gameOptions.add(gameRun);
        gameOptions.add(gameOpen);
        gameOptions.add(gameSave);
        gameOptions.add(gameClear);

        this.add(new DrawingSurface());
        getContentPane().add(optionsBar, BorderLayout.NORTH);
        optionsBar.add(gameInsert);
        gameInsert.add(insertPacman);
        gameInsert.add(insertFruit);


        setVisible(true);
    }

    public static void main(String[] args)
    {
        MyFrame asd = new MyFrame();
    }

    class DrawingSurface extends JComponent implements MouseListener
    {
        public DrawingSurface(){
            addMouseListener(this);
        }
        public void paintComponent(Graphics g)
        {
            super.paintComponent(g);
            BufferedImage myImage = null;
            try
            {
                myImage = ImageIO.read(new File("C:\\MyProjects\\GitRepo\\PACMAN-GIS\\Resources\\maps\\Ariel1.png"));
            }
            catch (IOException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            g.drawImage(myImage, 0, 0, this.getWidth(), this.getHeight(), this);

        }

        @Override
        public void mouseClicked(MouseEvent e)
        {
            System.out.println(e.getX() + "," + e.getY());
        }

        @Override
        public void mousePressed(MouseEvent e)
        {

        }

        @Override
        public void mouseReleased(MouseEvent e)
        {

        }

        @Override
        public void mouseEntered(MouseEvent e)
        {

        }

        @Override
        public void mouseExited(MouseEvent e)
        {

        }
    }

}