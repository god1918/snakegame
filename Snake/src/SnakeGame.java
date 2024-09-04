import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

public class SnakeGame extends JPanel implements ActionListener , KeyListener {


    static final int SCREEN_WIDTHX = 600;
    static final int SCREEN_HEIGHTY = 600;
    static final int SIZE = 30;
    static final int DELAY = 150;

    final int[] x = new int[SCREEN_HEIGHTY*SCREEN_WIDTHX];
    final int[] y = new int[SCREEN_HEIGHTY*SCREEN_WIDTHX];
    int score;
    int body = 2;
    int food;
    int foodx;
    int foody;
    int currentdirectionX= 1;
    int CurrentdirectionY =0;
    boolean running = false;
    Random random;
    Timer timer;

    public SnakeGame(){
         random = new Random();
         this.setPreferredSize(new Dimension(SCREEN_HEIGHTY,SCREEN_WIDTHX));
         this.setBackground(Color.black);
         this.setFocusable(true);
         this.addKeyListener(this);
         StartGame();
    }

    public void StartGame(){
        CreateFood();
        running = true;
        timer = new Timer(DELAY,this);
        timer.start();


    }

    public void paintComponent(Graphics g){
             super.paintComponent(g);
             draw(g);

    }

    public void draw(Graphics g){
        if(running) {
//            for (int i = 0; i < SCREEN_HEIGHTY; i++) {
//                g.drawLine(i * SIZE, 0, i * SIZE, SCREEN_HEIGHTY);
//                g.drawLine(0, i * SIZE, SCREEN_WIDTHX, i * SIZE);
//            }
            g.setColor(Color.red);
            g.fillOval(foodx, foody, SIZE, SIZE);

            for (int i = 0; i < body; i++) {
                g.setColor(Color.green);
                g.fillRect(x[i], y[i], SIZE, SIZE);
            }
        }
        else{
            GameOver(g);
        }

    }

    public void GameOver(Graphics g){
         g.setColor(Color.yellow);
         g.setFont(new Font("Serif",Font.BOLD,75));
         FontMetrics met = getFontMetrics(g.getFont());
         g.drawString("Score : "+score,SCREEN_HEIGHTY/4,SCREEN_WIDTHX/3);
        g.drawString("GAME OVER ",SCREEN_HEIGHTY/7,SCREEN_WIDTHX/2);

    }

    public void move(){
        for(int i=body;i>0;i--){
            x[i] = x[i-1];
            y[i] = y[i-1];
        }
        x[0] = x[0]+currentdirectionX*SIZE;
        y[0] = y[0] + CurrentdirectionY*SIZE;

    }
    public void CreateFood(){
        foodx = random.nextInt(((int) SCREEN_WIDTHX/SIZE))*SIZE;
        foody = random.nextInt(((int) SCREEN_HEIGHTY/SIZE))*SIZE;


    }


    public void CheckFood(){
        if(x[0] == foodx && y[0] == foody){
            CreateFood();
            body++;
            score++;
        }

    }

    public void CheckCollied(){
        for(int i =body;i>0;i--){
            if(x[0]==x[i] && y[0]== y[i]){
                running = false;
            }
            if(x[0] < 0){
                running = false;
            }
            if(x[0] >= SCREEN_WIDTHX){
                running = false;
            }
            if(y[0] < 0){
                running = false;
            }
            if(y[0] >= SCREEN_HEIGHTY){
                running = false;
            }
            if(!running){
                timer.stop();
            }
        }
    }



    @Override
    public void actionPerformed(ActionEvent e) {
        if(running){
            move();
            CheckFood();
            CheckCollied();
        }
        repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        switch (key){
            case KeyEvent.VK_LEFT:
                if(currentdirectionX  != 1) {
                    currentdirectionX = -1;
                    CurrentdirectionY = 0;
                }
                break;
            case KeyEvent.VK_RIGHT:
                if(currentdirectionX != -1) {
                    currentdirectionX = 1;
                    CurrentdirectionY = 0;
                }
                break;
            case KeyEvent.VK_UP:
                if(CurrentdirectionY != 1) {
                    currentdirectionX = 0;
                    CurrentdirectionY = -1;
                }
                break;

            case KeyEvent.VK_DOWN:
                if(CurrentdirectionY != -1) {
                    currentdirectionX = 0;
                    CurrentdirectionY = 1;
                }
                break;

        }

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
