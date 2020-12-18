package io.pluto.pixelpong.frames.load;

import com.pluto7073.systems.FileSystem;
import com.pluto7073.systems.LogOutput;
import io.pluto.pixelpong.Main;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class LoadFrame extends JFrame {

    public final Main main;
    public final FileSystem fileSystem;
    public final LogOutput logOutput;
    public final LoadPanel child;

    public LoadFrame(FileSystem fileSystem) {
        this.fileSystem = fileSystem;
        main = this.fileSystem.main;
        logOutput = main.logOutput;
        setTitle("PixelPong");
        try {
            Image icon = ImageIO.read(fileSystem.getFileFromResourceAsStream("assets/images/icon.png"));
            setIconImage(icon);
        } catch (IOException e) {
            e.printStackTrace();
        }
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setSize(new Dimension(600, 600));
        setUndecorated(true);
        child = new LoadPanel(this);
        add(child);
        setVisible(true);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(dim.width / 2 - getWidth() / 2, dim.height / 2 - getHeight() / 2);
    }

}
