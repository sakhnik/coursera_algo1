/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author sakhnik
 */
public class PercolationTest {

    @Test
    public void testConsistencyBeforeOpening() {
        Percolation p = new Percolation(3);
        assertFalse(p.isOpen(1, 1));
        assertFalse(p.isFull(1, 1));
    }

    @Test
    public void testPercolation3() {
        Percolation p = new Percolation(3);
        assertFalse(p.percolates());
        p.open(1, 1);
        p.open(2, 2);
        p.open(3, 3);
        assertFalse(p.percolates());
        p.open(1, 2);
        assertFalse(p.percolates());
        p.open(2, 3);
        assertTrue(p.percolates());
        assertEquals(5, p.numberOfOpenSites());
    }

    @Test
    public void testPercolation6() {
        Percolation p = new Percolation(6);
        p.open(1, 6);
        p.open(2, 6);
        p.open(3, 6);
        p.open(4, 6);
        p.open(5, 6);
        p.open(5, 5);
        p.open(4, 4);
        p.open(3, 4);
        p.open(2, 4);
        p.open(2, 3);
        p.open(2, 2);
        assertFalse(p.isFull(2, 2));
        p.open(2, 1);
        assertFalse(p.percolates());
        assertFalse(p.isFull(2, 1));
        p.open(3, 1);
        p.open(4, 1);
        p.open(5, 1);
        p.open(5, 2);
        p.open(6, 2);
        p.open(5, 4);
        assertTrue(p.percolates());
    }
}
