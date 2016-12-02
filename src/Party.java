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
    private Node curseur = null;


    Party()
    {
        board = new Board();
        tree = new Tree(board);
        turn = 0;
    }

    void gestionTourUn(byte i)
    {
        byte coupChoisi;
        byte token = (byte)((turn / 2)+1);
        turn++;
        tree.setBlueChoice(i, token); // le joueur joue

        coupChoisi = tourUn(); // on évalue le coup de l'IA
        tree.setIAChoice(coupChoisi, (byte)(token+6)); // on joue le coup de l'IA
        tree.root = new Node(i, token); // on définit la racine pour l'IA
        tree.root.addChild(coupChoisi); // on définit le premier noeud de l'IA
        tree.buildTree(); // on construit l'arbre des possibles
        curseur = tree.root; // on initialise le curseur avec le coup du joueur
        curseur = curseur.children[0]; // on déplace le curseur avec le coup de l'IA
    }

    void gestionTour(byte i)
    {
        byte coupChoisi;
        byte token = (byte)((turn / 2)+1);
        turn++;
        tree.setBlueChoice(i, token);
        curseur = curseur.children[findChild(i)]; // on bouge le curseur avant que l'IA choisisse son coup
        coupChoisi = easyMode();
        tree.setIAChoice(coupChoisi, (byte)(token+6)); // joue le coup choisis de l'IA
        curseur = curseur.children[findChild(coupChoisi)]; // on bouge le curseur en fonction du coup de l'IA
    }

    private int findChild(int coupChoisi)
    {
        for (int i = 0; i < curseur.children.length; i++)
        {
            if (curseur.children[i].idCell == coupChoisi)
                return i;
        }
        return -1; // en cas de pb
    }

    private byte tourUn()
    {
        ArrayList<Byte> listeCaseVide = new ArrayList<>();
        byte[] b = board.getBoard();
        for (byte j = 0; j < b.length; j++)
            if (b[j] == Board.VOID_CELL)
                listeCaseVide.add(j);

        return listeCaseVide.get(loto.nextInt(listeCaseVide.size()));
    }

    private byte easyMode()
    {
        double red, blue, draw, total;
        byte coupAJouer = 50;
        double pourcentageVictoire = 0;

        // on regarde quel est le "pire" coup à jouer et on le joue
        for (byte i = 0; i < curseur.children.length; i++)
        {
            tree.parcoursArbre(curseur.children[i]);
            red = tree.computeRedVictories(curseur.children[i]);
            blue = tree.computeBlueVictories(curseur.children[i]);
            draw = tree.computeDraws(curseur.children[i]);
            total = red + blue + draw;
            System.out.println(blue/total);

            // on prend le coup qui avantage le plus les bleus
            if (pourcentageVictoire <= blue / total)
            {
                coupAJouer = curseur.children[i].idCell;
                pourcentageVictoire = blue / total;
            }
        }
        System.out.println("---------------------------");
        return coupAJouer;
    }

    private int mediumMode()
    {
        double red, blue, draw, total;
        byte coupAJouer = 50;
        double pourcentageVictoire = 0;

        // on regarde quel est le "pire" coup à jouer et on le joue
        for (byte i = 0; i < curseur.children.length; i++)
        {
            tree.parcoursArbre(curseur.children[i]);
            red = tree.computeRedVictories(curseur.children[i]);
            blue = tree.computeBlueVictories(curseur.children[i]);
            draw = tree.computeDraws(curseur.children[i]);
            total = red + blue + draw;
            System.out.println(blue/total);

            // on essaie de laisser une chance sur 2 de victoire
            if (Math.abs(pourcentageVictoire)-0.5 <= Math.abs((blue / total)-0.5) )
            {
                coupAJouer = curseur.children[i].idCell;
                pourcentageVictoire = blue / total;
            }
        }
        System.out.println("---------------------------");
        return coupAJouer;
    }

    /**
     *
     * @return
     */
    private int hardMode()
    {
        double red, blue, draw, total;
        byte coupAJouer = 50;
        double pourcentageVictoire = 0;
        for (byte i = 0; i < curseur.children.length; i++)
        {
            tree.parcoursArbre(curseur.children[i]);
            red = tree.computeRedVictories(curseur.children[i]);
            blue = tree.computeBlueVictories(curseur.children[i]);
            draw = tree.computeDraws(curseur.children[i]);
            total = red + blue + draw;
            System.out.println(blue/total);

            // on prend le coup qui désavantage le plus les bleus
            if (pourcentageVictoire >= blue / total)
            {
                coupAJouer = curseur.children[i].idCell;
                pourcentageVictoire = blue / total;
            }
        }
        System.out.println("---------------------------");
        return coupAJouer;
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
