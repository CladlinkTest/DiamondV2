import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;

/**
 Created by mlucile on 05/11/16.
 */
class Party
{
    private static Random loto = new Random(Calendar.getInstance().getTimeInMillis());
    private Board board;
    private Tree tree;
    private byte turn;

    Party()
    {
        board = new Board();
        tree = new Tree(board);
        turn = 0;
    }

    /**
     *
     * @param i
     */
    void gestiontour(byte i)
    {
        int coupChoisi = easyMode();
        int token = turn / 2;
        turn++;
        tree.setBlueChoice(i, (byte)token);
        tree.setIAChoice(coupChoisi, (byte)(token+6));
    }

    /**
     *
     * @return
     */
    private int easyMode()
    {
        ArrayList<Byte> listeCaseVide = new ArrayList<>();
        byte[] b = board.getBoard();
        for (byte j = 0; j < b.length; j++)
            if (b[j] == Board.VOID_CELL)
                listeCaseVide.add(j);

        return listeCaseVide.get(loto.nextInt(listeCaseVide.size()));
    }

    /**
     *
     * @return
     */
    private int mediumMode()
    {

        return 0;
    }

    /**
     *
     * @return
     */
    private int hardMode()
    {

        return 0;
    }

    boolean victoire()
    {
        return false;
    }
    Board getBoard() {
        return board;
    }
    void addTurn() {
        this.turn++;
    }
    byte getTurn() {
        return turn;
    }
}
