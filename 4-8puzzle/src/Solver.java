
import edu.princeton.cs.algs4.MinPQ;
import java.util.ArrayList;
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

    private class Node implements Comparable<Node> {
        final private Board board;
        final private int moves;
        final private Node prev;
        final private int prio;

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

    private final LinkedList<Board> solution;

    private class Searcher {
        private MinPQ<Node> pq;

        Searcher(Board initial) {
            pq.insert(new Node(initial, null));
        }

        Node runOne() {
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
    }

    public Solver(Board initial) {           // find a solution to the initial board (using the A* algorithm)

        if (initial == null)
            throw new IllegalArgumentException();

        Searcher s = new Searcher(initial);
        Searcher s2 = new Searcher(initial.twin());

        while (true) {
            Node n = s.runOne();
            if (n != null) {
                solution = new LinkedList<>();
                while (n != null) {
                    solution.addFirst(n.getBoard());
                    n = n.prev;
                }
                return;
            }

            n = s2.runOne();
            if (n != null) {
                solution = null;
                return;
            }
        }
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

