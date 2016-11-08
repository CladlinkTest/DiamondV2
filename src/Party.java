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

    void gestiontour(byte i)
    {
        int premierChoix = loto.nextInt(14); // cf commentaire d'après
        int token = turn /2;
        turn++;
        tree.setBlueChoice(i, (byte)token);
        while (premierChoix == i)
            premierChoix = loto.nextInt(14);
        tree.setIAChoice(premierChoix, (byte)(token+6)); // pas bon faut un random (tant que l'IA est pas fait mais
        // random parmi les cases vides.
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
}
