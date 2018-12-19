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

    public MyFrame(Map playground)
    {
        this.playground = playground;


        bg = new PlayGroundBoard(playground);

        setSize(1433, 642);
        setMinimumSize(new Dimension(300, 100));

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
                File selectedFile = null;
                JFileChooser fileChooser = new JFileChooser("./Resources/CSV");
                int result = fileChooser.showOpenDialog(null);
                if (result == JFileChooser.APPROVE_OPTION)
                {
                    selectedFile = fileChooser.getSelectedFile();
                    System.out.println("Selected file: " + selectedFile.getAbsolutePath());
                }
                loadGameCSV(selectedFile);
                bg.loadGame(currentGame);
            }
        });

        gameSave.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                currentGame = bg.getGameSettings();
                saveGameCSV(currentGame);
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
        bg.runGame();
    }

    private void clearGame()
    {
        bg.clearBoard();
    }

    private void saveGameCSV(Game currentGame)
    {
        currentGame.saveToCSV();
    }

    private void loadGameCSV(File selectedFile)
    {
        currentGame = new Game(selectedFile);

    }

    public static void main(String[] args)
    {
        MyFrame asd = new MyFrame(MapInit.ArielMap());
    }
}