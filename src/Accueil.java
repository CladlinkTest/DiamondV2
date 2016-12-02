import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 Created by mlucile on 02/12/16.
 */
public class Accueil extends JFrame
{
    private JButton easy;
    private JButton medium;
    private JButton hard;
    private JButton quitter;
    private int xSize, ySize;

    public Accueil()
    {
        Toolkit tk = Toolkit.getDefaultToolkit();
        xSize = (int) tk.getScreenSize().getWidth();
        ySize = (int) tk.getScreenSize().getHeight();
        initAttribut();
        creerWidget();
        setName("Diamond");
        setResizable(false);
        pack();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

    }

    private void initAttribut()
    {
        easy = new JButton("Easy mode");
        medium = new JButton("Medium mode");
        hard = new JButton("Hard mode");
        quitter = new JButton("Quitter");
    }

    private void creerWidget()
    {
        JPanel menu = new JPanel(new GridLayout(4,1));
        menu.add(easy);
        menu.add(medium);
        menu.add(hard);
        menu.add(quitter);

        // Mise en place du fond d'écran
        setLayout(new BorderLayout());
        JLabel background = new JLabel(new ImageIcon("img/background1.jpg"));
        background.setSize(xSize, ySize);
        background.setLayout(new FlowLayout());
        background.add(menu, BorderLayout.SOUTH);
        setContentPane(background);
    }

    void setButtonControl(ActionListener listener)
    {
        easy.addActionListener(listener);
        medium.addActionListener(listener);
        hard.addActionListener(listener);
        quitter.addActionListener(listener);
    }

    JButton getEasy() {
        return easy;
    }

    public void setEasy(JButton easy) {
        this.easy = easy;
    }

    public JButton getMedium() {
        return medium;
    }

    public void setMedium(JButton medium) {
        this.medium = medium;
    }

    public JButton getHard() {
        return hard;
    }

    public void setHard(JButton hard) {
        this.hard = hard;
    }

    public JButton getQuitter() {
        return quitter;
    }

    public void setQuitter(JButton quitter) {
        this.quitter = quitter;
    }
}
