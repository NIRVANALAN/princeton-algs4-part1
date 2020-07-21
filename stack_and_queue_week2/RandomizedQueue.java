import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private int size;
    private Item[] Rqueue;

    // construct an empty randomized queue
    public RandomizedQueue() {
        size = 0;
        Rqueue = (Item[]) new Object[1];
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return size;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null)
            throw new IllegalArgumentException();
        if (size() == Rqueue.length) {
            resize(Rqueue.length * 2);
        }
        Rqueue[size++] = item;
    }

    private void swap(int i, int j) {
        Item item = Rqueue[i];
        Rqueue[i] = Rqueue[j];
        Rqueue[j] = item;
    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty())
            throw new NoSuchElementException();
        int index = StdRandom.uniform(size);
        swap(--size, index);
        Item item = Rqueue[size];
        Rqueue[size] = null;
        if (size > 0 && size == Rqueue.length / 4)
            resize(Rqueue.length / 2);
        return item;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (isEmpty())
            throw new NoSuchElementException();

        int index = StdRandom.uniform(size);
        return Rqueue[index];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomizedArrayIterator();
    }

    private class RandomizedArrayIterator implements Iterator<Item> {
        private int[] iteratorOrder = StdRandom.permutation(size); // ? size
        private int current = 0;

        public void remove() {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean hasNext() {
            return current != size;
        }

        @Override
        public Item next() {
            if (!hasNext())
                throw new NoSuchElementException();
            Item item = Rqueue[iteratorOrder[current++]];
            return item;
        }
    }

    private void resize(int capacity) {
        assert capacity > 0;
        Item[] copy = (Item[]) new Object[capacity];
        for (int i = 0; i < size; i++) {
            copy[i] = Rqueue[i];
        }
        Rqueue = copy;
        copy = null; // ? loitering
    }

    // unit testing (required)
    public static void main(String[] args) {
        RandomizedQueue<Integer> que = new RandomizedQueue<>();
        for (int i = 0; i < 5; i++) {
            que.enqueue(i);
        }
        while (!que.isEmpty()) {
            StdOut.print(que.dequeue());
        }
        // for (Integer item : que) {
        // StdOut.println(item);
        // }

    }

}