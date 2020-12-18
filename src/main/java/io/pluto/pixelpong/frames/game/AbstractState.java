package io.pluto.pixelpong.frames.game;

import io.pluto.pixelpong.frames.game.states.StateManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;

public abstract class AbstractState extends JPanel {

    public StateManager stateManager;

    public AbstractState(StateManager stateManager) {
        super();
        this.stateManager = stateManager;
    }

    public abstract void tick();
    public abstract void draw(Graphics g);
    public abstract void keyPressed(int k);
    public abstract void keyReleased(int k);
    public abstract void mouseClicked(MouseEvent e);
    public abstract void mouseDown(MouseEvent e);
    public abstract void mouseUp(MouseEvent e);
    public abstract void mouseEntered(MouseEvent e);

    @Override
    public Component add(Component comp) {
        return stateManager.add(comp);
    }
}
