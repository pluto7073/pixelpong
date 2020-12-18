package io.pluto.pixelpong.frames.game;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

public class GameClosing extends WindowAdapter {

    private final GameFrame parent;

    public GameClosing(GameFrame parent) {
        this.parent = parent;
    }

    public void windowClosing(WindowEvent e) {
        int returnVal = JOptionPane.showConfirmDialog(parent,
                "Are you sure you want to Exit PixelPong?",
                "Confirm Exit",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);
        if (returnVal == JOptionPane.YES_OPTION) {
            parent.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            parent.child.isRunning = false;
            parent.logOutput.print("\n\tStopping!");
            File file = new File(parent.fileSystem.getDir() + "\\temp\\font.ttf");
            System.exit(0);
            file.delete();
        } else if (returnVal == JOptionPane.NO_OPTION) {
            parent.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        }
    }

}
