import javax.swing.*;
public class frame extends JFrame {
    frame() {
        // adding the panel to frame
       this.add(new panel());
        //setting the title of the window
        this.setTitle("Snakegame");
        //ensuring uniform experiencing by fixing the size of the window
        this.setResizable(false);
        // layout manager will size the window according to dispaly
        this.pack();
        // by default it will be false , so put true to visible
        this.setVisible(true);
    }
}
