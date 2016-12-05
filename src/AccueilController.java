
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by mlucile on 02/12/16.
 */

public class AccueilController implements ActionListener
{
    private Accueil accueil;
    private Party party;

    public AccueilController(Accueil accueil)
    {
        this.accueil = accueil;
        accueil.setButtonControl(this);
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        System.out.println("coucou");
        if(e.getSource().equals(accueil.getQuitter()))
        {
            System.exit(0);
        }
        else if(e.getSource().equals(accueil.getEasy()))
        {
            party = new Party(Party.EASY_MODE);
            ControlClic clic = new ControlClic(party);
            accueil.dispose();
        }
        else if(e.getSource().equals(accueil.getMedium()))
        {
            party = new Party(Party.MEDIUM_MODE);
            ControlClic clic = new ControlClic(party);
            accueil.dispose();
        }
        else if(e.getSource().equals(accueil.getHard()))
        {
            party = new Party(Party.HARD_MODE);
            ControlClic clic = new ControlClic(party);
            accueil.dispose();
        }
    }
}
