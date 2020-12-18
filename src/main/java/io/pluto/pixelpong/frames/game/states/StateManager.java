package io.pluto.pixelpong.frames.game.states;

import io.pluto.pixelpong.Main;
import io.pluto.pixelpong.frames.game.AbstractState;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.Stack;

public class StateManager extends JPanel {

    public final Stack<AbstractState> states = new Stack<>();
    public final Main main;
    public AbstractState currentState;

    public StateManager(Main main) {
        this.main = main;
        currentState = new MenuState(this, main);
        states.push(currentState);
        setBackground(main.backgroundColor);
        setSize(800, 600);
    }

    public void tick() {
        currentState = states.peek();
        currentState.tick();
    }

    public void paintComponent(Graphics g) {
        currentState.draw(g);
    }

    public void draw(Graphics g) {
        states.peek().draw(g);
    }

    public void keyPressed(int k) {
        states.peek().keyPressed(k);
    }

    public void keyReleased(int k) {
        states.peek().keyReleased(k);
    }

    public void mouseClicked(MouseEvent e) {
        states.peek().mouseClicked(e);
    }

    public void mousePressed(MouseEvent e) {
        states.peek().mouseDown(e);
    }

    public void mouseReleased(MouseEvent e) {
        states.peek().mouseUp(e);
    }

    public void mouseEntered(MouseEvent e) {
        states.peek().mouseEntered(e);
    }

    public void addState(AbstractState state) {
        states.push(state);
        currentState = state;
    }

}
