import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.MouseListener;

/**
 Created by cladlink on 05/11/16.
 */
class VueBoard extends JFrame
{
    private Party party;
    private JLabel[] plateauDeCarte;
    private ImageIcon[] playerYellow;
    private ImageIcon[] playerBlue;
    private static int TAILLEPLATEAU = 13;
    private JLabel carteAJouerYellow;
    private JLabel carteAJouerBlue;
    private JLabel background;
    private int xSize, ySize;

    /**
     *
     * @param partie
     */
    VueBoard(Party partie)
    {
        this.party = partie;
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

    /**
     * initAttribut
     *     initialise... les attributs

     */
    private void initAttribut()
    {
        plateauDeCarte = new JLabel[13];
        for (int i = 0; i < plateauDeCarte.length; i++) plateauDeCarte[i]
                = new JLabel(new ImageIcon("img/void.png"));

        playerBlue = new ImageIcon[6];
        playerBlue[0] = new ImageIcon("img/BlueOne.png");
        playerBlue[1] = new ImageIcon("img/BlueTwo.png");
        playerBlue[2] = new ImageIcon("img/BlueThree.png");
        playerBlue[3] = new ImageIcon("img/BlueFour.png");
        playerBlue[4] = new ImageIcon("img/BlueFive.png");
        playerBlue[5] = new ImageIcon("img/BlueSix.png");

        playerYellow = new ImageIcon[6];
        playerYellow[0] = new ImageIcon("img/YellowOne.png");
        playerYellow[1] = new ImageIcon("img/YellowTwo.png");
        playerYellow[2] = new ImageIcon("img/YellowThree.png");
        playerYellow[3] = new ImageIcon("img/YellowFour.png");
        playerYellow[4] = new ImageIcon("img/YellowFive.png");
        playerYellow[5] = new ImageIcon("img/YellowSix.png");


        //plateauDeCarte[4].setIcon(playerBlue[1]);
        //plateauDeCarte[9].setIcon(playerYellow[5]);
        carteAJouerBlue = new JLabel(playerBlue[0]);
        carteAJouerYellow = new JLabel(playerYellow[0]);
        //carteAJouerYellow = new JLabel(playerYellow[party.getTurn()]);
        //carteAJouerBlue = new JLabel(playerBlue[party.getTurn()]);
    }

    /**
     * creerWidget
     * Met en place et organise les éléments graphiques
     */
    private void creerWidget()
    {
        Border border = BorderFactory.createLineBorder(Color.black);
        JPanel global = new JPanel();
        global.setOpaque(false);
        JPanel preGlobal = new JPanel();
        preGlobal.setOpaque(false);
        preGlobal.setLayout(new BoxLayout(preGlobal, BoxLayout.LINE_AXIS));
        JPanel panPlateau = new JPanel();
        panPlateau.setOpaque(false);
        panPlateau.setLayout(new BoxLayout(panPlateau, BoxLayout.Y_AXIS));
        JPanel ligneUn = new JPanel();
        ligneUn.setOpaque(false);
        ligneUn.setLayout(new BoxLayout(ligneUn, BoxLayout.LINE_AXIS));
        JPanel ligneDeux = new JPanel();
        ligneDeux.setOpaque(false);
        ligneDeux.setLayout(new BoxLayout(ligneDeux, BoxLayout.LINE_AXIS));
        JPanel ligneTrois = new JPanel();
        ligneTrois.setOpaque(false);
        ligneTrois.setLayout(new BoxLayout(ligneTrois, BoxLayout.LINE_AXIS));
        JPanel ligneQuatre = new JPanel();
        ligneQuatre.setOpaque(false);
        ligneQuatre.setLayout(new BoxLayout(ligneQuatre, BoxLayout.LINE_AXIS));
        JPanel ligneCinq = new JPanel();
        ligneCinq.setOpaque(false);
        ligneCinq.setLayout(new BoxLayout(ligneCinq, BoxLayout.LINE_AXIS));
        ligneUn.add(plateauDeCarte[0]);
        plateauDeCarte[0].setBorder(border);
        ligneUn.add(plateauDeCarte[1]);
        plateauDeCarte[1].setBorder(border);
        ligneUn.add(plateauDeCarte[2]);
        plateauDeCarte[2].setBorder(border);
        ligneDeux.add(plateauDeCarte[3]);
        plateauDeCarte[3].setBorder(border);
        ligneDeux.add(plateauDeCarte[4]);
        plateauDeCarte[4].setBorder(border);
        ligneDeux.add(plateauDeCarte[5]);
        plateauDeCarte[5].setBorder(border);
        ligneDeux.add(plateauDeCarte[6]);
        plateauDeCarte[6].setBorder(border);
        ligneTrois.add(plateauDeCarte[7]);
        plateauDeCarte[7].setBorder(border);
        ligneTrois.add(plateauDeCarte[8]);
        plateauDeCarte[8].setBorder(border);
        ligneTrois.add(plateauDeCarte[9]);
        plateauDeCarte[9].setBorder(border);
        ligneQuatre.add(plateauDeCarte[10]);
        plateauDeCarte[10].setBorder(border);
        ligneQuatre.add(plateauDeCarte[11]);
        plateauDeCarte[11].setBorder(border);
        ligneCinq.add(plateauDeCarte[12]);
        plateauDeCarte[12].setBorder(border);
        panPlateau.add(ligneUn);
        panPlateau.add(ligneDeux);
        panPlateau.add(ligneTrois);
        panPlateau.add(ligneQuatre);
        panPlateau.add(ligneCinq);
        preGlobal.add(carteAJouerBlue);
        carteAJouerYellow.add(Box.createHorizontalStrut(2000));
        preGlobal.add(panPlateau);
        panPlateau.add(Box.createHorizontalStrut(200));
        preGlobal.add(carteAJouerYellow);
        global.add(preGlobal);

        // Mise en place du fond d'écran
        setLayout(new BorderLayout());
        background = new JLabel(new ImageIcon("img/background1.jpg"));
        background.setSize(xSize, ySize);
        background.setLayout(new FlowLayout());
        background.add(global, BorderLayout.CENTER);

        setContentPane(background);
    }

    /**
     *
     */
    void actualiserVisuelPlateau()
    {
        byte placesValeur[] = party.getBoard().getBoard();
        byte turn = party.getTurn();
        for(int i=0; i<placesValeur.length; i++)
            if(placesValeur[i] != Board.VOID_CELL
                    && plateauDeCarte[i].getMouseListeners().length == 1)
            {
                if (placesValeur[i] <= 6)
                {
                    plateauDeCarte[i].setIcon(playerBlue[placesValeur[i]-1]);
                    plateauDeCarte[i].removeMouseListener(plateauDeCarte[i].getMouseListeners()[0]);
                }
                else
                {
                    plateauDeCarte[i].setIcon(playerYellow[placesValeur[i] - 7]);
                    plateauDeCarte[i].removeMouseListener(plateauDeCarte[i].getMouseListeners()[0]);
                }
            }
        if(turn<11)
        {
            carteAJouerYellow.setIcon(playerYellow[turn / 2]);
            carteAJouerBlue.setIcon(playerBlue[turn / 2]);
        }
        else
        {
            carteAJouerYellow.setIcon(null);
            carteAJouerBlue.setIcon(null);
        }
    }

    /**
     *
     * @param e (écouteur de type MouseListener)
     */
    void setPlateauListener(MouseListener e)
    {
        for (int i = 0; i < TAILLEPLATEAU; i++)
            plateauDeCarte[i].addMouseListener(e);
    }

    void jOptionMessage(String message)
    {
        JOptionPane.showMessageDialog(this, message, "Victoire !", JOptionPane.INFORMATION_MESSAGE);
    }

    JLabel[] getPlateauDeCarte() {
        return plateauDeCarte;
    }

    static int getTAILLEPLATEAU() {
        return TAILLEPLATEAU;
    }
}
