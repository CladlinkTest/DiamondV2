import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 Created by cladlink on 05/11/16.
 */
class ControlClic extends MouseAdapter
{
    private VueBoard vb;
    private Party party;

    ControlClic()
    {
        this.party = new Party();
        this.vb = new VueBoard(party);
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
        for(byte i=0; i<13; i++)
            if(e.getSource().equals(vb.getPlateauDeCarte()[i]))
            {
                party.gestiontour(i);
                party.addTurn();
                vb.actualiserVisuelPlateau();
            }

    }
}
