package MyFrame;

import GameObjects.*;
import Geom.Point3D;
import Geom.secondsPoint3D;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Playground for the map and game objects.
 */
public class PlayGroundBoard extends JPanel
{
    private BufferedImage myImage = null;
    private Map m;

    private int imgW, imgH, paneW, paneH;

    private Game gameSettings;

    MouseListener pacmanMouse;
    MouseListener fruitMouse;

    /**
     * cConstruct from game and map.
     */
    public PlayGroundBoard(Map playground)
    {
        m = playground;
        this.myImage = playground.getMapPath();
        gameSettings = new Game();
        imgW = this.myImage.getWidth();
        imgH = this.myImage.getHeight();
    }

    /**
     * Load game.
     */
    public void loadGame(Game g)
    {
        System.out.println("Loading game");
        ClearListener();
        gameSettings = g;
        repaint();
    }

    public Game getGameSettings()
    {
        return gameSettings;
    }

    /**
     * Clear game.
     */
    public void clearBoard()
    {
        System.out.println("Clearing game");
        ClearListener();
        gameSettings.resetGame();
        repaint();
    }

    private void ClearListener()
    {
        this.removeMouseListener(fruitMouse);
        this.removeMouseListener(pacmanMouse);
    }

    /**
     * Scaling the point to the required size before the resize.
     */
    public Point3D pointBeforeResize(Point3D p)
    {
        paneW = this.getWidth();
        paneH = this.getHeight();
        int x = (int) (p.x() * imgW / paneW);
        int y = (int) (p.y() * imgH / paneH);
        return new Point3D(x, y, 0);
    }

    /**
     * Scaling the point after the resize.
     */
    public Point3D pointAfterResize(Point3D p)
    {
        paneW = this.getWidth();
        paneH = this.getHeight();
        int x = (int) (p.x() * paneW / imgW);
        int y = (int) (p.y() * paneH / imgH);
        return new Point3D(x, y, 0);
    }

    /**
     * Paint the game and all its elements.
     */
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        g.drawImage(myImage, 0, 0, this.getWidth(), this.getHeight(), this);

        if (gameSettings != null)
        {
            Iterator<GameElement> itr = gameSettings.iterator();
            while (itr.hasNext())
            {
                GameElement object = itr.next();
                Point3D locationInGPS = object.getLocation();
                Point3D locationInPixel = m.gpsToPixle(locationInGPS);
                locationInPixel = pointAfterResize(locationInPixel);
                BufferedImage icon = object.getIcon();
                icon = rescaleIcon(icon);
                g.drawImage(icon, (int) locationInPixel.x() - (icon.getWidth() / 2),
                        (int) locationInPixel.y() - (icon.getHeight() / 2), this);
            }
        }

        ArrayList<PacMan> paclist = gameSettings.listPacman();
        Iterator<PacMan> itr = paclist.iterator();
        PacMan p;

        while (itr.hasNext())
        {
            p = itr.next();

            if (p.getPath() != null && !p.getPath().getPathList().isEmpty())
            {
                updateLines(g, p);
            }
        }
    }

    /**
     * Update the lines of each path
     */
    private void updateLines(Graphics g, PacMan p)
    {
        Graphics2D twoG = (Graphics2D) g;
        Stroke line = new BasicStroke(5, 1, 1);
        Path path = p.getPath();

        twoG.setColor(path.getColor());
        ArrayList<secondsPoint3D> pointsPaths = path.getPathList();
        System.out.println(pointsPaths.size());
        for (int i = 1; i < pointsPaths.size(); i++)
        {
            Point3D front = pointsPaths.get(i).getPoint();
            front = m.gpsToPixle(front);
            front = pointAfterResize(front);
            Point3D back = pointsPaths.get(i - 1).getPoint();
            //System.out.println("POINT IN PATH " + back.toFile());
            back = m.gpsToPixle(back);
            back = pointAfterResize(back);

            twoG.setStroke(line);
            twoG.drawLine(back.ix(), back.iy(), front.ix(), front.iy());
        }
    }

    /**
     * Rescale each icon
     */
    private BufferedImage rescaleIcon(BufferedImage icon)
    {
        double dx = this.getWidth() / 1433.0;
        double dy = this.getHeight() / 642.0;

        double x = icon.getWidth() * dx;
        double y = icon.getHeight() * dy;
        Image tmp = icon.getScaledInstance((int) x, (int) y, icon.SCALE_SMOOTH);

        BufferedImage dimg = new BufferedImage((int) x, (int) y, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2d = dimg.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();

        return dimg;

    }

    /**
     * Add pacman to the screen.
     */
    public void addPacman()
    {
        ClearListener();
        if (gameSettings == null)
        {
            gameSettings = new Game();
        }
        pacmanMouse = new MouseListener()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                Point3D clickLocation = new Point3D(e.getX(), e.getY(), 0);
                Point3D p = pointBeforeResize(clickLocation);
                Point3D inGPS = m.pixleToGPS(p);
                PacMan toAdd = new PacMan(inGPS.x(), inGPS.y(), inGPS.z(), 1, 1);
                gameSettings.add(toAdd);
                repaint();
                System.out.println("PACMAN ADDED " + toAdd.getId() + " --> PIXEL: [" + (int) p.x() + "," + (int) p.y() + "] GIS: [" + inGPS.x() + "," + inGPS.y() + "]");
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
        };
        this.addMouseListener(pacmanMouse);
    }

    public void addFruit()
    {
        ClearListener();
        if (gameSettings == null)
        {
            gameSettings = new Game();
        }
        fruitMouse = new MouseListener()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                Point3D clickLocation = new Point3D(e.getX(), e.getY(), 0);
                Point3D p = pointBeforeResize(clickLocation);
                Point3D inGPS = m.pixleToGPS(p);
                Fruit toAdd = new Fruit(inGPS.x(), inGPS.y(), inGPS.z(), 1);
                gameSettings.add(toAdd);
                repaint();
                System.out.println("FRUIT ADDED " + toAdd.getId() + " --> PIXEL: [" + (int) p.x() + "," + (int) p.y() + "] GIS: [" + inGPS.x() + "," + inGPS.y() + "]");
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
        };
        this.addMouseListener(fruitMouse);
    }

    /**
     * Run the game on a new thread real time.
     */
    public void runGame()
    {
        simulateRun();
    }

    private void simulateRun()
    {
        RealTime simulation = new RealTime(this);
        Thread thread = new Thread(simulation);
        thread.start();
    }
}