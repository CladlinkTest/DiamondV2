import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Calendar;
import java.util.Random;

/**
 Created by cladlink on 05/11/16.
 */
class ControlClic extends MouseAdapter
{
    private VueBoard vb;
    private Board board;
    private Tree tree;
    private int tour;
    private static Random loto = new Random(Calendar.getInstance().getTimeInMillis());

    ControlClic()
    {
        tour = 0;
        this.board = new Board();
        this.tree = new Tree(board);
        this.vb = new VueBoard(board);
        this.vb.setPlateauListener(this);
        this.vb.setVisible(true);
    }
    @Override
    public void mouseClicked(MouseEvent e)
    {
        /* A COMPLETER :
           OK - afficher le plateau de jeu
           - demander son 1er coup au joueur bleu, et mettre à jour l'arbre et le plateau
           - afficher le plateau de jeu
           - demander son 1er coup au joueur rouge, et mettre à jour l'arbre et le plateau
           - calculer l'arbre de tous les coups possibles
           - tant que partie non finie :
              - demander son prochain coup au joueur bleu, et mettre à jour l'arbre et le plateau
              - afficher le plateau de jeu
              - calculer le meilleur prochain cou du rouge et mettre à jour l'arbre et le plateau
               - afficher le plateau de jeu
            - fin tant que
            - afficher vainqueur

         */
        tour++;
        if(tour == 1)
        {
            for(int i=0; i<13; i++)
                if(e.getSource().equals(vb.getPlateauDeCarte()[i]))
                {
                    if(tour == 1)
                    {
                        tree.setFirstBlueChoice(i);
                        int premierChoix = loto.nextInt(14);
                        while (premierChoix == i)
                            premierChoix = loto.nextInt(14);
                        tree.setFirstRedChoice(premierChoix);
                        tour++;
                    }
                    vb.actualiserVisuelPlateau(board.getBoard());
                }
        }
    }
}
