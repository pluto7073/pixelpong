package com.pluto7073.systems;

import io.pluto.pixelpong.Main;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.Buffer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Stack;

public class FileSystem {

    public final Main main;
    private String[] packs = new String[0];
    private File[] packPaths = new File[0];
    public String selectedCustomResource;
    public boolean customBtn = false;
    public boolean customSelectedBtn = false;
    public InputStream button;
    public InputStream buttonSel;
    public String packPath;

    public FileSystem(Main main) {
        this.main = main;
        String runPath = getDir();
        File file = new File(runPath);
        if (!file.exists()) {
            file.mkdirs();
            String rsp = runPath + "\\customResources";
            file = new File(rsp);
            file.mkdirs();
            String logs = runPath + "\\logs";
            file = new File(logs);
            file.mkdirs();
            String temp = runPath + "\\temp";
            file = new File(temp);
            file.mkdirs();
            String config = runPath + "\\config.json";
            file = new File(config);
            try {
                file.createNewFile();
                FileWriter writer = new FileWriter(file);
                JSONObject object = new JSONObject();
                object.put("ballTrail", false);
                object.put("selectedPack", "<none>");
                writer.write(object.toJSONString());
                writer.close();
                file = new File(runPath + "\\data.json");
                file.createNewFile();
                writer = new FileWriter(file);
                object = new JSONObject();
                object.put("highScore", 0);
                writer.write(object.toJSONString());
                writer.close();
            } catch (IOException e) {
                main.logOutput.printThrowable(e);
            }
        }
        file = new File(runPath + "\\config.json");
        if (!file.exists()) {
            try {
                file.createNewFile();
                FileWriter writer = new FileWriter(file);
                JSONObject object = new JSONObject();
                object.put("ballTrail", false);
                object.put("selectedPack", "<none>");
                writer.write(object.toJSONString());
                writer.close();
                file = new File(runPath + "\\customResources\\resources.json");
                file.createNewFile();
                file = new File(runPath + "\\data.json");
                file.createNewFile();
                writer = new FileWriter(file);
                object = new JSONObject();
                object.put("highScore", "0");
                writer.write(object.toJSONString());
                writer.close();
            } catch (IOException e) {
                main.logOutput.printThrowable(e);
            }
        }
    }

