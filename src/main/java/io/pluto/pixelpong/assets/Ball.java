package io.pluto.pixelpong.assets;

import com.pluto7073.utils.vectors.*;
import io.pluto.pixelpong.Main;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.awt.*;
import java.io.*;
import java.util.Stack;

public class Ball {

    private Vector2f pos;
    private final Vector2i size;
    private final Color col;
    private Vector2i vel;
    private final Vector2i defVel;
    private int score;
    private final Font font;
    private int highScore;
    private final boolean trail;
    private final Stack<Vector2f> iStack = new Stack<>();
    private final Main main;

    public Ball(Vector2f pos, Vector2i size, Color col, Vector2i vel, Font font, boolean trail, Main main) {
        this.pos = pos;
        this.size = size;
        this.col = col;
        this.vel = vel;
        this.defVel = vel;
        this.font = font.deriveFont(24f);
        this.trail = trail;
        this.main = main;
        score = 0;
        highScore = 0;
        getHighScore();
    }

    public void move() {
        float x = pos.x;
        float y = pos.y;
        x += (vel.x / 10.0);
        y += (vel.y / 10.0);
        if (x < -0.01 || x > 799.99) {
            vel.x *= -1;
        }
        if (y < -0.01 || y > 599.99) {
            if (y > 599.99) {
                reset();
                return;
            }
            vel.y *= -1;
        }
        pos = new Vector2f(x, y);
        iStack.add(0, pos);
        if (iStack.size() == 50) {
            iStack.remove(49);
        }
    }

    public void draw(Graphics g) {
        g.setColor(col);
        g.fill3DRect((int)pos.x, (int)pos.y, size.x, size.y, true);
        g.setFont(font);
        g.drawString("Current Score: " + score, 10, 100);
        g.drawString("High Score: " + highScore, 10, 200);
        if (trail) {
            for (int i = 0; i < iStack.size(); i++) {
                if (i < 50) {
                    Vector2f p = iStack.get(i);
                    g.setColor(new Color(col.getRed(), col.getGreen(), col.getBlue(), (int)4.25 * (25 + -(i - 25))));
                    g.fillRect((int) p.x, (int) p.y, size.x, size.y);
                }
            }
        }
    }

    public void checkCollision(int x, int y, int sizeX, int sizeY) {
        int xVel = vel.x;
        int yVel = vel.y;
        if ((pos.x >= x && pos.x <= x + sizeX)) {
            if (pos.y >= y && pos.y <= y + sizeY) {
                yVel *= -1;
                if (xVel > 0) xVel += 1;
                else if (xVel < 0) xVel -= 1;
                if (yVel > 0) yVel += 1;
                else if (yVel < 0) yVel -= 1;
                vel = new Vector2i(xVel, yVel);
                pos.y -= pos.y - y;
                modifyScore(1);
            }
        }
    }

    public void modifyScore(int modifier) {
        score += modifier;
    }

    public void getHighScore() {
        File file = new File(main.fileSystem.getDir() + "\\data.json");
        try (FileReader reader = new FileReader(file)) {
            JSONParser parser = new JSONParser();
            JSONObject object = (JSONObject) parser.parse(reader);
            highScore = Integer.parseInt((String) object.get("highScore"));
        } catch (IOException | ParseException e) {
            main.logOutput.printThrowable(e);
        }
        if (score > highScore) {
            highScore = score;
            try (FileWriter writer = new FileWriter(file)) {
                JSONObject object = new JSONObject();
                object.put("highScore", "" + highScore);
                writer.write(object.toJSONString());
            } catch (IOException e) {
                main.logOutput.printThrowable(e);
            }
        }
    }

    private void reset() {
        if (score <= 20) {
            score = 0;
        } else {
            modifyScore((int) -(Math.floor(score / 2.0)));
        }
        pos.x = 380.0f;
        pos.y = 280.0f;
        vel = defVel;
        iStack.empty();
        iStack.add(0, pos);
    }

}
