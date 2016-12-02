/**
 Created by cladlink on 05/11/16.
 */
public class Diamond
{
    public static void main (String[] args)
    {
        javax.swing.SwingUtilities.invokeLater(new Runnable()
        {
            @Override
            public void run()
            {
                Accueil accueil = new Accueil();
                AccueilController controler = new AccueilController(accueil);
                accueil.setVisible(true);
            }
        });
    }
}
