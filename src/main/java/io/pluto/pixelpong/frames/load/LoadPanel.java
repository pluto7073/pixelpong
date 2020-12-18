package io.pluto.pixelpong.frames.load;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class LoadPanel extends JPanel implements Runnable {

    public LoadFrame parent;
    public boolean isRunning = false;

    public LoadPanel(LoadFrame parent) {
        this.parent = parent;
        start();
    }

    public void start() {
        isRunning = true;
        Thread thread = new Thread(this);
        thread.start();
    }

    public void run() {
        while (isRunning) {
            repaint();
        }
        parent.setVisible(false);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.clearRect(0, 0, 600, 600);
        g.setColor(new Color(0x101010));
        g.fillRect(0, 0, 600, 600);
        try {
            Image i = ImageIO.read(parent.fileSystem.getFileFromResourceAsStream("assets/images/icon.png"));
            g.drawImage(i, 0, 0, 600, 600, this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
