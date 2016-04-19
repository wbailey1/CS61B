package hw4.puzzle;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.MinPQ;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

public class Solver {
    MinPQ<SearchNode> queue;
    int moves;
    List<Board> solution;
    private class SearchNode implements Comparable<SearchNode> {
        Board board;
        int moves;
        int priority;
        SearchNode prev;
        public SearchNode(Board b, SearchNode p, int m) {
            board = b;
            prev = p;
            moves = m;
            priority = board.manhattan() + moves;
        }
        @Override
        public int compareTo(SearchNode otherNode) {
            return this.priority - otherNode.priority;
        }
    }
    public Solver(Board initial) {
        queue = new MinPQ<SearchNode>();
        queue.insert(new SearchNode(initial, null, 0));
        boolean solved = false;
        SearchNode curr;
        SearchNode point;
        solution = new ArrayList<Board>();
        while (!solved) {
            curr = queue.delMin();
            if (curr.board.isGoal()) {
                solved = true;
                moves = curr.moves;
                solution.add(curr.board);
                while (curr.prev != null) {
                    solution.add(curr.prev.board);
                    curr = curr.prev;
                }
                Collections.reverse(solution);
            } else {
                for (Board b : BoardUtils.neighbors(curr.board)) {
                    if (curr.prev == null || !b.equals(curr.prev.board)) {
                        queue.insert(new SearchNode(b, curr, curr.moves + 1));
                    }
                }
            }
        }

    }
    public int moves() {
        return moves;
    }
    public Iterable<Board> solution() {
        return solution;
    }
    // DO NOT MODIFY MAIN METHOD
    public static void main(String[] args) {
        In in = new In(args[0]);
        int N = in.readInt();
        int[][] tiles = new int[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                tiles[i][j] = in.readInt();
            }
        }
        Board initial = new Board(tiles);
        Solver solver = new Solver(initial);
        StdOut.println("Minimum number of moves = " + solver.moves());
        for (Board board : solver.solution()) {
            StdOut.println(board);
        }
    }   
}
