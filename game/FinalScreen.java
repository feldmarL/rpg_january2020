import javax.swing.*;

public class FinalScreen extends JFrame { // New window using JFrame
    private JPanel pan = new JPanel();
    private JButton solo = new JButton("Solo");
    private JButton multi = new JButton("Multiplayer");
    private JButton exit = new JButton("Exit");
    private ImageIcon imageleft = new ImageIcon("images/logo_left.png");
    private ImageIcon imageright = new ImageIcon("images/logo_right.png");

    public FinalScreen(boolean win) {
        this.setSize(650, 112);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        JLabel left_logo = new JLabel(imageleft);
        pan.add(left_logo);
        if (win) { // Change the title and the final sentence according to the win or the lose of the player
            this.setTitle("You win!");
            JLabel label = new JLabel("You succesfully eliminated all your enemys. Great job. Want to play another game ?");
            pan.add(label);
        } else {
            this.setTitle("You lose!");
            JLabel label = new JLabel("You've got killed by your ennemys. Nice try. Do you want to take your revenge ?");
            pan.add(label);
        }
        JLabel right_logo = new JLabel(imageright);
        pan.add(right_logo);
        pan.add(solo);
        pan.add(multi);
        pan.add(exit);
        this.setContentPane(pan);
        this.setVisible(true);
        exit.addActionListener(exit -> { // Add exit action to exit button
            System.exit(0);
        });
        solo.addActionListener(start -> { // Start a new Sgame if the user click on solo (same as start menu)
            this.setVisible(false);
            try {
                GameMain.getInstance().setGame(new Sgame());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}