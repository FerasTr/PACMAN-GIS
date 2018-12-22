package MyFrame;

import Algorithms.ShortestPathAlgo;
import GameObjects.Game;
import GameObjects.PacMan;
import Geom.secondsPoint3D;
import Geom.Point3D;

import java.util.Iterator;

public class RealTime implements Runnable
{
    PlayGroundBoard simulationBoard;
    Game game;
    private volatile boolean running = true;

    public RealTime(PlayGroundBoard pg)
    {
        this.simulationBoard = pg;
        this.game = pg.getGameSettings();
    }


    @Override
    public void run()
    {
        ShortestPathAlgo.algorithm(game);
        long beforeTime, timeDiff, sleep;
        int timeForGame =(int) game.getTimeForGame();
        System.out.println(timeForGame);
        int i = 1;
        while (i < timeForGame)
        {
            beforeTime = System.currentTimeMillis();
            timeDiff = System.currentTimeMillis() - beforeTime;
            for(PacMan pac: game.listPacman())
            {
                Iterator<secondsPoint3D> itr = pac.getPath().getPathList().iterator();
                while(itr.hasNext())
                {
                    Point3D toMove = itr.next().getPoint();
                    pac.updateLocation(toMove);
                    itr.remove();
                    break;
                }
            }
            sleep = 1000 - timeDiff;
            try
            {
                simulationBoard.repaint();
                Thread.sleep(sleep);
            }
            catch (InterruptedException e)
            {

                String msg = String.format("Thread interrupted: %s", e.getMessage());
                System.out.println(msg);

            }

            beforeTime = System.currentTimeMillis();
        }

    }
}
