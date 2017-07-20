/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.ArrayList;
import static java.util.Arrays.asList;
import java.util.Iterator;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author sakhnik
 */
public class DequeTest {
    
    public DequeTest() {
    }

    @Test
    public void testIsEmpty() {
        Deque d = new Deque();
        assertTrue(d.isEmpty());
    }

    @Test
    public void testSize() {
        Deque d = new Deque();
        assertEquals(0, d.size());
    }

    @Test
    public void testAddFirst() {
        Deque<Integer> d = new Deque<>();
        d.addFirst(1);
        assertFalse(d.isEmpty());
        assertEquals(1, d.size());
        d.addFirst(2);
        assertFalse(d.isEmpty());
        assertEquals(2, d.size());
        d.addFirst(3);
        assertFalse(d.isEmpty());
        assertEquals(3, d.size());

        List<Object> dump = new ArrayList<>();
        for (Integer i : d)
            dump.add(i);
        Object[] ref = asList(3,2,1).toArray();
        assertArrayEquals(ref, dump.toArray());
    }

    @Test
    public void testAddLast() {
        Deque<Integer> d = new Deque<>();
        d.addLast(1);
        assertFalse(d.isEmpty());
        assertEquals(1, d.size());

        d.addLast(2);
        d.addLast(3);

        List<Object> dump = new ArrayList<>();
        for (Integer i : d)
            dump.add(i);
        Object[] ref = asList(1,2,3).toArray();
        assertArrayEquals(ref, dump.toArray());
    }

    @Test
    public void testAddComplex() {
        Deque<Integer> d = new Deque<>();
        d.addLast(3);
        d.addFirst(2);
        d.addLast(4);
        d.addFirst(1);
        d.addLast(5);

        List<Object> dump = new ArrayList<>();
        for (Integer i : d)
            dump.add(i);
        Object[] ref = asList(1,2,3,4,5).toArray();
        assertArrayEquals(ref, dump.toArray());
    }

    @Test
    public void testRemove() {
        Deque<Integer> d = new Deque<>();
        d.addLast(1);
        d.addLast(2);
        d.addLast(3);
        d.addLast(4);

        assertEquals(4, d.removeLast().intValue());
        assertEquals(1, d.removeFirst().intValue());
        assertEquals(2, d.size());
        assertEquals(2, d.removeFirst().intValue());
        assertEquals(1, d.size());
        assertEquals(3, d.removeLast().intValue());
        assertEquals(0, d.size());
    }
}
