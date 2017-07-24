/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author sakhnik
 */
public class RandomizedQueueTest {
    
    @Test
    public void testSize() {
        RandomizedQueue<Integer> q = new RandomizedQueue<>();
        assertTrue(q.isEmpty());
        assertEquals(0, q.size());
        q.enqueue(1);
        assertFalse(q.isEmpty());
        assertEquals(1, q.size());
        assertEquals(1, q.dequeue().intValue());
        assertTrue(q.isEmpty());
        assertEquals(0, q.size());
    }

    @Test
    public void testIterator() {
        RandomizedQueue<Integer> q = new RandomizedQueue<>();
        for (int i = 0; i < 5; ++i)
            q.enqueue(i);
        assertEquals(5, q.size());

        List<Integer> dump = new ArrayList<>();
        q.iterator().forEachRemaining(dump::add);
        dump.sort((a,b) -> a.compareTo(b));
        for (int i = 0; i < 5; ++i)
            assertEquals(i, dump.get(i).intValue());
    }
}
