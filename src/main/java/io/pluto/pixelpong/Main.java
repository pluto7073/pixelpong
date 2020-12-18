package io.pluto.pixelpong;

import com.pluto7073.systems.FileSystem;
import com.pluto7073.systems.LogOutput;
import io.pluto.pixelpong.frames.game.GameFrame;
import io.pluto.pixelpong.frames.load.LoadFrame;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class Main {

    public final FileSystem fileSystem;
    public final LogOutput logOutput;
    public LoadFrame loadFrame;
    public Color ballColor;
    public Color paddleColor;
    public Color backgroundColor;
    public Color selBtnColor;
    public Color btnColor;
    public Color textColor;
    public JFrame currentFrame;
    public GameFrame gameFrame;
    public Font defFont;
    public boolean trail;
    public static final int VERSION_CODE = 2;
    public String VERSION_STRING;
    public VersionType VERSION_TYPE = VersionType.Alpha;
    private enum VersionType {
        Alpha,
        Beta,
        Release
    }

    public static void main(String[] args) {
        new Main();
    }

    public Main() {
        fileSystem = new FileSystem(this);
        logOutput = new LogOutput(fileSystem.getDir() + "\\logs", this);
        try {
            createVersionString();
            logOutput.print("Successfully created FileSystem and LogOutput Classes");
            sleep(500);
            logOutput.print("Creating Load Frame...");
            loadFrame = new LoadFrame(fileSystem);
            currentFrame = loadFrame;
            logOutput.print("Successfully Created Load Frame");
            sleep(500);
            logOutput.print("Loading Colors From File: \"colors.json\"");
            loadColors();
            logOutput.print("Successfully Loaded Colors From File: \"colors.json\"");
            sleep(500);
            loadFonts();
            sleep(500);
            logOutput.print("Loading Options from config.properties...");
            loadOptions();
            logOutput.print("Options Successfully Loaded");
            sleep(500);
            logOutput.print("Loading Custom Resources...");
            fileSystem.loadCustomResources();
            sleep(500);
            fileSystem.getAndLoadSelectedPack();
            sleep(5000);
            loadFrame.child.isRunning = false;
            logOutput.print("Creating GameFrame...");
            gameFrame = new GameFrame(fileSystem);
            currentFrame = gameFrame;
            logOutput.print("GameFrame Successfully Created");
        } catch (Exception e) {
            logOutput.printThrowable(e);
        }
    }

    public void createVersionString() {
        double version_id = ((VERSION_CODE - 1.0) / 10.0) + 1.0;
        char vChar;
        if (VERSION_TYPE == VersionType.Alpha) {
            vChar = 'a';
        } else if (VERSION_TYPE == VersionType.Beta) {
            vChar = 'b';
        } else {
            vChar = 'v';
        }
        VERSION_STRING = "" + vChar + version_id;
        logOutput.print("Loading PixelPong " + VERSION_STRING);
    }

    public void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            logOutput.printThrowable(e);
        }
    }

    public void loadOptions() {
        JSONParser jsonParser = new JSONParser();
        try {
            File file = new File(fileSystem.getDir() + "\\config.json");
            FileReader reader = new FileReader(file);
            Object obj = jsonParser.parse(reader);
            JSONObject options = (JSONObject) obj;
            trail = (Boolean) options.get("ballTrail");
            logOutput.print("'Show Ball Trail' is currently set to: " + trail);
        } catch (Exception e) {
            logOutput.printThrowable(e);
        }
    }

    public void loadColors() {
        JSONParser jsonParser = new JSONParser();
        try {
            InputStream is = fileSystem.getFileFromResourceAsStream("assets/data/colors.json");
            byte[] buffer = new byte[is.available()];
            is.read(buffer);
            File file = new File(fileSystem.getDir() + "\\temp\\colors.json");
            Files.write(file.toPath(), buffer);
            FileReader reader = new FileReader(file);
            logOutput.print("Started JSON Reader");
            Object obj = jsonParser.parse(reader);
            JSONObject list = (JSONObject) obj;
            logOutput.print("Reading JSON Object \"color.json\"");
            JSONObject game = (JSONObject) list.get("Game");
            getGameColors(game);
            JSONObject menu = (JSONObject) list.get("Menu");
            getMenuColors(menu);
        } catch (Exception e) {
            logOutput.printThrowable(e);
        }
    }

    public void getGameColors(JSONObject game) {
        try {
            logOutput.print("Reading JSON Object \"Game Colors\"");

            //Ball
            String ballColStr = (String) game.get("Ball");
            int ballCol = Integer.parseInt(ballColStr);
            ballColor = new Color(ballCol);
            logOutput.print("Loaded Ball Color: " + ballColStr);
            sleep(500);

            //Paddle
            String paddleColStr = (String) game.get("Paddle");
            int paddleCol = Integer.parseInt(paddleColStr);
            paddleColor = new Color(paddleCol);
            logOutput.print("Loaded Paddle Color: " + paddleColStr);
            sleep(500);
        } catch (Exception e) {
            logOutput.printThrowable(e);
        }
    }

    public void getMenuColors(JSONObject menu) {
        try {
            logOutput.print("Reading JSON Object \"Menu Colors\"");

            //Background
            String bgColStr = (String) menu.get("Background");
            int bgCol = Integer.parseInt(bgColStr);
            backgroundColor = new Color(bgCol);
            logOutput.print("Loaded Background Color: " + bgColStr);
            sleep(500);

            //Text
            String textColStr = (String) menu.get("Text");
            int textCol = Integer.parseInt(textColStr);
            textColor = new Color(textCol);
            logOutput.print("Loaded Text Color: " + textColStr);
            sleep(500);
        } catch (Exception e) {
            logOutput.printThrowable(e);
        }
    }

    public void loadFonts() {
        try {
            logOutput.print("Loading Default Font...");
            URL url = new URL("https://github.com/pluto7073/plutoprogs/raw/main/default.ttf");
            InputStream is = url.openStream();

            File file = new File(fileSystem.getDir() + "\\temp\\font.ttf");
            if (file.exists()) file.delete();
            Files.copy(is, file.toPath(), StandardCopyOption.REPLACE_EXISTING);

            defFont = Font.createFont(Font.TRUETYPE_FONT, file).deriveFont(12F);

            logOutput.print("Successfully Loaded Default Font");
        } catch (Exception e) {
            logOutput.printThrowable(e);
        }
    }

}
