/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arkanoidnetbeans;

import static arkanoidnetbeans.Ball.ball;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

/**
 *
 * @author Dawid
 */
public class ArkanoidNetBeans extends JFrame {

    private JPanel panelButton = new JPanel();
    private GamePanel panelGame = new GamePanel();
    private JButton startButton = new JButton();
    private JButton stopButton = new JButton();

    public ArkanoidNetBeans() {
        this.setTitle("Arkanoid z kropelki");
        this.setBounds(200, 200, 400, 400);
        panelGame.setBackground(Color.BLACK);

        startButton.setText("Start");
        stopButton.setText("Stop");

        panelButton.add(startButton);
        panelButton.add(stopButton);

        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startButton.setText("Pause");
                startGame();

            }
        });
        stopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                stopGame();
            }
        });
        this.getContentPane().add(panelGame);
        this.getContentPane().add(panelButton, BorderLayout.SOUTH);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void startGame() {
        panelGame.startBall();
    }

    public void stopGame() {
        panelGame.stopBall();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here

        new ArkanoidNetBeans().setVisible(true);
    }

    class GamePanel extends JPanel {

        Ball newBall = new Ball();
        Thread threadBall;
        JPanel thisPanel = this;

        public void startBall() {

            threadBall = new Thread(new BallRunnable(newBall));
            threadBall.start();
//            Ball firstBall = 
//            Ball newBall = new Ball();
//            for (int i = 0; i < 2000; i++) {
//                newBall.traficBall(this);
//                this.paint(this.getGraphics());
//                System.out.println("piłka nr:" + i);
//
//                try {
//                    Thread.sleep(1);
//                } catch (InterruptedException ex) {
//                    Logger.getLogger(ArkanoidNetBeans.class.getName()).log(Level.SEVERE, null, ex);
//                }
//            }
        }

        public void stopBall() {

        }

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);

            g.drawImage(Ball.getImg(), (newBall.getX()), newBall.getY(), null);
        }
    }

    public class BallRunnable implements Runnable {

        Ball ball;

        public BallRunnable(Ball ball) {
            this.ball = ball;
        }

        @Override
        public void run() {
            try {
                while (!Thread.currentThread().isInterrupted()) {

                    this.ball.traficBall(thisPanel);
                    repaint();
                    Thread.sleep(1);
                }
            } catch (InterruptedException ex) {
                Logger.getLogger(ArkanoidNetBeans.class.getName()).log(Level.SEVERE, null, ex);
                repaint();
            }
        }
    }

}

class Ball {

    public static Image getImg() {
        return Ball.ball;
    }

    public void traficBall(JPanel panel) {
        //bound - granica, obramowanie
        Rectangle bound = panel.getBounds();
        x += dx;
        y += dy;

        if (y + yBall >= bound.getMaxY()) {
            y = (int) (bound.getMaxY() - yBall);
            dy = -dy;
        }
        if (x + xBall >= bound.getMaxX()) {
            x = (int) (bound.getMaxX() - xBall);
            dx = -dx;
        }
        if (y < bound.getMinY()) {
            y = (int) (bound.getMinY());
            dy = -dy;
        }
        if (x < bound.getMinX()) {
            x = (int) (bound.getMinX());
            dx = -dx;
        }

    }
    public static Image ball = new ImageIcon("kropelka.gif").getImage();

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    int x = 0;
    int y = 0;
    int dx = 1;
    int dy = 1;
    int xBall = ball.getWidth(null);
    int yBall = ball.getHeight(null);
}
