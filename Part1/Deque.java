// package stack_and_queue_week2;

import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdOut;

public class Deque<Item> implements Iterable<Item> {
    // generics: Discover type mismatch errors at compile-time instead of run-time

    private Item[] s;
    private int tail = 0, head = 0; // next insert position

    // construct an empty deque
    public Deque() {
        s = (Item[]) new Object[1];
    }

    // is the deque empty?
    public boolean isEmpty() {
        return tail == 0;
    }

    // return the number of items on the deque
    public int size() {
        int n = 0;
        for (Item item : this) {
            n++;
        }
        return n;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null)
            throw new IllegalArgumentException();
        if (this.size() == s.length - 1) { // !
            resize(s.length * 2);
        }
        head = (head - 1 + s.length) % s.length; // -1 first
        s[head] = item;
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null)
            throw new IllegalArgumentException();
        if (this.size() == s.length - 1) {
            resize(s.length * 2);
        }
        s[tail] = item;
        tail = (tail + 1) % s.length;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (this.size() == 0)
            throw new NoSuchElementException();
        if (this.size() == s.length / 4)
            this.resize(s.length / 2);
        var item = s[head];
        head = (head + 1) % s.length; //
        return item;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (this.size() == 0)
            throw new NoSuchElementException();
        tail = (tail - 1 + s.length) % s.length; // -1 first
        var item = s[tail];
        if (this.size() == s.length / 4)
            this.resize(s.length / 2);
        return item;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new ArrayIterator();
    }

    private class ArrayIterator implements Iterator<Item> {
        private int i = head;

        public void remove() {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean hasNext() {
            return i != tail;
        }

        @Override
        public Item next() {
            if (!hasNext())
                throw new NoSuchElementException();
            var ret = s[i];// ?
            i = (i + 1) % s.length;
            return ret;
        }

    }

    // resize
    private void resize(int capacity) {
        var copy = (Item[]) new Object[capacity];
        int j = 0;
        for (Item item : this) { // !
            copy[j] = item;
            j++;
        }
        s = copy;
    }

    // unit testing (required)
    public static void main(String[] args) {
        var que = new Deque<Integer>();
        que.addLast(1);
        que.addLast(2);
        que.addLast(3);
        que.addFirst(-1);
        StdOut.println(que.size());
        que.removeFirst();
        que.removeLast();
        que.removeLast();

        // StdOut.print(que.size());
        for (Integer integer : que) {
            StdOut.println(integer);
        }
        StdOut.println(que.size());
    }

}
