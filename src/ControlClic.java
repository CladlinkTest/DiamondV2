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
        for(byte i=0; i<13; i++)
            if(e.getSource().equals(vb.getPlateauDeCarte()[i]))
            {
                if (party.getTurn() == 0)
                    party.gestionTourUn(i);
                else
                    party.gestionTour(i);
                party.addTurn();
                vb.actualiserVisuelPlateau();
                if(party.getTurn() == 12)
                {
                    for (i = 0; i < VueBoard.getTAILLEPLATEAU(); i++)
                        vb.getPlateauDeCarte()[i].removeMouseListener(this);
                    party.getBoard().computeScore();
                    if(party.getBoard().blueScore > party.getBoard().redScore)
                        vb.jOptionMessage("Le joueur rouge a gagné !");
                    else if (party.getBoard().blueScore < party.getBoard().redScore)
                        vb.jOptionMessage("Le joueur bleu a gagné !");
                    else
                        vb.jOptionMessage("Egalité !");
                }
            }
    }
}
