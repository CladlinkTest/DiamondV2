import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


/**
 Created by cladlink on 05/11/16.
 */
class ControlClic extends MouseAdapter
{
    private VueBoard vb;
    private Tree mb;
    private Board board;

    ControlClic()
    {
        board = new Board();
        this.mb = new Tree(board);
        this.vb = new VueBoard(mb);
        this.vb.setPlateauListener(this);
        this.vb.setVisible(true);

    }
    @Override
    public void mouseClicked(MouseEvent e)
    {
        System.out.println("coucou");
    }
}
