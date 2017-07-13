
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author sakhnik
 */
public class Percolation {

    private final int n;
    private final int upper, lower;
    private byte[] site;
    private static final byte OPEN_FLAG = 1;
    private static final byte HAS_TOP = 2;
    private static final byte HAS_BOTTOM = 4;
    private int nOpen;
    private final WeightedQuickUnionUF uf;

    public Percolation(int n) {               // create n-by-n grid, with all sites blocked
        if (n <= 0)
            throw new IllegalArgumentException();
        this.n = n;
        upper = n*n;
        lower = upper + 1;
        nOpen = 0;

        site = new byte[n*n];

        uf = new WeightedQuickUnionUF(n*n + 2);
    }

    private int getIdx(int row, int col) {
        if (row < 1 || col < 1 || row > n || col > n)
            throw new IllegalArgumentException();

        return n*(row-1) + col-1;
    }

    private boolean isOpen(int idx) {
        return (site[idx] & OPEN_FLAG) != 0;
    }

    private void setOpen(int idx) {
        site[idx] |= OPEN_FLAG;
    }

    public    void open(int row, int col) {    // open site (row, col) if it is not open already
        int idx = getIdx(row, col);

        if (isOpen(idx))
            return;
        setOpen(idx);
        ++nOpen;

        if (row > 1) {
            if (isOpen(idx-n)) {
                uf.union(idx, idx-n);
            }
        }
        else {
            uf.union(idx, upper);
        }

        if (row < n) {
            if (isOpen(idx+n)) {
                uf.union(idx, idx+n);
            }
        } else {
            uf.union(idx, lower);
        }

        if (col > 1 && isOpen(idx-1)) {
            uf.union(idx, idx-1);
        }
        if (col < n && isOpen(idx+1)) {
            uf.union(idx, idx+1);
        }
    }

    public boolean isOpen(int row, int col) {  // is site (row, col) open?
        return isOpen(getIdx(row, col));
    }

    public boolean isFull(int row, int col) {  // is site (row, col) full?
        return uf.connected(getIdx(row, col), upper);
    }

    public     int numberOfOpenSites() {      // number of open sites
        return nOpen;
    }

    public boolean percolates() {              // does the system percolate?
        return uf.connected(upper, lower);
    }


    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Percolation p = new Percolation(Integer.parseInt(args[1]));
        assert !p.percolates();
    }
}
