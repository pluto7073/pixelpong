package io.pluto.pixelpong.frames.game.states;

import com.pluto7073.systems.FileSystem;
import com.pluto7073.systems.LogOutput;
import com.pluto7073.utils.vectors.Vector2i;
import io.pluto.pixelpong.Main;
import io.pluto.pixelpong.assets.Button;
import io.pluto.pixelpong.frames.game.AbstractState;

import java.awt.*;
import java.awt.event.MouseEvent;

public class MenuState extends AbstractState {

    public final Main main;
    public final FileSystem fileSystem;
    public final LogOutput logOutput;
    public final Button playButton;
    public final Button quitButton;
    public final Button htpButton;
    public final Button optionsBtn;
    public final Button changelogBtn;

    public MenuState(StateManager stateManager, Main main) {
        super(stateManager);
        this.main = main;
        fileSystem = main.fileSystem;
        logOutput = main.logOutput;
        playButton = new Button(new Vector2i(400, 120),
                new Vector2i(200, 50),
                "Play",
                main.defFont.deriveFont(48f),
                main.textColor,
                main);
        htpButton = new Button(new Vector2i(400, 215),
                new Vector2i(200, 50),
                "How To Play",
                main.defFont.deriveFont(48f),
                main.textColor,
                main);
        optionsBtn = new Button(new Vector2i(400, 310),
                new Vector2i(200, 50),
                "Options",
                main.defFont.deriveFont(48f),
                main.textColor,
                main);
        quitButton = new Button(new Vector2i(400, 500),
                new Vector2i(200, 50),
                "Quit",
                main.defFont.deriveFont(48f),
                main.textColor,
                main);
        changelogBtn = new Button(new Vector2i(400, 405),
                new Vector2i(200, 50),
                "ChangeLog",
                main.defFont.deriveFont(48f),
                main.textColor,
                main);
    }

    public void tick() {
        if (main.gameFrame != null) {
            Point mouse = MouseInfo.getPointerInfo().getLocation();
            int x = mouse.x - main.gameFrame.getLocation().x;
            int y = mouse.y - main.gameFrame.getLocation().y;
            playButton.checkSelection(x, y);
            htpButton.checkSelection(x, y);
            optionsBtn.checkSelection(x, y);
            quitButton.checkSelection(x, y);
            changelogBtn.checkSelection(x, y);
        }
    }

    public void draw(Graphics g) {
        g.setColor(main.backgroundColor);
        g.fillRect(0, 0, 800, 600);
        playButton.drawButton(g);
        htpButton.drawButton(g);
        optionsBtn.drawButton(g);
        quitButton.drawButton(g);
        changelogBtn.drawButton(g);
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void keyPressed(int k) {

    }

    public void keyReleased(int k) {

    }

    public void mouseClicked(MouseEvent e) {
        if (playButton.checkPressed()) stateManager.states.push(new MainState(stateManager, main));
        if (htpButton.checkPressed()) stateManager.states.push(new HowToPlayState(stateManager, main));
        if (optionsBtn.checkPressed()) stateManager.states.push(new OptionsState(stateManager, main));
        if (changelogBtn.checkPressed()) stateManager.states.push(new ChangelogState(stateManager, main));
        if (quitButton.checkPressed()) quit();
    }

    public void mouseDown(MouseEvent e) {

    }

    public void mouseUp(MouseEvent e) {

    }

    public void quit() {
        main.gameFrame.child.isRunning = false;
        main.logOutput.print("\n\tStopping!");
        System.exit(0);
    }

}
