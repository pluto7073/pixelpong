package io.pluto.pixelpong.frames.game.states;

import com.pluto7073.utils.vectors.*;
import io.pluto.pixelpong.Main;
import io.pluto.pixelpong.assets.Ball;
import io.pluto.pixelpong.assets.Paddle;
import io.pluto.pixelpong.frames.game.AbstractState;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

public class MainState extends AbstractState {

    public final Main main;
    public final Ball ball;
    public final Paddle paddle;
    public boolean left = false;
    public boolean right = false;
    public boolean f3 = false;
    public boolean t = false;

    public MainState(StateManager stateManager, Main main) {
        super(stateManager);
        this.main = main;
        ball = new Ball(new Vector2f(380.0f, 280.0f),
                new Vector2i(20, 20),
                main.ballColor,
                new Vector2i(25, 25),
                main.defFont,
                main.trail,
                main);
        paddle = new Paddle(new Vector2f(300, 500),
                main.paddleColor,
                new Vector2i(200, 25));
    }

    public void tick() {
        float x = paddle.getPos().x;
        float y = paddle.getPos().y;
        int sizeX = paddle.getSize().x;
        int sizeY = paddle.getSize().y;
        ball.move();
        ball.checkCollision((int)x, (int)y, sizeX, sizeY);
        ball.getHighScore();
        if (left) paddle.moveLeft();
        if (right) paddle.moveRight();
        main.sleep(1000 / 120);
    }

    public void draw(Graphics g) {
        g.setColor(main.backgroundColor);
        g.fillRect(0, 0, 800, 600);
        ball.draw(g);
        paddle.draw(g);
    }

    public void keyPressed(int k) {
        if (k == KeyEvent.VK_ESCAPE) {
            Container contentPane = main.gameFrame.getContentPane();
            BufferedImage image = new BufferedImage(contentPane.getWidth(), contentPane.getHeight(),
                    BufferedImage.TYPE_INT_RGB);
            Graphics2D g2d = image.createGraphics();
            contentPane.printAll(g2d);
            g2d.dispose();
            clearKeyPresses();
            stateManager.states.push(new PauseState(stateManager, image, main));
        }
        if (k == KeyEvent.VK_LEFT) left = true;
        if (k == KeyEvent.VK_RIGHT) right = true;
        if (k == KeyEvent.VK_F3) f3 = true;
        if (k == KeyEvent.VK_T) t = true;
    }

    public void keyReleased(int k) {
        if (k == KeyEvent.VK_LEFT) left = false;
        if (k == KeyEvent.VK_RIGHT) right = false;
        if (k == KeyEvent.VK_F3) f3 = false;
        if (k == KeyEvent.VK_T) t = false;
    }

    public void mouseClicked(MouseEvent e) {

    }

    public void mouseDown(MouseEvent e) {

    }

    public void mouseUp(MouseEvent e) {

    }

    public void mouseEntered(MouseEvent e) {

    }

    public void clearKeyPresses() {
        left = false;
        right = false;
        f3 = false;
        t = false;
    }

}
