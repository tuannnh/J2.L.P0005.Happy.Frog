/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package happyfrog;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.List;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Rectangle2D;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import static java.lang.Thread.sleep;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author tuannnh
 */
public class GameScreen extends JPanel implements KeyListener {

    JLabel lblPoint;
    Random rand = new Random();
    private final int WIN_HEIGHT = 350;
    private final int WIN_WIDTH = 500;
    private final int PIPE_WIDTH = 50;
    private final int PIPE_GAP = 100;
    private final int FROG_WIDTH = 25;
    private final int FROG_HEIGHT = 25;
    private final int FROG_START_X = 100;
    private final int FROG_START_Y = 180;
    private int dxFrog;
    private int dyFrog;
    private final int MOVE_SPEED = 5;
    private final int GRAVITY = 4;
    private final int FROG_JUMP_SPEED = 40;

    protected boolean alive = false;
    protected boolean running = false;
    protected int points;

    private Rectangle2D frog = new Rectangle2D.Double(FROG_START_X, FROG_START_Y, FROG_WIDTH,
            FROG_HEIGHT);

    private int pipe1Height;
    private int pipe2Height;
    private int pipe3Height;
    private int pipe4Height;
    private int pipe5Height;
    private int pipe6Height;

    private Rectangle2D pipe1 = new Rectangle2D.Double(WIN_WIDTH + PIPE_WIDTH * 2, 0, 0, 0);
    private Rectangle2D pipe2 = new Rectangle2D.Double(WIN_WIDTH + PIPE_WIDTH * 2, 0, 0, 0);
    private Rectangle2D pipe3 = new Rectangle2D.Double(WIN_WIDTH + PIPE_WIDTH * 6, 0, 0, 0);
    private Rectangle2D pipe4 = new Rectangle2D.Double(WIN_WIDTH + PIPE_WIDTH * 6, 0, 0, 0);
    private Rectangle2D pipe5 = new Rectangle2D.Double(WIN_WIDTH + PIPE_WIDTH * 10, 0, 0, 0);
    private Rectangle2D pipe6 = new Rectangle2D.Double(WIN_WIDTH + PIPE_WIDTH * 10, 0, 0, 0);

    Thread checkCollision;
    Thread moveScreen;

    List<Rectangle2D> pipes;
    JButton btn;

    String pointSaveFile = "points.txt";
    String objectSaveFile = "objects.txt";

    List<Rectangle2D> dataList;

    AwardWindow award;

    public GameScreen(JButton btnStart, JLabel point) {
        this.addKeyListener(this);
        this.setFocusable(true);
        this.btn = btnStart;
        this.lblPoint = point;
        initComponents();
    }

    private void initComponents() {
        this.setBackground(new Color(102, 204, 255));
        pipes = new ArrayList();
        pipes.add(pipe1);
        pipes.add(pipe2);
        pipes.add(pipe3);
        pipes.add(pipe4);
        pipes.add(pipe5);
        pipes.add(pipe6);
        createNewGame();
        try {
            moveScreen = new Thread() {
                public void run() {
                    while (true) {
                        if (btn.getText().equalsIgnoreCase("Pause") && alive) {
                            moveScreen();
                        }
                        if (frog.getMinX() == pipe1.getMaxX() || frog.getMinX() == pipe3.getMaxX()
                                || frog.getMinX() == pipe5.getMaxX()) {
                            points++;
                        }
                        try {
                            sleep(45);
                        } catch (InterruptedException ex) {
                            System.out.println(ex);
                        }
                    }

                }
            };
        } catch (Exception e) {
        }

        checkCollision = new Thread() {
            public void run() {
                while (true) {
                    try {
                        if (collision() && alive) {
                            alive = false;
                            award = new AwardWindow(points);
                            award.setVisible(true);
                            award.setBounds(200, 300, 400, 300);
                        }
                        sleep(50);

                    } catch (Exception e) {
                        System.out.println(e);
                    }

                }

            }
        };
        moveScreen.start();
        checkCollision.start();
    }

    protected void createNewGame() {

        randomPipeHeight(1);
        randomPipeHeight(2);
        randomPipeHeight(3);

        pipe1.setRect(WIN_WIDTH + PIPE_WIDTH * 2, 0, 0, 0);
        pipe2.setRect(WIN_WIDTH + PIPE_WIDTH * 2, 0, 0, 0);
        pipe3.setRect(WIN_WIDTH + PIPE_WIDTH * 6, 0, 0, 0);
        pipe4.setRect(WIN_WIDTH + PIPE_WIDTH * 6, 0, 0, 0);
        pipe5.setRect(WIN_WIDTH + PIPE_WIDTH * 10, 0, 0, 0);
        pipe6.setRect(WIN_WIDTH + PIPE_WIDTH * 10, 0, 0, 0);

        dxFrog = FROG_START_X;
        dyFrog = FROG_START_Y;
        points = 0;
        repaint();
        alive = true;
    }

    public void updatePoint() {
        lblPoint.setText("" + points);
    }

