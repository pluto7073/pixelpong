package io.pluto.pixelpong.frames.game;

import com.pluto7073.systems.FileSystem;
import com.pluto7073.systems.LogOutput;
import io.pluto.pixelpong.Main;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;

public class GameFrame extends JFrame {

    public final FileSystem fileSystem;
    public final LogOutput logOutput;
    public final Main main;
    public final GamePanel child;

    public GameFrame(FileSystem fileSystem) {
        this.fileSystem = fileSystem;
        main = this.fileSystem.main;
        logOutput = main.logOutput;
        setTitle("PixelPong");
        InputStream is = fileSystem.getFileFromResourceAsStream("assets/images/icon.png");
        try {
            Image icon = ImageIO.read(is);
            setIconImage(icon);
        } catch (IOException e) {
            logOutput.printThrowable(e);
        }
        setSize(new Dimension(800, 600));
        setResizable(false);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new GameClosing(this));
        child = new GamePanel(this);
        add(child);
        setVisible(true);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(dim.width / 2 - getWidth() / 2, dim.height / 2 - getHeight() / 2);
    }

}
