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
public class PercolationStatsTest {
	
	@Test
	public void test1() {
		PercolationStats p = new PercolationStats(200, 100);
		assertEquals(0.593, p.mean(), 0.005);
	}

	@Test
	public void test2() {
		PercolationStats p = new PercolationStats(2, 10000);
		assertEquals(0.666, p.mean(), 0.05);
	}
}
