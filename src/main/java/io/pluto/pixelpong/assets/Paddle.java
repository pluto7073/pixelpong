package io.pluto.pixelpong.assets;

import com.pluto7073.utils.vectors.Vector2f;
import com.pluto7073.utils.vectors.Vector2i;

import java.awt.*;

public class Paddle {

    private Vector2f pos;
    private final Color col;
    private final Vector2i size;

    public Paddle(Vector2f pos, Color col, Vector2i size) {
        this.pos = pos;
        this.col = col;
        this.size = size;
    }

    public void moveLeft() {
        if (!(pos.x <= 0)) {
            float x = pos.x;
            float y = pos.y;
            x -= 2.5f;
            pos = new Vector2f(x, y);
        } else pos.x += 0.1f;
    }

    public void moveRight() {
        if (!((pos.x + size.x) >= 799)) {
            float x = pos.x;
            float y = pos.y;
            x += 2.5f;
            pos = new Vector2f(x, y);
        } else pos.x -= 0.1f;
    }

    public void draw(Graphics g) {
        g.setColor(col);
        g.fill3DRect((int)pos.x, (int)pos.y, size.x, size.y, true);
    }

    public Vector2f getPos() {
        return pos;
    }

    public Vector2i getSize() {
        return size;
    }
}
