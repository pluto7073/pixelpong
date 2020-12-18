package io.pluto.pixelpong.frames.game.states;

import com.pluto7073.utils.vectors.Vector2i;
import io.pluto.pixelpong.Main;
import io.pluto.pixelpong.frames.game.AbstractState;
import io.pluto.pixelpong.assets.Button;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.*;

public class OptionsState extends AbstractState {

    private final Main main;
    private boolean trail;
    private String loadedPack = "";
    private final Button trailToggle;
    private final Button doneBtn;
    private final Button resToggle;
    private String[] packIDs;
    private int selectedPackIndex;
    private boolean refreshRequired = false;

    public OptionsState(StateManager stateManager, Main main) {
        super(stateManager);
        this.main = main;
        loadProperties();
        trailToggle = new Button(new Vector2i(250, 150),
                new Vector2i(400, 50),
                "Ball Trail: " + trail,
                main.defFont.deriveFont(48f),
                main.textColor,
                main);
        doneBtn = new Button(new Vector2i(400, 500),
                new Vector2i(200, 50),
                "Done",
                main.defFont.deriveFont(48f),
                main.textColor,
                main);
        resToggle = new Button(new Vector2i(250, 250),
                new Vector2i(400, 50),
                "Loaded Pack: " + loadedPack,
                main.defFont.deriveFont(48f),
                main.textColor,
                main);
        int size = main.fileSystem.getPacks().length + 1;
        packIDs = new String[size];
        packIDs[0] = "<none>";
        for (int i = 1; i < size; i++) {
            packIDs[i] = main.fileSystem.getPacks()[i - 1];
            if (packIDs[i].equals(main.fileSystem.selectedCustomResource)) {
                selectedPackIndex = i;
            }
        }
    }

    public void loadProperties() {
        trail = main.trail;
    }

    public void tick() {
        trailToggle.setText("Ball Trail: " + getOnOff(trail));
        if (main.gameFrame != null) {
            Point mouse = MouseInfo.getPointerInfo().getLocation();
            int x = mouse.x - main.gameFrame.getLocation().x;
            int y = mouse.y - main.gameFrame.getLocation().y;
            trailToggle.checkSelection(x, y);
            doneBtn.checkSelection(x, y);
            resToggle.checkSelection(x, y);
        }
        String selID = packIDs[selectedPackIndex];
        if (!selID.equals("<none>")) {
            loadedPack = main.fileSystem.getPackName(selID);
        } else loadedPack = "<none>";
        resToggle.setText("Resource Pack: " + loadedPack);
    }

    public void draw(Graphics g) {
        g.setColor(main.backgroundColor);
        g.fillRect(0, 0, 800, 600);
        g.setColor(main.textColor);
        g.setFont(main.defFont.deriveFont(48f));
        g.drawString("Options", 50, 98);
        trailToggle.drawButton(g);
        doneBtn.drawButton(g);
        resToggle.drawButton(g);
    }

    public void keyPressed(int k) {
        if (k == KeyEvent.VK_ESCAPE) {
            stateManager.states.pop();
        }
    }
    public void keyReleased(int k) {}

    public void mouseClicked(MouseEvent e) {
        if (trailToggle.checkPressed()) toggleTrail();
        if (doneBtn.checkPressed()) {
            stateManager.states.pop();
            if (refreshRequired) stateManager.states.push(new ReloadState(stateManager, main));
        }
        if (resToggle.checkPressed()) toggleSelectedPack();
    }

    public void mouseDown(MouseEvent e) {}
    public void mouseUp(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}

    public void toggleTrail() {
        trail = !trail;
        try {
            refreshProperties();
        } catch (Exception e) {
            main.logOutput.printThrowable(e);
        }
    }

    public void refreshProperties() throws Exception {
        File file = new File(main.fileSystem.getDir() + "\\config.json");
        FileReader reader = new FileReader(file);
        JSONParser jsonParser = new JSONParser();
        Object obj = jsonParser.parse(reader);
        JSONObject properties = (JSONObject) obj;
        properties.put("ballTrail", trail);
        properties.put("selectedPack", packIDs[selectedPackIndex]);
        try (FileWriter writer = new FileWriter(main.fileSystem.getDir() + "\\config.json")) {
            writer.write(properties.toJSONString());
        }
        main.trail = trail;
    }

    public String getOnOff(boolean b) {
        if (b) {
            return "On";
        } else {
            return "Off";
        }
    }

    public void toggleSelectedPack() {
        selectedPackIndex += 1;
        if (selectedPackIndex > packIDs.length - 1) {
            selectedPackIndex = 0;
        }
        try {
            refreshProperties();
        } catch (Exception e) {
            main.logOutput.printThrowable(e);
        }
        JOptionPane.showMessageDialog(main.gameFrame, "Restart PixelPong for your Resource Pack to be applied");
        refreshRequired = true;
    }

}
