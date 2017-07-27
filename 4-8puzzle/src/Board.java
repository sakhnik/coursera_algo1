
import java.util.ArrayList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author sakhnik
 */
public class Board {
    private final int[][] blocks;

    public Board(int[][] blocks) {           // construct a board from an n-by-n array of blocks
                                             // (where blocks[i][j] = block in row i, column j)
        this.blocks = cloneBlocks(blocks);
    }

    private Board(boolean copy, int[][] blocks) {
        if (!copy)
            this.blocks = blocks;
        else
            this.blocks = cloneBlocks(blocks);
    }

    public int dimension() {                 // board dimension n
        return blocks.length;
    }

    public int hamming() {                   // number of blocks out of place
        int count = 0;
        int n = 1;
        for (int row = 0; row < blocks.length; ++row)
            for (int col = 0; col < blocks.length; ++col, ++n)
                if (blocks[row][col] != n)
                    ++count;
        return count-1;  // empty is depicted with 0, not m*n
    }

    public int manhattan() {                 // sum of Manhattan distances between blocks and goal
        int dist = 0;
        for (int row = 0; row < blocks.length; ++row)
            for (int col = 0; col < blocks.length; ++col)
            {
                int block = blocks[row][col];
                if (0 == block)
                    continue;
                int goalRow = (block - 1) / blocks.length;
                int goalCol = (block - 1) % blocks.length;

                dist += Math.abs(goalRow - row) + Math.abs(goalCol - col);
            }
        return dist;
    }

    public boolean isGoal() {                // is this board the goal board?
        int n = 1;
        for (int row = 0; row < blocks.length; ++row)
            for (int col = 0; col < blocks.length; ++col, ++n)
                if (blocks[row][col] != n && n != blocks.length*blocks.length)
                    return false;
        return true;
    }

    private static int[][] cloneBlocks(int[][] blocks) {
        // Perform deep copy
        int[][] b = blocks.clone();
        for (int i = 0; i < blocks.length; ++i)
            b[i] = blocks[i].clone();
        return b;
    }

    private static void swap(int[][] b, int row1, int col1, int row2, int col2) {
        int t = b[row1][col1];
        b[row1][col1] = b[row2][col2];
        b[row2][col2] = t;
    }

    public Board twin() {                    // a board that is obtained by exchanging any pair of blocks
        int[][] b = cloneBlocks(blocks);

        // Find two non-empty blocks
        int r1 = 0;
        int c1 = 0;
        int stage = 0;

        for (int row = 0; row < blocks.length; ++row)
            for (int col = 0; col < blocks.length; ++col) {
                if (blocks[row][col] != 0) {
                    if (stage == 0) {
                        r1 = row;
                        c1 = col;
                        ++stage;
                        continue;
                    }

                    // Exchange them
                    swap(b, r1, c1, row, col);

                    // Construct a new board
                    return new Board(false, b);
                }
            }

        return null;
    }

    @Override
    public boolean equals(Object y) {        // does this board equal y?
        if (y == null) return false;
        if (this == y) return true;
        if (getClass() != y.getClass()) return false;

        Board that = (Board) y;
        if (dimension() != that.dimension())
            return false;
        for (int row = 0; row < dimension(); ++row)
            for (int col = 0; col < dimension(); ++col)
                if (blocks[row][col] != that.blocks[row][col])
                    return false;
        return true;
    }

    public Iterable<Board> neighbors() {     // all neighboring boards
        for (int row = 0; row < blocks.length; ++row)
            for (int col = 0; col < blocks.length; ++col)
                if (0 == blocks[row][col]) {
                    ArrayList<Board> neighbors = new ArrayList<>();
                    neighbors.ensureCapacity(4);

                    if (row > 0) {
                        int[][] b = cloneBlocks(blocks);
                        swap(b, row, col, row - 1, col);
                        neighbors.add(new Board(false, b));
                    }

                    if (col > 0) {
                        int[][] b = cloneBlocks(blocks);
                        swap(b, row, col, row, col - 1);
                        neighbors.add(new Board(false, b));
                    }

                    if (row + 1 < blocks.length) {
                        int[][] b = cloneBlocks(blocks);
                        swap(b, row, col, row + 1, col);
                        neighbors.add(new Board(false, b));
                    }

                    if (col + 1 < blocks.length) {
                        int[][] b = cloneBlocks(blocks);
                        swap(b, row, col, row, col + 1);
                        neighbors.add(new Board(false, b));
                    }

                    return neighbors;
                }

        assert false;
        return null;
    }

    @Override
    public String toString() {               // string representation of this board (in the output format specified below)
        StringBuilder b = new StringBuilder();
        b.append(blocks.length);
        b.append('\n');
        for (int row = 0; row < blocks.length; ++row) {
            for (int col = 0; col < blocks.length; ++col) {
                b.append(' ');
                b.append(blocks[row][col]);
            }
            b.append('\n');
        }
        return b.toString();
    }

    public static void main(String[] args) { // unit tests (not graded)

    }
}