/**
  Created by cladlink on 05/11/16.
 */
class Tree
{
    Node root;
    private Board board;
    int nbConfigurations;

    /**
     * Tree
     * Construit l'arbre
     * @param board (plateau de jeu)
     */
    Tree(Board board)
    {
        root = null;
        this.board = board;
        nbConfigurations = 0;
    }

    /** setBlueChoice():
     create the root node of the tree, that represents the first turn of a party, i.e.
     the cell chosen by the blue player to put its pawn #1
     CAUTION: it also sets the pawn in the board
     */
    void setBlueChoice(int idCell, byte nb)
    {
        root = new Node(idCell, nb);
        board.setPawn(idCell, nb);
    }

    /** setIAChoice():
     create the node that represents the second turn of a party, i.e.
     the cell chosen by the red player to put its pawn #1

     CAUTION: it also sets the pawn in the board
     */
    void setIAChoice(int idCell, byte nb)
    {
        root.addChild(idCell);
        board.setPawn(idCell, nb);
    }

    /** buildTree();
     build the tree of all possible evolution of the party, taking into account
     the first choice of blue and red
     */
    void buildTree()
    {
        nbConfigurations = 0;
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
        if (n.turn == 12) // if n represents the last turn of the party, stop the recursion and determine winner
        {
            board.computeScore();
            int r = board.redScore;
            int b = board.blueScore;
            if (b == r) { n.result = Node.DRAW_PARTY; }
            else if (b < r) { n.result = Node.BLUE_WINS; } // if blue obtains less than red -> blue wins
            else { n.result = Node.RED_WINS; } // if red obtains less than blue -> red wins
            nbConfigurations += 1;
            if ((nbConfigurations % 1000000) == 0) System.out.print(".");
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

    /** computeBlueVictories()
     determine recursively in how many cases blue wins
     in the tree that begins with n. This can be done
     by using the value of result attribute in leaves.
     */
    int computeBlueVictories(Node n)
    {
        int nb = 0;
        if (n.turn == 12 && n.result == Node.BLUE_WINS) return 1;
        if (n.result == Node.RED_WINS || n.result == Node.DRAW_PARTY) return 0;
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
        if (n.turn == 12 && n.result == Node.RED_WINS) return 1;
        if (n.result == Node.BLUE_WINS || n.result == Node.DRAW_PARTY) return 0;
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
        if (n.turn == 12 && n.result == Node.DRAW_PARTY) return 1;
        if (n.result == Node.RED_WINS || n.result == Node.BLUE_WINS) return 0;
        return nb;
    }
}
