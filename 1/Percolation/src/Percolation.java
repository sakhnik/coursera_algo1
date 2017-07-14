
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

    private static final byte OPEN_FLAG = 1;
    private static final byte TOP_FLAG = 2;
    private static final byte BOTTOM_FLAG = 4;

    private final int n;
    private byte[] site;
    private int nOpen;
    private final WeightedQuickUnionUF uf;
    private boolean percolates = false;

    public Percolation(int n) {               // create n-by-n grid, with all sites blocked
        if (n <= 0)
            throw new IllegalArgumentException();
        this.n = n;
        nOpen = 0;
        site = new byte[n*n];
        uf = new WeightedQuickUnionUF(n*n);
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

    private void join(int idx1, int idx2) {
        byte s1 = site[uf.find(idx1)];
        byte s2 = site[uf.find(idx2)];
        uf.union(idx1, idx2);
        int r = uf.find(idx1);
        site[r] |= s1 | s2;
        if ((site[r] & (TOP_FLAG | BOTTOM_FLAG)) == (TOP_FLAG | BOTTOM_FLAG))
            percolates = true;
    }

    public    void open(int row, int col) {    // open site (row, col) if it is not open already
        int idx = getIdx(row, col);

        if (isOpen(idx))
            return;
        setOpen(idx);
        ++nOpen;

        if (row > 1) {
            if (isOpen(idx-n)) {
                join(idx, idx-n);
            }
        }
        else {
            site[uf.find(idx)] |= TOP_FLAG;
            if ((site[uf.find(idx)] & BOTTOM_FLAG) != 0)
                percolates = true;
        }

        if (row < n) {
            if (isOpen(idx+n)) {
                join(idx, idx+n);
            }
        } else {
            site[uf.find(idx)] |= BOTTOM_FLAG;
            if ((site[uf.find(idx)] & TOP_FLAG) != 0)
                percolates = true;
        }

        if (col > 1 && isOpen(idx-1)) {
            join(idx, idx-1);
        }
        if (col < n && isOpen(idx+1)) {
            join(idx, idx+1);
        }
    }

    public boolean isOpen(int row, int col) {  // is site (row, col) open?
        return isOpen(getIdx(row, col));
    }

    public boolean isFull(int row, int col) {  // is site (row, col) full?
        return 0 != (site[uf.find(getIdx(row, col))] & TOP_FLAG);
    }

    public     int numberOfOpenSites() {      // number of open sites
        return nOpen;
    }

    public boolean percolates() {              // does the system percolate?
        return percolates;
    }


    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Percolation p = new Percolation(Integer.parseInt(args[1]));
        assert !p.percolates();
    }
}
