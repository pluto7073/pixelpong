package io.pluto.pixelpong.frames.game.states;

import io.pluto.pixelpong.Main;
import io.pluto.pixelpong.frames.game.AbstractState;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class HowToPlayState extends AbstractState {

    private final Main main;

    public HowToPlayState(StateManager stateManager, Main main) {
        super(stateManager);
        this.main = main;
    }

    public void tick() {

    }

    public void draw(Graphics g) {
        g.setColor(main.backgroundColor);
        g.fillRect(0, 0, 800, 600);
        g.setColor(main.textColor);
        g.setFont(main.defFont.deriveFont(24f));
        g.drawString("How to Play:", 25, 25);
        g.drawString("", 25, 50);
        g.drawString("Use Left & Right Arrow keys to move", 25, 75);
        g.drawString("Use ESCAPE to Pause", 25, 100);
        g.drawString("Gain a point by hitting the ball", 25, 125);
        g.drawString("Lose a Point by missing the ball", 25, 150);
        g.drawString("(Press ESCAPE to go back to the main menu)", 25, 175);
    }

    public void keyPressed(int k) {
        if (k == KeyEvent.VK_ESCAPE) {
            stateManager.states.pop();
        }
    }

    public void keyReleased(int k) {

    }

    public void mouseClicked(MouseEvent e) {

    }

    public void mouseDown(MouseEvent e) {

    }

    public void mouseUp(MouseEvent e) {

    }

    public void mouseEntered(MouseEvent e) {

    }

}
