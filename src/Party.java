import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Created by mlucile on 05/11/16.
 */
public class Party
{
    Board board;
    Tree tree;
    BufferedReader consoleIn;

    Party()
    {
        consoleIn = new BufferedReader(new InputStreamReader(System.in));
        board = new Board();
        tree = new Tree(board);
    }

    void start()
    {

        board.clearBoard();
        /* A COMPLETER :
           - afficher le plateau de jeu
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
    }
}
