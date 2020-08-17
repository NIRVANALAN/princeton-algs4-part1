package assignment_4;
/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

public class Solver {
    private Node goal;
    private final int moves;
    private Stack<Board> shortestPath;

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null) throw new IllegalArgumentException();
        // solve
        MinPQ<Node> minPQ = new MinPQ<>();
        MinPQ<Node> twinMinPQ = new MinPQ<>();
        minPQ.insert(new Node(initial, 0, null));
        twinMinPQ.insert(new Node(initial.twin(), 0, null));
        Node currentNode, twinCurrentNode;

        while (!minPQ.min().board.isGoal() && !twinMinPQ.min().board.isGoal()) {
            currentNode = minPQ.delMin();
            twinCurrentNode = twinMinPQ.delMin();
            for (var nb : currentNode.board.neighbors()) {
                if (currentNode.prev == null || !nb.equals(currentNode.prev.board))
                    minPQ.insert(new Node(nb, currentNode.moves + 1, currentNode));
            }
            for (var nb : twinCurrentNode.board.neighbors()) {
                if (twinCurrentNode.prev == null || !nb.equals(twinCurrentNode.prev.board))
                    twinMinPQ.insert(new Node(nb, twinCurrentNode.moves + 1, twinCurrentNode));
            }
        }

        if (minPQ.min().board.isGoal()) {
            goal = minPQ.min();
            moves = minPQ.min().moves;
        } else moves = -1;
    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        return moves() != -1;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        return moves;
    }

    private class Node implements Comparable<Node> {

        private Board board;
        private int moves;
        private Node prev; // ArrayList.
        private int priority = -1;

        Node(Board board, int moves, Node prev) {
            this.board = board;
            this.moves = moves;
            this.prev = prev;
        }

        private int getPriority() {
            if (priority == -1) {
                priority = moves + board.manhattan();
            }
            return priority;
        }

        // private int getManhattanPriority(){
        //     if(priority==-1){
        //         priority = moves + board.manhattan();
        //     }
        //     return priority;
        // }

        public int compareTo(Node node) {
            return Integer.compare(this.getPriority(), node.getPriority()); //!
        }
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        if (!isSolvable()) return null;
        if (shortestPath == null) {
            shortestPath = new Stack<>();
            var copyGoal = goal;
            while (copyGoal != null) {
                shortestPath.push(copyGoal.board);
                copyGoal = copyGoal.prev;
            }
        }
        return shortestPath;
    }

    // test client (see below)
    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);

        }
    }
}
