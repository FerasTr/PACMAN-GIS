package MyFrame;

import Algorithms.ShortestPathAlgo;
import GameObjects.Game;
import GameObjects.Map;
import GameObjects.MapInit;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.*;

/**
 * This is the GUI main class, supports running the game in realtime and saving/ loading from a csv file as well as
 * saving to a kml file.
 */
public class MyFrame extends JFrame
{
    Map playground;
    Game currentGame;
    PlayGroundBoard bg;

    private JMenuBar optionsBar = new JMenuBar();

    private JMenu gameOptions = new JMenu("Options");
    private JMenuItem gameOpen = new JMenuItem("Open");
    private JMenuItem gameSave = new JMenuItem("Save");
    private JMenuItem gameClear = new JMenuItem("Clear");

    private JMenu gameInsert = new JMenu("Insert");
    private JMenuItem insertPacman = new JMenuItem("PACMAN");
    private JMenuItem insertFruit = new JMenuItem("Fruit");
    private JMenuItem gameRun = new JMenuItem("Run");

    /**
     * Using the map, construct a UI for the game.
     */
    public MyFrame(Map playground)
    {
        this.playground = playground;

        bg = new PlayGroundBoard(playground);

        setSize(1433, 642);
        setMinimumSize(new Dimension(300, 100));
        // Give actions to each button.
        insertPacman.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                bg.addPacman();
            }
        });

        insertFruit.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                bg.addFruit();
            }
        });

        gameOpen.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                File selectedFile;
                JFileChooser fileChooser = new JFileChooser("./Resources/CSV");
                int result = fileChooser.showOpenDialog(null);
                if (result == JFileChooser.APPROVE_OPTION)
                {
                    selectedFile = fileChooser.getSelectedFile();
                    loadGameCSV(selectedFile);
                    bg.loadGame(currentGame);
                    System.out.println("Selected file: " + selectedFile.getAbsolutePath());
                }
            }
        });

        gameSave.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                currentGame = bg.getGameSettings();
                saveGameCSV(currentGame);
                if (currentGame != null && !currentGame.listPacman().isEmpty() && !currentGame.listFruit().isEmpty())
                {
                    if (!currentGame.isPlayed())
                    {
                        Game temp = new Game(currentGame);
                        ShortestPathAlgo.algorithm(temp);
                        temp.saveToKML();
                    }
                    else
                    {
                        currentGame.saveToKML();
                    }
                    System.out.println("Game is saved as KML and CSV");
                }
                else
                {
                    System.out.println("Add elements first...");
                }

            }
        });

        gameClear.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                clearGame();
            }
        });

        gameRun.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                runAlg();
            }
        });

        optionsBar.add(gameOptions);
        gameOptions.add(gameRun);
        gameOptions.add(gameOpen);
        gameOptions.add(gameSave);
        gameOptions.add(gameClear);
        this.add(bg);
        getContentPane().add(optionsBar, BorderLayout.NORTH);
        optionsBar.add(gameInsert);
        gameInsert.add(insertPacman);
        gameInsert.add(insertFruit);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void runAlg()
    {
        if (currentGame != null && !currentGame.listPacman().isEmpty() && !currentGame.listFruit().isEmpty())
        {
            System.out.println("Running game...");
            bg.runGame();
        }
        System.out.println("Add elements first...");
    }

    private void clearGame()
    {
        bg.clearBoard();
    }

    private void saveGameCSV(Game currentGame)
    {
        if (currentGame != null && !currentGame.listPacman().isEmpty() && !currentGame.listFruit().isEmpty())
        {
            System.out.println("Saving to CSV...");
            Game toSave = new Game(currentGame);
            toSave.resetPacPost();
            toSave.saveToCSV();
        }
        else
        {
            System.out.println("Add elements first...");
        }
    }

    private void loadGameCSV(File selectedFile)
    {
        currentGame = new Game(selectedFile);
    }
}