package MyFrame;

import GameObjects.*;
import Geom.Point3D;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.util.Iterator;

public class PlayGroundBoard extends JPanel
{
    private BufferedImage myImage = null;
    private Map m;

    private int imgW, imgH, paneW, paneH;

    private Game gameSettings;

    MouseListener pacmanMouse;
    MouseListener fruitMouse;


    public PlayGroundBoard(Map playground)
    {
        m = playground;
        this.myImage = playground.getMapPath();
        gameSettings = new Game();
        imgW = this.myImage.getWidth();
        imgH = this.myImage.getHeight();
    }

    public void loadGame(Game g)
    {
        this.removeMouseListener(fruitMouse);
        this.removeMouseListener(pacmanMouse);
        gameSettings = g;
        repaint();
    }

    public Game getGameSettings()
    {
        return gameSettings;
    }

    public void clearBoard()
    {
        this.removeMouseListener(fruitMouse);
        this.removeMouseListener(pacmanMouse);
        gameSettings.clear();
        repaint();
    }

    public Point3D pointBeforeResize(Point3D p)
    {
        paneW = this.getWidth();
        paneH = this.getHeight();
        int x = (int) (p.x() * imgW / paneW);
        int y = (int) (p.y() * imgH / paneH);
        return new Point3D(x, y, 0);
    }

    public Point3D pointAfterResize(Point3D p)
    {
        paneW = this.getWidth();
        paneH = this.getHeight();
        int x = (int) (p.x() * paneW / imgW);
        int y = (int) (p.y() * paneH / imgH);
        return new Point3D(x, y, 0);
    }

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
    }

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

    public void addPacman()
    {
        this.removeMouseListener(fruitMouse);
        this.removeMouseListener(pacmanMouse);
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
                gameSettings.add(new PacMan(inGPS.x(), inGPS.y(), inGPS.z(), 1, 1));
                repaint();
                System.out.println("PACMAN ADDED --> PIXEL: [" + (int) p.x() + "," + (int) p.y() + "] GIS: [" + inGPS.x() + "," + inGPS.y() + "]");
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
        this.removeMouseListener(fruitMouse);
        this.removeMouseListener(pacmanMouse);
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
                gameSettings.add(new Fruit(inGPS.x(), inGPS.y(), inGPS.z(), 1));
                repaint();
                System.out.println("FRUIT ADDED --> PIXEL: [" + (int) p.x() + "," + (int) p.y() + "] GIS: [" + inGPS.x() + "," + inGPS.y() + "]");
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
}