    public String getDir() {
        File file = new File(".temp");
        try {
            if (file.createNewFile());
            String path = file.getAbsolutePath();
            file.delete();
            return path.replace("\\.temp", "\\run");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "C:";
    }

    public InputStream getFileFromResourceAsStream(String filename) {
        ClassLoader classLoader = getClass().getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(filename);
        if (inputStream == null) {
            throw new IllegalArgumentException("file not found! " + filename);
        } else {
            return inputStream;
        }
    }

    public void loadCustomResources() {
        String p = getDir() + "\\customResources";
        File crDir = new File(p);
        File[] files = crDir.listFiles();
        Stack<String> packs = new Stack<>();
        Stack<File> packPaths = new Stack<>();
        assert files != null;
        for (File f : files) {
            if (checkIfPack(f)) {
                packs.add(getPackID(f.getAbsolutePath()));
                packPaths.add(f);
            }
        }
        int size = packs.size();
        this.packs = new String[size];
        for (int i = 0; i < size; i++) {
            String pack = packs.elementAt(i);
            this.packs[i] = pack;
        }
        size = packPaths.size();
        this.packPaths = new File[size];
        for (int i = 0; i < size; i++) {
            File f = packPaths.elementAt(i);
            this.packPaths[i] = f;
        }
        createPackFile(this.packs, this.packPaths);
    }

    private boolean checkIfPack(File dir) {
        File[] files = dir.listFiles();
        if (files != null) {
            for (File f : files) {
                if (f.getName().equals("resources.pongpack")) {
                    return true;
                }
            }
        }
        return false;
    }

    public String getPackID(String packPath) {
        try {
            File file = new File(packPath + "\\resources.pongpack");
            FileReader reader = new FileReader(file);
            JSONParser parser = new JSONParser();
            Object obj = parser.parse(reader);
            JSONObject object = (JSONObject) ((JSONObject) obj).get("pack");
            if ((Integer.parseInt(object.get("version_target").toString())) == Main.VERSION_CODE) {
                return object.get("pack_id").toString();
            } else {
                main.logOutput.print("Pack " + object.get("pack_name") + " Is for an older version of PixelPong. Loading Default Resources...");
                return "<none>";
            }
        } catch (Exception e) {
            e.printStackTrace();
            main.logOutput.printThrowable(e);
        }
        return "0";
    }

    private void createPackFile(String[] ids, File[] paths) {
        JSONObject obj = new JSONObject();
        for (int i = 0; i < ids.length; i++) {
            String id = ids[i];
            File path = paths[i];
            obj.put(id, path.getAbsolutePath());
        }
        String json = obj.toJSONString();
        File file = new File(getDir() + "\\customResources\\resources.json");
        try (FileWriter writer = new FileWriter(file)) {
            writer.write(json);
            writer.close();
            main.logOutput.print("resources.json Successfully Updated");
        } catch (IOException e) {
            main.logOutput.printThrowable(e);
        }
    }

    public void getAndLoadSelectedPack() {
        main.logOutput.print("Loading Selected Pack...");
        File file = new File(getDir() + "\\config.json");
        try (FileReader reader = new FileReader(file)) {
            JSONParser parser = new JSONParser();
            JSONObject object = (JSONObject) parser.parse(reader);
            String selectedPackID = (String) object.get("selectedPack");
            selectedCustomResource = selectedPackID;
            if (selectedPackID.equals("<none>")) {
                main.logOutput.print("No Selected Pack", LogOutput.WARN);
                customBtn = false;
                customSelectedBtn = false;
                main.loadColors();
            } else {
                main.logOutput.print("Loading pack with ID " + selectedPackID);
                String path;
                file = new File(getDir() + "\\customResources\\resources.json");
                FileReader r = new FileReader(file);
                object = (JSONObject) parser.parse(r);
                path = (String) object.get(selectedPackID);
                packPath = path;
                File colors = new File(path + "\\assets\\data\\colors.json");
                Toolkit t = Toolkit.getDefaultToolkit();
                if (colors.exists()) {
                    r = new FileReader(colors);
                    object = (JSONObject) parser.parse(r);
                    getCustomGameColors((JSONObject) object.get("Game"));
                    getCustomMenuColors((JSONObject) object.get("Menu"));
                } else main.loadColors();
                File button = new File(path + "\\assets\\images\\button.png");
                if (button.exists()) {
                    BufferedImage img = ImageIO.read(button);
                    ByteArrayOutputStream os = new ByteArrayOutputStream();
                    ImageIO.write(img,"png", os);
                    InputStream is = new ByteArrayInputStream(os.toByteArray());
                    customBtn = true;
                    this.button = is;
                } else customBtn = false;
                File buttonSel = new File(path + "\\assets\\images\\buttonSel.png");
                if (buttonSel.exists()) {
                    InputStream is = Files.newInputStream(Paths.get(path + "\\assets\\images\\buttonSel.png"));
                    customSelectedBtn = true;
                    this.buttonSel = is;
                } else customSelectedBtn = false;

            }
        } catch (Exception e) {
            main.logOutput.printThrowable(e);
        }
    }

    public void getCustomGameColors(JSONObject game) {
        String ballColStr = (String) game.get("Ball");
        int ballCol = Integer.parseInt(ballColStr);
        main.ballColor = new Color(ballCol);

        String paddleColStr = (String) game.get("Paddle");
        int paddleCol = Integer.parseInt(paddleColStr);
        main.paddleColor = new Color(paddleCol);
    }

    public void getCustomMenuColors(JSONObject menu) {
        String bgColStr = (String) menu.get("Background");
        int bgCol = Integer.parseInt(bgColStr);
        main.backgroundColor = new Color(bgCol);

        String textColStr = (String) menu.get("Text");
        int textCol = Integer.parseInt(textColStr);
        main.textColor = new Color(textCol);
    }

    public String[] getPacks() {
        return packs;
    }

    public String getPackName(String packID) {
        File file = new File(getDir() + "\\customResources\\resources.json");
        try (FileReader reader = new FileReader(file)) {
            JSONParser parser = new JSONParser();
            JSONObject object = (JSONObject) parser.parse(reader);
            String path = (String) object.get(packID);
            file = new File(path + "\\resources.pongpack");
            FileReader r = new FileReader(file);
            object = (JSONObject) ((JSONObject) parser.parse(r)).get("pack");
            return (String) object.get("pack_name");
        } catch (IOException | ParseException e) {
            main.logOutput.printThrowable(e);
        }
        return packID;
    }

}
