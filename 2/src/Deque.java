
import java.util.Iterator;
import java.util.NoSuchElementException;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author sakhnik
 */
public class Deque<Item> implements Iterable<Item> {

    private class N {
        Item v;
        N prev;
        N next;
    }

    private N root = null;
    private int size = 0;

    public Deque() {                           // construct an empty deque

    }

    public boolean isEmpty() {                 // is the deque empty?
        return size == 0;
    }

    public int size() {                        // return the number of items on the deque
        return size;
    }

    public void addFirst(Item item) {          // add the item to the front
        addLast(item);
        root = root.prev;
    }

    public void addLast(Item item) {           // add the item to the end
        if (item == null)
            throw new IllegalArgumentException();

        N n = new N();
        n.v = item;

        if (root == null) {
            root = n.next = n.prev = n;
        } else {
            n.next = root;
            n.prev = root.prev;
            root.prev = n;
            n.prev.next = n;
        }
        ++size;
    }

    public Item removeFirst() {                // remove and return the item from the front
        root = root.next;
        return removeLast();
    }

    public Item removeLast() {                 // remove and return the item from the end
        if (size == 0)
            throw new NoSuchElementException();

        N last = root.prev;
        --size;

        if (size == 0) {
            root = null;
            return last.v;
        }

        last.prev.next = last.next;
        last.next.prev = last.prev;
        return last.v;
    }

    private class DequeIterator implements Iterator<Item> {

        private N cur;
        private final int size;
        private int i = 0;

        private DequeIterator(N root, int size) {
            cur = root;
            this.size = size;
        }

        @Override
        public boolean hasNext() {
            return i != size;
        }

        @Override
        public Item next() {
            if (i == size)
                throw new NoSuchElementException();
            Item v = cur.v;
            ++i;
            cur = cur.next;
            return v;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    };

    @Override
    public Iterator<Item> iterator() {         // return an iterator over items in order from front to end
        return new DequeIterator(root, size);
    }

    public static void main(String[] args) {   // unit testing (optional)

    }
}