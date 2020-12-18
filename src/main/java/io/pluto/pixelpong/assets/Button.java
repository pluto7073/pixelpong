package io.pluto.pixelpong.assets;

import com.pluto7073.utils.vectors.Vector2i;
import io.pluto.pixelpong.Main;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class Button extends Component {

    private final Vector2i pos;
    private final Vector2i size;
    private String text;
    private final Font font;
    private final Color textCol;
    private int activeOutline;
    private final Main main;

    public Button(Vector2i pos, Vector2i size,  String text, Font font, Color textCol, Main main) {
        this.pos = pos;
        this.size = size;
        this.text = text;
        this.font = font;
        this.textCol = textCol;
        this.main = main;
        activeOutline = 0;
    }

    public void drawButton(Graphics g) {
        try {
            InputStream is;
            Image btn;
            if (main.fileSystem.customBtn) {
                File file = new File(main.fileSystem.packPath + "\\assets\\images\\button.png");
                btn = ImageIO.read(file);
            } else {
                is = main.fileSystem.getFileFromResourceAsStream("assets/images/button.png");
                btn = ImageIO.read(is);
            }
            InputStream is2;
            Image selBtn;
            if (main.fileSystem.customSelectedBtn) {
                File file = new File(main.fileSystem.packPath + "\\assets\\images\\buttonSel.png");
                selBtn = ImageIO.read(file);
            } else {
                is2 = main.fileSystem.getFileFromResourceAsStream("assets/images/buttonSel.png");
                selBtn = ImageIO.read(is2);
            }
            if (activeOutline == 0) {
                g.drawImage(btn, pos.x - (size.x / 2), pos.y - (size.y / 2), size.x, size.y, this);
            } else if (activeOutline == 1) {
                g.drawImage(selBtn, pos.x - (size.x / 2), pos.y - (size.y  /2), size.x, size.y, this);
            }
            g.setFont(font.deriveFont(24F));
            g.setColor(textCol);
            g.drawString(text, pos.x - (size.x / 2) + 25, pos.y + 10);
        } catch (IOException e) {
            main.logOutput.printThrowable(e);
        }
    }

    public void checkSelection(int x, int y) {
        if (x >= pos.x - (size.x / 2) && x <= pos.x + (size.x / 2) + 25) {
            if (y >= pos.y - (size.y / 2) && y <= pos.y + (size.y / 2) + 25) {
                activeOutline = 1;
            } else {
                activeOutline = 0;
            }
        } else {
            activeOutline = 0;
        }
    }

    public boolean checkPressed() {
        return activeOutline == 1;
    }

    public void setText(String text) {
        this.text = text;
    }

}
