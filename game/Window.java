import javax.swing.*;

public class Window extends JFrame {
    private JPanel pan = new JPanel();
    private JButton solo = new JButton("Solo");
    private JButton multi = new JButton("Multiplayer");
    private JButton exit = new JButton("Exit");
    private ImageIcon imageleft = new ImageIcon("images/logo_left.png");
    private ImageIcon imageright = new ImageIcon("images/logo_right.png");

    public Window() { // Constructor to open a new window using JFrame 
        this.setTitle("RPG");
        this.setSize(400, 85);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        JLabel left_logo = new JLabel(imageleft);
        pan.add(left_logo);
        pan.add(solo);
        pan.add(multi);
        pan.add(exit);
        JLabel right_logo = new JLabel(imageright);
        pan.add(right_logo);
        this.setContentPane(pan);
        this.setVisible(true);
        exit.addActionListener(exit -> { // add action to exit button
            System.exit(0);
        });
        solo.addActionListener(start -> { // to solo button
            this.setVisible(false);
            try {
                GameMain.getInstance().setGame(new Sgame()); // Start a new solo game using Sgame constructor
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}