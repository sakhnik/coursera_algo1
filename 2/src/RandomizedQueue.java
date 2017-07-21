
import edu.princeton.cs.algs4.StdRandom;
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
public class RandomizedQueue<Item> implements Iterable<Item> {

    private Item[] items = (Item[]) new Object[1];
    private int size = 0;

    public RandomizedQueue() {                 // construct an empty randomized queue
    }

    public boolean isEmpty() {                 // is the queue empty?
        return size == 0;
    }

    public int size() {                        // return the number of items on the queue
        return size;
    }

    private void resize(int capacity) {
        Object[] copy = new Object[capacity];
        for (int i = 0; i < size; ++i)
            copy[i] = items[i];
        items = (Item[]) copy;
    }

    public void enqueue(Item item) {           // add the item
        if (item == null)
            throw new IllegalArgumentException();

        if (size == items.length) {
            resize(size*2);
        }
        items[size++] = item;
    }
    
    private void randomExchange() {
        int idx = StdRandom.uniform(size);
        Item v = items[idx];
        items[idx] = items[size - 1];
        items[size - 1] = v;
    }

    public Item dequeue() {                    // remove and return a random item
        if (size == 0)
            throw new NoSuchElementException();

        randomExchange();

        Item v = items[--size];
        items[size] = null;
        if (size * 4 == items.length)
            resize(size*2);
        return v;
    }

    public Item sample() {                     // return (but do not remove) a random item
        if (size == 0)
            throw new NoSuchElementException();
        randomExchange();
        return items[size-1];
    }

    private class ShuffleIterator<Item> implements Iterator<Item> {

        private final Item[] items;
        private int cur = 0;

        public ShuffleIterator(Item[] items, int size) {
            this.items = (Item[]) new Object[size];
            for (int i = 0; i != size; ++i) {
                int j = StdRandom.uniform(i+1);
                this.items[i] = this.items[j];
                this.items[j] = items[i];
            }
        }

        @Override
        public boolean hasNext() {
            return cur != items.length;
        }

        @Override
        public Item next() {
            if (cur == items.length)
                throw new NoSuchElementException();
            return items[cur++];
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    public Iterator<Item> iterator() {         // return an independent iterator over items in random order
        return new ShuffleIterator<>(items, size);
    }
    
    public static void main(String[] args) {   // unit testing (optional)
        
    }
}
