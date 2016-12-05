import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;

/**
 * Party
 *     Gère les tours de la partie, les appels à l'IA selon trois niveau de difficulté (easy, medium, hard)
 */

class Party
{
    static final byte EASY_MODE = 1;
    static final byte MEDIUM_MODE = 2;
    static final byte HARD_MODE = 3;
    private byte modeJeu;
    private static Random loto = new Random(Calendar.getInstance().getTimeInMillis());
    private Board board;
    private Tree tree;
    private byte turn;
    private Node curseur = null;


    Party(byte modeJeu)
    {
        this.modeJeu = modeJeu;
        board = new Board();
        tree = new Tree(board);
        turn = 0;
    }

    /**
     * gestionTourUn
     *     gère le tour 1 a part (car trop bordélique en une méthode)
     * @param i ()
     */
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

    /**
     * gestionTour
     *     gère le déplacement du joueur et le tour de l'IA
     * @param i (id de cellule choisis par le joueur pour son déplacement)
     */
    void gestionTour(byte i)
    {
        byte coupChoisi;
        byte token = (byte)((turn / 2)+1);
        turn++;
        tree.setBlueChoice(i, token);
        curseur = curseur.children[findChild(i)]; // on bouge le curseur avant que l'IA choisisse son coup
        switch (modeJeu)
        {
            case EASY_MODE:
                coupChoisi = easyMode();
                break;
            case MEDIUM_MODE:
                coupChoisi = mediumMode();
                break;
            case HARD_MODE:
                coupChoisi = hardMode();
                break;
            default:
                coupChoisi = hardMode();
        }
        tree.setIAChoice(coupChoisi, (byte)(token+6)); // joue le coup choisis de l'IA
        curseur = curseur.children[findChild(coupChoisi)]; // on bouge le curseur en fonction du coup de l'IA
    }

    /**
     * trouve le fils associé à l'id de cellule en paramètre
     * @param coupChoisi ()
     * @return l'indice du fils à donner au curseur
     */
    private int findChild(int coupChoisi)
    {
        for (int i = 0; i < curseur.children.length; i++)
        {
            if (curseur.children[i].idCell == coupChoisi)
                return i;
        }
        return -1; // en cas de pb
    }

    /**
     * tourUn
     *     Choisis un coup possible parmi les possibles
     * @return le coup à jouer
     */
    private byte tourUn()
    {
        ArrayList<Byte> listeCaseVide = new ArrayList<>();
        byte[] b = board.getBoard();
        for (byte j = 0; j < b.length; j++)
            if (b[j] == Board.VOID_CELL)
                listeCaseVide.add(j);

        return listeCaseVide.get(loto.nextInt(listeCaseVide.size()));
    }

    /**
     * easyMode
     *     parcours l'arbre, compte le nombre de victoire de l'IA, du joueur, de draws et
     *     choisis le coup qui avantage le plus le joueur
     * @return le coup à jouer
     */
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
            System.out.println("EASY " + blue/total);

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

    /**
     * mediumMode
     *     prend le coup qui permet de laisser une chance sur deux entre victoire et défaite
     * @return le coup à jouer
     */
    private byte mediumMode()
    {
        double red, blue, draw, total;
        byte coupAJouer = 50;
        double pourcentageVictoire = 0.5;

        // on regarde quel est le "pire" coup à jouer et on le joue
        for (byte i = 0; i < curseur.children.length; i++)
        {
            tree.parcoursArbre(curseur.children[i]);
            red = tree.computeRedVictories(curseur.children[i]);
            blue = tree.computeBlueVictories(curseur.children[i]);
            draw = tree.computeDraws(curseur.children[i]);
            total = red + blue + draw;
            System.out.println("MEDIUM " + blue/total);
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
     * hardMode
     *     évalue le coup qui désaventage le plus le joueur bleu
     * @return le coup à jouer
     */
    private byte hardMode()
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
            System.out.println("HM " + red/total);

            // on prend le coup qui désavantage le plus les bleus
            if (pourcentageVictoire <= red / total)
            {
                coupAJouer = curseur.children[i].idCell;
                pourcentageVictoire = red / total;
            }
        }
        System.out.println("--------------------------");
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
