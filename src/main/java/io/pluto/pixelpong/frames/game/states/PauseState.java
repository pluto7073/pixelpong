package io.pluto.pixelpong.frames.game.states;

import com.pluto7073.utils.vectors.Vector2i;
import io.pluto.pixelpong.Main;
import io.pluto.pixelpong.assets.Button;
import io.pluto.pixelpong.frames.game.AbstractState;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class PauseState extends AbstractState {

    public Image snapshot;
    public final Main main;
    public final Button resumeBtn;
    public final Button quitBtn;

    public PauseState(StateManager stateManager, Image snapshot, Main main) {
        super(stateManager);
        this.snapshot = snapshot;
        this.main = main;
        resumeBtn = new Button(new Vector2i(400, 300),
                new Vector2i(200, 50),
                "Resume",
                main.defFont.deriveFont(24f),
                main.textColor,
                main);
        quitBtn = new Button(new Vector2i(400, 400),
                new Vector2i(200, 50),
                "Quit",
                main.defFont.deriveFont(24f),
                main.textColor,
                main);
    }

    public void tick() {
        if (main.gameFrame != null) {
            Point mouse = MouseInfo.getPointerInfo().getLocation();
            int x = mouse.x - main.gameFrame.getLocation().x;
            int y = mouse.y - main.gameFrame.getLocation().y;
            resumeBtn.checkSelection(x, y);
            quitBtn.checkSelection(x, y);
        }
    }

    public void draw(Graphics g) {
        g.drawImage(snapshot, 0, 0, this);
        g.setColor(new Color(0, 0, 0, 128));
        g.fillRect(0, 0, 800, 600);
        g.setColor(main.textColor);
        g.setFont(main.defFont.deriveFont(48f));
        g.drawString("Game Paused", 100, 100);
        resumeBtn.drawButton(g);
        quitBtn.drawButton(g);
    }

    public void keyPressed(int k) {
        if (k == KeyEvent.VK_ESCAPE) {
            stateManager.states.pop();
        }
    }

    public void keyReleased(int k) {

    }

    public void mouseClicked(MouseEvent e) {
        if (resumeBtn.checkPressed()) stateManager.states.pop();
        if (quitBtn.checkPressed()) {
            stateManager.states.pop();
            stateManager.states.pop();
        }
    }

    public void mouseDown(MouseEvent e) {

    }

    public void mouseUp(MouseEvent e) {

    }

    public void mouseEntered(MouseEvent e) {

    }

}