    public void saveGame() {
        try {
            FileWriter fw = new FileWriter(pointSaveFile);
            fw.write("" + points);
            fw.close();
            dataList = new ArrayList<>();
            dataList.add(frog);
            dataList.add(pipe1);
            dataList.add(pipe2);
            dataList.add(pipe3);
            dataList.add(pipe4);
            dataList.add(pipe5);
            dataList.add(pipe6);

            FileOutputStream fos = new FileOutputStream(objectSaveFile);
            ObjectOutputStream out = new ObjectOutputStream(fos);
            out.writeObject(dataList);
            out.close();
            fos.close();
        } catch (Exception e) {
        }

    }

    public void loadGame() {
        try {
            FileReader fr = new FileReader(pointSaveFile);
            BufferedReader br = new BufferedReader(fr);
            points = Integer.parseInt(br.readLine());
            
            dataList = new ArrayList<>();
            FileInputStream fis = new FileInputStream(objectSaveFile);
            ObjectInputStream out = new ObjectInputStream(fis);
            dataList = (List<Rectangle2D>)out.readObject();
            
            frog.setRect(dataList.get(0));
            pipe1.setRect(dataList.get(1));
            pipe2.setRect(dataList.get(2));
            pipe3.setRect(dataList.get(3));
            pipe4.setRect(dataList.get(4));
            pipe5.setRect(dataList.get(5));
            pipe6.setRect(dataList.get(6));
            
            fis.close();
            out.close();
            
            repaint();
            btn.setText("Resume");
        } catch (Exception e) {
        }

    }

    @Override
    protected void paintComponent(Graphics grphcs) {
        super.paintComponent(grphcs);
        Graphics2D gr = (Graphics2D) grphcs;
        gr.draw(frog);

        gr.draw(pipe1);
        gr.draw(pipe2);
        gr.draw(pipe3);
        gr.draw(pipe4);
        gr.draw(pipe5);
        gr.draw(pipe6);

    }

    private void randomPipeHeight(int coupleNumber) {
        if (coupleNumber == 1) {
            pipe1Height = rand.nextInt((190 - 125) + 1) + 125;
        }
        if (coupleNumber == 2) {
            pipe3Height = rand.nextInt((190 - 125) + 1) + 125;
        }
        if (coupleNumber == 3) {
            pipe5Height = rand.nextInt((190 - 125) + 1) + 125;
        }
    }

    private boolean collision() {
        for (Rectangle2D aPipe : pipes) {
            if (frog.intersects(aPipe)) {
                return true;
            }
        }

        if (frog.getMaxY() >= WIN_HEIGHT) {
            return true;
        }
        return false;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public synchronized void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            if (alive && !btn.getText().equalsIgnoreCase("Resume")) {
                if (frog.getMinY() <= 10) {
                    dyFrog = 15;
                } else {
                    dyFrog -= FROG_JUMP_SPEED;
                }
                frog.setRect(dxFrog, dyFrog, FROG_WIDTH, FROG_HEIGHT);
                repaint();
            }

        }
    }

    public void moveScreen() {
        updatePoint();
        dyFrog += GRAVITY;
        frog.setRect(dxFrog, dyFrog, FROG_WIDTH, FROG_HEIGHT);
        pipe1.setRect(pipe1.getX() - MOVE_SPEED, 0, PIPE_WIDTH, pipe1Height);
        pipe2.setRect(pipe2.getX() - MOVE_SPEED, pipe1Height + PIPE_GAP, PIPE_WIDTH, WIN_HEIGHT - (pipe1Height + PIPE_GAP));
        pipe3.setRect(pipe3.getX() - MOVE_SPEED, 0, PIPE_WIDTH, pipe3Height);
        pipe4.setRect(pipe4.getX() - MOVE_SPEED, pipe3Height + PIPE_GAP, PIPE_WIDTH, WIN_HEIGHT - (pipe3Height + PIPE_GAP));
        pipe5.setRect(pipe5.getX() - MOVE_SPEED, 0, PIPE_WIDTH, pipe5Height);
        pipe6.setRect(pipe6.getX() - MOVE_SPEED, pipe5Height + PIPE_GAP, PIPE_WIDTH, WIN_HEIGHT - (pipe5Height + PIPE_GAP));
        if (pipe1.getMaxX() == 0) {
            randomPipeHeight(1);
            pipe1.setRect(WIN_WIDTH, 0, PIPE_WIDTH, pipe1Height);
            pipe2.setRect(WIN_WIDTH, WIN_HEIGHT - pipe2Height, PIPE_WIDTH, pipe2Height);
        }
        if (pipe3.getMaxX() == 0) {
            randomPipeHeight(2);
            pipe3.setRect(WIN_WIDTH, 0, PIPE_WIDTH, pipe3Height);
            pipe4.setRect(WIN_WIDTH, WIN_HEIGHT - pipe4Height, PIPE_WIDTH, pipe4Height);
        }
        if (pipe5.getMaxX() == 0) {
            randomPipeHeight(3);
            pipe5.setRect(WIN_WIDTH, 0, PIPE_WIDTH, pipe5Height);
            pipe6.setRect(WIN_WIDTH, WIN_HEIGHT - pipe6Height, PIPE_WIDTH, pipe6Height);
        }
        repaint();
    }

}
