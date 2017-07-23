/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.Comparator;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author sakhnik
 */
public class PointTest {
    
    @Test
    public void testSlopeTo() {
        Point p1 = new Point(0,0);
        assertEquals(0, p1.slopeTo(new Point(1,0)), Double.MIN_VALUE);
        assertEquals(1, p1.slopeTo(new Point(1,1)), Double.MIN_VALUE);
        assertEquals(Double.POSITIVE_INFINITY, p1.slopeTo(new Point(0,1)), Double.MIN_VALUE);
        assertEquals(Double.NEGATIVE_INFINITY, p1.slopeTo(new Point(0,0)), Double.MIN_VALUE);
        assertEquals(-1, p1.slopeTo(new Point(-1,1)), Double.MIN_VALUE);
    }

    @Test
    public void testCompareTo() {
        Point p1 = new Point(0,0);
        assertEquals(1, p1.compareTo(new Point(3,-1)));
        assertEquals(-1, p1.compareTo(new Point(-3,3)));
        assertEquals(0, p1.compareTo(new Point(0,0)));
    }

    @Test
    public void testSlopeOrder() {
    }
}
