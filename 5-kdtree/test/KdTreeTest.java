/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import edu.princeton.cs.algs4.Point2D;
import org.junit.Test;

/**
 *
 * @author sakhnik
 */
public class KdTreeTest {
    
    @Test
    public void testInsert() {
        KdTree t = new KdTree();
        t.insert(new Point2D(0.7, 0.2));
        t.insert(new Point2D(0.5, 0.4));
        t.insert(new Point2D(0.2, 0.3));
        t.insert(new Point2D(0.4, 0.7));
        t.insert(new Point2D(0.9, 0.6));
    }
}
