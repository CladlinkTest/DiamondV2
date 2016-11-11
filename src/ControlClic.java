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
                party.gestiontour(i);
                party.addTurn();
                vb.actualiserVisuelPlateau();
            }
    }
}
