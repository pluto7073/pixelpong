package io.pluto.pixelpong.frames.game.states;

import com.pluto7073.utils.Document;
import io.pluto.pixelpong.Main;
import io.pluto.pixelpong.frames.game.AbstractState;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Scanner;
import java.util.Stack;

public class ChangelogState extends AbstractState {

    private final Main main;
    private Document doc;

    public ChangelogState(StateManager stateManager, Main main) {
        super(stateManager);
        this.main = main;
        InputStream is = main.fileSystem.getFileFromResourceAsStream("assets/data/changelog.txt");
        try {
            Files.copy(is, Paths.get(main.fileSystem.getDir() + "\\temp\\changelog.txt"), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            main.logOutput.printThrowable(e);
        }
        Stack<String> changeLog = new Stack<>();
        File file = new File(this.main.fileSystem.getDir() + "\\temp\\changelog.txt");
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String data = scanner.nextLine();
                changeLog.push(data);
            }
            doc = new Document(changeLog);
        } catch (FileNotFoundException e) {
            this.main.logOutput.printThrowable(e);
        }
    }

    public void tick() {

    }

    public void draw(Graphics g) {
        g.setColor(main.backgroundColor);
        g.fillRect(0, 0, 800, 600);
        g.setColor(main.textColor);
        g.setFont(main.defFont.deriveFont(12f));
        String[] lines = doc.getLines();
        for (int i = 0; i < lines.length; i++) {
            String s = lines[i];
            g.drawString(s, 10, (i + 1) * 25);
        }
    }

    public void keyPressed(int k) {
        if (k == KeyEvent.VK_ESCAPE) {
            stateManager.states.pop();
        }
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
