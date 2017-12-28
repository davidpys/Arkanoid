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
        this.setBounds(300, 100, 500, 550);
        panelGame.setBackground(Color.BLACK);

        startButton.setText("Start");
        stopButton.setText("Stop");

        panelButton.add(startButton);
        panelButton.add(stopButton);

        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                startGame();
                startButton.setVisible(false);
                stopButton.setVisible(true);
//                try {
//                    Thread.sleep(2000);
//                } catch (InterruptedException ex) {
//                    Logger.getLogger(ArkanoidNetBeans.class.getName()).log(Level.SEVERE, null, ex);
//                }

            }
        });
        stopButton.setVisible(false);
        stopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                stopGame();
                startButton.setVisible(true);
                stopButton.setVisible(false);
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

        Ball tabBall = new Ball();
        Thread threadBall;
        Thread threadPaddle;
        Paddle paddle = new Paddle();
//        Ball newBall = new Ball();
        JPanel thisPanel = this;

        public void startBall() {
//             Ball newBall = new Ball();
//            tabBall[0] = new Ball();
            threadBall = new Thread(new BallRunnable(tabBall));
            threadPaddle = new Thread(new PaddleRunnable(paddle));
            threadBall.start();
            threadPaddle.start();
            int i =1;
            System.out.println(i++);
        }

        public void stopBall() {
            threadBall.interrupt();
            threadPaddle.interrupt();
        }

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(Paddle.getImg(), paddle.x, paddle.y, null);
            g.drawImage(Ball.getImg(), tabBall.x, tabBall.y, null);
            
        }

        public class BallRunnable implements Runnable {

            Ball ball;
            Paddle paddle;

            public BallRunnable(Ball ball ) {
                this.ball = ball;
                
            }

            @Override
            public void run() {
                try {
                    while (!threadBall.isInterrupted()) {
                        this.ball.traficBall(thisPanel);
                        repaint();
                        Thread.sleep(5);
                    }
                } catch (InterruptedException ex) {
                    System.out.println(ex.getMessage());
                }
            }

        }
        public class PaddleRunnable implements Runnable {

            
            Paddle paddle;

            public PaddleRunnable(Paddle paddle ) {
                this.paddle = paddle;
                
            }

            @Override
            public void run() {
                try {
                    while (!threadPaddle.isInterrupted()) {
                        this.paddle.traficBall(thisPanel);
                        repaint();
                        Thread.sleep(1);
                    }
                } catch (InterruptedException ex) {
                    System.out.println(ex.getMessage());
                }
            }
        }
    }

}

class Ball {
    Paddle padlle;

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

    int x = 200;
    int y = 395;
    int dx = 1;
    int dy = 1;
    int xBall = ball.getWidth(null);
    int yBall = ball.getHeight(null);
}

class Paddle{
    
    public static Image getImg() {
        return Paddle.paddle;
    }

    public void traficBall(JPanel panel) {
        //bound - granica, obramowanie
        Rectangle bound = panel.getBounds();
        x += dx;
        y += dy;

        if (y + yPaddle >= bound.getMaxY()) {
            y = (int) (bound.getMaxY() - yPaddle);
            dy = -dy;
        }
        if (x + xPaddle >= bound.getMaxX()) {
            x = (int) (bound.getMaxX() - xPaddle);
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
    public static Image paddle = new ImageIcon("paddle.gif").getImage();

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    int x = 150;
    int y = 400;

    public int getxPaddle() {
        return xPaddle;
    }

    public int getyPaddle() {
        return yPaddle;
    }
    int dx = 1;
    int dy = 0;
    int xPaddle = paddle.getWidth(null);
    int yPaddle = paddle.getHeight(null);
}
