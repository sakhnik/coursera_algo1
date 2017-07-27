
import edu.princeton.cs.algs4.MinPQ;
import java.util.LinkedList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author sakhnik
 */
public class Solver {

    private final LinkedList<Board> solution;

    private class Node implements Comparable<Node> {
        private final Board board;
        private final int moves;
        private final Node prev;
        private final int prio;

        Node(Board b, Node p) {
            board = b;
            prev = p;
            if (p != null) {
                moves = p.moves + 1;
            } else {
                moves = 0;
            }
            prio = board.manhattan() + moves;
        }

        Board getBoard() {
            return board;
        }

        int getMoves() {
            return moves;
        }

        Node getPrev() {
            return prev;
        }

        @Override
        public int compareTo(Node t) {
            return Integer.compare(prio, t.prio);
        }
    }

    public Solver(Board initial) {           // find a solution to the initial board (using the A* algorithm)

        if (initial == null)
            throw new IllegalArgumentException();

        MinPQ<Node> pq = new MinPQ<>();
        pq.insert(new Node(initial, null));
        MinPQ<Node> pq2 = new MinPQ<>();
        pq2.insert(new Node(initial.twin(), null));

        while (true) {
            Node n = runOneSearch(pq);
            if (n != null) {
                solution = new LinkedList<>();
                while (n != null) {
                    solution.addFirst(n.getBoard());
                    n = n.prev;
                }
                return;
            }

            n = runOneSearch(pq2);
            if (n != null) {
                solution = null;
                return;
            }
        }
    }

    private Node runOneSearch(MinPQ<Node> pq) {
        Node cur = pq.delMin();
        Board curBoard = cur.getBoard();
        if (curBoard.isGoal()) {
            return cur;
        }

        Board prevBoard = null;
        if (cur.prev != null)
            prevBoard = cur.prev.getBoard();

        for (Board b : curBoard.neighbors()) {
            // Critical optimization: don't enqueue the previous board
            if (!b.equals(prevBoard))
                pq.insert(new Node(b, cur));
        }
        return null;
    }

    public boolean isSolvable() {            // is the initial board solvable?
        return solution != null;
    }

    public int moves() {                     // min number of moves to solve initial board; -1 if unsolvable
        return solution.size();
    }

    public Iterable<Board> solution() {      // sequence of boards in a shortest solution; null if unsolvable
        return solution;
    }

    public static void main(String[] args) { // solve a slider puzzle (given below)

    }
}

