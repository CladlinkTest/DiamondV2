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
                ControlClic controler = new ControlClic();
            }
        });
    }
}
