package io.pluto.pixelpong.frames.game.states;

import io.pluto.pixelpong.Main;
import io.pluto.pixelpong.frames.game.AbstractState;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import static java.lang.Thread.sleep;

public class ReloadState extends AbstractState {

    private Main main;
    private int i = 0;

    public ReloadState(StateManager stateManager, Main main) {
        super(stateManager);
        this.main = main;
    }

    public void tick() {
        try {
            if (i == 0) {
                main.fileSystem.loadCustomResources();
                i++;
                sleep(500);
            } else if (i == 1) {
                main.fileSystem.getAndLoadSelectedPack();
                i++;
                sleep(500);
            } else {
                stateManager.states.pop();
                stateManager.states.push(new MenuState(stateManager, main));
            }
        } catch (InterruptedException e) {
            main.logOutput.printThrowable(e);
        }
    }

    public void draw(Graphics g) {
        g.setColor(main.backgroundColor);
        g.fillRect(0, 0, 800, 600);
        g.setColor(main.textColor);
        g.drawString("Reloading Resources...", 25, 575);
    }

    public void keyPressed(int k) {

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
