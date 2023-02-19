import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;
import java.util.Random;

public class panel extends JPanel implements ActionListener {

    static int width = 1200;
    static int height = 600;
    static int unit = 50;

    Timer timer;
    static int delay = 160;
    Random random;

    //food coordinates
    int fx, fy;

    //body length of the snake initially
    int body = 3;
    char dir = 'R';
    int score;

    // to check the state of the game
    boolean flag = false;

    //arrays to store the coordinates of snake
    int xsnake[] = new int[288];
    int ysnake[] = new int[288];

    panel() {
        //sets the panel size
        this.setPreferredSize(new Dimension(width, height));
        this.setBackground(Color.BLACK);
        // to enable the keyboard input
        this.setFocusable(true);
        // adding keyListener, it tells me what key is pressed
        this.addKeyListener(new mykey());

        random = new Random();
        //function
        gamestart();
    }

    public void gamestart() {
        // flag is set true means game is running
        flag = true;
        //function to spawn the food
        spawnfood();
        // we are applying the delay to this because we didnt created any objects in panel so we use this
        timer = new Timer(delay, this);
        timer.start();
    }

    public void spawnfood() {
        // random multiple of 50 from 0 to 24
        fx = random.nextInt((int) width / unit) * unit;
        // random int between 0 to 12 amd mul by 50 (multiples of 50)
        fy = random.nextInt((int) height / unit) * unit;
    }

    public void paintComponent(Graphics graphic) {
        super.paintComponent(graphic);
        draw(graphic);
    }

    public void draw(Graphics graphic) {
        if (flag == true) {
            //spawned the food particle
            graphic.setColor(Color.orange);
            graphic.fillOval(fx, fy, unit, unit);
       // spawning the snake 0 is the head of the snake
       for(int i =0;i<body;i++){
           if(i==0){
               //for head
               graphic.setColor(Color.red);
               //coordinates of rect and height and width
               graphic.fillRect(xsnake[i],ysnake[i],unit,unit);
           }
           else{
               //for rest of body of snake
               graphic.setColor(Color.green);
               graphic.fillRect(xsnake[i],ysnake[i],unit,unit);
           }
       }
       //spawning the score display
       graphic.setColor(Color.CYAN);
       //setting the font
       graphic.setFont(new Font("comic sans", Font.BOLD, 40));
      // fontmatrics gives the length of font
       FontMetrics fme = getFontMetrics(graphic.getFont());
       graphic.drawString("Score:" + score,(width - fme.stringWidth("Score:"+score))/2,graphic.getFont().getSize());
       }
        else{
            gameover(graphic);
        }
    }

    public void gameover(Graphics graphic){
        //drawing the score
        graphic.setColor(Color.CYAN);
        //setting the font
        graphic.setFont(new Font("comic sans", Font.BOLD, 40));
        // fontmatrics gives the length of font
        FontMetrics fme = getFontMetrics(graphic.getFont());
        graphic.drawString("Score:" + score,(width - fme.stringWidth("Score:"+score))/2,graphic.getFont().getSize());

        //drawing the gameover text
        graphic.setColor(Color.red);
        //setting the font
        graphic.setFont(new Font("comic sans", Font.BOLD, 40));
        // fontmatrics gives the length of font
        FontMetrics fme1 = getFontMetrics(graphic.getFont());
        graphic.drawString("Game Over",(width - fme1.stringWidth("Game Over"))/2,height/2);

        //drawing the replay prompt
        graphic.setColor(Color.green);
        //setting the font
        graphic.setFont(new Font("comic sans", Font.BOLD, 40));
        // fontmatrics gives the length of font
        FontMetrics fme2 = getFontMetrics(graphic.getFont());
        graphic.drawString("Press R to replay",(width - fme2.stringWidth("Press R to replay"))/2,height/2-150);

    }

    public void move(){
        // starting from tail updating the rest of the body except the head
        for(int i = body;i>0;i--){
            xsnake[i] = xsnake[i-1];
            ysnake[i] = ysnake[i-1];
        }
        switch(dir){
            // updating the head of the snake
            case 'R':
                xsnake[0] = xsnake[0] + unit;
            break;
            case 'L':
                xsnake[0] = xsnake[0] - unit;
            break;
            case 'U':
                ysnake[0] = ysnake[0] - unit;
            break;
            case 'D':
                ysnake[0] = ysnake[0] + unit;
            break;
        }
    }

        public void check(){
        // checking out of bounds
        if(xsnake[0]<0){
            flag = false;
        }
        else if(xsnake[0]>width){
            flag = false;
        }
        else if(ysnake[0]<0){
            flag = false;
        }
        else if(ysnake[0]>height){
            flag = false;
        }
        
        //checking hit with body
            for(int i = body ;i>0;i--){
                if((xsnake[0] == xsnake[i]) && (ysnake[0] == ysnake[i])){
                    flag = false;
                }

                if(flag == false){
                    timer.stop();
                }
            }
        }

        public void eat(){
          if((xsnake[0] == fx) && (ysnake[0] == fy)){
              body++;
              score++;
              spawnfood();
            }
        }
    public class mykey extends KeyAdapter {
        public void keyPressed(KeyEvent e){
            switch(e.getKeyCode()){
                case KeyEvent.VK_UP:
                   if(dir!= 'D'){
                       dir = 'U';
                   }
                   break;
                case KeyEvent.VK_DOWN:
                    if(dir!= 'U'){
                        dir = 'D';
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    if(dir!='L'){
                        dir = 'R';
                    }
                    break;
                case KeyEvent.VK_LEFT:
                    if(dir!='R'){
                        dir = 'L';
                    }
                    break;
                case KeyEvent.VK_R:
                    if(!flag){
                        score = 0;
                        body = 3;
                        dir = 'R';
                        Arrays.fill(xsnake, 0);
                        Arrays.fill(ysnake, 0);
                        gamestart();
                    }
                    break;

            }
        }
    }

    public void actionPerformed(ActionEvent e) {
     if(flag){
         move();
         eat();
         check();
     }
     repaint();

    }
}