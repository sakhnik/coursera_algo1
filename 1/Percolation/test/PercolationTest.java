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
	public void testPercolation3() {
		Percolation p = new Percolation(3);
		assertFalse(p.percolates());
		assertFalse(p.isOpen(1, 1));
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
}
