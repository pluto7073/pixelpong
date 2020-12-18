package io.pluto.pixelpong.frames.game;

import io.pluto.pixelpong.frames.game.states.StateManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class GamePanel extends JPanel implements Runnable, KeyListener, MouseListener {

    public final StateManager stateManager;
    public boolean isRunning;
    public final GameFrame parent;

    public GamePanel(GameFrame parent) {
        super(new BorderLayout(1, 4));
        this.parent = parent;
        stateManager = new StateManager(parent.main);
        setFocusable(true);
        Thread thread = new Thread(this);
        addMouseListener(this);
        addKeyListener(this);
        isRunning = true;
        thread.start();
    }

    public void keyTyped(KeyEvent e) {}

    public void keyPressed(KeyEvent e) {
        stateManager.keyPressed(e.getKeyCode());
    }

    public void keyReleased(KeyEvent e) {
        stateManager.keyReleased(e.getKeyCode());
    }

    public void mouseClicked(MouseEvent e) {
        stateManager.mouseClicked(e);
    }

    public void mousePressed(MouseEvent e) {
        stateManager.mousePressed(e);
    }
    public void mouseReleased(MouseEvent e) {
        stateManager.mouseReleased(e);
    }

    public void mouseEntered(MouseEvent e) {stateManager.mouseEntered(e);}

    public void mouseExited(MouseEvent e) {}

    public void run() {
        while(isRunning) {
            stateManager.tick();
            repaint();
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.clearRect(0, 0, 800, 600);
        stateManager.draw(g);
    }

}
