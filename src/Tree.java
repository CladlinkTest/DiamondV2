/**
  Created by cladlink on 05/11/16.
 */

class Tree
{
    Node root;
    private Board board;

    /**
     * Tree
     * Construit l'arbre
     * @param board (plateau de jeu)
     */
    Tree(Board board)
    {
        root = null;
        this.board = board;
    }

    /** setBlueChoice():
     create the root node of the tree, that represents the first turn of a party, i.e.
     the cell chosen by the blue player to put its pawn #1
     CAUTION: it also sets the pawn in the board
     */
    void setBlueChoice(int idCell, byte nb)
    {
        board.setPawn(idCell, nb);
    }

    /** setIAChoice():
     create the node that represents the second turn of a party, i.e.
     the cell chosen by the red player to put its pawn #1

     CAUTION: it also sets the pawn in the board
     */
    void setIAChoice(int idCell, byte nb)
    {
        board.setPawn(idCell, nb);
    }

    /** buildTree();
     build the tree of all possible evolution of the party, taking into account
     the first choice of blue and red
     */
    void buildTree()
    {
        // get the single child of the root, i.e. the node that represents the first pawn played by red player.
        Node n = root.children[0];
        // call the recursive method that build the tree from that node
        computePossibilities(n);
        System.out.println(" done.");
    }

    /** computePossibilities(Node n):
     create all possible children of Node n.
     */
    private void computePossibilities(Node n)
    {
        /*for (int i = 0; i < board.board.length; i++) {
            System.out.print(board.board[i] + " ");
        }
        System.out.println();*/
        if (n.turn == 12) // if n represents the last turn of the party, stop the recursion and determine winner
        {
            stateLeave(n);
            return;
        }
        // determine value of the next pawn that must be played from n.turn
        int nextPawnValue = (n.turn+2)/2; // get the "real" value (i.e. from 1 to 6)
        if ((n.turn+1)%2 == 0) nextPawnValue += 6; // get the value in the board if it is a red one*
        for(int i=0;i<13;i++) // check for all remaining void cells and try to place a pawn
            if (board.board[i] == Board.VOID_CELL) // if the cell i is empty
            {
                board.setPawn(i,(byte)nextPawnValue); // place the pawn here
                Node child = n.addChild(i); // create the corresponding node in the tree
                computePossibilities(child); // recursive call
                board.setPawn(i,Board.VOID_CELL); // remove pawn so that the cell appears to be free
            }
    }

    /**
     * parcoursArbre
     *     parcours... l'arbre construit précédement à partir d'un noeud donné et
     *     mets à jour les états des feuilles:
     *     *    (redWin, blueWin, Draw)
     * @param n (noeud de départ du parcours)
     */
    void parcoursArbre(Node n)
    {
        if (n.turn == 12) // if n represents the last turn of the party, stop the recursion and determine winner
        {
            stateLeave(n);
            return;
        }
        // determine value of the next pawn that must be played from n.turn
        int nextPawnValue = (n.turn+2)/2; // get the "real" value (i.e. from 1 to 6)
        if ((n.turn+1)%2 == 0) nextPawnValue += 6; // get the value in the board if it is a red one*
        for(int i=0;i<n.children.length;i++) // check for all remaining void cells and try to place a pawn
        {
            board.setPawn(n.children[i].idCell,(byte)nextPawnValue); // place the pawn here
            Node child = n.children[i]; // create the corresponding node in the tree
            parcoursArbre(child); // recursive call
            board.setPawn(n.children[i].idCell,Board.VOID_CELL); // remove pawn so that the cell appears to be free
        }
    }

    /**
     * stateLeave
     *     éval
     *     met à jour l'état des feuilles (BLUE_WINS, RED_WINS, DRAW_PARTY)
     * @param n
     */
    private void stateLeave(Node n)
    {
        board.computeScore();
        int r = board.redScore;
        int b = board.blueScore;
        if (b == r) { n.result = Node.DRAW_PARTY; }
        else if (b < r) { n.result = Node.BLUE_WINS; } // if blue obtains less than red -> blue wins
        else { n.result = Node.RED_WINS; } // if red obtains less than blue -> red wins
    }


    /** computeBlueVictories()
     determine recursively in how many cases blue wins
     in the tree that begins with n. This can be done
     by using the value of result attribute in leaves.
     */
    int computeBlueVictories(Node n)
    {
        int nb = 0;
        if (n.result == Node.BLUE_WINS && n.turn == 12) return 1;
        for (int i = 0; i < n.nbChildren; i++)
            nb += computeBlueVictories(n.children[i]);

        return nb;
    }

    /** computeRedVictories()
     determine recursively in how many cases red wins
     in the tree that begins with n. This can be done
     by using the value of result attribute in leaves.
     */
    int computeRedVictories(Node n)
    {
        int nb = 0;
        if (n.result == Node.RED_WINS && n.turn == 12)
            return 1;
        for (int i = 0; i < n.nbChildren; i++)
            nb += computeRedVictories(n.children[i]);
        return nb;
    }

    /** computeDraws()
     determine recursively in how many cases blue and red
     are draw in the tree that begins with n. This can be done
     by using the value of result attribute in leaves.
     */
    int computeDraws(Node n)
    {
        int nb = 0;
        if (n.result == Node.DRAW_PARTY && n.turn == 12) return 1;
        for (int i = 0; i < n.nbChildren; i++)
            nb += computeDraws(n.children[i]);
        return nb;
    }
}
